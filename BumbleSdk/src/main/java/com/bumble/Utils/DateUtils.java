package com.bumble.Utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {


    private static DateUtils dateUtils;
    private static final DateFormat FORMAT_UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

    static {
        FORMAT_UTC.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    /**
     * Method to get instance of this class
     *
     * @return
     */
    public static DateUtils getInstance() {

        if (dateUtils == null)
            dateUtils = new DateUtils();

        return dateUtils;
    }

    /**
     * Method to get Todays date in a specified format
     *
     * @return
     */
    public static String getFormattedDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).format(date);
    }

    public static String getFormattedDate(Date date, String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH).format(date);
    }

    /**
     * Method to convert the UTC date time to Local
     *
     * @return
     */
    public String convertToLocal(String dateTime) {

        return convertToLocal(dateTime, STANDARD_DATE_FORMAT_TZ);
    }

    public String convertToLocal(String dateTime, String format) {
        return convertToLocal(dateTime, STANDARD_DATE_FORMAT_TZ, format);
    }

    public String convertToLocal(String dateTime, String format, String outputFormat) {

        //FuguLog.e("UTC Date", dateTime + "");

        DateFormat utcFormat = new SimpleDateFormat(format);
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date;
        try {
            date = utcFormat.parse(dateTime);
        } catch (ParseException e) {
            try {
                date = utcToLocalTZ(dateTime);
            } catch (Exception e1) {
                date = new Date();
                e1.printStackTrace();
            }
        }

        DateFormat pstFormat = new SimpleDateFormat(outputFormat);
        pstFormat.setTimeZone(TimeZone.getDefault());

        String result = pstFormat.format(date);

        //System.out.println(result);

        // FuguLog.e("Local Date", result + "");
        return result;
    }

    public static Date utcToLocalTZ(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2018-06-26T10:21:23.000Z
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2018-07-24T09:41:27.875+0000
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            utcTime = utcTime.replace("T", " ");
            utcTime = utcTime.split("\\.")[0];
            Date myDate = simpleDateFormat.parse(utcTime);
//            String localDate = sdf.format(myDate);
            return myDate;
        } catch (Exception e1) {
            e1.printStackTrace();
            return new Date();
        }
    }

    static String STANDARD_DATE_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String STANDARD_DATE_ONLY_FORMAT = "yyyy-MM-dd";

    /**
     * Method to convert Local date time to UTC
     *
     * @return
     */
    public String convertToUTC(String dateTime) {

        // FuguLog.e("Local Date", dateTime + "");

        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_TZ);
        Date localTime = null;
        try {
            localTime = sdf.parse(dateTime);
        } catch (ParseException e) {
            localTime = new Date();
            e.printStackTrace();
        }

        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String result = sdf.format(localTime);

        //FuguLog.e("UTC Date", result + "");
        return result;
    }


    public static String getTime(String dateTime) {
        try {

            String time = dateTime.split("T")[1].replace("Z", "");

            String hour = time.split(":")[0];
            String min = time.split(":")[1];

            String amOrpm = Integer.parseInt(hour) >= 12 ? "PM" : "AM";
            String newHour = "";

            if (Integer.parseInt(hour) == 0) {
                newHour = "12";
            } else if (Integer.parseInt(hour) <= 12) {
                newHour = hour;
            } else {
                newHour = "" + (Integer.parseInt(hour) - 12);
            }

            String finalTime = "" + newHour + ":" + min + " " + amOrpm;

            SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_TZ);
            Date tempDate = sdf.parse(dateTime);

            return finalTime;
        } catch (Exception e) {
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String getDate(String dateTime) {
        try {

            String finalDate = "";

            if (!dateTime.isEmpty()) {
                String date = dateTime.split("T")[0];

                String year = date.split("-")[0];
                String month = date.split("-")[1];
                String day = date.split("-")[2];

                SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_ONLY_FORMAT);
                Date tempDate = sdf.parse(dateTime);

                if (android.text.format.DateUtils.isToday(tempDate.getTime())) {
                    return "Today";
                } else if (String.valueOf(android.text.format.DateUtils.getRelativeTimeSpanString(tempDate.getTime(),
                        sdf.parse(getFormattedDate(new Date())).getTime(),
                        android.text.format.DateUtils.FORMAT_NUMERIC_DATE
                )).equalsIgnoreCase("yesterday")) {
                    return "Yesterday";
                }

                if ("01".equalsIgnoreCase(month)) {
                    month = "Jan";
                } else if ("02".equalsIgnoreCase(month)) {
                    month = "Feb";
                } else if ("03".equalsIgnoreCase(month)) {
                    month = "Mar";
                } else if ("04".equalsIgnoreCase(month)) {
                    month = "Apr";
                } else if ("05".equalsIgnoreCase(month)) {
                    month = "May";
                } else if ("06".equalsIgnoreCase(month)) {
                    month = "Jun";
                } else if ("07".equalsIgnoreCase(month)) {
                    month = "Jul";
                } else if ("08".equalsIgnoreCase(month)) {
                    month = "Aug";
                } else if ("09".equalsIgnoreCase(month)) {
                    month = "Sept";
                } else if ("10".equalsIgnoreCase(month)) {
                    month = "Oct";
                } else if ("11".equalsIgnoreCase(month)) {
                    month = "Nov";
                } else if ("12".equalsIgnoreCase(month)) {
                    month = "Dec";
                }

                finalDate = day + " " + month + " " + year;
            }

            return finalDate;
        } catch (Exception e) {
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String getRelativeDate(String dateTime, boolean isTime) {

        String finalDate = "";


        try {
            SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_TZ);
            Date tempDate = sdf.parse(dateTime);

            if (android.text.format.DateUtils.isToday(tempDate.getTime()) && isTime) {
                String time = dateTime.split("T")[1].replace("Z", "");

                String hour = time.split(":")[0];
                String min = time.split(":")[1];

                String amOrpm = Integer.parseInt(hour) >= 12 ? "PM" : "AM";
                String newHour = "";

                if (Integer.parseInt(hour) == 0) {
                    newHour = "12";
                } else if (Integer.parseInt(hour) <= 12) {
                    newHour = hour;
                } else {
                    newHour = "" + (Integer.parseInt(hour) - 12);
                }

                String finalTime = "" + newHour + ":" + min + " " + amOrpm;

                finalDate = finalTime;
            } else {
                finalDate = String.valueOf(android.text.format.DateUtils.getRelativeTimeSpanString(tempDate.getTime(),
                        sdf.parse(getFormattedDate(new Date())).getTime(),
                        android.text.format.DateUtils.DAY_IN_MILLIS,
                        android.text.format.DateUtils.FORMAT_ABBREV_MONTH |
                                android.text.format.DateUtils.FORMAT_SHOW_YEAR
                ));
            }

            // finalDate = dateTime.split("T")[0];
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalDate;
    }

    public static int getTimeInMinutes(String dateTime) {
        try {
            String time = dateTime.split("T")[1].replace("Z", "");
            int hour = Integer.parseInt(time.split(":")[0]);
            int min = Integer.parseInt(time.split(":")[1]);
            return (hour) * 60 + min;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Date getUtcDateWithTimeZone(String utcDate) {

        try {
            BumbleLog.v("ttttttttttt", FORMAT_UTC.parse(utcDate).toString());
            return FORMAT_UTC.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Calendar feedPostedCal = Calendar.getInstance();
    private static Calendar currentDateCal = Calendar.getInstance();

    public static String getTimeToDisplay(String createdAtTime) {
        feedPostedCal.setTime(getUtcDateWithTimeZone(createdAtTime));
        currentDateCal.setTimeInMillis(System.currentTimeMillis());
        int yearPosted, yearCurrent, currentMonth, postedMonth, currentDate, postedDate, postedHour, currentHour, postedMin, currentMin;
        yearPosted = feedPostedCal.get(Calendar.YEAR);
        yearCurrent = currentDateCal.get(Calendar.YEAR);
        currentMonth = currentDateCal.get(Calendar.MONTH);
        postedMonth = feedPostedCal.get(Calendar.MONTH);
        currentDate = currentDateCal.get(Calendar.DATE);
        postedDate = feedPostedCal.get(Calendar.DATE);

        int diff = yearCurrent - yearPosted;
        if (postedMonth > currentMonth || (postedMonth == currentMonth && postedDate > currentDate)) {
            diff--;
        }

        if (diff > 0) {
            if (diff == 1)
                return diff + " year ago";
            else
                return diff + " years ago";
        }

        if (yearPosted != yearCurrent) {
            diff = currentMonth + (11 - postedMonth);
            if (postedDate > currentDate) diff--;
        } else {

            diff = currentMonth - postedMonth;
            if (currentMonth != postedMonth && postedDate > currentDate)
                diff--;

        }

        if (diff > 0) {
            if (diff == 1)
                return diff + " month ago";
            else
                return diff + " months ago";
        }


        long diffT = currentDateCal.getTimeInMillis() - feedPostedCal.getTimeInMillis();
        long diffC = diffT / (24 * 60 * 60 * 1000 * 7);
        if (diffC >= 1) return diffC + (diffC == 1 ? " week ago" : " weeks ago");
        diffC = diffT / (24 * 60 * 60 * 1000);
        if (diffC >= 1) return diffC + (diffC == 1 ? " day ago" : " days ago");
        diffC = diffT / (60 * 60 * 1000) % 24;
        if (diffC >= 1) return diffC + (diffC == 1 ? " hour ago" : " hours ago");
        diffC = diffT / (60 * 1000) % 60;
        if (diffC >= 1) return diffC + (diffC == 1 ? " minute ago" : " minutes ago");
        diffC = diffT / 1000 % 60;

        return " Just now";
           /* if (diffC >= 0)
                return diffC + "s";
            else
                return 0 + "s";*/


    }


    public static String getDateTimeToShow(String createdAtTime) {
        feedPostedCal.setTime(getUtcDateWithTimeZone(createdAtTime));
        currentDateCal.setTimeInMillis(System.currentTimeMillis());
        int yearPosted, yearCurrent, currentMonth, postedMonth, currentDate, postedDate, postedHour, currentHour, postedMin, currentMin;
        yearPosted = feedPostedCal.get(Calendar.YEAR);
        yearCurrent = currentDateCal.get(Calendar.YEAR);
        currentMonth = currentDateCal.get(Calendar.MONTH);
        postedMonth = feedPostedCal.get(Calendar.MONTH);
        currentDate = currentDateCal.get(Calendar.DATE);
        postedDate = feedPostedCal.get(Calendar.DATE);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(feedPostedCal.getTime());
        String year_name= String.valueOf(yearPosted).substring(2);

        int diff = yearCurrent - yearPosted;
        if (postedMonth > currentMonth || (postedMonth == currentMonth && postedDate > currentDate)) {
            diff--;
        }

        if (diff > 0) {
            if (diff == 1)
                return  " " +postedDate + " " + month_name + " " + year_name;
            else
                return  " " +postedDate + " " + month_name + " " + year_name;
        }

        if (yearPosted != yearCurrent) {
            diff = currentMonth + (11 - postedMonth);
            if (postedDate > currentDate) diff--;
        } else {

            diff = currentMonth - postedMonth;
            if (currentMonth != postedMonth && postedDate > currentDate)
                diff--;

        }

        if (diff > 0) {
            if (diff == 1)
                return " " + postedDate + " " + month_name + " " + year_name;
            else
                return " " + postedDate + " " + month_name + " " + year_name;
        }


        long diffT = currentDateCal.getTimeInMillis() - feedPostedCal.getTimeInMillis();
        long diffC = diffT / (24 * 60 * 60 * 1000 * 7);
        if (diffC >= 1)
            return (diffC == 1 ? " " + postedDate + " " + month_name + " " + year_name : " " + postedDate + " " + month_name + " " + year_name);
        diffC = diffT / (24 * 60 * 60 * 1000);
        if (diffC >= 1)
            return (diffC == 1 ? " " + postedDate + " " + month_name + " " + year_name : " " + postedDate + " " + month_name + " " + year_name);
        diffC = diffT / (60 * 60 * 1000) % 24;
        if (diffC >= 1) return diffC + (diffC == 1 ? " hr ago" : " hrs ago");
        diffC = diffT / (60 * 1000) % 60;
        if (diffC >= 1) return diffC + (diffC == 1 ? " min ago" : " mins ago");
        diffC = diffT / 1000 % 60;

        return " Just now";
           /* if (diffC >= 0)
                return diffC + "s";
            else
                return 0 + "s";*/
    }

    public static long getTimeInLong(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_TZ);
        try {
            Date lastMessage = sdf.parse(dateString);
            return lastMessage.getTime();
        } catch (Exception e) {
            return -100;
        }
    }

    private final static long MESSAGE_EXPIRED_TIME = 10 * 60 * 1000; //3 sec is 3 * 1000
    public static boolean getTimeDiff(String dateTime) {
        feedPostedCal.setTime(getUtcDateWithTimeZone(dateTime));
        currentDateCal.setTimeInMillis(System.currentTimeMillis());

        long diffT = currentDateCal.getTimeInMillis() - feedPostedCal.getTimeInMillis();
        if(diffT >= MESSAGE_EXPIRED_TIME) {
            return true;
        }
        return false;
    }
}
