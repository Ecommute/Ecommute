package com.example.ecommute;

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

    int datos = 0; //0 =puntos; 1=co2
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



        Button cerrar = popupView.findViewById(R.id.cerrar_popup);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //As an example, display the message
                //Toast.makeText(view.getContext(), "Wow, popup window closed", Toast.LENGTH_SHORT).show();

            }
        });

        Button cambiar = popupView.findViewById(R.id.cambiardatos);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos ==1) datos = 0;
                else datos = 1;
                view.refreshDrawableState();
            }
        });

        GraphView graph;
        LineGraphSeries<DataPoint> series;       //an Object of the PointsGraphSeries for plotting scatter graphs
        graph = (GraphView) popupView.findViewById(R.id.graph);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        List<String> points = new ArrayList<>();
        JSONObject respuesta = getDataGraficoGeneral();
        points = cambiarDatos(respuesta);
        if (!points.isEmpty()){
            series= new LineGraphSeries(dataGrafico(points));   //initializing/defining series to get the data from the method 'data()'

        }else{
            series= new LineGraphSeries(data());   //initializing/defining series to get the data from the method 'data()'
        }

        graph.addSeries(series);                   //adding the series to the GraphView


    }

    public DataPoint[] data(){
        double[] x= new double[7];
        double[] y= new double[7];
        for (int i= 0; i<7; i++){
            x[i]= i;
            y[i]= i;
        }
        int n=7;     //to find out the no. of data-points
        DataPoint[] values = new DataPoint[n];     //creating an object of type DataPoint[] of size 'n'
        for(int i=0;i<n;i++){
            DataPoint v = new DataPoint(Double.parseDouble(String.valueOf(x[i])),Double.parseDouble(String.valueOf(y[i])));
            values[i] = v;
        }
        return values;
    }

    public DataPoint[] dataGrafico(List<String> valores){
        int nentradas = valores.size();
        double[] x= new double[nentradas];
        double[] y= new double[nentradas];
        for (int i= 0; i<nentradas; i++){
            x[i]= i;
            y[i]= Double.parseDouble(valores.get(i));
        }
        DataPoint[] values = new DataPoint[nentradas];
        for(int i=0;i<nentradas;i++){
            DataPoint v = new DataPoint(x[i],y[i]);
            values[i] = v;
        }
        return values;
    }

    public List<String> cambiarDatos(JSONObject respuesta2) throws JSONException {
        if(respuesta2.getString("result").equals("Success")) {
            String total;
            List<String> list = new ArrayList<String>();
            if(datos==0){   //puntos
                total = respuesta2.getString("totalPoints");
                JSONObject days = respuesta2.getJSONObject("days");

                //CAMBIAR ESTA COSA TAN HORRIBLE. HACER MONTHS ARRAY
                list.add(days.getJSONObject("1").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("2").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("3").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("4").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("5").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("6").getJSONObject("totalDay").getString("points"));
                list.add(days.getJSONObject("7").getJSONObject("totalDay").getString("points"));
            }if(datos==1){  //co2
                total = respuesta2.getString("totalPoints");
                JSONObject days = respuesta2.getJSONObject("days");

                //CAMBIAR ESTA COSA TAN HORRIBLE. HACER MONTHS ARRAY
                list.add(days.getJSONObject("1").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("2").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("3").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("4").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("5").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("6").getJSONObject("totalDay").getString("co2"));
                list.add(days.getJSONObject("7").getJSONObject("totalDay").getString("co2"));
            }
            return list;
        }
        return Collections.emptyList();
    }

    public JSONObject getDataGraficoGeneral() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //String urlParameters  = "&username="+username+ "&password="+pass;
        //String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;
        String urlParameters  = "&username=marcelurpi&password=password";

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/week?"+urlParameters)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());
        Log.d("requestSemanal", respuesta2.toString());

        return respuesta2;
    }

}
