package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.input.adapter.PhotoAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Report;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.PictureUI;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.task.StartTaskUI;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.manager.DeleteDocManager;

/**
 * Created by jinke on 2017/8/10.
 * 定期报告显示界面
 */

@Route(path = RouteUtils.R_REPORT_SHOW_UI)
public class ShowReportDetailUI extends ProjectBaseUI implements OnItemClickListener {

    @ViewInject(R.id.report_operator_layout)
    private RelativeLayout mOperateLayout;

    @ViewInject(R.id.report_persion_tv)
    private TextView mPersionTV;

    @ViewInject(R.id.report_address_tv)
    private TextView mAddressTV;

    @ViewInject(R.id.report_time_tv)
    private TextView mDateTV;

    @ViewInject(R.id.report_photo_gridview)
    private NewGridView mPhotoGV;

    private Report mReport = null;
    private Persion mPersion = null;
    private Document mDocument = null;

    private PhotoAdapter mPhotoAdapter = null;
    private List<String> mPhotoList = new ArrayList<>();
    private int mType = 0;

    private int[] MSG = new int[]{JUDGE_CREATETIME, ACCESS_NET_FAILED, REQ_DELETE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case JUDGE_CREATETIME:
                dismissLoading();
                String result = (String)msg.obj;
                if (msg.arg1 == SUCCESS){
                    String o_type = null;
                    String context = null;
                    switch (mType){
                        case T_DELETE:
                            o_type = getString(R.string.shanchu);
                            break;
                        case T_MODIFY:
                            o_type = getString(R.string.xiugai);
                            break;
                        default:
                            break;
                    }
                    context = String.format(getString(R.string.dqbgcz),o_type);
                    operationDialog(context, mType);
                }else{
                    hintDialog(result);
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case REQ_DELETE_DOC:
                dismissLoading();
                if (msg.arg1 == SUCCESS){
                    showToast(R.string.sccg);
                    finish();
                }else{
                    showToast(R.string.scsb);
                }
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_report_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessages(MSG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterMessages(MSG);
    }

    @Override
    protected void onInitView() {
        UserCard userCard = MasterManager.getInstance().getUserCard();
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mReport = (Report)bundle.getSerializable(OBJECT);
            mPersion = (Persion)bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if(header != null){
            String title = String.format(getString(R.string.bgxqbt), mPersion.getRealName());
            header.titleText.setText(title);
        }

        if(JDZG.equals(userCard.getUserType())){
            if(mOperateLayout.getVisibility() == View.GONE){
                mOperateLayout.setVisibility(View.VISIBLE);
            }
        }else {
            if(mOperateLayout.getVisibility() == View.VISIBLE){
                mOperateLayout.setVisibility(View.GONE);
            }
        }

        String path = mReport.getFileAdd();
        String cameraPath = mReport.getCameraPicAdd();

        if(!TextUtils.isEmpty(path)){
            String[] paths = path.split(",");
            for(int i=0,size=paths.length;i<size;i++){
                mPhotoList.add(paths[i]);
            }

        }

        if(!TextUtils.isEmpty(cameraPath)){
            mPhotoList.add(cameraPath);
        }

        mPhotoGV.setOnItemClickListener(this);
        mPhotoAdapter = new PhotoAdapter(this);
        mPhotoAdapter.setData(mPhotoList);
        mPhotoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mPhotoGV.setAdapter(mPhotoAdapter);
        mPhotoAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onInitData() {

        mPersionTV.setText(mReport.getRecordPerson());
        mAddressTV.setText(mReport.getReportPlache());
        mDateTV.setText(DateUtil.getInstance().changeDate(mReport.getReportDate()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> list = new ArrayList<>();
        for(String s : mPhotoList){
            list.add(s);
        }
        PictureUI.startActivity(this, list, position);
    }

    @Event(value = {R.id.r_delete_layout, R.id.r_modify_layout}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.r_delete_layout:
                mType = T_DELETE;
                judgeCreateTime(mReport.getId());
                break;
            case R.id.r_modify_layout:
                mType = T_MODIFY;
                judgeCreateTime(mReport.getId());
                break;
            default:
                break;
        }
    }

    private void judgeCreateTime(String id){
        if(isNetworkConnected()){
            showLoading();
            TaskManager.getInstance().judgeCreateTime(id, DOC_TYPE_12);
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    private void hintDialog(String context){
        AppDialog dialog = new AppDialog(this, context, getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ok:
                        StartTaskUI.navigation(mType, mDocument.getId(), mPersion.getId(), mReport.getId(), DOC_TYPE_12);
                        break;
                    case R.id.cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void operationDialog(String context, final int type){
        AppDialog dialog = new AppDialog(this, context, getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ok:
                        switch (type){
                            case T_DELETE:
                                showLoading();
                                DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_12, mReport.getId());
                                break;
                            case T_MODIFY:
                                switchOutNet();
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xiugai));
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

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            Bundle bundle = new Bundle();
            bundle.putSerializable(DOCUMENT, mDocument);
            bundle.putSerializable(OBJECT, mReport);
            bundle.putSerializable(PERSION, mPersion);
            bundle.putSerializable(TYPE, false);
            ARouter.getInstance().build(RouteUtils.R_REPORT_SAVE_UI).withBundle(BUNDLE, bundle).navigation();
            finish();
        }
    }
}
