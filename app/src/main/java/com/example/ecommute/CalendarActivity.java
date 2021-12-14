package com.example.ecommute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.ui.perfil.PerfilFragment;

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
                //PopUpEventos popUp = new PopUpEventos(year, month, dayOfMonth);
                //popUp.onCreate(savedInstanceState);
                Intent intent = new Intent(CalendarActivity.this, PopUpEventos.class);
                startActivity(intent);
            }
        });


    }
}
