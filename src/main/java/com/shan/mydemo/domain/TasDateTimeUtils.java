package com.shan.mydemo.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间工具
 *
 * @author zhangbr
 * @date 2020-12-29
 */
@Slf4j
public final class TasDateTimeUtils extends DateUtils {

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_YEAR_PATTERN = "yyyy";
    public static final String DEFAULT_YEAR_MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    public static final String DEFAULT_DATE_TIME_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN_2 = "yyyy/MM/dd";

    /** 大写数字 */
    private static final String[] NUMBERS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * format date.
     *
     * @param date date
     * @param pattern pattern
     * @return formatted string
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * format date_time, pattern {@code DEFAULT_DATE_TIME_PATTERN}.
     *
     * @param date date
     * @return formatted string
     */
    public static String format(Date date) {
        return formatDateTime(date);
    }

    public static String formatDateTime(Date date) {
        return DateFormatUtils.format(date, DEFAULT_DATE_TIME_PATTERN);
    }

    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatYearMonth(Date date) {
        return DateFormatUtils.format(date, DEFAULT_YEAR_MONTH_PATTERN);
    }

    public static String formatTime(Date date) {
        return DateFormatUtils.format(date, DEFAULT_TIME_PATTERN);
    }

    /**
     * format date.
     *
     * @param date date
     * @param pattern pattern
     * @param timeZone timeZone
     * @param locale locale
     * @return formatted string
     */
    public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
        return DateFormatUtils.format(date, pattern, timeZone, locale);
    }

    /**
     * parse date string to date object, defaults to null.
     *
     * @param dateStr date, time, or date-time string
     * @return date instance, defaults to null.
     */
    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return parseDate(dateStr,
                DEFAULT_DATE_TIME_PATTERN,
                DEFAULT_DATE_TIME_PATTERN_2,
                DEFAULT_DATE_PATTERN,
                DEFAULT_TIME_PATTERN);
        } catch (ParseException pe) {
            log.warn("Parse input string '{}' to date error.", dateStr);
            return null;
        }
    }

    /**
     * yyyy/MM/dd HH:mm:ss
     * yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static String formatDateStr(String dateStr) {
        Date date = parseDate(dateStr);
        return formatDate(date);
    }

    /**
     * parse date string to date object, defaults to null.
     *
     * @param dateStr date, time, or date-time string
     * @return date instance, defaults to null.
     */
    public static Date parseDate2(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return parseDate(dateStr,
                DEFAULT_DATE_TIME_PATTERN_2,
                DEFAULT_DATE_PATTERN_2,
                DEFAULT_TIME_PATTERN);
        } catch (ParseException pe) {
            log.warn("Parse input string '{}' to date error.", dateStr);
            return null;
        }
    }

    /**
     * 获取当前时间的中文时间
     *
     * @return 中文时间
     */
    public static String getCurrentCNDate() {
        // 使用日历类
        Calendar cal = Calendar.getInstance();
        // 得到年
        String year = String.valueOf(cal.get(Calendar.YEAR));
        // 得到月，因为从0开始的，所以要加1
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        // 得到天
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        StringBuilder cnDate = new StringBuilder();
        for (int i = 0; i < year.length(); i++) {
            cnDate.append(NUMBERS[Integer.parseInt(String.valueOf(year.charAt(i)))]);
        }
        cnDate.append("年");

        cnDate.append(transformMonAndDay(month));
        cnDate.append("月");

        cnDate.append(transformMonAndDay(day));
        cnDate.append("日");

        return cnDate.toString();
    }

    private static String transformMonAndDay(String time) {
        String zero = "0";
        StringBuilder sb = new StringBuilder();
        if (time.length() == 2) {
            Integer value = Integer.valueOf(String.valueOf(time.charAt(0)));
            if (!value.equals(1)) {
                sb.append(NUMBERS[value]);
            }
            sb.append("十");
            if (!zero.equals(String.valueOf(time.charAt(1)))) {
                sb.append(NUMBERS[Integer.parseInt(String.valueOf(time.charAt(1)))]);
            }
        } else {
            sb.append(NUMBERS[Integer.parseInt(String.valueOf(time.charAt(0)))]);
        }
        return sb.toString();
    }



    /**
     * @param date1
     *            需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2
     *            被比较的时间 为空(null)则为当前时间
     * @param stype
     *            返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(Date date1, Date date2, int stype) {
        int n = 0;

        String[] u = { "天", "月", "年" };

        date2 = date2 == null ? new Date() : date2;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(date1);
            c2.setTime(date2);
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            // 这里可以把间隔的日期存到数组中 打印出来
            // list.add(df.format(c1.getTime()));
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }

        n = n - 1;

        if (stype == 2) {
            n = n / 365;
        }

        System.out.println(date1 + " -- " + date2 + " 相差多少" + u[stype] + ":" + n);
        return n;
    }
}
