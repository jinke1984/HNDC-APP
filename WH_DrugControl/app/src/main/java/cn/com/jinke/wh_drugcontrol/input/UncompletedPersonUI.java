package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.database.table.TableCachePerson;
import cn.com.jinke.wh_drugcontrol.input.adapter.UncompletedAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * 未完成输入信息人员列表
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/23
 */
@Route(path = RouteUtils.R_UNCOMPLETED_PERSION_UI)
public class UncompletedPersonUI extends ProjectBaseUI implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.qiandw_system_uncompleted_list)
    private ListView mUncompletedLV;

    private TableCachePerson tablePerson = DbManager.getPersonDb().getTableCachePerson();

    private List<CachePerson> personList = null;

    private UncompletedAdapter mAdapter;

    private int[] MSG = new int[]{UPDATE_PERSON_CACHE};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case UPDATE_PERSON_CACHE:
            getCacheData();
            break;
        default:
            break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_uncompletedpersion);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {
        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.cacheperson);
        }
        mAdapter = new UncompletedAdapter(this);
        mUncompletedLV.setAdapter(mAdapter);
        mUncompletedLV.setOnItemClickListener(this);
    }

    @Override
    protected void onInitData() {
        getCacheData();

        if (personList == null || personList.size() == 0) {
            ARouter.getInstance().build(RouteUtils.R_ADD_PERSION_UI).navigation();
        }
    }

    @Event(value = {R.id.new_add_iv})
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.new_add_iv:
            ARouter.getInstance().build(RouteUtils.R_ADD_PERSION_UI).navigation();
            break;

        default: // no code
            break;
        }
    }

    private void getCacheData() {
        showLoading();
        personList = tablePerson.getCachePersonList();
        mAdapter.setData(personList);
        mAdapter.notifyDataSetChanged();
        dismissLoading();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CachePerson person = personList.get(position);
        ARouter.getInstance().build(RouteUtils.R_ADD_PERSION_UI)
                .withSerializable(EXTRA_PERSON, person)
                .navigation();
    }

}
