package com.tenutohahwi.hahwiportfolio.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author shyknight_m@naver.com
 * @class SAT
 * @date 2016-05-24
 * @see
 */
public class DateUtil
{

    //날짜 앞에 0이 없을경우 붙임. 2017-09-15
    public static String getMonth( String dateString )
    {
        String[] dateArray = dateString.split("-");
        return dateArray[1];
    }

    //날짜 앞에 0이 없을경우 붙임.
    public static String getDay( String dateString )
    {
        String[] dateArray = dateString.split("-");
        if (dateArray[2].startsWith("0"))
        {
            dateArray[2] = dateArray[2].substring(dateArray[2].length() - 1, dateArray[2].length());
        }
        return dateArray[2];
    }

    /**
     * Created by hahwi on 2017. 05. 11..
     * "2017-05-11", "2017-04-28" 형식의 String날짜를 비교하는 함수
     */
    public static int compareDateToString( String compareDate1, String compareDate2 )
    {
        String[] compareDate1Array = compareDate1.split("-");
        String[] compareDate2Array = compareDate2.split("-");

        String compare1date = String.format("%s%s%s", compareDate1Array[0], compareDate1Array[1], compareDate1Array[2]);
        String compare2date = String.format("%s%s%s", compareDate2Array[0], compareDate2Array[1], compareDate2Array[2]);

        return compare1date.compareTo(compare2date); //1번보다 2번이 작으면 -1, 같으면 0, 크면 1
    }

    //날짜 기간 차이
    public static long diffOfDate( String begin, String end ) throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date beginDate = formatter.parse(begin);
        Date endDate   = formatter.parse(end);

        long diff     = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }


    private static class TIME_MAXIMUM
    {
        public static final int SEC   = 60;
        public static final int MIN   = 60;
        public static final int HOUR  = 24;
        public static final int DAY   = 30;
        public static final int MONTH = 12;
    }

    public static String formatTimeString( long regTime )
    {
        long   curTime  = System.currentTimeMillis();
        long   diffTime = (curTime - regTime) / 1000;
        String msg      = null;
        if (diffTime < TIME_MAXIMUM.SEC)
        {
            msg = "방금 전";
        }
        else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN)
        {
            msg = diffTime + "분 전";
        }
        else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR)
        {
            msg = (diffTime) + "시간 전";
        }
        else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY)
        {
            msg = (diffTime) + "일 전";
        }
        else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH)
        {
            msg = (diffTime) + "달 전";
        }
        else
        {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

    public static String formatTimeString( String dateTime )
    {
        try
        {
            SimpleDateFormat dt            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            Date             date          = dt.parse(dateTime);
            long             translongTime = date.getTime();
            return formatTimeString(translongTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return dateTime;
        }
    }

    //오늘이면 오전 10:00  a hh:mm  아니면 2018-07-05 10:00
    public static String formatTimeA( String dateTime )
    {
        try
        {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            Date             currentTime       = new Date();
            String           sCurrentTime      = mSimpleDateFormat.format(currentTime); //현재시간 (String)

            Date pDate       = mSimpleDateFormat.parse(dateTime);
            Date currentDate = mSimpleDateFormat.parse(sCurrentTime);
            int  compareDay  = currentDate.compareTo(pDate); // 날짜비교 1번보다 2번이 작으면 -1, 같으면 0, 크면 1

            if (0 == compareDay)
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                Date             date       = dateFormat.parse(dateTime);

                SimpleDateFormat dt         = new SimpleDateFormat("a hh:mm", Locale.KOREA);
                String           returnDate = dt.format(date);
                return returnDate;
            }
            else
            {
                return dateTime;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return dateTime;
        }
    }


    // 두날짜의 차이 구하기
    public void doDiffOfDate()
    {
        String start = "2015-04-01";
        String end   = "2015-05-05";

        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date             beginDate = formatter.parse(start);
            Date             endDate   = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff     = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.println("날짜차이=" + diffDays);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }


    public static String getCurSaturday()
    {

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        //c.add(c.DATE,6);

        return formatter.format(c.getTime());

    }


    //현재 날짜 일요일
    public static String getCurSunday()
    {

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        c.add(c.DATE, 7);

        return formatter.format(c.getTime());

    }

    public static String getToday()
    {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String           sCurrentDate      = mSimpleDateFormat.format(new Date());

        return sCurrentDate;
    }

    public static String getLottoRound()
    {
        String round  = "823"; //2018-09-08
        int    iRound = 823;
        try
        {
            //이번주 토요일이랑 비교
            long differ = diffOfDate("2018-09-08", getToday()); //오늘이랑 9월 8일이랑 비교해서 그 값을 7로 나눈 몫을 회차에 더한다. 그러나 나머지가 0이라면 토요일이니까 밤 8시를 비교해서 넘었으면 +1 아니면 -1한다.
            if (0 == (differ / 7))
            {
                return iRound + "";
            }
            else
            {
                if (0 == differ % 7)
                {
                    TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
                    Calendar cal = Calendar.getInstance(jst);
                    if (21 >= cal.get(Calendar.HOUR_OF_DAY))
                    {
                        iRound = iRound + ((int) (differ / 7));
                    }
                    else
                    {
                        iRound = iRound + ((int) (differ / 7)) - 1;
                    }
                    return iRound + "";
                }
                else
                {
                    iRound = iRound + ((int) (differ / 7));
                    return iRound + "";
                }

            }
            //Log.e("m-Log", "9월 8일과, 이번주 토요일 " + getCurSaturday() + " 차이는? " + differ + " 7로 나눈 몫은? " + differ / 7);
        }
        catch (Exception e)
        {
            return round;
        }
    }
}