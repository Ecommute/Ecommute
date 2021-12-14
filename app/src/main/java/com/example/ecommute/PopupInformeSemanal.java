package com.example.ecommute;

import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PopupInformeSemanal {

    int datos = 1; //0 =puntos; 1=co2; 2=distancia
    JSONObject respostaRequestProgress;
    JSONObject respostaRequestWeek;
    int month;
    BarChart barchart;
    TextView resumen;
    ArrayList<BarEntry> entries = null;
    BarDataSet barDataSet = null;
    BarData barData = null;
    int tiempo = 1; // 0 = semana; 1 = mes; 2 = alltime

    public PopupInformeSemanal() throws IOException, JSONException {
        respostaRequestProgress = respostaRequestProgress();
        respostaRequestWeek = respostaRequestWeek();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        month = Integer.valueOf(dateFormat.format(date));
        Log.d("Gráficospopup", String.valueOf(month));

    }
    public void showPopupWindow(final View view) throws IOException, JSONException {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_informe_semanal, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(null);

        //respostaRequest = respostaRequest();

        barchart = (BarChart) popupView.findViewById(R.id.graph);
        resumen = popupView.findViewById(R.id.resumengrafico);

        barchart.setFitBars(true);
        barchart.animateY(2000);
        barchart.getLegend().setEnabled(false);
        barchart.getXAxis().setEnabled(false);
        barchart.getAxisLeft().setAxisMinimum(0.0f);
        barchart.getAxisRight().setAxisMinimum(0.0f);
        barchart.getAxisRight().setDrawLabels(false);

        setGrafico();


        Button cambiarDatos = popupView.findViewById(R.id.cambiardatos);
        cambiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos == 0){ //puntos
                    datos++;
                    cambiarDatos.setText("ver distancia");
                }else if (datos ==1){ //co2
                    datos++;
                    cambiarDatos.setText("ver puntos");
                }else if (datos == 2){ //distancia
                    datos = 0;
                    cambiarDatos.setText("ver co2 ahorrado");
                }
                try {
                    setGrafico();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button cambiarTiempo = popupView.findViewById(R.id.cambiartiempo);
        cambiarTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 0 = semana; 1 = mes; 2 = alltime
                if (tiempo == 0){
                    tiempo++;
                    cambiarTiempo.setText("ver Año");
                }else if (tiempo ==1){
                    tiempo++;
                    cambiarTiempo.setText("ver semana");
                }else if (tiempo == 2){
                    tiempo = 0;
                    cambiarTiempo.setText("ver mes");
                }
                try {
                    setGrafico();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Button cerrar = popupView.findViewById(R.id.cerrar_popup);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //As an example, display the message
                //Toast.makeText(view.getContext(), "Wow, popup window closed", Toast.LENGTH_SHORT).show();

            }
        });





    }

    public void setGrafico() throws IOException, JSONException {
        //0 =puntos; 1=co2; 2=distancia
        if(datos == 0){
            if(tiempo == 0) {
                entries = semPoints();
                resumen.setText("Puntos conseguidos esta semana");
            }
            else if (tiempo == 1){
                entries = monthPoints();
                resumen.setText("Puntos conseguidos esta mes");
            }
            else{
                entries = alltimePoints();
                resumen.setText("Puntos conseguidos este año");
            }
            barchart.getDescription().setText("Puntos conseguidos");
        }else if (datos == 1){
            if(tiempo == 0){
                entries = semCo2();
                resumen.setText("Co2 ahorrado esta semana");
            }
            else if (tiempo == 1) {
                entries = monthCo2();
                resumen.setText("Co2 ahorrado este mes");
            }
            else {
                entries = alltimeCo2();
                resumen.setText("Co2 ahorrado este año");
            }
            barchart.getDescription().setText("Co2 ahorrado");
        }else{
            if(tiempo == 0) {
                entries = semDist();
                resumen.setText("Distancia recorrida esta semana");
            }
            else if (tiempo == 1) {
                entries = monthDist();
                resumen.setText("Distancia recorrida este mes");
            }
            else {
                entries = alltimeDistance();
                resumen.setText("Distancia recorrida este año");
            }
            barchart.getDescription().setText("Distancia recorrida");
        }
        barDataSet = new BarDataSet(entries, "datos");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barData = new BarData(barDataSet);

        barchart.setData(barData);
        barchart.invalidate();
    }

    public JSONObject respostaRequestProgress() throws JSONException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/progress?"+urlParameters)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());

        return respuesta2;
    }

    public JSONObject respostaRequestWeek() throws JSONException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/week?"+urlParameters)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());

        return respuesta2;
    }

    public ArrayList<BarEntry> alltimeCo2() throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");
        Log.d("numeroDias", String.valueOf(ja.length()));

        JSONArray dias = ja.names();

        for (int i = 0; i < ja.length(); i++){
            Object dia = dias.get(i);
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("savedco2");
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }

        return entry;
    }

    public ArrayList<BarEntry> alltimePoints() throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");

        JSONArray dias = ja.names();

        for (int i = 0; i < ja.length(); i++){
            Object dia = dias.get(i);
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("points");
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }

        return entry;
    }

    public ArrayList<BarEntry> alltimeDistance() throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");

        JSONArray dias = ja.names();

        for (int i = 0; i < ja.length(); i++){
            Object dia = dias.get(i);
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("distance");
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }

        return entry;
    }

    public ArrayList<BarEntry>  semPoints() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONArray ja = respostaRequestWeek.getJSONArray("days");

        for (int i = 0; i < ja.length(); i++){
            String co2 = ja.getJSONObject(i).getJSONObject("totalDay").getString("points");
            entry.add(new BarEntry(i, Float.parseFloat(co2)));
        }

        return entry;
    }

    public ArrayList<BarEntry>  semDist() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONArray ja = respostaRequestWeek.getJSONArray("days");

        for (int i = 0; i < ja.length(); i++){
            String co2 = ja.getJSONObject(i).getJSONObject("totalDay").getString("distance");
            entry.add(new BarEntry(i, Float.parseFloat(co2)));
        }

        return entry;
    }

    public ArrayList<BarEntry>  semCo2() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONArray ja = respostaRequestWeek.getJSONArray("days");

        for (int i = 0; i < ja.length(); i++){
            String co2 = ja.getJSONObject(i).getJSONObject("totalDay").getString("co2");
            entry.add(new BarEntry(i, Float.parseFloat(co2)));
        }

        return entry;
    }

    public ArrayList<BarEntry> monthCo2() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");

        JSONArray dias = ja.names();

        for (int i = 0; i < 30; i++){
            Object dia = dias.get(i+(month-1)*30);
            Log.d("monthPopup", dia.toString());
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("savedco2");
                Log.d("monthPopup", co2);
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }
        return entry;
    }

    public ArrayList<BarEntry> monthDist() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");

        JSONArray dias = ja.names();

        for (int i = 0; i < 30; i++){
            Object dia = dias.get(i+(month-1)*30);
            Log.d("monthPopup", dia.toString());
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("distance");
                Log.d("monthPopup", co2);
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }
        return entry;
    }

    public ArrayList<BarEntry> monthPoints() throws JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respostaRequestProgress.getJSONObject("days");

        JSONArray dias = ja.names();

        for (int i = 0; i < 30; i++){
            Object dia = dias.get(i+(month-1)*30);
            Log.d("monthPopup", dia.toString());
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("distance");
                Log.d("monthPopup", co2);
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }
        return entry;
    }

}
