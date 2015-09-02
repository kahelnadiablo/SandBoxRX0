package com.kahel.sandboxrx0.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kahel.sandboxrx0.R;
import com.kahel.sandboxrx0.utils.VolleyQueue;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mark on 8/20/2015.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> article;
    ImageLoader mImageLoader;

    @Override
    public int getItemCount() {
        return article.size();
    }

    public ArticlesAdapter(Context context, ArrayList<HashMap<String, String>> article){
        this.context = context;
        this.article = article;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position==0){
            return 2;
        }else{
            return 1;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(article.get(position).get("title"));
        holder.date.setText(article.get(position).get("post_date"));

        mImageLoader = VolleyQueue.getInstance(context).getImageLoader();
        holder.image.setImageUrl(article.get(position).get("image"), mImageLoader);
        holder.image.setDefaultImageResId(R.drawable.test);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView name;
        @Bind(R.id.date) TextView date;
        @Bind(R.id.image) NetworkImageView image;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
    }



}
}
