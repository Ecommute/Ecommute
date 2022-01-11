package com.example.ecommute;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommute.databinding.FragmentArticleCommentsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleCommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ArticleCommentsFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected AdapterComment mAdapter;

    private static int idArticle;
    private Integer[] arrayIds;
    private String[] arrayAuthors;
    private String[] arrayContents;
    private String[] arrayCreatedAts;
    private Boolean[] arrayOwns;
    private Boolean[] arrayReporteds;
    private int countNulls;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ArticleCommentsFragment.
     * @param idA
     */
    public static ArticleCommentsFragment newInstance(Integer idA) {
        ArticleCommentsFragment fragment = new ArticleCommentsFragment();
        idArticle = idA;
        return fragment;
    }

    public ArticleCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setUpDataSet();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_comments, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.commentsRV);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterComment(getActivity(), arrayIds, arrayAuthors, arrayContents, arrayCreatedAts, arrayOwns, arrayReporteds, idArticle, countNulls);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }


    private void setUpDataSet() throws IOException, JSONException {
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/articles/" + idArticle + "?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();
        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);

        JSONObject info = new JSONObject(Jobject.getString("article"));
        JSONArray Jarray = info.getJSONArray("comments");

        idArticle = Integer.parseInt(info.getString("id"));

        Bundle bundle = new Bundle();
        bundle.putInt("idArticle", idArticle);

        int n = Jarray.length();

        arrayIds = new Integer[n];
        arrayAuthors = new String[n];
        arrayContents = new String[n];
        arrayCreatedAts = new String[n];
        arrayOwns = new Boolean[n];
        arrayReporteds = new Boolean[n];
        countNulls = 0;

        for(int i = 0; i < n; i++){

            JSONObject object = Jarray.getJSONObject(i);

            arrayIds[i] = Integer.valueOf((object.getString("id")));
            arrayAuthors[i] = object.getString("author");
            arrayContents[i] = object.getString("content");
            arrayCreatedAts[i] = object.getString("createdAt");
            arrayOwns[i] = Boolean.valueOf(object.getString("own"));
            arrayReporteds[i] = Boolean.valueOf(object.getString("reported"));

        }
    }
}