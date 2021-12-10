package com.example.ecommute;

import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopupInformeSemanal {

    int datos = 0; //0 =puntos; 1=co2; 2=distancia
    JSONObject respostaRequest ;

    public PopupInformeSemanal() throws IOException, JSONException {
        respostaRequest = respostaRequest();
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

        BarChart barchart = (BarChart) popupView.findViewById(R.id.graph);
        ArrayList<BarEntry> entries;
        BarDataSet barDataSet = null;

        try {
            entries = entryPoints(respostaRequest);
            barDataSet = new BarDataSet(entries, "puntos");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(barDataSet);

        barchart.setData(barData);
        barchart.setFitBars(true);
        barchart.animateY(2000);
        barchart.getDescription().setText("Puntos conseguidos");
        barchart.getLegend().setEnabled(false);
        barchart.getXAxis().setEnabled(false);
        barchart.getAxisLeft().setAxisMinimum(0.0f);
        barchart.getAxisRight().setAxisMinimum(0.0f);
        barchart.getAxisRight().setDrawLabels(false);

        Button cambiar = popupView.findViewById(R.id.cambiardatos);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos == 0){ //puntos
                    datos++;

                    cambiar.setText("Distancia");
                    ArrayList<BarEntry> entries = null;
                    BarDataSet barDataSet = null;
                    try {
                        entries = entryCo2(respostaRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    barDataSet = new BarDataSet(entries, "co2");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData barData = new BarData(barDataSet);
                    barchart.setData(barData);
                    barchart.getDescription().setText("CO2 consumido");
                    barchart.invalidate();

                }else if (datos ==1){ //co2
                    datos++;

                    cambiar.setText("Puntos");
                    ArrayList<BarEntry> entries = null;
                    BarDataSet barDataSet = null;
                    try {
                        entries = entryDistance(respostaRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    barDataSet = new BarDataSet(entries, "distance");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData barData = new BarData(barDataSet);
                    barchart.setData(barData);
                    barchart.getDescription().setText("Distancia recorrida");
                    barchart.invalidate();
                }else if (datos == 2){ //distancia
                    datos=0;

                    cambiar.setText("Co2"); // dos mas
                    ArrayList<BarEntry> entries = null;
                    BarDataSet barDataSet = null;
                    try {
                        entries = entryPoints(respostaRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    barDataSet = new BarDataSet(entries, "points");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData barData = new BarData(barDataSet);
                    barchart.setData(barData);
                    barchart.getDescription().setText("Puntos conseguidos");
                    barchart.invalidate();
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

    public void setData(){

    }
    public JSONObject respostaRequest() throws JSONException, IOException {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d("variablesglobales", "global variables "+ GlobalVariables.username);
        Log.d("variablesglobales", "global variables "+ GlobalVariables.password);
        String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;
        Log.d("request urlParameters", urlParameters);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/progress?"+urlParameters)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());
        Log.d("requestLlamada", respuesta2.toString());

        if(respuesta2.getString("result").equals("Success")) {

            Log.d("request", "inside");
            String npuntos = respuesta2.getString("totalPoints");
            Log.d("request", "puntos" + npuntos);

        }
        return respuesta2;
    }


    public ArrayList<BarEntry> entryCo2(JSONObject respuesta2) throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respuesta2.getJSONObject("days");
        Log.d("numeroDias", String.valueOf(ja.length()));

        JSONArray dias = ja.names();

        for (int i = 0; i < ja.length(); i++){
            Object dia = dias.get(i);
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("co2");
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }

        return entry;
    }

    public ArrayList<BarEntry> entryPoints(JSONObject respuesta2) throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respuesta2.getJSONObject("days");

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

    public ArrayList<BarEntry> entryDistance(JSONObject respuesta2) throws IOException, JSONException {

        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        JSONObject ja = respuesta2.getJSONObject("days");

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
