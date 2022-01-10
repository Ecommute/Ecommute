package com.example.ecommute.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommute.AdapterComments;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.R;
import com.example.ecommute.databinding.FragmentCommentsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsFragment extends Fragment {
    private FragmentCommentsBinding binding;

    private RecyclerView comments;
    private RecyclerView.LayoutManager mLayoutManager;

    private Integer[] arrayIds;
    private String[] arrayAuthors;
    private String[] arrayContents;
    private String[] arrayCreatedAts;
    private Boolean[] arrayOwns;
    private Boolean[] arrayReporteds;
    private Integer idArticle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            setUpCommentsRecycler();
        } catch (Exception e) {
            e.printStackTrace();
        }

        comments = binding.commentsRV;
        AdapterComments mAdapter = new AdapterComments(this.getActivity(), idArticle, arrayIds, arrayAuthors, arrayContents, arrayCreatedAts, arrayOwns, arrayReporteds);
        comments.setAdapter((mAdapter));

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        comments.setLayoutManager(mLayoutManager);

        return root;
    }

    private void setUpCommentsRecycler() throws IOException, JSONException {
        /*String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/articles/" + id + "?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();
        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);

        JSONObject info = new JSONObject(Jobject.getString("article"));
        JSONArray Jarray = info.getJSONArray("comments");

        idArticle = Integer.valueOf(info.getString("id"));

        System.out.println(Jarray);

        int n = Jarray.length();

        Log.d("test comments", String.valueOf(n));


        arrayIds = new Integer[n];
        arrayAuthors = new String[n];
        arrayContents = new String[n];
        arrayCreatedAts = new String[n];
        arrayOwns = new Boolean[n];
        arrayReporteds = new Boolean[n];

        for(int i = 0; i < n; i++){
            JSONObject object = Jarray.getJSONObject(i);
            arrayIds[i] = Integer.valueOf((object.getString("id")));
            arrayAuthors[i] = object.getString("author");
            arrayContents[i] = object.getString("content");
            arrayCreatedAts[i] = object.getString("createdAt");
            arrayOwns[i] = Boolean.valueOf(object.getString("own"));
            arrayReporteds[i] = Boolean.valueOf(object.getString("reported"));
        }

        Log.d("test comments", "num comments: " + String.valueOf(arrayIds.length));
*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}