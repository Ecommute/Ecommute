package com.example.ecommute;

import android.content.Intent;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//Extracted from https://medium.com/@evanbishop/popupwindow-in-android-tutorial-6e5a18f49cc7

public class PopUpClass {

    //PopupWindow display method

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

        TextView origen = popupView.findViewById(R.id.dorigen);
        test2.setText(R.string.textTitle);
        TextView destino = popupView.findViewById(R.id.ddestino);
        test2.setText(R.string.textTitle);
        TextView consumo = popupView.findViewById(R.id.dconsumo);
        test2.setText(R.string.textTitle);
        TextView comparacion = popupView.findViewById(R.id.dcomparacion);
        test2.setText(R.string.textTitle);

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
                v.getContext().startActivity(intent);
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

}