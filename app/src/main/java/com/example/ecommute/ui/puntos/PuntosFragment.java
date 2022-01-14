package com.example.ecommute.ui.puntos;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.GlobalVariables;
import com.example.ecommute.PopUpCoche;
import com.example.ecommute.PopUpInfopuntos;
import com.example.ecommute.R;
import com.example.ecommute.databinding.FragmentPuntosBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PuntosFragment extends Fragment {

    private PuntosViewModel puntosViewModel;
    private FragmentPuntosBinding binding;
    String nivel;
    String puntosActuales;
    String puntosNivel;
    Integer pct;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        puntosViewModel =
                new ViewModelProvider(this).get(PuntosViewModel.class);

        binding = FragmentPuntosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton infopuntos = binding.informacion;
        infopuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopUpInfopuntos.class);
                startActivity(intent);
            }
        });

        try {
            verPuntos();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        binding.nivelActual.setText("Nivel " + nivel);
        binding.puntos.setText(puntosActuales + "/" + puntosNivel);
        binding.progresoNivel.setProgress(pct);
        switch(nivel){
            case "1":
                binding.iconoNivel.setImageResource(R.drawable.nivel1);
                break;
            case "2":
                binding.iconoNivel.setImageResource(R.drawable.nivel2);
                break;
            case "3":
                binding.iconoNivel.setImageResource(R.drawable.nivel3);
                break;
            case "4":
                binding.iconoNivel.setImageResource(R.drawable.nivel4);
                break;
            case "5":
                binding.iconoNivel.setImageResource(R.drawable.nivel5);
                break;
            default:
                binding.iconoNivel.setImageResource(R.drawable.nivel6);
                break;
        }
        return root;
    }

    private void verPuntos() throws IOException, JSONException {
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/stats/levels?username="+username+"&password="+password)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject respuesta = new JSONObject(response.body().string());
        //JSONObject nivel = new JSONObject(respuesta.getString("currentLevel"));
        nivel = respuesta.getString("currentLevel");
        puntosActuales = respuesta.getString("points");
        puntosNivel = respuesta.getString("nextLevelRequirement");
        int cpts = Integer.parseInt(puntosActuales);
        int lvlpts = Integer.parseInt(puntosNivel);
        pct = (cpts*100)/lvlpts;
        System.out.println(pct);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}