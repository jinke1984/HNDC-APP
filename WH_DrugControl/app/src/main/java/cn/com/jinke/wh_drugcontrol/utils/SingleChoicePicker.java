package cn.com.jinke.wh_drugcontrol.utils;

import android.app.Activity;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * SingleChoicePicker
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/24
 */

public class SingleChoicePicker {

    private SingleChoicePicker() {
    }

    public static void showString(final Activity activity,
                                  final List<String> items,
                                  final OptionPicker.OnOptionPickListener listener) {
        OptionPicker s_picker = new OptionPicker(activity, items);
        s_picker.setCanceledOnTouchOutside(false);
        s_picker.setDividerRatio(WheelView.DividerConfig.FILL);
        s_picker.setShadowColor(Color.RED, 40);
        s_picker.setSelectedIndex(0);
        s_picker.setCycleDisable(true);
        s_picker.setTextSize(14);
        s_picker.setOnOptionPickListener(listener);
        s_picker.show();
    }

    public static void showNation(final Activity activity,
                                  final List<Nation> items,
                                  final OnNationOptionPickListener listener) {
        final ArrayList<String> list = new ArrayList<>();
        for (Nation item : items) {
            list.add(item.getText());
        }
        showString(activity, list, new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if (listener != null) {
                    listener.onOptionPicked(index, items.get(index));
                }
            }
        });
    }

    public interface OnNationOptionPickListener {
        void onOptionPicked(int index, Nation nation);
    }

}
