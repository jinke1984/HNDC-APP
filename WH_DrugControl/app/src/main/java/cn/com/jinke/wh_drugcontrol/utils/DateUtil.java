package cn.com.jinke.wh_drugcontrol.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.com.jinke.wh_drugcontrol.R;

/**
 * Created by jinke on 2017/5/30.
 */

public class DateUtil {

    private static DateUtil instance;

    private DateUtil() {

    }

    public static DateUtil getInstance() {
        if (instance == null) {
            synchronized (DateUtil.class) {
                if (instance == null) {
                    instance = new DateUtil();
                }
            }
        }
        return instance;
    }

    public String getDate(long data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt = new Date(data);
        //得到精确到秒的表示：08/31/2006 21:08:00
        String sDateTime = sdf.format(dt);
        return sDateTime;
    }

    public String getTimeOfString(Context context) {

        String time = "";
        Calendar nowTime = Calendar.getInstance();
        int hour = nowTime.get(Calendar.HOUR_OF_DAY);
        if (hour < 7 || hour > 18) {
            //晚上
            time = context.getString(R.string.wsh);
        } else if (hour >= 7 && hour < 12) {
            //上午
            time = context.getString(R.string.zsh);
        } else {
            //下午
            time = context.getString(R.string.xwh);
        }

        return time;

    }

    public String changeDate(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        String sDateTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(data);
            sDateTime = sdf.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDateTime;
    }

    /**
     * 解析日期
     *
     * @param dateString 日期字符串 "1989-06-04 18:00:00"
     * @param dateFormat 日期格式 "yyyy-MM-dd HH:mm:ss"
     * @return 返回日期 null表示发生错误
     */
    public Date parseDate(String dateString, String dateFormat) {
        Date date = null;
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (format != null) {
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public enum DateType {
        TODAY, YESTERDAY, THIS_YEAR, OTHER_YEAR
    }

    /**
     * 日期转字符串
     * @param date 要转换的日期
     * @param dateFormat "yyyy-MM-dd HH:mm:ss"
     * @return 返回日期 null表示发生错误
     */
    public String parseString(Date date, String dateFormat) {
        String dateString = null;
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (format != null && date != null) {
            dateString = format.format(date);
        }
        return dateString;
    }

    public boolean isSameDay(long timeA, long timeB) {
        return isSameDay(new Date(timeA), new Date(timeB));
    }

    public boolean isSameDay(Date dateA, Date dateB) {
        return getYear(dateA) == getYear(dateB) && getMonth(dateA) == getMonth(dateB) && getDay(dateA) == getDay(dateB);
    }

    /**
     * 把毫秒转化为友好的时间显示
     * @param milliseconds 微秒值
     * @return 友好格式的时间字符串，传入不正确的值是返回null
     */
    public String friendlyDT2(Context context, long milliseconds) {
        Date date = new Date(milliseconds);
        DateType dateType = getDateType(date);
        if (dateType == DateUtil.DateType.TODAY) {
            return context.getString(R.string.common_today);
        }
        else if (dateType == DateUtil.DateType.YESTERDAY) {
            return context.getString(R.string.common_yesterday);
        }
        else if (dateType == DateUtil.DateType.THIS_YEAR) {
            return parseString(date, context.getString(R.string.common_date_format_month_day));
        }
        else if (dateType == DateUtil.DateType.OTHER_YEAR) {
            return parseString(date, context.getString(R.string.common_date_format_year_month_day));
        }
        else {
            return null;
        }
    }

    /**
     * 获取日期类型
     * @param dstDate 日期
     * @return {@link DateType} 今天、昨天、今年、非今年
     */
    public DateType getDateType(Date dstDate) {
        if (dstDate == null) {
            return DateType.OTHER_YEAR;
        }
        int dstYear = getYear(dstDate);
        int dstMonth = getMonth(dstDate);
        int dstDay = getDay(dstDate);

        Date currentDate = new Date();
        int currentYear = getYear(currentDate);
        int currentMonth = getMonth(currentDate);
        int currentDay = getDay(currentDate);

        if (dstYear == currentYear) {
            // 这个月的日期
            if (dstMonth == currentMonth) {
                // 今天
                if (dstDay == currentDay) {
                    return DateType.TODAY;
                }
                // 昨天
                else if (currentDay - dstDay == 1) {
                    return DateType.YESTERDAY;
                }
            }
            // 今年的日期
            return DateType.THIS_YEAR;
        }
        // 不是今年的日期
        return DateType.OTHER_YEAR;
    }

    /**
     * 获取date的年份
     * @param date
     * @return 返回年份 -1表示发生错误
     */
    public int getYear(Date date) {
        return calendarGetField(Calendar.YEAR, date);
    }

    /**
     * 获取date的月份 (1-12月)
     * @param date
     * @return 返回月份 -1表示发生错误
     */
    public int getMonth(Date date) {
        // 返回的month -1 是发生了异常 0-11 表示12个月 12表示转换时错误
        int month = calendarGetField(Calendar.MONTH, date);
        return month <= Calendar.DECEMBER && month >= Calendar.JANUARY ? month + 1 : -1;
    }

    /**
     * 获取date的日份(该日是该月中的哪天)
     * @param date
     * @return 返回日份 -1表示发生错误
     */
    public int getDay(Date date) {
        return calendarGetField(Calendar.DAY_OF_MONTH, date);
    }

    /**
     * 获取date的日份是星期几(该日是星期几)
     * @param date
     * @return 返回星期几 -1表示发生错误
     */
    public int getDayOfWeek(Date date) {
        return calendarGetField(Calendar.DAY_OF_WEEK, date);
    }

    /**
     * 获取date的小时
     * @param date 日期
     * @return 返回小时 -1表示发生错误
     */
    public int getHour(java.util.Date date) {
        return calendarGetField(Calendar.HOUR_OF_DAY, date);
    }

    /**
     * 获取date的分钟
     * @param date 日期
     * @return 返回分钟 -1表示发生错误
     */
    public int getMinute(Date date) {
        return calendarGetField(Calendar.MINUTE, date);
    }

    /**
     * 获取date的秒钟
     * @param date 日期
     * @return 返回秒钟 -1表示发生错误
     */
    public int getSecond(Date date) {
        return calendarGetField(Calendar.SECOND, date);
    }

    private int calendarGetField(int field, Date date) {
        int ret = -1;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            ret = calendar.get(field);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Date stringToDate(String string){
        Date date = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = (Date) sdf.parse(string);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    public long dateToLong(Date date){
        long time = date.getTime();
        return time;
    }


    public long stringToLong(String string){
        long time = dateToLong(stringToDate(string));
        return time;
    }

    /**
     * date类型转换为long类型
     * @param strTime
     * @return
     */
    public long dateToLong(String strTime){
        Date date = stringToDate(strTime);
        if (date == null) {
            return 0;
        } else {
            long currentTime = date.getTime();
            return currentTime;
        }
    }

    public String longToString(long currentTime) {
        String strTime = null;
        try {
            Date date = longToDate(currentTime);
            strTime = dateToString(date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return strTime;
    }


    public Date longToDate(long currentTime) throws ParseException {
        Date dateOld = new Date(currentTime);
        String sDateTime = dateToString(dateOld);
        Date date = stringToDate(sDateTime);
        return date;
    }

    public String dateToString(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }
}
