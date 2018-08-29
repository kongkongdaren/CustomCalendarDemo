package com.software.wen.customcalendardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity implements NewCalender.CalendarAdapter.NewCalendarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      NewCalender nCalender= (NewCalender) findViewById(R.id.nc_calender);
        nCalender.listener=this;
    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat df= SimpleDateFormat.getDateInstance();
        Toast.makeText(this, df.format(day), Toast.LENGTH_SHORT).show();
    }
}
