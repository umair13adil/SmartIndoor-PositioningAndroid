package com.cubivue.inlogic.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * The type Date time utils.
 */
public class DateTimeUtils {

    public static final String DATE_FORMAT_DEFAULT = "MM/dd/yyyy";
    public static final String TIME_FORMAT_DEFAULT = "hh:mm:ss a";
    public static final String TIME_FORMAT_SHORT = "hh:mm";
    public static final String TIME_FORMAT_24 = "kk:mm";
    public static final String TIME_FORMAT_24_FULL = "dd/MM/yyyy kk:mm:ss";
    public static final String DATE_FORMAT_FULL = "ddMMyyyy";
    public static final String TIME_FORMAT_SIMPLE = "kk:mm:ss";
    public static final String TIME_FORMAT_FULL = "MM:dd:yyyy hh:mm:ss a";
    public static final String TIME_FORMAT_READABLE = "dd MMMM yyyy hh:mm:ss a";
    public static final String TIME_FORMAT_READABLE_DIALOG = "MMMM dd, yyyy - HH:mm:ss";
    public static String TAG = "DateTimeUtils";

    public static String getFullDateString(int year, int monthOfYear, int dayOfMonth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = sdf.parse(String.format("%2d/%2d/%4d", dayOfMonth, monthOfYear, year));

            String dayNumberSuffix = getDayOfMonthSuffix(dayOfMonth);
            SimpleDateFormat f1 = new SimpleDateFormat("d'" + dayNumberSuffix + "' MMM yyyy", Locale.ENGLISH);
            String formatted = f1.format(date);
            return formatted;
        } catch (ParseException e) {
            // handle exception here !
        }
        return String.format(DATE_FORMAT_DEFAULT, monthOfYear, dayOfMonth, year);
    }

    public static String getFullDateString(long timestamp) {
        Date date = new Date(timestamp);

        String dayNumberSuffix = getDayOfMonthSuffix(date.getDate());
        SimpleDateFormat f1 = new SimpleDateFormat("d'" + dayNumberSuffix + "' MMMM yyyy", Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getFullDateTimeString(long timestamp) {
        Date date = new Date(timestamp);

        String dayNumberSuffix = getDayOfMonthSuffix(date.getDate());
        SimpleDateFormat f1 = new SimpleDateFormat("d'" + dayNumberSuffix + "' MMMM yyyy hh:mm:ss a", Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getFullDateTimeStringCompressed(long timestamp) {
        Date date = new Date(timestamp);
        String formatted = "" + System.currentTimeMillis();
        try {
            String dayNumberSuffix = getDayOfMonthSuffix(date.getDate());
            SimpleDateFormat f1 = new SimpleDateFormat("ddMMyyyy_kkmmss", Locale.ENGLISH);
            formatted = f1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public static String getLogFileName(long timestamp) {
        Date date = new Date(timestamp);
        String formatted = "" + System.currentTimeMillis();
        try {
            String dayNumberSuffix = getDayOfMonthSuffix(date.getDate());
            SimpleDateFormat f1 = new SimpleDateFormat("d_MM_hh", Locale.ENGLISH);
            formatted = f1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public static String getDateString(int year, int monthOfYear, int dayOfMonth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = sdf.parse(String.format("%2d/%2d/%4d", dayOfMonth, monthOfYear, year));

            SimpleDateFormat f1 = new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.ENGLISH);
            return f1.format(date);
        } catch (ParseException e) {
            // handle exception here !
        }
        return String.format(DATE_FORMAT_DEFAULT, monthOfYear, dayOfMonth, year);
    }

    public static String getDateString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getDateStringFull(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(DATE_FORMAT_FULL, Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getTimeStringFull(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(TIME_FORMAT_SIMPLE, Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getHour(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat("kk", Locale.ENGLISH);
        return f1.format(date);
    }

    public static String getTimeString(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(TIME_FORMAT_DEFAULT, Locale.ENGLISH);
        return f1.format(date1);
    }

    public static String getTimeString24(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat outFormat = new SimpleDateFormat(TIME_FORMAT_24, Locale.ENGLISH);
        return outFormat.format(date1);
    }

    public static String getFullTimeString24(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat outFormat = new SimpleDateFormat(TIME_FORMAT_24_FULL, Locale.ENGLISH);
        return outFormat.format(date1);
    }

    public static String getReadableTime(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat outFormat = new SimpleDateFormat(TIME_FORMAT_READABLE, Locale.ENGLISH);
        return outFormat.format(date1);
    }

    public static Date getDate(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_READABLE, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long time2LocalTimeZone(long date) {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;
        return date + offsetFromUtc;
    }

    public static String getTimeStringShort(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(TIME_FORMAT_SHORT, Locale.ENGLISH);
        return f1.format(date1);
    }

    public static String getTimeFormatted(long timestamp) {
        Date date1 = new Date(timestamp);
        SimpleDateFormat f1 = new SimpleDateFormat(TIME_FORMAT_FULL, Locale.ENGLISH);
        return f1.format(date1);
    }

    public static String getTimeString(int hour, int minute) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            Date date = sdf.parse(String.format("%2s:%2s", hour, minute));

            SimpleDateFormat f1 = new SimpleDateFormat(TIME_FORMAT_DEFAULT, Locale.ENGLISH);
            return f1.format(date);
        } catch (ParseException e) {
            // handle exception here !
        }
        return null;
    }

    public static long getTimestamp(int year, int month, int day) {
        try {
            String strDate = String.format("%2d-%2d-%4d", day, month, year);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = formatter.parse(strDate);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0;
    }

    public static long getTimestamp(String d) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_24_FULL, Locale.ENGLISH);
            Date date = formatter.parse(d);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTimestampReadable(String d) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_READABLE, Locale.ENGLISH);
            Date date = formatter.parse(d);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTimestampFromTimeString(String d) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_SIMPLE, Locale.ENGLISH);
            Date date = formatter.parse(d);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTimeStampFormatted(Date date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static long getTimestamp(int year, int month, int day, int hour, int minute) {
        try {
            String strDate = String.format("%2d-%2d-%4d %2d:%2d", day, month, year, hour, minute);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
            Date date = formatter.parse(strDate);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0;
    }

    public static String getDayOfMonthSuffix(int n) {
        if (n < 1 || n > 31) {
            throw new IllegalArgumentException("Illegal day of month");
        }
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static long getUTCTimeStamp(long timestamp) {
        Date netDate = null;
        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        long time_utc = new Date(timestamp).getTime();
        Log.i(TAG, "Time: " + time_utc);
        return time_utc / 1000L;
    }

    public static String formatDate(String date) {
        String dateFormatted = "";
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        dateFormatted = spf.format(newDate);
        return dateFormatted;
    }

    public static String formatComplaintDate(String date) {
        String dateFormatted = "";
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getFullDateString(newDate.getTime());
    }

    public static String getTimeDifference(String d1, String d2) {

        long elapsedHours = 0;
        long elapsedMinutes = 0;
        long elapsedSeconds = 0;

        if (!TextUtils.isEmpty(d1) && !d1.contains("-") && !TextUtils.isEmpty(d2) && !d2.contains("-")) {
            try {
                //milliseconds
                long different = getTimestampReadable(d2) - getTimestampReadable(d1);

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;

                elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                elapsedMinutes = different / minutesInMilli;

                elapsedSeconds = different / secondsInMilli;


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return elapsedHours + " hours " + elapsedMinutes + " mins " + elapsedSeconds + " secs";
    }

    public static String getTimeDifference(Long d1, Long d2) {

        long elapsedHours = 0;
        long elapsedMinutes = 0;
        long elapsedSeconds = 0;

        try {
            //milliseconds
            long different = getTimestampFromTimeString(getTimeStringFull(d2)) - getTimestampFromTimeString(getTimeStringFull(d1));

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;

            elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            elapsedMinutes = different / minutesInMilli;

            elapsedSeconds = different / secondsInMilli;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return elapsedHours + " hours " + elapsedMinutes + " mins " + elapsedSeconds + " secs";
    }

    private static Date getPreviousWeekDate() {
        Calendar someDate = GregorianCalendar.getInstance();
        someDate.add(Calendar.DAY_OF_YEAR, -7);
        return someDate.getTime();
    }

    public static String getLastHourTime() {
        Calendar someDate = Calendar.getInstance();
        someDate.add(Calendar.HOUR_OF_DAY, -1);
        Date date = someDate.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_SIMPLE, Locale.ENGLISH);
        return dateFormat.format(date);
    }

    private static Date getDateFromString(String dtStart) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_FULL, Locale.ENGLISH);
        try {
            return format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getNextDayDate(String dtStart) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDateFromString(dtStart));
        c.add(Calendar.DATE, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_24_FULL, Locale.ENGLISH);
        return dateFormat.format(c.getTime());
    }

    public static List<String> getDatesBetween() {
        List<String> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getPreviousWeekDate());

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new Date());
        endCalendar.add(Calendar.DATE, 1);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_FULL, Locale.ENGLISH);
            String date = dateFormat.format(result);
            datesInRange.add(date);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    public static String getFormattedTime(String ourDate) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.ENGLISH);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            return dateFormatter.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ourDate;
    }

    public static String getFormattedTimeNoUTC(String ourDate) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            return dateFormatter.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ourDate;
    }

    public static TimeZone timeZone = TimeZone.getTimeZone("UTC");

    public static boolean isYesterday(Long date){
        Calendar now = Calendar.getInstance(timeZone);
        Calendar cdate = Calendar.getInstance(timeZone);
        cdate.setTimeInMillis(date);

        now.add(Calendar.DATE, -1);

        return (now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE));
    }


    public static boolean isToday(Long date){
        Calendar now = Calendar.getInstance(timeZone);
        Calendar cdate = Calendar.getInstance(timeZone);
        cdate.setTimeInMillis(date);

        return (now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE));
    }

    public static boolean isTomorrow(Long date){
        Calendar now = Calendar.getInstance(timeZone);
        Calendar cdate = Calendar.getInstance(timeZone);
        cdate.setTimeInMillis(date);

        now.add(Calendar.DATE, 1);

        return (now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE));
    }


}
