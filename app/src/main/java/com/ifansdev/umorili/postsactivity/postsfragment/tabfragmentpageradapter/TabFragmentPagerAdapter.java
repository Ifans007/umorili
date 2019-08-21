package com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifansdev.umorili.postsactivity.postsfragment.tabfragmentpageradapter.pagefragment.PageFragment;
import com.ifansdev.umorili.api.dataclass.Quote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private HashMap<Integer, List<Quote>> listQuotes;
    private String[] tabTitles;

    public TabFragmentPagerAdapter(FragmentManager fm, HashMap<Integer, List<Quote>> listQuotes, ArrayList<String> nameSource) {
        super(fm);
        this.listQuotes = listQuotes;
        tabTitles = nameSource.toArray(new String[0]);
    }

    @Override public int getCount() {
        return tabTitles.length;
    }

    @Override public Fragment getItem(int position) {
        PageFragment pageFragment = new PageFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable("quotes", (Serializable) listQuotes.get(position));
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
