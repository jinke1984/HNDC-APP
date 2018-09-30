package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.persion.adapter.PictureAdapter;

/**
 * Author: lufengwen
 * Date: 2016-05-15 13:14
 * Description:
 */
public class PictureUI extends ProjectBaseUI {
	private final static String EXTRA_PICTURE_LIST = "picture_list";
	private final static String EXTRA_PICTURE_INDEX = "picture_index";

	@ViewInject(R.id.view_pager)
	private ViewPager mViewPager;

	@ViewInject(R.id.count_index)
	private TextView mCountIndex;

	private ArrayList<String> mList;
	private int mIndex = 0;
	private PictureAdapter mAdapter;

	public static void startActivity(Context context, ArrayList<String> list, int index) {
		Intent intent = new Intent(context, PictureUI.class);
		intent.putExtra(EXTRA_PICTURE_LIST, list);
		intent.putExtra(EXTRA_PICTURE_INDEX, index);
		context.startActivity(intent);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_picture);
	}

	@Override
	protected void onPreInitView() {
		mList = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_PICTURE_LIST);
		mIndex = getIntent().getIntExtra(EXTRA_PICTURE_INDEX, mIndex);
		if (mList == null || mList.isEmpty()) {
			finish();
			return;
		}
	}

	@Override
	protected void onInitView() {
		// mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
		mAdapter = new PictureAdapter(this, mList, mIndex);
		mViewPager.setAdapter(mAdapter);
		mViewPager.addOnPageChangeListener(mListener);
		mViewPager.setCurrentItem(mIndex);
		mCountIndex.setText(String.format("%1$d/%2$d", mIndex+1, mList.size()));
	}

	private ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			mCountIndex.setText(String.format("%1$d/%2$d", position + 1, mList.size()));
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	@Override
	public void onPause() {
		super.onPause();
	}
}
