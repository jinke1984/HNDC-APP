package cn.com.jinke.wh_drugcontrol.password;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import cn.com.jinke.wh_drugcontrol.R;


/**
 * 手势密码图案提示
 */
public class GestureThumbnail extends View {
    private int numRow = 3;    // 行
    private int numColumn = 3; // 列
    private int patternWidth = 40;
    private int patternHeight = 40;
    private int f = 5;
    private int g = 5;
    private Paint paint = null;
    private Drawable patternNormal = null;
    private Drawable patternPressed = null;
    private String lockPassStr; // 手势密码

    public GestureThumbnail(Context paramContext) {
        super(paramContext);
    }

    public GestureThumbnail(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        patternNormal = getResources().getDrawable(R.drawable.gesture_thumbnail_normal);
        patternPressed = getResources().getDrawable(R.drawable.gesture_thumbnail_selected);
        if (patternPressed != null) {
            patternWidth = patternPressed.getIntrinsicWidth() + 8;
            patternHeight = patternPressed.getIntrinsicHeight() + 8;
            this.f = (patternWidth / 4);
            this.g = (patternHeight / 4);
            patternPressed.setBounds(0, 0, patternWidth, patternHeight);
            patternNormal.setBounds(0, 0, patternWidth, patternHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if ((patternPressed == null) || (patternNormal == null)) {
            return;
        }
        // 绘制3*3的图标
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numColumn; j++) {
                paint.setColor(Color.WHITE);
                int i1 = j * patternHeight + j * this.g;
                int i2 = i * patternWidth + i * this.f;
                canvas.save();
                canvas.translate(i1, i2);
                String curNum = String.valueOf(numColumn * i + (j + 1));
                if (!TextUtils.isEmpty(lockPassStr)) {
                    if (!lockPassStr.contains(curNum)) {
                        // 未选中
                        patternNormal.draw(canvas);
                    } else {
                        // 被选中
                        patternPressed.draw(canvas);
                    }
                } else {
                    // 重置状态
                    patternNormal.draw(canvas);
                }
                canvas.restore();
            }
        }
    }

    @Override
    protected void onMeasure(int paramInt1, int paramInt2) {
        if (patternPressed != null)
            setMeasuredDimension(numColumn * patternHeight + this.g * (-1 + numColumn),
                    numRow * patternWidth + this.f * (-1 + numRow));
    }

    /**
     * 请求重新绘制
     *
     * @param paramString 手势密码字符序列
     */
    public void setPath(String paramString) {
        lockPassStr = paramString;
        invalidate();
    }
}