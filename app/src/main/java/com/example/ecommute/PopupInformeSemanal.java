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

    int datos = 0; //0 =puntos; 1=co2; 2=distancia
    JSONObject respostaRequestProgress;
    JSONObject resportaRequestWeek;
    String month;
    BarChart barchart;
    ArrayList<BarEntry> entries = null;
    BarDataSet barDataSet = null;
    BarData barData = null;
    int tiempo = 2; // 0 = semana; 1 = mes; 2 = alltime

    public PopupInformeSemanal() throws IOException, JSONException {
        respostaRequestProgress = respostaRequestProgress();
        resportaRequestWeek = respostaRequestWeek();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        month = date.toString();
        Log.d("Gráficospopup",dateFormat.format(date)); //se hace bien!!

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

        barchart.setFitBars(true);
        barchart.animateY(2000);
        barchart.getLegend().setEnabled(false);
        barchart.getXAxis().setEnabled(false);
        barchart.getAxisLeft().setAxisMinimum(0.0f);
        barchart.getAxisRight().setAxisMinimum(0.0f);
        barchart.getAxisRight().setDrawLabels(false);

        setGrafico();


        Button cambiar = popupView.findViewById(R.id.cambiardatos);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos == 0){ //puntos
                    datos++;
                    cambiar.setText("distancia");
                }else if (datos ==1){ //co2
                    datos++;
                    cambiar.setText("puntos");
                }else if (datos == 2){ //distancia
                    datos = 0;
                    cambiar.setText("co2 ahorrado");
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
            if(tiempo == 0) entries = alltimePoints();
            else if (tiempo == 1) entries = alltimePoints();
            else entries = alltimePoints();
            barchart.getDescription().setText("Puntos conseguidos");
        }else if (datos == 1){
            barchart.getDescription().setText("Co2 ahorrado");
        }else{
            barchart.getDescription().setText("Distancia recorrida");
        }
        barDataSet = new BarDataSet(entries, "datos");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barData = new BarData(barDataSet);

        barchart.setData(barData);

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


}
