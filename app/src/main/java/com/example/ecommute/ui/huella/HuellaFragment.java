package com.example.ecommute.ui.huella;

import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterHistorial;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.MainActivity;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.PopupInformeSemanal;
import com.example.ecommute.R;
import com.example.ecommute.Ruta;
import com.example.ecommute.Usuario;
import com.example.ecommute.databinding.FragmentHuellaBinding;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HuellaFragment extends Fragment{

    private HuellaViewModel huellaViewModel;
    private FragmentHuellaBinding binding;
    RecyclerView historial;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        huellaViewModel =
                new ViewModelProvider(this).get(HuellaViewModel.class);

        binding = FragmentHuellaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        GraphView graph;
        LineGraphSeries<DataPoint> series;       //an Object of the PointsGraphSeries for plotting scatter graphs
        graph = (GraphView) root.findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(12);

/*
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
*/
        List<String> points = new ArrayList<>();
        try {
            points = getDataGraficoGeneral();
            Log.d("points en try catch", points.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (points != null){
            series= new LineGraphSeries(dataGrafico(points));   //initializing/defining series to get the data from the method 'data()'

        }else{
            series= new LineGraphSeries(dataDefault());   //initializing/defining series to get the data from the method 'data()'
        }
        series.setDrawDataPoints(true);
        graph.addSeries(series);                   //adding the series to the GraphView


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Puntos: "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });

        //Recogemos origenes, destinos y puntos
        setUpHistorial();

        Button mostrarInfo = binding.mostrarInfo;
        mostrarInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupInformeSemanal popUpClass = new PopupInformeSemanal();
                popUpClass.showPopupWindow(v);
            }
        });

        return root;
    }

    public List<String> getDataGraficoGeneral() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //String urlParameters  = "&username="+username+ "&password="+pass;
        //String urlParameters  = "&username="+ GlobalVariables.username+ "&password="+GlobalVariables.password;
        String urlParameters  = "&username=marcelurpi&password=password";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/progress?"+urlParameters)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());
        Log.d("request", respuesta2.toString());

        if(respuesta2.getString("result").equals("Success")) {

            Log.d("request", "inside");
            String npuntos = respuesta2.getString("totalPoints");
            Log.d("request", "puntos"+ npuntos);
            List<String> list = new ArrayList<String>();
            JSONObject months = respuesta2.getJSONObject("months");

            //CAMBIAR ESTA COSA TAN HORRIBLE. HACER MONTHS ARRAY
            list.add(months.getJSONObject("1").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("2").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("3").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("4").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("5").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("6").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("7").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("8").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("9").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("10").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("11").getJSONObject("totalDay").getString("points"));
            list.add(months.getJSONObject("12").getJSONObject("totalDay").getString("points"));

            Log.d("request", "list "+ list.toString());
            return list;
        }
        return null;
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

    public DataPoint[] dataDefault(){
        double[] x= new double[10];
        double[] y= new double[10];
        for (int i= 0; i<10; i++){
            x[i]= i;
            y[i]= i;
        }
        int n=10;     //to find out the no. of data-points
        DataPoint[] values = new DataPoint[n];     //creating an object of type DataPoint[] of size 'n'
        for(int i=0;i<n;i++){
            DataPoint v = new DataPoint(Double.parseDouble(String.valueOf(x[i])),Double.parseDouble(String.valueOf(y[i])));
            values[i] = v;
        }
        return values;
    }

    private void setUpHistorial() {
        //Per instanciar els arrays mirar el size de rutasRealizadas de l'usuari
        int n = 10;

        String[] arrayOrigenes = new String[n];
        String[] arrayDestinos = new String[n];
        Integer[] arrayPuntos = new Integer[n];

        //CODI DE PROVES: omplim els 3 arrays amb filler només per provar el recycler

        Arrays.fill(arrayPuntos, 0);

        for(int i = 0; i<n; ++i){
           arrayOrigenes[i] = "o" + i;
           arrayDestinos[i] = "d" + i;
        }

        /*CODI SEMI DEFINITIU
        //Comptant que la resta està implementada--
        Vector<Integer> rutasRealizadas = usuarioActivo.getRutasRealizadas();
        for(int i = 0; i < rutasRealizadas.size(); ++i){
            ruta = getRutaById(rutasRealizadas[i]) -> algo així, not yet implemented
            origen = ruta.getOrigen();
            destino = ruta.getDestino();
            puntos = ruta.getPuntos();

            arrayOrigenes[i] = origen;
            arrayDestinos[i] = destino;
            arrayPuntos[i] = puntos;
        }*/

        historial = binding.historial;
        AdapterHistorial mAdapter = new AdapterHistorial(this.getActivity(), arrayOrigenes, arrayDestinos, arrayPuntos);
        historial.setAdapter(mAdapter);

        mLayoutManager=new LinearLayoutManager(this.getActivity());
        historial.setLayoutManager(mLayoutManager);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}