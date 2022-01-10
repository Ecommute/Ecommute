package com.example.ecommute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.ViewHolder> {
    Context mContext;
    Integer[] mIds;
    String[] mAuthors, mContents, mCreatedAt;
    Boolean[] mOwn, mReported;
    Integer mIdArticle;

    public AdapterComments(Context context, Integer idArticle, Integer[] ids, String[] authors, String[] contents, String[] createdAts, Boolean[] owns, Boolean[] reported){
        mContext = context;
        mIds = ids;
        mAuthors = authors;
        mContents = contents;
        mCreatedAt = createdAts;
        mOwn = owns;
        mReported = reported;
        mIdArticle = idArticle;
    }

    @NonNull
    @Override
    public AdapterComments.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAuthor.setText(mAuthors[position]);
        holder.tvContent.setText(mContents[position]);
        holder.strCreatedAt = mCreatedAt[position];
        holder.id = mIds[position];
        holder.own = mOwn[position];
        holder.reported = mReported[position];
        holder.idArticle = mIdArticle;
    }

    @Override
    public int getItemCount() {
        return mAuthors.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String username = GlobalVariables.username;
        public String password = GlobalVariables.password;

        public TextView tvContent, tvAuthor;
        public String strCreatedAt;
        public int id, idArticle;
        public Boolean own, reported;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.commContent);
            tvAuthor = itemView.findViewById(R.id.commCreatedAt);
            TextView createdAt = itemView.findViewById(R.id.commCreatedAt);
            createdAt.setText(strCreatedAt); //temporal: fer servir alguna llibreria per posarho maco

            Button report = itemView.findViewById(R.id.report);
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Response[] response2 = new Response[1];

                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = RequestBody.create("", mediaType);
                    Request request = new Request.Builder()
                            .url("10.4.41.35:3000/articles/" + idArticle + "/comment/" + id + "/report?username=" + username + "&password=" + password)
                            .method("POST", body)
                            .build();
                    try {
                        response2[0] = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            Button delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Response[] response2 = new Response[1];

                    OkHttpClient client = new OkHttpClient().newBuilder().build();

                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = RequestBody.create("", mediaType);
                    Request request = new Request.Builder()
                            .url("10.4.41.35:3000/articles/" + idArticle + "/comment/" + id + "?username=" + username + "&password=" + password)
                            .method("DELETE", body)
                            .build();
                    try {
                        response2[0] = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
}
