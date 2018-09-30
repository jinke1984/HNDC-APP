package cn.com.jinke.wh_drugcontrol.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.util.List;

/**
 * @Author: jinke
 * @Date: 2016/4/6 13:57
 * @Description: Fragment管理器
 */
public class FragmentTabManager {
    private String mCurrentTag;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<FragmentTab> mItems;
    private int mContainerId;
    private OnTabChangedListener mTabChangedListener;

    public FragmentTabManager(Context context, FragmentManager fm, List<FragmentTab> items, int containerId) {
        mContext = context;
        mFragmentManager = fm;
        mItems = items;
        mContainerId = containerId;
    }

    public String getCurrentTag() {
        return mCurrentTag;
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager == null ? null : mFragmentManager.findFragmentByTag(mCurrentTag);
    }

    public void setOnTabChangedListener(OnTabChangedListener listener) {
        mTabChangedListener = listener;
    }

    public void setCurrent(String tag) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getTag().equals(tag)) {
                setCurrent(i);
                break;
            }
        }
    }

    public void setCurrent(int index) {
        String toTag = mItems.get(index).getTag();

        Fragment fromFrag = null;
        if (!TextUtils.isEmpty(mCurrentTag)) {
            if (mCurrentTag.equals(toTag)) {
                return;
            }
            fromFrag = mFragmentManager.findFragmentByTag(mCurrentTag);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fromFrag != null) {
//            transaction.detach(fromFrag);
            transaction.hide(fromFrag);

            if (fromFrag instanceof OnTabFragmentVisibleListener) {
                ((OnTabFragmentVisibleListener) fromFrag).onVisible(false);
            }
        }

        Fragment toFrag = mFragmentManager.findFragmentByTag(toTag);
        if (toFrag != null) {
//            transaction.attach(toFrag);
            transaction.show(toFrag);
        } else {
            toFrag = mItems.get(index).newInstance(mContext);
            transaction.add(mContainerId, toFrag, toTag);
        }

        if (toFrag instanceof OnTabFragmentVisibleListener) {
            ((OnTabFragmentVisibleListener) toFrag).onVisible(true);
        }

        mCurrentTag = toTag;
//        transaction.commitAllowingStateLoss();
        transaction.commit();

        if (toFrag instanceof OnTabListener) {
            ((OnTabListener) toFrag).onTabSelected();
        }

        if (mTabChangedListener != null) {
            mTabChangedListener.onTabChanged(toFrag, index);
        }
    }

    public static class FragmentTab {
        private Class<? extends Fragment> mClass;
        private Bundle mBundle;
        private String mTag;

        public FragmentTab(Class<? extends Fragment> cls) {
            this(cls, null, cls.getName());
        }

        public FragmentTab(Class<? extends Fragment> cls, Bundle bundle) {
            this(cls, bundle, cls.getName());
        }

        public FragmentTab(Class<? extends Fragment> cls, Bundle bundle, String tag) {
            mClass = cls;
            mBundle = bundle;
            mTag = tag;
        }

        public String getTag() {
            return mTag;
        }

        public Fragment newInstance(Context context) {
            return Fragment.instantiate(context, mClass.getName(), mBundle);
        }
    }

    public static interface OnTabChangedListener {
        public void onTabChanged(Fragment current, int index);
    }

    public interface OnTabChangeListener {
        void onTabChange(Fragment current, int toIndex);
    }

    public interface OnTabFragmentVisibleListener {
        void onVisible(boolean visible);
    }

    public static interface OnTabListener {
        public void onTabSelected();
    }
}