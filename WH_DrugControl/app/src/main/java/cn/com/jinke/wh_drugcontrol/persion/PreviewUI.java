package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bm.library.PhotoView;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/9/12.
 */

@Route(path = RouteUtils.R_PERSION_PREVIEW_UI)
public class PreviewUI extends ProjectBaseUI {

    private String mLocalPath = null;
    private ImageOptions mOptions;

    @ViewInject(R.id.photo_preview)
    private PhotoView mPhotoView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_preview);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            mLocalPath = intent.getStringExtra(B_PATH);
        }

        Header header = getHeader();
        if(header != null){
            header.titleText.setText(R.string.fjyl);
        }
    }

    @Override
    protected void onInitData() {

        if(!TextUtils.isEmpty(mLocalPath)){
            mPhotoView.enable();
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setFailureDrawableId(R.drawable.common_picture_default);
            builder.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            mOptions = builder.build();
            x.image().bind(mPhotoView, mLocalPath, mOptions);
        }
    }
}
