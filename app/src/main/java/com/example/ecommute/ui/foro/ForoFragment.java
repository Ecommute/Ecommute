package com.example.ecommute.ui.foro;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.AdapterForum;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.PopUpClass;
import com.example.ecommute.databinding.FragmentForoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForoFragment extends Fragment {

    private ForoViewModel foroViewModel;
    private FragmentForoBinding binding;

    private RecyclerView forum;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foroViewModel =
                new ViewModelProvider(this).get(ForoViewModel.class);

        binding = FragmentForoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;

        /* whats this?
        foroViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        /*Button detalles = binding.detalles;
        detalles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });*/

        /*textView.setText(Html.fromHtml("<iframe width=\"20\" height=\"20\" frameborder=\"0\" style=\"border:0\" " +
                "src=https://www.google.com/maps/embed/v1/directions?key=AIzaSyApUk0xJoZuc46YAjVQEhF1ul67ObY80Sk&origin=Barcelona&destination=Madrid&mode=driving&region=es " +
                "allowfullscreen></iframe>"));*/

        //textView.setText("Route Map");

        try {
            setUpRecycler();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void setUpRecycler() throws IOException, JSONException {

        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/articles/list?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();

        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONArray Jarray = Jobject.getJSONArray("articles");

        int n = Jarray.length();

        String[] arrayTitulos = new String[n];
        String[] arrayContenido = new String[n];
        Boolean[] arrayLiked = new Boolean[n];
        Integer[] arrayNumLikes = new Integer[n];
        Integer[] arrayIds = new Integer[n];;

        for (int i = 0; i < n; i++) {
            Log.d("Forum", "Loop Json objects" + i);
            JSONObject object = Jarray.getJSONObject(i);
            arrayIds[i] = Integer.valueOf(object.getString("id"));
            arrayTitulos[i] = object.getString("title");
            arrayContenido[i] = object.getString("content");
            arrayLiked[i] = Boolean.valueOf(object.getString("liked"));
            arrayNumLikes[i] = Integer.valueOf(object.getString("numLikes"));
        }

        forum = binding.forum;
        AdapterForum mAdapter = new AdapterForum(this.getActivity(), arrayTitulos, arrayContenido, arrayLiked, arrayIds, arrayNumLikes);
        forum.setAdapter((mAdapter));

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        forum.setLayoutManager(mLayoutManager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}