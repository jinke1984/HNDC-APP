package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.input.adapter.WordProcAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.manager.ViolateAgreeManager;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;

/**
 * 文书列表
 * Created by jinke on 2017/8/22.
 */

@Route(path = RouteUtils.R_WORD_PROC_UI)
public class WordProcUI extends ProjectBaseUI implements OnChildClickListener {

    @ViewInject(R.id.word_listview)
    private ExpandableListView mWordLV = null;
    private WordProcAdapter mWPAdapter = null;

    private Persion mPersion;
    private Document mDocument;

    private int[] MSG = new int[]{LOAD_VIOLATION_AGREEMENT_LIST, ACCESS_NET_FAILED};
    private ArrayList<ViolateAgreement> violateAgreements;
    private ViolateAgreement mViolateAgreement;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOAD_VIOLATION_AGREEMENT_LIST:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    violateAgreements = (ArrayList<ViolateAgreement>) msg.obj;
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_word_proc);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if (header != null) {
            String title = String.format(getString(R.string.wslb), mPersion.getRealName());
            header.titleText.setText(title);
        }

        mWordLV.setOnChildClickListener(this);
        mWordLV.setGroupIndicator(null);
        mWPAdapter = new WordProcAdapter();
        mWordLV.setAdapter(mWPAdapter);
    }

    @Override
    protected void onInitData() {
        ArrayList<Nation> words = CommUtils.getInstance().getWordParent();
        ArrayList<Nation> details = CommUtils.getInstance().getWordChildren();
        ArrayList<ArrayList<Nation>> childs = new ArrayList<>();

        for (Nation n : words) {
            ArrayList<Nation> list = new ArrayList<>();
            String id = n.getId();
            for (Nation dn : details) {
                String dId = dn.getId();
                if (id.equals(dId)) {
                    list.add(dn);
                }
            }
            childs.add(list);
        }

        mWPAdapter.setParent(words);
        mWPAdapter.setChild(childs);
        mWPAdapter.notifyDataSetChanged();

        showLoading();
        ViolateAgreeManager.getInstance().loadDataList(mDocument.getId());
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        AppLogger.e("groupPosition=" + groupPosition + "");
        AppLogger.e("childPosition=" + childPosition + "");
        switch (groupPosition) {
            case 0:
                //决定书管理
                bundleSendData(RouteUtils.R_WORD_DECISION_UI);
                break;
            case 1:
                //告知书管理
                bundleSendData(RouteUtils.R_WORD_NOTIFY_UI);
                break;
            case 2:
                //协议书管理
                bundleSendData(RouteUtils.R_WORD_AGREEMENT_UI);
                break;
            case 3:
                //人员守则管理
                bundleSendData(RouteUtils.R_WORD_RULES_UI);
                break;
            case 4:
                //计划书管理
                bundleSendData(RouteUtils.R_WORD_PLAN_UI);
                break;
            case 5:
                //违反协议管理
                switch (childPosition) {
                    case 0:
                        // 一般
                        if (checkViolateAgreementLevel("1")) {
                            //劝诫书
                            bundleSendData(RouteUtils.R_WORD_EXHORT_UI);
                        } else {
                            showToast(R.string.nolevelagreement);
                        }
                        break;
                    case 1:
                        // 较严重
                        if (checkViolateAgreementLevel("2")) {
                            //告诫书
                            bundleSendData(RouteUtils.R_WORD_WARNING_UI);
                        } else {
                            showToast(R.string.nolevelagreement);
                        }
                        break;
                    case 2:
                        //违反协议记录
                        bundleSendData(RouteUtils.R_WORD_VIOLATEAGREELIST_UI);
                        break;
                    default:
                        break;
                }
                break;
            case 6:
                //中止/继续管理
                switch (childPosition) {
                    case 0:
                        //中止记录
                        bundleSendData(RouteUtils.R_WORD_SUSPENDED_LIST_UI);
                        break;
                    case 1:
                        //继续执行列表
                        bundleSendData(RouteUtils.R_WORD_CONTINUE_LIST_UI);
                        break;
//                    case 2:
//                        //终止记录
//                        String l_url = CommUtils.getInstance().createWebUrl(mPersion, mDocument, "/drugs/file/stopApp/main.html?",
//                                false, null).toString();
//                        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
//                                .withString(TITLE, getString(R.string.status_10))
//                                .withString(URL, l_url)
//                                .navigation();
//                        break;
                    default:
                        break;
                }
                break;
//            case 7:
//                //变更执行地
//                bundleSendData(RouteUtils.R_CHANGE_ADDRESS_UI);
//                break;
//            case 8:
//                //解除社戒社康通知
//                String j_url = CommUtils.getInstance().createWebUrl(mPersion, mDocument, "/drugs/file/releaseApp/main.html?",
//                        false, null).toString();
//                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
//                        .withString(TITLE, getString(R.string.status_11))
//                        .withString(URL, j_url)
//                        .navigation();
//                break;
//            case 9:
//                //工作小组
//                String g_url = CommUtils.getInstance().groupWebUrl(mPersion, mDocument, "/drugs/file/workteamApp/main.html?").toString();
//                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
//                        .withString(TITLE, getString(R.string.status_13))
//                        .withString(URL, g_url)
//                        .navigation();
//                break;
            default:
                break;
        }
        return true;
    }

    private boolean checkViolateAgreementLevel(String level) {
        if (violateAgreements == null){
            return false;
        }

        for (ViolateAgreement violateAgreement : violateAgreements) {
            if (level.equals(violateAgreement.getViolateLevel())) {
                mViolateAgreement = violateAgreement;
                return true;
            }
        }
        return false;
    }

    private void bundleSendData(String aARouterPath) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(AGREEMENT, mViolateAgreement);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }
}
