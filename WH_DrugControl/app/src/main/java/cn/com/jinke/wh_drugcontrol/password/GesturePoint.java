package cn.com.jinke.wh_drugcontrol.password;

import android.widget.ImageView;

import cn.com.jinke.wh_drugcontrol.R;

/**
 * GesturePoint
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/30
 */

public class GesturePoint {

    public enum State {NORMAL, SELECTED, ERROR}

    /**
     * 左边x的值
     */
    private int left;
    /**
     * 右边x的值
     */
    private int right;
    /**
     * 上边y的值
     */
    private int top;
    /**
     * 下边y的值
     */
    private int bottom;
    /**
     * 这个点对应的ImageView控件
     */
    private ImageView image;

    /**
     * 中心x值
     */
    private int centerX;

    /**
     * 中心y值
     */
    private int centerY;

    /**
     * 状态值
     */
    private State state;

    /**
     * 代表这个Point对象代表的数字，从1开始(直接感觉从1开始)
     */
    private int indicator;

    public GesturePoint(ImageView image, int indicator, int left, int right, int top, int bottom) {
        super();
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.image = image;

        this.centerX = (left + right) / 2;
        this.centerY = (top + bottom) / 2;

        this.indicator = indicator;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        switch (state) {
        case NORMAL:
            this.image.setBackgroundResource(R.drawable.gesture_normal);
            break;
        case SELECTED:
            this.image.setBackgroundResource(R.drawable.gesture_selected);
            break;
        case ERROR:
            this.image.setBackgroundResource(R.drawable.gesture_error);
            break;
        default:
            break;
        }
    }

    public int getIndicator() {
        return indicator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bottom;
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + left;
        result = prime * result + right;
        result = prime * result + top;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GesturePoint other = (GesturePoint) obj;
        if (bottom != other.bottom)
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (left != other.left)
            return false;
        if (right != other.right)
            return false;
        if (top != other.top)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Point [left=" + left + ", right=" + right + ", top="
                + top + ", bottom=" + bottom + "]";
    }

}
