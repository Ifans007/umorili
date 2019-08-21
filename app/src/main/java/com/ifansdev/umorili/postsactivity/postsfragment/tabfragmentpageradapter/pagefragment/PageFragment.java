package com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter.pagefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifansdev.umorili.R;
import com.ifansdev.umorili.api.dataclass.Quote;
import com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter.pagefragment.postadapter.PostsAdapter;

import java.util.ArrayList;

public class PageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_page, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Bundle bundle = getArguments();
        ArrayList<Quote> quotes = new ArrayList<>();

        if (bundle != null) {
            quotes = (ArrayList<Quote>) bundle.getSerializable("quotes");
        }

        PostsAdapter postsAdapter = new PostsAdapter(quotes);
        recyclerView.setAdapter(postsAdapter);

        return recyclerView;
    }
}
