package cn.com.jinke.wh_drugcontrol.input;

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
import cn.com.jinke.wh_drugcontrol.input.adapter.ReportAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.ReportManager;
import cn.com.jinke.wh_drugcontrol.input.model.Report;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**定期报告
 * Created by jinke on 2017/8/7.
 */

@Route(path = RouteUtils.R_REPORT_UI)
public class ReportUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener{

    @ViewInject(R.id.report_listview)
    private PullToRefreshListView mReportLV;

    private Persion mPersion = null;
    private Document mDocument = null;
    private List<Report> mReportList = new ArrayList<>();
    private ReportAdapter mReportAdapter = null;

    private int[] MSG = new int[]{ADD_REPORT_MSG, REPORT_MSG, ACCESS_NET_FAILED, REQ_DELETE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case REPORT_MSG:
                dismissLoading();
                mReportLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mReportList = (List<Report>)msg.obj;
                    mReportAdapter.setData(mReportList);
                    mReportAdapter.notifyDataSetChanged();
                }
                setFooter();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case REQ_DELETE_DOC:
            case ADD_REPORT_MSG:
                getReportList();
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
        setContentView(R.layout.ui_report);
    }

    @Override
    protected void onInitData() {
        getReportList();
    }

    @Override
    protected void onInitView() {

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion)bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if(header != null){
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.sddqbg), name);
            header.titleText.setText(title);

            UserCard userCard = MasterManager.getInstance().getUserCard();
            if(JDZG.equals(userCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightText.setText(R.string.xz);
                header.rightLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //addReport();
                        switchOutNet();
                    }
                });
            }
        }

        PullToRefreshHelper.initHeader(mReportLV);
        PullToRefreshHelper.initFooter(mReportLV);
        mReportLV.setOnRefreshListener(this);
        mReportLV.setOnItemClickListener(this);

        mReportAdapter = new ReportAdapter(this);
        mReportLV.setAdapter(mReportAdapter);
        setFooter();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {

        if (isNetworkConnected()) {
            String idCard = mPersion.getId();
            ReportManager.getInstance().getReportList(idCard, true);
        } else {
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

        if (isNetworkConnected()) {
            String idCard = mPersion.getId();
            ReportManager.getInstance().getReportList(idCard, false);
        } else {
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    private void getReportList(){

        if(isNetworkConnected()){
            showLoading();
            String idCard = mPersion.getId();
            ReportManager.getInstance().getReportList(idCard, true);

        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    private void setFooter() {
        if (mReportAdapter.getCount() == 0) {
            mReportLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
        else {
            if (ReportManager.getInstance().isFinish()) {
                mReportLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }
            else {
                mReportLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Report report = mReportList.get(position-1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(OBJECT, report);
        bundle.putSerializable(PERSION, mPersion);
        ARouter.getInstance().build(RouteUtils.R_REPORT_SHOW_UI).withBundle(BUNDLE, bundle).navigation();
    }

    private void addReport(){
        int num = 0;
        if(mReportList.size() > 0){
            num = mReportList.get(0).getReportCounts();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(NUM, num);
        ARouter.getInstance().build(RouteUtils.R_REPORT_SAVE_UI).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * 定期报告新增功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xzdqbg));
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            addReport();
        }
    }
}
