package com.ifansdev.umorili.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ifansdev.umorili.CreateFragment;
import com.ifansdev.umorili.R;
import com.ifansdev.umorili.api.SearchRepository;
import com.ifansdev.umorili.api.SearchRepositoryProvider;
import com.ifansdev.umorili.api.dataclass.SourceOfQuotes;
import com.ifansdev.umorili.commonfragments.ErrorsFragment;
import com.ifansdev.umorili.commonfragments.LoadingFragment;
import com.ifansdev.umorili.postsactivity.PostsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ErrorsFragment.HandlerRefresher{

    private SearchRepository searchRepository = SearchRepositoryProvider.INSTANCE.provideSearchRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingSourceOfQuote();
    }

    public void loadingSourceOfQuote() {
        new CreateFragment(this, R.id.main_activity_container, new LoadingFragment());

        searchRepository.searchSourcesOfQuotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this::createListViewForSourceOfQuotes,
                        this::handleError);
    }

    public void handleError(Throwable error) {
        new CreateFragment(this, R.id.main_activity_container, error);
    }

    private void createListViewForSourceOfQuotes(List<List<SourceOfQuotes>> listSourceOfQuotes) {

        ListSourceOfQuotesFragment listSourceOfQuotesFragment = new ListSourceOfQuotesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sourceOfQuotes", (Serializable) listSourceOfQuotes);
        listSourceOfQuotesFragment.setArguments(bundle);

        new CreateFragment(this, R.id.main_activity_container, listSourceOfQuotesFragment);
    }

    public void createPostsActivity(ArrayList<String> siteNameDescArray) {
        Intent intent = new Intent(MainActivity.this, PostsActivity.class);
        intent.putStringArrayListExtra("siteNameDescArray", siteNameDescArray);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        loadingSourceOfQuote();
    }
}


