package cn.com.jinke.wh_drugcontrol.customview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;

/**
 * Created by jinke on 2017/7/28.
 */

public class MultipeChooseView extends ViewGroup {

    private List<Nation> mList;
    private boolean[] checkedInxs;
    private int VIEW_MARGIN = ActivityHelper.dipTopx(getContext(), 6);

    private OnCheckChangedListener mListener;

    public MultipeChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final MultipeChooseView thiz = this;

        if (mList != null) {
            LayoutInflater mInflater = LayoutInflater.from(getContext());
            for (int inx = 0; inx < mList.size(); ++inx) {
                final Nation nation = mList.get(inx);

                String name = nation.getText();
                LinearLayout navigate = (LinearLayout) mInflater.inflate(R.layout.item_custom_checkbox, null);
                final CheckBox checkBox = (CheckBox) navigate.findViewById(R.id.custom_cb);
                TextView textView = (TextView) navigate.findViewById(R.id.custom_cb_tv);
                textView.setText(name);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(!checkBox.isChecked());
                    }
                });
                checkBox.setChecked(checkedInxs[inx]);

                final int finalInx = inx;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkedInxs[finalInx] = isChecked;
                        if (mListener != null) {
                            mListener.onCheckChanged(thiz, finalInx, nation, isChecked);
                        }
                    }
                });
                this.addView(navigate);
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int row = 0;
        int lengthX = l;
        int lengthY = 0;
        for (int i = 0; i < count; i++) {

            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width + VIEW_MARGIN;
            lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height;
            if (lengthX > r) {

                lengthX = width + VIEW_MARGIN + l;
                row++;
                lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setItems(List<Nation> items) {
        setItems(items, null);
    }

    public void setItems(final List<Nation> items, final String checkedStr) {
        mList = items;
        setCheckedInx(checkedStr);

        removeAllViewsInLayout();
        init();
        requestLayout();

    }

    public static void initData(MultipeChooseView view, Context context, List<Nation> list) {
        view.setItems(list);
    }

    public static void initData(MultipeChooseView view, Context context, List<Nation> list, String checkedInx) {
        view.setItems(list, checkedInx);
    }

    /**
     * @param checkedInx eg.("1|2|4")
     */
    private void setCheckedInx(String checkedInx) {
        checkedInxs = new boolean[mList.size()];
        Arrays.fill(checkedInxs, false);
        if (TextUtils.isEmpty(checkedInx)) return;

        String[] checkInxs = checkedInx.split("\\|");
        for (String checkInx : checkInxs) {
            int index = Integer.parseInt(checkInx);
            if (index < mList.size()) {
                checkedInxs[index] = true;
            }
        }
    }

    public String getCheckedInx() {
        String checked = "";
        for (int inx = 0; inx < mList.size(); ++inx) {
            if (checkedInxs[inx]) {
                checked += inx + "|";
            }
        }
        if (checked.length() > 0) {
            checked = checked.substring(0, checked.length() - 1);
        }

        return checked;
    }

    public MultipeChooseView setOnCheckChangedListener(OnCheckChangedListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnCheckChangedListener {
        void onCheckChanged(MultipeChooseView view, int index, Nation nation, boolean isChecked);
    }

}
