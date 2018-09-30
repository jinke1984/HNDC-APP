package cn.com.jinke.wh_drugcontrol.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class DrawImageView extends AppCompatImageView {
    private int startx;
    private int starty;
    private int endx;
    private int endy;

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

    }

    public void initX(int startx, int starty, int endx, int endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;

    }

    Paint paint = new Paint();
    {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(8f);//设置线宽
        paint.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawRect(new Rect(startx, starty, endx, endy), paint);//绘制矩形
    }

    public void Change_onDraw(Canvas canvas) {
        canvas.drawRect(new Rect(startx, starty, endx, endy), paint);//绘制矩形
    }


}
