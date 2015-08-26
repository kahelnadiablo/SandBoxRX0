package com.kahel.sandboxrx0.adapter;

import android.content.Context;
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
public class ArticlesAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> article;
    ImageLoader mImageLoader;

    public ArticlesAdapter(Context context, ArrayList<HashMap<String, String>> article){
        this.context = context;
        this.article = article;
    }

    @Override
    public int getCount() {
        return article.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(this.context);

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.articles_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.name.setText(article.get(position).get("title"));
        holder.date.setText(article.get(position).get("post_date"));

        mImageLoader = VolleyQueue.getInstance(context).getImageLoader();
        holder.image.setImageUrl(article.get(position).get("image"), mImageLoader);
        holder.image.setDefaultImageResId(R.drawable.test);

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.title) TextView name;
        @Bind(R.id.date) TextView date;
        @Bind(R.id.image) NetworkImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
