package com.example.ecommute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    Context mContext;
    private Integer[] mIds;
    private String[] mAuthors;
    private String[] mContents;
    private String[] mCreatedAts;
    private Boolean[] mOwns;
    private Boolean[] mReporteds;
    private Integer midArticle;

    public AdapterComment(Context context, Integer[] ids, String[] authors, String[] contents, String[] createdAts, Boolean[] owns, Boolean[] reporteds, Integer idArticle){
        mContext = context;
        mIds = ids;
        mAuthors = authors;
        mContents = contents;
        mCreatedAts = createdAts;
        mOwns = owns;
        mReporteds = reporteds;
        midArticle = idArticle;
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
        holder.createdAt.setText(mCreatedAts[position]);
        holder.id = mIds[position];
        holder.idArticle = midArticle;
        holder.own = mOwns[position];
        holder.reported = mReporteds[position];


    }

    @Override
    public int getItemCount() {
        return mAuthors.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String username = GlobalVariables.username;
        public String password = GlobalVariables.password;

        public TextView author, content, createdAt;
        public Integer id, idArticle;
        public Boolean own, reported;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.commContent);
            createdAt = itemView.findViewById(R.id.commCreatedAt);




        }
    }
}
