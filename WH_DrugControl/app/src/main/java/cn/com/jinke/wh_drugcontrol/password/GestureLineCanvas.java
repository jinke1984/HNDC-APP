package cn.com.jinke.wh_drugcontrol.password;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.jinke.wh_drugcontrol.utils.BitmapUtils;

/**
 * 手势密码路径绘制
 */
public class GestureLineCanvas extends View {

    private Paint paint;// 声明画笔
    private Canvas canvas;// 画布
    private Bitmap bitmap;// 位图
    private List<GesturePoint> list;// 装有各个view坐标的集合
    private List<Pair<GesturePoint, GesturePoint>> lineList;// 记录画过的线
    private Map<String, GesturePoint> autoCheckPointMap;// 自动选中的情况点
    private boolean isDrawEnable = true; // 是否允许绘制

    /**
     * 手指当前在哪个Point内
     */
    private GesturePoint currentPoint;

    private OnPasswordSetListener onPasswordSetListener;
    private OnPasswordClearListener onPasswordClearListener;

    /**
     * 用户当前绘制的图形密码
     */
    private StringBuilder passwordSb;

    /**
     * 是否为校验
     */
    private boolean isVerify;

    public GestureLineCanvas(Context context, List<GesturePoint> list, boolean isVerify) {
        super(context);

        // 屏幕的宽度和高度
        int width = BitmapUtils.getScreenWidth(context);
        int height = BitmapUtils.getScreenHeight(context);

        paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // 设置位图的宽高
        canvas = new Canvas(bitmap);
        paint.setStyle(Style.STROKE);// 设置非填充
        paint.setStrokeWidth(5);// 笔宽5像素
        paint.setColor(Color.parseColor("#708be9"));// 设置默认连线颜色
        paint.setAntiAlias(true);// 不显示锯齿

        this.list = list;
        this.lineList = new ArrayList<>();

        initAutoCheckPointMap();

        // 初始化密码缓存
        this.passwordSb = new StringBuilder();
        this.isVerify = isVerify;
    }

    /**
     * 设置是否为验证密码
     *
     * @param isVerify true：验证密码
     * @return GestureLineCanvas
     */
    public GestureLineCanvas setIsVerify(boolean isVerify) {
        this.isVerify = isVerify;
        return this;
    }

    public boolean isVerify() {
        return isVerify;
    }

    private void initAutoCheckPointMap() {
        autoCheckPointMap = new HashMap<>();
        autoCheckPointMap.put("1,3", getGesturePointByNum(2));
        autoCheckPointMap.put("1,7", getGesturePointByNum(4));
        autoCheckPointMap.put("1,9", getGesturePointByNum(5));
        autoCheckPointMap.put("2,8", getGesturePointByNum(5));
        autoCheckPointMap.put("3,7", getGesturePointByNum(5));
        autoCheckPointMap.put("3,9", getGesturePointByNum(6));
        autoCheckPointMap.put("4,6", getGesturePointByNum(5));
        autoCheckPointMap.put("7,9", getGesturePointByNum(8));
    }

    private GesturePoint getGesturePointByNum(int num) {
        for (GesturePoint point : list) {
            if (point.getIndicator() == num) {
                return point;
            }
        }
        return null;
    }

