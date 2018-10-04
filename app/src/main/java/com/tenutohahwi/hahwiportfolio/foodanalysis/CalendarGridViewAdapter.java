package com.tenutohahwi.hahwiportfolio.foodanalysis;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.realmvo.DBHelper;
import com.tenutohahwi.hahwiportfolio.realmvo.EatFood;
import com.tenutohahwi.hahwiportfolio.util.Application;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-25
 * @see
 */
public class CalendarGridViewAdapter extends BaseAdapter
{
    Context ctx;
    private Application app;
    int layout;
    //private List<LearningLog> learningLogList;
    private ArrayList<Date> days;
    private int mItemSelected = -1;
    boolean isCurrentMonth = true; //현재 달인가용?
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    private List<EatFood> eatFoodList;
    //    private List<LearningLog> coachLog;
    //    private List<CoachComment> coachCommentList;
    private String selectYearMonth = "";

    private Map<String, EatFood> calendarEatFoodMap = new HashMap<>();
    //    private Map<String , LearningLog> calendarLearningLogMap = new HashMap<>();
    //    private Map<String , CoachComment> calendarCoachCommentMap = new HashMap<>();

    public CalendarGridViewAdapter( Application app, Context ctx, int layout )
    {
        this.ctx = ctx;
        this.layout = layout;
        this.app = app;
    }

    public void setData( ArrayList<Date> days )
    {
        this.days = days;
    }

    public void setItemSelected( int position )
    {
        mItemSelected = position;
    }

    public int getItemSelected()
    {
        return mItemSelected;
    }

    public void setIsCurrentMonth( boolean isCurrentMonth )
    {
        this.isCurrentMonth = isCurrentMonth;
    }

    public void setYearMonth( String yearMonth )
    { //2017-10
        this.selectYearMonth = yearMonth;
        //String whereArags[] = {yearMonth+"-01" , yearMonth+"-31" , "Y"};
        eatFoodList = DBHelper.getInstance().getEatFoodMonth(yearMonth);

        for (int i = 0; i < eatFoodList.size(); i++)
        {
            EatFood  eatFood         = eatFoodList.get(i);
            String[] eatFoodDateTime = eatFood.getDatetime().split(" ");

            EatFood mapEatFood = calendarEatFoodMap.get(eatFoodDateTime[0]);
            if (null == mapEatFood)
            {
                mapEatFood = eatFood;
            }
            calendarEatFoodMap.put(eatFoodDateTime[0], mapEatFood);
        }
    }

    @Override
    public int getCount()
    {
        if (null == days) return 0;
        return days.size();
    }

    @Override
    public Object getItem( int position )
    {
        if (days == null || days.get(position) == null)
        {
            return null;
        }
        return days.get(position);
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }


    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        ViewHolder holder = null;
        //Log.e("m-Log", "getView : "+convertView);
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.llAll = (LinearLayout) convertView.findViewById(R.id.llAll);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.ivStamp = (ImageView) convertView.findViewById(R.id.ivStamp);
            holder.ivLearningLog = (ImageView) convertView.findViewById(R.id.ivLearningLog);
            holder.ivCoach = (ImageView) convertView.findViewById(R.id.ivCoach);
            convertView.setTag(holder);
            //Log.e("m-Log", "convertview null");
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            //Log.e("m-Log", "convertview not null");
        }

        if (null != days)
        {
            // today
            Date date = days.get(position);
            updateCalendarItemView(holder, date, position);


            // set text
            holder.tvDate.setText(String.valueOf(date.getDate()));
            holder.ivStamp.setVisibility(View.GONE);
            holder.ivLearningLog.setVisibility(View.GONE);
            holder.ivCoach.setVisibility(View.GONE);

            String sCurrentDate = mSimpleDateFormat.format(date);


            EatFood eatFood = calendarEatFoodMap.get(sCurrentDate);
            if (null != eatFood)
            {
                holder.ivLearningLog.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.ivLearningLog.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    public void updateCalendarItemView( ViewHolder holder, Date date, int position )
    {
        int day   = date.getDate();
        int month = date.getMonth();
        int year  = date.getYear();

        Date today = new Date();

        if (month != today.getMonth() || year != today.getYear())
        {
            // if this day is outside current month, grey it out
            holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.greyed_out));
            if (isCurrentMonth)
            {
                holder.tvDate.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.tvDate.setVisibility(View.VISIBLE);
            }
        }
        else if (day == today.getDate())
        {
            holder.llAll.setBackground(ctx.getResources().getDrawable(R.color.bg_calendar_toady));
            // if it is today, set it to blue/bold
            holder.tvDate.setTypeface(null, Typeface.BOLD);
            holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.text_six));
        }

        //Log.e("m-Log", "mItemSelected :  "+mItemSelected);
        if (mItemSelected == position)
        { //선택된 날짜인경우
            holder.llAll.setBackground(ctx.getResources().getDrawable(R.drawable.button_line_calendar));
            holder.tvDate.setTypeface(null, Typeface.BOLD);
            holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.text_six));
        }
        else
        {
            holder.llAll.setBackground(null);
            holder.tvDate.setTypeface(null, Typeface.NORMAL);
            //holder.llAll.setBackground(ctx.getResources().getDrawable(R.color.bg_calendar_cell));
            holder.llAll.setBackground(ctx.getResources().getDrawable(R.color.colorWhite));

            if (0 == position)
            { //일요일 빨간색 색 처리
                holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.color_red));
            }
            else if (position % 7 == 0)
            {
                holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.color_red));
            }
            else
            {
                holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.text_six));
            }

            if (month != today.getMonth() || year != today.getYear())
            {//저번달, 다음달 날짜일경우
                // if this day is outside current month, grey it out
                holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.greyed_out));
                if (isCurrentMonth)
                {
                    holder.tvDate.setVisibility(View.INVISIBLE);
                }
                else
                {
                    holder.tvDate.setVisibility(View.VISIBLE);
                }

            }
            else if (day == today.getDate())
            { //오늘일경우
                holder.llAll.setBackground(ctx.getResources().getDrawable(R.color.bg_calendar_toady));
                // if it is today, set it to blue/bold
                holder.tvDate.setTypeface(null, Typeface.BOLD);
                holder.tvDate.setTextColor(ctx.getResources().getColor(R.color.text_six));
            }
        }
    }

    public class ViewHolder
    {
        public LinearLayout llAll;
        public TextView     tvDate;
        public ImageView    ivStamp;
        public ImageView    ivLearningLog;
        public ImageView    ivCoach;
    }
}
