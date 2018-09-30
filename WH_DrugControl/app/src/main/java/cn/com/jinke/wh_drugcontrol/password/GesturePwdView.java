package cn.com.jinke.wh_drugcontrol.password;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.utils.BitmapUtils;

/**
 * 手势密码容器类
 */
public class GesturePwdView extends ViewGroup {

    private int baseNum = 6;

    /**
     * 每个点区域的宽度
     */
    private int blockWidth;
    /**
     * 声明一个集合用来封装坐标集合
     */
    private List<GesturePoint> list;
    private Context context;
    private GestureLineCanvas gestureLineCanvas;

    /**
     * 包含9个ImageView的容器，初始化
     *
     * @param context  Context
     * @param isVerify 是否为校验手势密码
     */
    public GesturePwdView(Context context, ViewGroup parentView, boolean isVerify) {
        super(context);
        int width = BitmapUtils.getScreenWidth(context);
        blockWidth = width / 4;
        this.list = new ArrayList<>();
        this.context = context;
        // 添加9个图标
        addGesturePoints(9);
        // 初始化一个可以画线的view
        gestureLineCanvas = new GestureLineCanvas(context, list, isVerify);

        LayoutParams layoutParams = new LayoutParams(width, width);
        this.setLayoutParams(layoutParams);
        gestureLineCanvas.setLayoutParams(layoutParams);
        parentView.addView(gestureLineCanvas);
        parentView.addView(this);
    }

    /**
     * 设置是否为验证密码
     *
     * @param isVerify true：验证密码
     * @return GesturePwdView
     */
    public GesturePwdView setIsVerify(boolean isVerify) {
        gestureLineCanvas.setIsVerify(isVerify);
        return this;
    }

    public boolean isVerify() {
        return gestureLineCanvas.isVerify();
    }


    public GesturePwdView setOnPasswordSetListener(final OnPasswordSetListener listener) {
        final GesturePwdView thiz = this;
        gestureLineCanvas.setOnPasswordSetListener(new GestureLineCanvas.OnPasswordSetListener() {
            @Override
            public void onSetPassword(GestureLineCanvas gestureLineCanvas, String password, boolean isVerify) {
                if (listener != null) {
                    listener.onSetPassword(thiz, password, isVerify);
                }
            }
        });
        return this;
    }

    public GesturePwdView setOnPasswordClearListener(final OnPasswordClearListener listener) {
        final GesturePwdView thiz = this;
        gestureLineCanvas.setOnPasswordClearListener(new GestureLineCanvas.OnPasswordClearListener() {
            @Override
            public void onPasswordClear() {
                if (listener != null) {
                    listener.onPasswordClear(thiz);
                }
            }
        });
        return this;
    }

    private void addGesturePoints(int points) {
        final int translation = blockWidth / 2;

        for (int i = 0; i < points; i++) {
            ImageView image = new ImageView(context);
            image.setBackgroundResource(R.drawable.gesture_normal);
            this.addView(image);
            invalidate();
            // 第几行
            int row = i / 3;
            // 第几列
            int col = i % 3;
            // 定义点的每个属性
            int left = col * blockWidth + blockWidth / baseNum + translation;
            int top = row * blockWidth + blockWidth / baseNum + translation;
            int right = col * blockWidth + blockWidth - blockWidth / baseNum + translation;
            int bottom = row * blockWidth + blockWidth - blockWidth / baseNum + translation;
            GesturePoint p = new GesturePoint(image, i + 1, left, right, top, bottom);
            this.list.add(p);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            GesturePoint point = list.get(i);
            v.layout(point.getLeft(), point.getTop(), point.getRight(), point.getBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 遍历设置每个子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 保留路径delayTime时间长
     *
     * @param delayTime
     */
    public void reset(long delayTime) {
        gestureLineCanvas.reset(delayTime);
    }

    public void reset(long delayTime, boolean call) {
        gestureLineCanvas.reset(false, delayTime, call);
    }

    public void reset(boolean showError, long delayTime) {
        gestureLineCanvas.reset(showError, delayTime);
    }

    public void reset(boolean showError, long delayTime, boolean call) {
        gestureLineCanvas.reset(showError, delayTime, call);
    }

    public interface OnPasswordSetListener {
        void onSetPassword(GesturePwdView gesturePwdView, String password, boolean isVerify);
    }

    public interface OnPasswordClearListener {
        void onPasswordClear(GesturePwdView gesturePwdView);
    }

}
