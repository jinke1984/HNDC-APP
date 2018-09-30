package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.utils.EmptyUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.adapter.Abs_IQ_Adapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Abs_IQ_Result;
import cn.com.jinke.wh_drugcontrol.persion.model.IntergratedQueryResult;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_AcceptDrugsEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_CommunityDrugDetoxificationOrRecovery;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_EmploymentEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SocialRehabilitationServiceForDrugAddicts;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/** 吸毒人员综合查询 ui
 * Created by admin on 2017/10/31.
 */
@Route(path = RouteUtils.R_PERSION_INTERGRATED_QUERY)
public class Q_IntergratedQueryUI extends ProjectBaseUI{

    @ViewInject(R.id.query_elv)
    private ExpandableListView mQueryELV;

    @ViewInject(R.id.all_qeury)
    private TextView mAllQeuryTV;

    Abs_IQ_Adapter mAdapter;
    Persion mPersion;

    ArrayList<Abs_IQ_Result> mParentList = new ArrayList<>();
    ArrayList<ArrayList<Abs_IQ_Result>> mChildList = new ArrayList<>();

    //需要传到下一级
    Result_SocialRehabilitationServiceForDrugAddicts srsfd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_person_query);
        registerMessages(MSG_PERSION_INTERGRATED_QUERY);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mAllQeuryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PERSION,mPersion);
                bundle.putSerializable(OBJECT,srsfd);
                ARouter.getInstance().build(RouteUtils.R_PERSION_INTERGRATED_QUERY_ALL)
                        .withBundle(BUNDLE,bundle)
                        .navigation();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
        }
        mAdapter = new Abs_IQ_Adapter();
        mQueryELV.setAdapter(mAdapter);
        Header header = getHeader();
        if(header != null){
            header.titleText.setText(mPersion.getRealName() + getString(R.string.zhcx));
        }
    }

    @Override
    protected void onInitData() {
        getQueryResult();
    }

    private void getQueryResult(){
        showLoading();
        QueryDataManager.getInstance().getIntergratedQueryResult(mPersion.getIdentityCard());
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_INTERGRATED_QUERY:
                dismissLoading();
                IntergratedQueryResult result = (IntergratedQueryResult) msg.obj;
                if(result == null){
                    break ;
                }
                Result_CommunityDrugDetoxificationOrRecovery community_DDR = result.getDrugRehabInfo();
                srsfd = result.getStationIn();
                Result_EmploymentEntity eif = result.getSettlement();
                //社区戒毒/社区康复信息
                if(EmptyUtils.isNotEmpty(community_DDR)){
                    community_DDR.setItem_title_name(getString(R.string.sqjdkf));
                    mParentList.add(community_DDR);
                    ArrayList<Abs_IQ_Result> itemList = new ArrayList<>();
                    itemList.add(community_DDR);
                    mChildList.add(itemList);
                }

                // 社会化戒毒康复服务信息
                if(EmptyUtils.isNotEmpty(srsfd)){
                    srsfd.setUserId(mPersion.getPersonID());
                    srsfd.setItem_title_name(getString(R.string.jssqh));
                    mParentList.add(srsfd);
                    ArrayList<Abs_IQ_Result> itemList = new ArrayList<>();
                    itemList.add(srsfd);
                    mChildList.add(itemList);
                }

                //就业信息
                if(EmptyUtils.isNotEmpty(eif)){
                    eif.setItem_title_name(getString(R.string.ygqyjyxx));
                    mParentList.add(eif);
                    ArrayList<Abs_IQ_Result> itemList = new ArrayList<>();
                    itemList.add(eif);
                    mChildList.add(itemList);
                }

                //接受药物治疗
                Result_AcceptDrugsEntity ade = result.getMedicineTreatmentInfo();
                if(EmptyUtils.isNotEmpty(ade)){
                    ade.setIdentityCard(mPersion.getIdentityCard());
                    ade.setItem_title_name(getString(R.string.jsywwczl));
                    mParentList.add(ade);
                    ArrayList<Abs_IQ_Result> itemList = new ArrayList<>();
                    itemList.add(ade);
                    mChildList.add(itemList);
                }
                mAdapter.setParent(mParentList);
                mAdapter.setChild(mChildList);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
}
