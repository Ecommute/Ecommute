package com.example.ecommute.ui.huella;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterHistorial;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.MainActivity;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.PopupInformeSemanal;
import com.example.ecommute.R;
import com.example.ecommute.Ruta;
import com.example.ecommute.Usuario;
import com.example.ecommute.databinding.FragmentHuellaBinding;
import com.example.ecommute.databinding.ItemHistorialBinding;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HuellaFragment extends Fragment{
    String[] arrayOrigenes;
    String[] arrayDestinos;
    Integer[] arrayPuntos;
    Integer[] arrayIds;
    Integer[] arrayFavs;

    private HuellaViewModel huellaViewModel;
    private FragmentHuellaBinding bindingH;
    RecyclerView historial;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        huellaViewModel =
                new ViewModelProvider(this).get(HuellaViewModel.class);

        bindingH = FragmentHuellaBinding.inflate(inflater, container, false);
        View root = bindingH.getRoot();

        //Recogemos origenes, destinos y puntos
        try {
            setUpHistorial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        historial = bindingH.historial;
        AdapterHistorial mAdapter = new AdapterHistorial(this.getActivity(), arrayOrigenes, arrayDestinos, arrayPuntos, arrayIds, arrayFavs);
        historial.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        historial.setLayoutManager(mLayoutManager);


        BarChart barchart = (BarChart) root.findViewById(R.id.graph);
        ArrayList<BarEntry> entries = null;

        try {
            entries = entry();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Valores del dataset", entries.toString());

        BarDataSet barDataSet = new BarDataSet(entries, "co2");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(barDataSet);

        barchart.setData(barData);
        barchart.setFitBars(true);
        barchart.animateY(2000);
        barchart.getDescription().setText("CO2 consumido");
        barchart.getLegend().setEnabled(false);
        barchart.getXAxis().setEnabled(false);
        barchart.getAxisLeft().setAxisMinimum(0.0f);
        barchart.getAxisRight().setAxisMinimum(0.0f);
        barchart.getAxisRight().setDrawLabels(false);

        Log.d("Checkpoint", "hehe");

        Button mostrarInfo = bindingH.mostrarInfo;
        mostrarInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupInformeSemanal popUpClass = new PopupInformeSemanal();
                try {
                    popUpClass.showPopupWindow(v);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        return root;
    }

    public ArrayList<BarEntry> entry() throws IOException, JSONException {
        ArrayList<BarEntry> entry = new ArrayList<BarEntry>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d("variablesglobales", "global variables "+ GlobalVariables.username);
        Log.d("variablesglobales", "global variables "+ GlobalVariables.password);
        //String urlParameters  = "&username="+username+ "&password="+pass;
        String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;
        //String urlParameters  = "&username=marcelurpi&password=password";
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

        JSONObject ja = respuesta2.getJSONObject("days");
        Log.d("numeroDias", String.valueOf(ja.length()));

        JSONArray dias = ja.names();

        for (int i = 0; i < ja.length(); i++){
            Object dia = dias.get(i);
            if (dia != null){       //no deberia serlo porque uso su lenght
                String co2 = ja.getJSONObject((String) dia).getString("co2");
                Log.d("Misco2", co2);
                entry.add(new BarEntry(i, Float.parseFloat(co2)));
            }
        }

        return entry;
    }


    private void setUpHistorial() throws Exception {

        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/list?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();

        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONArray Jarray = Jobject.getJSONArray("routes");

        int n = Jarray.length();
        arrayOrigenes = new String[n];
        arrayDestinos = new String[n];
        arrayPuntos = new Integer[n];
        arrayIds = new Integer[n];
        arrayFavs = new Integer[n];

        for (int i = 0; i < n; i++) {
            JSONObject object = Jarray.getJSONObject(i);
            arrayIds[i] = Integer.valueOf(object.getString("id"));
            arrayOrigenes[i] = object.getString("origin");
            arrayDestinos[i] = object.getString("destination");
            arrayPuntos[i] = Integer.valueOf(object.getString("points"));
            arrayFavs[i] = Integer.valueOf(object.getString("favourite"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindingH = null;
    }
}