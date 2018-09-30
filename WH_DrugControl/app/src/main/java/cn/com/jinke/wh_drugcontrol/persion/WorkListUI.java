package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.adapter.WorkAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.WorkPersion;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/9/18.
 */

@Route(path = RouteUtils.R_PERSION_WORK_UI)
public class WorkListUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener{

    @ViewInject(R.id.work_listview)
    private PullToRefreshListView mDataLV = null;

    private WorkAdapter mWorkAdapter = null;
    private List<WorkPersion> mList = new ArrayList<>();
    private int[] MSG = new int[]{WORK_REMIND_MSG, ACCESS_NET_FAILED};
    private int mType = 0;

    @Override
    protected boolean handleMessage(Message msg) {
        mDataLV.onRefreshComplete();
        switch (msg.what){
            case WORK_REMIND_MSG:
                dismissLoading();
                mDataLV.onRefreshComplete();
                if (msg.arg1 == SUCCESS){
                    mList = (List<WorkPersion>)msg.obj;
                    mWorkAdapter.setData(mList);
                    mWorkAdapter.notifyDataSetChanged();
                }
                setFooter();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
        setContentView(R.layout.ui_work_list);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mType = bundle.getInt(B_TYPE, mType);
        }

        Header header = getHeader();
        if(header != null){
            header.titleText.setText(CommUtils.getInstance().getWorkType(mType));
        }

        PullToRefreshHelper.initHeader(mDataLV);
        PullToRefreshHelper.initFooter(mDataLV);
        mDataLV.setOnRefreshListener(this);
        mDataLV.setOnItemClickListener(this);

        mWorkAdapter = new WorkAdapter(this);
        mDataLV.setAdapter(mWorkAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        if(isNetworkConnected()){
            showLoading();
            PersionManager.getInstance().getWorkPersionData(true, String.valueOf(mType));
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WorkPersion workPersion = mList.get(position-1);
        Persion persion = new Persion();
        persion.setId(workPersion.getId());
        persion.setRealName(workPersion.getRealName());
        persion.setType(workPersion.getType());
        persion.setIdentityCard(workPersion.getIdentityCard());
        Document document = new Document();
        document.setType(workPersion.getType());
        document.setId(workPersion.getDocId());
        switch (mType){
            case WORK_TALK:
                bundleSendData(RouteUtils.R_INTERVIEW_UI, persion, document);
                break;
            case WORK_URINE:
                bundleSendData(RouteUtils.R_URINE_TEST_UI, persion, document);
                break;
            case WORK_REPORT:
                bundleSendData(RouteUtils.R_REPORT_UI, persion, document);
                break;
            case WORK_EVALUATE:
                switchOutNet(true, persion, document);
                break;
            case WORK_DYNAMIC:
                switchOutNet(false, persion, document);
                break;
            default:
                break;
        }
    }

    private StringBuilder createWebUrl(Persion persion,Document document,String path, int type){
        StringBuilder sb = new StringBuilder(RequestHelper.getInstance().IN_IMAGE_URL+path);
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            sb.append("personId=").append(persion.getId())
                    .append("&docId=").append(document.getId())
                    .append("&userId=").append(userCard.getUserId());
            switch (type){
                case 1:
                    sb.append("&pageType=1");
                    break;
                default:
                    break;
            }
        }
        AppLogger.d("url:"+sb.toString());
        return sb;
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(boolean model, Persion persion, Document document){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(!result){
            switchOutNetDialog();
        }else{
            if(model){
                String e_url = createWebUrl(persion,document,"/drugs/file/regularAppraisalApp/main.html?", 1).toString();
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE, getString(R.string.dqpg))
                        .withString(URL,e_url)
                        .navigation();
            }else{
                String d_url = createWebUrl(persion,document,"/drugs/file/dynamicAppInfo/main.html?", 0)
                        .append("&realName=").append(persion.getRealName()).toString();
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE, getString(R.string.dtxxwh))
                        .withString(URL,d_url)
                        .navigation();
            }
        }
    }

    private void switchOutNetDialog(){
        String content = getString(R.string.nwts);
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void bundleSendData(String aARouterPath, Persion aPersion, Document aDocument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, aPersion);
        bundle.putSerializable(DOCUMENT, aDocument);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
            PersionManager.getInstance().getWorkPersionData(true, String.valueOf(mType));
        }else{
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
            PersionManager.getInstance().getWorkPersionData(false, String.valueOf(mType));
        }else{
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    private void setFooter() {
        if (mWorkAdapter.getCount() == 0) {
            mDataLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (PersionManager.getInstance().isFinish()) {
                mDataLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mDataLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
