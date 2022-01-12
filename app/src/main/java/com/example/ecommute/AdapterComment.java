package com.example.ecommute;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    Context mContext;
    private Integer[] mIds;
    private String[] mAuthors;
    private String[] mContents;
    private String[] mCreatedAts;
    private Boolean[] mOwns;
    private Boolean[] mReporteds;
    private Integer midArticle;
    private int mCountNulls;
    private boolean liked;

    public AdapterComment(Context context, Integer[] ids, String[] authors, String[] contents, String[] createdAts, Boolean[] owns, Boolean[] reporteds, Integer idArticle, int countNulls, boolean likeda){
        mContext = context;
        mIds = ids;
        mAuthors = authors;
        mContents = contents;
        mCreatedAts = createdAts;
        mOwns = owns;
        mReporteds = reporteds;
        midArticle = idArticle;
        mCountNulls = countNulls;
        liked = likeda;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_comment, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.author.setText(mAuthors[position]);
         holder.content.setText(mContents[position]);

         holder.id = mIds[position];
         holder.idArticle = midArticle;
         holder.own = mOwns[position];
         holder.reported = mReporteds[position];

         if(mOwns[position]) holder.report.setVisibility(View.GONE);
         else {
             holder.delete.setVisibility(View.GONE);
             holder.edit.setVisibility(View.GONE);
         }


         /*java.sql.Timestamp ts = java.sql.Timestamp.valueOf(mCreatedAts[position]);
         long tsTime = ts.getTime();
         String t = (String) getRelativeTimeSpanString (tsTime);*/

        holder.createdAtPretty.setText(mCreatedAts[position]);

        if(mReporteds[position]) holder.report.setImageResource(R.drawable.ic_baseline_error_red);

        holder.contentSt = mContents[position];
    }

    @Override
    public int getItemCount() {
        return mAuthors.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String username = GlobalVariables.username;
        public String password = GlobalVariables.password;

        public TextView author, content, createdAtPretty;
        public String createdAt, contentSt;
        public Integer id, idArticle;
        public Boolean own, reported;
        public ImageButton report, delete, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.commAuthor);
            content = itemView.findViewById(R.id.commContent);
            createdAtPretty = itemView.findViewById(R.id.commCreatedAt);

            report = itemView.findViewById(R.id.report);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.editCommButton);

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    report.setImageResource(R.drawable.ic_baseline_error_red);

                    final Response[] response2 = new Response[1];

                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = RequestBody.create("", mediaType);
                    Request request = new Request.Builder()
                            .url("http://10.4.41.35:3000/articles/" + idArticle + "/comment/" + id + "/report?username=" + username + "&password=" + password)
                            .method("POST", body)
                            .build();
                    try {
                        response2[0] = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Response[] response2 = new Response[1];

                    OkHttpClient client = new OkHttpClient().newBuilder().build();

                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = RequestBody.create("", mediaType);
                    Request request = new Request.Builder()
                            .url("http://10.4.41.35:3000/articles/" + idArticle + "/comment/" + id + "?username=" + username + "&password=" + password)
                            .method("DELETE", body)
                            .build();
                    try {
                        response2[0] = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent((Activity) mContext, PopUpEditComment.class);
                    intent.putExtra("id", id);
                    intent.putExtra("idArticle", idArticle);
                    intent.putExtra("liked", liked);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
