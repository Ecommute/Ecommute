package com.example.ecommute;

import android.content.Intent;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Extracted from https://medium.com/@evanbishop/popupwindow-in-android-tutorial-6e5a18f49cc7

public class PopUpClass {

    //PopupWindow display method
    String origen = "Origen";
    String destino = "Destino";


    public void showPopupWindow(final View view, Context mContext, Integer idRuta) {

        //Create a View object yourself through inflater
        /*LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.detalles_ruta, null);*/
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.detalles_ruta, null);

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

        TextView test2 = popupView.findViewById(R.id.titleText);
        test2.setText(R.string.textTitle);

        try {
            setUpBackend(idRuta);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView origen1 = popupView.findViewById(R.id.dorigen);
        origen1.setText(origen);
        TextView destino1 = popupView.findViewById(R.id.ddestino);
        destino1.setText(destino);
        TextView consumo = popupView.findViewById(R.id.dconsumo);
        consumo.setText("consumo");
        TextView comparacion = popupView.findViewById(R.id.dcomparacion);
        comparacion.setText("comparacion");

        Button buttonClose = popupView.findViewById(R.id.messageButton);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popupWindow.dismiss();
                //As an example, display the message
                Toast.makeText(view.getContext(), "Wow, popup window closed", Toast.LENGTH_SHORT).show();

            }
        });

        Button buttonEdit = popupView.findViewById(R.id.editar);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext(), EditActivity.class);
                int idR = idRuta;
                intent.putExtra("idRuta", idR);
                v.getContext().startActivity(intent);
                popupWindow.dismiss();
            }

        });

        ImageButton cerrar = popupView.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //As an example, display the message
                //Toast.makeText(view.getContext(), "Wow, popup window closed", Toast.LENGTH_SHORT).show();

            }
        });

        //Handler for clicking on the inactive zone of the window
        //Se cierra la ventana muy fácilmente, con lo cual lo he pasado a un boton

        /*popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });*/
    }
    public void setUpBackend(int id) throws Exception{
        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/routes/show/" + id +"?username="+ GlobalVariables.username +"&password="+GlobalVariables.password)
                .method("GET", null)
                .build();
        response[0] = client.newCall(request).execute();
        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONObject Jarray = new JSONObject(Jobject.getString("route"));
        origen = Jarray.getString("origin");
        destino = Jarray.getString("destination");
    }

}