package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragment;
import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.database.table.TableCachePerson;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;

/**
 * BasePersonFragment
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/24
 */

public abstract class BasePersonFragment extends ProjectBaseFragment implements
        FragmentTabManager.OnTabFragmentVisibleListener {

    protected CachePerson mPerson = null;
    private TableCachePerson mTablePerson = DbManager.getPersonDb().getTableCachePerson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPerson = (CachePerson) getArguments().getSerializable(EXTRA_PERSON);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewResId(), container, false);
        x.view().inject(this, view);
        initHeader(view);
        initView(mPerson);
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 对应于FragmentTabManager中切换frangment时使用hide()/showString()方法
     *
     * @param visible
     */
    @Override
    public void onVisible(boolean visible) {
        if (!visible) {
            onSaveData(mPerson);
            mTablePerson.save(mPerson);
            MessageProxy.sendEmptyMessage(UPDATE_PERSON_CACHE);
        }
    }

    protected void forceSaveToDb() {
        mTablePerson.save(mPerson);
        MessageProxy.sendEmptyMessage(UPDATE_PERSON_CACHE);
    }

    protected void delete(CachePerson person) {
        mTablePerson.delete(person);
        MessageProxy.sendEmptyMessage(UPDATE_PERSON_CACHE);
    }

    abstract void onSaveData(CachePerson person);

    abstract int getContentViewResId();

    protected void changeToFragment(int index) {
        if (getActivity() instanceof FragmentTabManager.OnTabChangeListener) {
            FragmentTabManager.OnTabChangeListener listener = (FragmentTabManager.OnTabChangeListener) getActivity();
            listener.onTabChange(this, index);
        }
    }

    abstract protected void initData();

    abstract protected void initView(CachePerson person);

}
