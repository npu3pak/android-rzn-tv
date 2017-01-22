package tv.rzn.rzntv.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Автор: EVSafronov
 * Дата: 05.01.15.
 */
public class DateHelper {
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat dateTimeFormat;

    static {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    public static boolean isYesterday(Date date) {
        Calendar todayCal = new GregorianCalendar();
        Calendar yesterdayCal = new GregorianCalendar();
        yesterdayCal.add(Calendar.DAY_OF_YEAR, -1);

        yesterdayCal.set(Calendar.HOUR, 0);
        yesterdayCal.set(Calendar.MINUTE, 0);
        yesterdayCal.set(Calendar.SECOND, 0);
        yesterdayCal.set(Calendar.MILLISECOND, 0);

        Date todayStart = new Date(todayCal.getTimeInMillis());
        Date yesterdayStart = new Date(yesterdayCal.getTimeInMillis());
        return date.compareTo(yesterdayStart) >= 0 && date.compareTo(todayStart) < 0;
    }


    public static boolean isToday(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date todayStart = new Date(calendar.getTimeInMillis());
        return date.compareTo(todayStart) > 0;
    }

    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
