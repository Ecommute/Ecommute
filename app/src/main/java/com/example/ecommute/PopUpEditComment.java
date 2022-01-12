package com.example.ecommute;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopUpEditComment {
    public String username = GlobalVariables.username;
    public String password = GlobalVariables.password;

    private Integer mId, mIdArticle;

    public void showPopUpWindow(final View view, Context context, Integer id, Integer idArticle, String contentOG){
        View popUpView = LayoutInflater.from(context).inflate(R.layout.pop_up_editcomment, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(null);

        EditText editText = popUpView.findViewById(R.id.editCommentInput);
        editText.setHint(contentOG);

        Button confirm = popUpView.findViewById(R.id.confirmButton);
        ImageButton close = popUpView.findViewById(R.id.closeButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Response[] response = new Response[1];
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create("", mediaType);
                Request request = new Request.Builder()
                        .url("http://10.4.41.35:3000/articles/" + idArticle + "/comment/" + id +
                                "?username=" + username + "&password=" + password + "&content=" + contentOG)
                        .method("PUT", body)
                        .build();
                try {
                    response[0] = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                popupWindow.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


    }

}
