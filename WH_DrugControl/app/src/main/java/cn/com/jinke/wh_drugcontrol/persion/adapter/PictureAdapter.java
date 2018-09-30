package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;


/**
 * @Author: lufengwen
 * @Date: 2016/5/19 8:59
 * @Description:
 */
public class PictureAdapter extends PagerAdapter implements OnClickListener {
	private List<View> mViewList;
	private List<String> mImgUrlList;
	private ImageOptions mOptions;
	private Activity mActivity;
	private String mUrl;
	private int mIndex = 0;

	public PictureAdapter(Activity activity, List<String> list, int index) {
		mUrl = RequestHelper.getInstance().getImageRequestHeader();
		mIndex = index;
		mImgUrlList = list;
		this.mActivity = activity;
		ImageOptions.Builder builder = new ImageOptions.Builder();
		builder.setFailureDrawableId(R.drawable.common_picture_default);
		builder.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
		mOptions = builder.build();
		init(activity);
	}

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = mViewList.get(position);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	private void init(final Context context) {
		mViewList = new ArrayList<>();
		int size = mImgUrlList.size();
		for (int i = 0; i < size; i++) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_picture, null);
			PhotoView image = (PhotoView) view.findViewById(R.id.photo_view);
			image.setOnClickListener(this);
			image.enable();
			image.setScaleType(ImageView.ScaleType.FIT_START);
			StringBuffer url = new StringBuffer();
			url.append(mUrl);
			url.append(mImgUrlList.get(i));
			x.image().bind(image, url.toString(), mOptions);
			mViewList.add(view);
		}
	}

	public List<View> getItems() {
		return mViewList;
	}

	@Override
	public void onClick(View v) {
		mActivity.finish();
	}
}
