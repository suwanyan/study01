package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author suwanyan
 * @date 2021/1/7 15:06
 */
public class TimeUtil {
    /**
     * 202001010030 -> 2020-01-01 08:30:00
     * @param date
     * @return
     */
    public static Date strToDate (String date) {
        Date rtn = null;
        try{
            Date date1 = null;
            StringBuilder sb = new StringBuilder(date);
            sb.insert(4, "-");
            sb.insert(7, "-");
            sb.insert(10, " ");
            sb.insert(13, ":");
            System.out.println(sb.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date1 = sdf.parse(sb.toString());
            //时区+8
            long rightTime = (long) (date1.getTime() + 8 * 60 * 60 * 1000);
            rtn = sdf.parse(sdf.format(rightTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public static Date strToDate1 (String date) {
        Date date1 = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat (format);
        String strDate = sdf.format(date);
        return strDate;
    }

    public static void main(String[] args) {
        System.out.println(strToDate("202001010030").toString());
    }
}
