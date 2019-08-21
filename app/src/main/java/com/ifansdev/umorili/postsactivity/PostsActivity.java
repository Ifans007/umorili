package com.ifansdev.umorili.postsactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ifansdev.umorili.CreateFragment;
import com.ifansdev.umorili.R;
import com.ifansdev.umorili.api.SearchRepository;
import com.ifansdev.umorili.api.SearchRepositoryProvider;
import com.ifansdev.umorili.api.dataclass.Quote;
import com.ifansdev.umorili.commonfragments.ErrorsFragment;
import com.ifansdev.umorili.commonfragments.LoadingFragment;
import com.ifansdev.umorili.postsactivity.postsfragment.PostsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostsActivity extends AppCompatActivity implements ErrorsFragment.HandlerRefresher {

    private SearchRepository searchRepository = SearchRepositoryProvider.INSTANCE.provideSearchRepository();

    private ArrayList<String> siteSource;
    private ArrayList<String> nameSource;
    private ArrayList<String> descSource;

    private ArrayList<String> siteNameDescArray;

    private HashMap<Integer, List<Quote>> listQuotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        siteNameDescArray = getIntent().getStringArrayListExtra("siteNameDescArray");
        siteSource = new ArrayList<>();
        nameSource = new ArrayList<>();
        descSource = new ArrayList<>();

        makeSiteNameDescArrays(siteNameDescArray);

        listQuotes = new HashMap<>();

        loadingQuotes();
    }

    private void makeSiteNameDescArrays(ArrayList<String> siteNameDescArray) {
        int i = 0;
        for (; i < siteNameDescArray.size();) {
            siteSource.add(siteNameDescArray.get(i));
            nameSource.add(siteNameDescArray.get(i + 1));
            descSource.add(siteNameDescArray.get(i + 2));
            i += 3;
        }
    }

    private void loadingQuotes() {

        new CreateFragment(this, R.id.posts_activity_container, new LoadingFragment());

        for (int i = 0; i < siteSource.size(); i++) {

            int positionInListQuotes = i;

            searchRepository.searchQuotes(this.siteSource.get(i), nameSource.get(i))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            result -> createQuotes(result, positionInListQuotes, siteSource.size()),
                            this::handleError);
        }
    }

    public void handleError(Throwable error) {
        new CreateFragment(this, R.id.posts_activity_container, error);
    }

    private void createQuotes(List<Quote> quotes, int positionInListQuotes, int size) {

        listQuotes.put(positionInListQuotes, quotes);

        if (listQuotes.size() == size) {

            PostsFragment postsFragment = new PostsFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("listQuotes", listQuotes);
            bundle.putStringArrayList("descSource", descSource);

            postsFragment.setArguments(bundle);

            new CreateFragment(this, R.id.posts_activity_container, postsFragment);
        }
    }

    @Override
    public void refresh() {
        loadingQuotes();
    }
}
