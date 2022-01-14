package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        Log.d("eventos", "calendar view"+ calendarView.getFirstDayOfWeek());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("eventos", "on day change!");

                if(month < 9){
                    GlobalVariables.fecha = String.valueOf(year) + "-0" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                }else {
                    GlobalVariables.fecha = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                }


                Intent intent = new Intent(CalendarActivity.this, PopUpEventos.class);
                startActivity(intent);
            }
        });


    }
}