    // 画位图
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDrawEnable) return true;

        paint.setColor(Color.parseColor("#708be9"));// 设置默认连线颜色
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            int mov_x = (int) event.getX();
            int mov_y = (int) event.getY();
            // 判断当前点击的位置是处于哪个点之内
            currentPoint = getPointAt(mov_x, mov_y);
            if (currentPoint != null) {
                currentPoint.setState(GesturePoint.State.SELECTED);
                passwordSb.append(currentPoint.getIndicator());
                invalidate();
            }
            break;

        case MotionEvent.ACTION_MOVE:
            if (currentPoint == null) return true;

            clearScreenAndDrawList();

            // 得到当前移动位置是处于哪个点内
            GesturePoint pointAt = getPointAt((int) event.getX(), (int) event.getY());
            // 代表当前用户手指处于点与点之前
            if (currentPoint == null && pointAt == null) {
                return true;
            } else {// 代表用户的手指移动到了点上
                if (currentPoint == null) {// 先判断当前的point是不是为null
                    // 如果为空，那么把手指移动到的点赋值给currentPoint
                    currentPoint = pointAt;
                    // 把currentPoint这个点设置选中为true;
                    currentPoint.setState(GesturePoint.State.SELECTED);
                    passwordSb.append(currentPoint.getIndicator());
                }
            }
            if (pointAt == null || currentPoint.equals(pointAt) || GesturePoint.State.SELECTED == pointAt.getState()) {
                // 点击移动区域不在圆的区域，或者当前点击的点与当前移动到的点的位置相同，或者当前点击的点处于选中状态
                // 那么以当前的点中心为起点，以手指移动位置为终点画线
                canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), event.getX(), event.getY(), paint);// 画线
            } else {
                // 如果当前点击的点与当前移动到的点的位置不同
                // 那么以前前点的中心为起点，以手移动到的点的位置画线
                canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), pointAt.getCenterX(), pointAt.getCenterY(), paint);// 画线
                pointAt.setState(GesturePoint.State.SELECTED);

                // 判断是否中间点需要选中
                GesturePoint betweenPoint = getBetweenCheckPoint(currentPoint, pointAt);
                if (betweenPoint != null && GesturePoint.State.SELECTED != betweenPoint.getState()) {
                    // 存在中间点并且没有被选中
                    Pair<GesturePoint, GesturePoint> pair1 = new Pair<>(currentPoint, betweenPoint);
                    lineList.add(pair1);
                    passwordSb.append(betweenPoint.getIndicator());
                    Pair<GesturePoint, GesturePoint> pair2 = new Pair<>(betweenPoint, pointAt);
                    lineList.add(pair2);
                    passwordSb.append(pointAt.getIndicator());
                    // 设置中间点选中
                    betweenPoint.setState(GesturePoint.State.SELECTED);
                    // 赋值当前的point;
                    currentPoint = pointAt;
                } else {
                    Pair<GesturePoint, GesturePoint> pair = new Pair<>(currentPoint, pointAt);
                    lineList.add(pair);
                    passwordSb.append(pointAt.getIndicator());
                    // 赋值当前的point;
                    currentPoint = pointAt;
                }
            }
            invalidate();
            break;
        case MotionEvent.ACTION_UP:// 当手指抬起的时候
            if (currentPoint == null) return true;

            isDrawEnable = false;
            if (onPasswordSetListener != null) {
                onPasswordSetListener.onSetPassword(this, passwordSb.toString(), isVerify);
            }
            break;
        default:
            break;
        }
        return true;
    }

    public GestureLineCanvas setOnPasswordSetListener(OnPasswordSetListener onPasswordSetListener) {
        this.onPasswordSetListener = onPasswordSetListener;
        return this;
    }

    public GestureLineCanvas setOnPasswordClearListener(OnPasswordClearListener listener) {
        this.onPasswordClearListener = listener;
        return this;
    }

    /**
     * 指定时间去清除绘制的状态
     *
     * @param delayTime 延迟执行时间
     */
    public void reset(long delayTime) {
        reset(false, delayTime);
    }

    /**
     * 指定时间去清除绘制的状态
     *
     * @param delayTime 延迟执行时间
     */
    public void reset(boolean showError, long delayTime, boolean call) {
        setEnabled(false);
        if (showError) {
            // 绘制红色提示路线
            isDrawEnable = false;
            drawErrorPathTip();
        }
        new Handler().postDelayed(new clearStateRunnable(call), delayTime);
    }

    public void reset(boolean showError, long delayTime) {
        reset(showError, delayTime, true);
    }

    /**
     * 清除绘制状态的线程
     */
    private final class clearStateRunnable implements Runnable {
        private boolean call = true;

        clearStateRunnable(boolean call) {
            this.call = call;
        }

        public void run() {
            setEnabled(true);
            // 重置passWordSb
            passwordSb = new StringBuilder();
            // 清空保存点的集合
            lineList.clear();
            // 重新绘制界面
            clearScreenAndDrawList();
            for (GesturePoint p : list) {
                p.setState(GesturePoint.State.NORMAL);
            }
            invalidate();
            isDrawEnable = true;
            if (onPasswordClearListener != null && call) {
                onPasswordClearListener.onPasswordClear();
            }
        }
    }

    /**
     * 通过点的位置去集合里面查找这个点是包含在哪个Point里面的
     *
     * @param x
     * @param y
     * @return 如果没有找到，则返回null，代表用户当前移动的地方属于点与点之间
     */
    private GesturePoint getPointAt(int x, int y) {

        for (GesturePoint point : list) {
            // 先判断x
            int leftX = point.getLeft();
            int rightX = point.getRight();
            if (!(x >= leftX && x < rightX)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            int topY = point.getTop();
            int bottomY = point.getBottom();
            if (!(y >= topY && y < bottomY)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            // 如果执行到这，那么说明当前点击的点的位置在遍历到点的位置这个地方
            return point;
        }

        return null;
    }

    private GesturePoint getBetweenCheckPoint(GesturePoint pointStart, GesturePoint pointEnd) {
        int startNum = pointStart.getIndicator();
        int endNum = pointEnd.getIndicator();
        String key = null;
        if (startNum < endNum) {
            key = startNum + "," + endNum;
        } else {
            key = endNum + "," + startNum;
        }
        return autoCheckPointMap.get(key);
    }

    /**
     * 清掉屏幕上所有的线，然后画出集合里面的线
     */
    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<GesturePoint, GesturePoint> pair : lineList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);// 画线
        }
    }

    /**
     * 校验错误/两次绘制不一致提示
     */
    private void drawErrorPathTip() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setColor(Color.parseColor("#fb5230"));// 设置错误连线颜色
        for (Pair<GesturePoint, GesturePoint> pair : lineList) {
            pair.first.setState(GesturePoint.State.ERROR);
            pair.second.setState(GesturePoint.State.ERROR);
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);// 画线
        }
        invalidate();
    }

    public interface OnPasswordSetListener {
        void onSetPassword(GestureLineCanvas gestureLineCanvas, String password, boolean isVerify);
    }

    public interface OnPasswordClearListener {
        void onPasswordClear();
    }

}
