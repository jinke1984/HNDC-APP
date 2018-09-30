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
import cn.com.jinke.wh_drugcontrol.customview.AppTwoDialog;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.input.adapter.PhotoAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Urine;
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
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.manager.DeleteDocManager;

/**
 * @author jinke
 * @date 2017/11/20
 * @description 尿检数据展现界面
 */

@Route(path = RouteUtils.R_SHOW_URINE_UI)
public class ShowUrineUI extends ProjectBaseUI implements OnItemClickListener {

    @ViewInject(R.id.operator_layout)
    private RelativeLayout mOperateLayout;

    @ViewInject(R.id.urine_persion_tv)
    private TextView mPersionTV;

    @ViewInject(R.id.urine_date_tv)
    private TextView mDateTV;

    @ViewInject(R.id.urine_address_tv)
    private TextView mAddressTV;

    @ViewInject(R.id.urine_result_tv)
    private TextView mResultTV;

    @ViewInject(R.id.urine_type_tv)
    private TextView mTypeTV;

    @ViewInject(R.id.urine_photo_gridview)
    private NewGridView mPhotoGV;

    private Urine mUrine = null;
    private Persion mPersion = null;
    private Document mDocument = null;
    private PhotoAdapter mPhotoAdapter = null;
    private int mType = 0;

    private List<String> mPhotoList = new ArrayList<>();
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
                    context = String.format(getString(R.string.nqdynj),o_type);
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
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_show_urine);
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
    protected void onInitData() {
        String persion = mUrine.getTestcontactPerson();
        String date = DateUtil.getInstance().changeDate(mUrine.getUrineDate());
        String address = mUrine.getUrinePlace();
        String result = CommUtils.getInstance().getUrineResult(mUrine.getUrineResult());
        String type = CommUtils.getInstance().getUrineType(mUrine.getUrineType());
        mPersionTV.setText(persion);
        mDateTV.setText(date);
        mAddressTV.setText(address);
        mResultTV.setText(result);
        mTypeTV.setText(type);
    }

    @Override
    protected void onInitView() {
        UserCard userCard = MasterManager.getInstance().getUserCard();
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mUrine = (Urine)bundle.getSerializable(OBJECT);
            mPersion = (Persion)bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if(header != null){
            String title = String.format(getString(R.string.njxqbt), mPersion.getRealName(), String.valueOf(mUrine.getUrineCounts()));
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

        String filePath = mUrine.getFileAdd();
        if(!TextUtils.isEmpty(filePath)){
            String[] paths = filePath.split(",");
            for(int i=0,size=paths.length;i<size;i++){
                mPhotoList.add(paths[i]);
            }
        }

        mPhotoGV.setOnItemClickListener(this);
        mPhotoAdapter = new PhotoAdapter(this);
        mPhotoAdapter.setData(mPhotoList);
        mPhotoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mPhotoGV.setAdapter(mPhotoAdapter);
        mPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> list = new ArrayList<>();
        for(String s : mPhotoList){
            list.add(s);
        }
        PictureUI.startActivity(this, list, position);
    }

    @Event(value = {R.id.u_delete_layout, R.id.u_modify_layout}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.u_delete_layout:
                mType = T_DELETE;
                judgeCreateTime(mUrine.getId());
                break;
            case R.id.u_modify_layout:
                mType = T_MODIFY;
                judgeCreateTime(mUrine.getId());
                break;
            default:
                break;
        }
    }

    private void judgeCreateTime(String id){
        if(isNetworkConnected()){
            showLoading();
            TaskManager.getInstance().judgeCreateTime(id, DOC_TYPE_11);
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
                        StartTaskUI.navigation(mType, mDocument.getId(), mPersion.getId(), mUrine.getId(), DOC_TYPE_11);
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
                        showLoading();
                        switch (type){
                            case T_DELETE:
                                showLoading();
                                DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_11, mUrine.getId());
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

    private void bundleSendData(String aARouterPath) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(OBJECT, mUrine);
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(TYPE, false);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            bundleSendData(RouteUtils.R_ADD_URINE_UI);
            finish();
        }
    }
}
