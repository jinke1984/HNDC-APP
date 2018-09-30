package cn.com.jinke.wh_drugcontrol.utils;

import android.app.Activity;
import android.graphics.Color;

import java.util.Calendar;
import java.util.Locale;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * BirthdayPicker
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/24
 */

public class BirthdayPicker {

    private BirthdayPicker() {
    }

    public static void showYearMonthDay(final Activity activity,
                                        final DatePicker.OnYearMonthDayPickListener listener) {
        Calendar current = Calendar.getInstance(Locale.CHINA);
        int currentYear = current.get(Calendar.YEAR);
        int startYear = currentYear - 150;
        int endYear = currentYear + 150;
        int month = current.get(Calendar.MONTH) + 1;
        int day = current.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = new DatePicker(activity);
        datePicker.setRangeStart(startYear, month, day);
        datePicker.setRangeEnd(endYear, month, day);
        datePicker.setCanceledOnTouchOutside(false);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setShadowColor(Color.RED, 40);
        datePicker.setCycleDisable(true);
        datePicker.setTextSize(14);
        datePicker.setSelectedItem(currentYear, month, day);
        datePicker.setOnDatePickListener(listener);
        datePicker.show();
    }

    public static void showYearMonth(final Activity activity, final DatePicker.OnYearMonthPickListener listener) {
        Calendar current = Calendar.getInstance(Locale.CHINA);
        int currentYear = current.get(Calendar.YEAR);
        int startYear = currentYear - 150;
        int endYear = currentYear + 150;
        int month = current.get(Calendar.MONTH) + 1;
        int day = current.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = new DatePicker(activity, DatePicker.YEAR_MONTH);
        datePicker.setRangeStart(startYear, month, day);
        datePicker.setRangeEnd(endYear, month, day);
        datePicker.setCanceledOnTouchOutside(false);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setShadowColor(Color.RED, 40);
        datePicker.setCycleDisable(true);
        datePicker.setTextSize(14);
        datePicker.setSelectedItem(currentYear, month);
        datePicker.setOnDatePickListener(listener);
        datePicker.show();
    }

}
