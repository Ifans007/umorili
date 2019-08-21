package com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter.pagefragment.postadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifansdev.umorili.R;
import com.ifansdev.umorili.api.dataclass.Quote;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private ArrayList<Quote> posts;

    public PostsAdapter(ArrayList<Quote> posts) {
        this.posts = posts;
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.text);
        }
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_list_item, viewGroup, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i) {
        postsViewHolder.text.setText(Html.fromHtml(posts.get(i).getHtmlText()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
