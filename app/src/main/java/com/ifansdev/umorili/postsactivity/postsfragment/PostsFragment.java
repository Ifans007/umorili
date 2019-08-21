package com.ifansdev.umorili.postsactivity.postsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifansdev.umorili.R;
import com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter.TabFragmentPagerAdapter;
import com.ifansdev.umorili.api.dataclass.Quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PostsFragment extends Fragment {

    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        ViewPager viewPager = view.findViewById(R.id.viewpager);

        FragmentManager fragmentManager = myContext.getSupportFragmentManager();

        Bundle bundle = getArguments();
        HashMap<Integer, List<Quote>> listQuotes = new HashMap<>();
        ArrayList<String> descSource = new ArrayList<>();

        if (bundle != null) {
            listQuotes = (HashMap<Integer, List<Quote>>) bundle.getSerializable("listQuotes");
            descSource = bundle.getStringArrayList("descSource");
        }

        viewPager.setAdapter(
                new TabFragmentPagerAdapter(
                        fragmentManager,
                        listQuotes,
                        descSource));

        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        myContext = getActivity();
        super.onAttach(context);
    }
}
