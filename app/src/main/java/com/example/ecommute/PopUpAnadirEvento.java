package com.example.ecommute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PopUpAnadirEvento extends Activity {

    EditText titulo;
    EditText localizacion;
    EditText descripcion;
    Button crearEvento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_anadir_evento);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        titulo = findViewById(R.id.titulo);
        localizacion = findViewById(R.id.localizacion_evento);
        descripcion = findViewById(R.id.descripcion_evento);
        crearEvento = findViewById(R.id.crear_evento);

        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titulo.getText().toString().isEmpty() && !localizacion.getText().toString().isEmpty()
                    && !descripcion.getText().toString().isEmpty()){

                    Calendar fecha = new GregorianCalendar();
                    fecha.set(Integer.parseInt(GlobalVariables.fecha.substring(0,4)),
                            Integer.parseInt(GlobalVariables.fecha.substring(5,7)) - 1,
                            Integer.parseInt(GlobalVariables.fecha.substring(8,10)));

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, localizacion.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fecha.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fecha.getTimeInMillis());

                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(PopUpAnadirEvento.this, "No se encuentra ninguna aplicación capaz de gestionar el evento", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PopUpAnadirEvento.this, "Porfavor introduzca todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
