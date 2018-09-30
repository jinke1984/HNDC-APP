package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.adapter.MyTaskAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.MyTaskEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SocialRehabilitationServiceForDrugAddicts;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by admin on 2017/10/31.
 * 吸毒人员查询全部综合信息
 */
@Route(path = RouteUtils.R_PERSION_INTERGRATED_QUERY_ALL)
public class Q_IntergratedQueryAllUI extends ProjectBaseUI implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.query_list)
    private PullToRefreshListView mQueryLV;

    MyTaskAdapter mQueryAdapter;

    List<MyTaskEntity> mQueryList = new ArrayList<>();

    Persion mPersion;

    //接收上一级传递的值  并传递到下一级
    Result_SocialRehabilitationServiceForDrugAddicts ssfd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_query);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            ssfd = (Result_SocialRehabilitationServiceForDrugAddicts) bundle.getSerializable(OBJECT);
        }

        Header header = getHeader();
        if(header != null){
            header.titleText.setText(mPersion.getRealName() + getString(R.string.zhcx));
        }

        mQueryLV.setOnItemClickListener(this);
        mQueryLV.setMode(PullToRefreshBase.Mode.DISABLED);
        mQueryAdapter = new MyTaskAdapter(this);
        mQueryLV.setAdapter(mQueryAdapter);
    }

    @Override
    protected void onInitData() {
        initData();
    }

    private void initData(){

        MyTaskEntity sqjdkfTaskEntity = new MyTaskEntity();
        sqjdkfTaskEntity.setName(getString(R.string.sqjdkf));
        mQueryList.add(sqjdkfTaskEntity);

        MyTaskEntity acceptshEntity = new MyTaskEntity();
        acceptshEntity.setName(getString(R.string.jssqh));
        mQueryList.add(acceptshEntity);

        MyTaskEntity sunEntity = new MyTaskEntity();
        sunEntity.setName(getString(R.string.ygqyjyxx));
        mQueryList.add(sunEntity);

        MyTaskEntity ywzlfTaskEntity = new MyTaskEntity();
        ywzlfTaskEntity.setName(getString(R.string.jsywwczl));
        mQueryList.add(ywzlfTaskEntity);

        MyTaskEntity tsbqkEntity = new MyTaskEntity();
        tsbqkEntity.setName(getString(R.string.tsbqrycy));
        mQueryList.add(tsbqkEntity);

        MyTaskEntity jdjgcsEntity = new MyTaskEntity();
        jdjgcsEntity.setName(getString(R.string.jdjgsrscs));
        mQueryList.add(jdjgcsEntity);

        MyTaskEntity gababmEntity = new MyTaskEntity();
        gababmEntity.setName(getString(R.string.gababuch));
        mQueryList.add(gababmEntity);

        mQueryAdapter.setData(mQueryList);
        mQueryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 1:
                buildData(RouteUtils.R_PERSION_RECOVERY_INFORMATION);
                break;

            case 2:
                Bundle bundle = new Bundle();
                bundle.putSerializable(PERSION,ssfd);
                ARouter.getInstance().build(RouteUtils.R_PERSION_ACCEPT_RECOVVERY_INFORMATION)
                        .withBundle(BUNDLE,bundle)
                        .navigation();
                break;

            case 3:
                buildData(RouteUtils.R_PERSION_SUNSHINE);
                break;
            case 4:
                buildData(RouteUtils.R_PERSION_ACCEPT_DRUGS);
                break;
            case 5:
                buildData(RouteUtils.R_PERSION_HOSPITAL_INFORMATION);
                break;
            case 6:
                buildData(RouteUtils.R_PERSION_SUPERVISE);
                break;
            case 7:
                buildData(RouteUtils.R_PERSION_SEIZED_INFORMATION);
                break;
            default:
                break;
        }
    }

    private void buildData(String path){
        ARouter.getInstance().build(path)
                .withString(IDCARD,mPersion.getIdentityCard())
                .navigation();
    }
}
