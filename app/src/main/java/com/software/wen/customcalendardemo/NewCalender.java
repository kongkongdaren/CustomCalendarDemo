package com.software.wen.customcalendardemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wen on 2018/8/24.
 */

public class NewCalender extends LinearLayout {
    private ImageView mIvPrev,mIvNext;
    private TextView mTvDate;
    private GridView mGridView;
    private  String displayFormat;
    private Calendar  calendar=Calendar.getInstance();
    public CalendarAdapter.NewCalendarListener listener;
    public NewCalender(Context context) {
        super(context);
    }

    public NewCalender(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public NewCalender(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }
    private  void initControl(Context context, AttributeSet attrs){
        bindControl(context);
        bindControlEvent();
        TypedArray ta=getContext().obtainStyledAttributes(attrs,R.styleable.NewCalender);
        try {
            String format = ta.getString(R.styleable.NewCalender_dateFormat);
           displayFormat=format;
            if (displayFormat==null){
                displayFormat="MMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        renderCalendar();
    }


    private void bindControlEvent() {
        mIvPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });
        mIvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }


    private void bindControl(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_view, this);
        mIvPrev= (ImageView) view.findViewById(R.id.iv_pre);
        mIvNext= (ImageView) view.findViewById(R.id.iv_next);
        mTvDate= (TextView) view.findViewById(R.id.tv_date);
        mGridView= (GridView) view.findViewById(R.id.gv_calendar_grid);
    }
    //渲染日历视图
    private void renderCalendar() {
        SimpleDateFormat sdf=new SimpleDateFormat(displayFormat);
        mTvDate.setText(sdf.format(calendar.getTime()));
        ArrayList<Date> cells=new ArrayList<>();
        Calendar mCalendar= (Calendar) calendar.clone();
        mCalendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        mCalendar.add(Calendar.DAY_OF_MONTH,-prevDays);
        int maxCellCount=6*7;
        while (cells.size()<maxCellCount){
            cells.add(mCalendar.getTime());
            mCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        mGridView.setAdapter(new CalendarAdapter(getContext(),cells));
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener==null){
                    return false;
                }else{
                    listener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                 }

            }
        });
    }

    public static class CalendarAdapter extends ArrayAdapter<Date>{

       LayoutInflater inflater;
        public CalendarAdapter(Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day,days);
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);
            if (convertView==null){
               convertView= inflater.inflate(R.layout.calendar_text_day,null);
            }
            int day=date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));
            Date now=new Date();
           Boolean isTheSameMonth=false;
            if (date.getMonth()==now.getMonth()){
                isTheSameMonth=true;
            }
            if (isTheSameMonth){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else{
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }

            if (now.getDate()==date.getDate()&&now.getMonth()==date.getMonth()
                    &&now.getYear()==date.getYear()){
                ((TextView)convertView).setTextColor(Color.parseColor("#ff0000"));
                ((CalendarItemTextView)convertView).isToday=true;
            }
            return convertView;
        }
        public interface  NewCalendarListener{
            void  onItemLongPress(Date day);
        }
    }
}
