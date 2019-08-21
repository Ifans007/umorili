package com.ifansdev.umorili.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifansdev.umorili.R;
import com.ifansdev.umorili.api.dataclass.SourceOfQuotes;

import java.util.ArrayList;
import java.util.List;

public class ListSourceOfQuotesFragment extends Fragment {

    private static final String TAG = "ListSourceOfQuotes";

    private ListView listView;
    private List<SourceOfQuotes> sourceOfQuotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_source_of_quotes, container, false);

        new ListSourceOfQuotes(view);
        new BottomNavigation(view);

        return view;
    }

    private void listViewIterationAll(ListView listView, boolean b) {
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            listView.setItemChecked(i, b);
        }
    }

    private class ListSourceOfQuotes {
        private ArrayList<String> ListItemsNameArray;
        private ListSourceOfQuotes(View view) {

            createListSourceOfQuotes(getListSourceOfQuotes());

            createArrayListForListView();

            createListView(view);
        }


        private List<List<SourceOfQuotes>> getListSourceOfQuotes() {
            Bundle bundle = getArguments();
            List<List<SourceOfQuotes>> lists = null;

            try {
                lists = (List<List<SourceOfQuotes>>) bundle.getSerializable("sourceOfQuotes");
            } catch (Exception e) {
                Log.e(TAG, String.valueOf(e));
                if (getActivity() != null) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.handleError(e);
                }
            }

            return lists;
        }

        private void createListSourceOfQuotes(List<List<SourceOfQuotes>> lists) {
            sourceOfQuotes = new ArrayList<>();

            for (List<SourceOfQuotes> sourceOfQuotesList : lists) {
                sourceOfQuotes.addAll(sourceOfQuotesList);
            }
        }

        private void createArrayListForListView(){
            ListItemsNameArray = new ArrayList<>();

            for (SourceOfQuotes i : sourceOfQuotes) {
                ListItemsNameArray.add(i.getDesc());
            }
        }

        private void createListView(View view) {
            listView = view.findViewById(R.id.listview);

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_checked, ListItemsNameArray);
            listView.setAdapter(adapter);

            listViewIterationAll(listView, false);
        }

    }

    private class BottomNavigation {
        private BottomNavigation(View view) {

            BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
            bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_done_all:
                        listViewIterationAll(listView, true);
                        return true;
                    case R.id.navigation_ok:
                        beginCreatePostsActivity(listView);
                        return true;
                    case R.id.navigation_clear_all:
                        listViewIterationAll(listView, false);
                        return true;
                }
                return false;
            });
        }

        private void beginCreatePostsActivity(ListView listView) {
            startPostActivity(getCheckedItemPositions(listView));
        }

        private void startPostActivity(ArrayList<String> siteNameDescArray) {
            if (siteNameDescArray != null) {
                if (getActivity() != null) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.createPostsActivity(siteNameDescArray);
                }
            }
        }

        private ArrayList<String> getCheckedItemPositions(ListView listView) {
            SparseBooleanArray checked = listView.getCheckedItemPositions();

            ArrayList<String> siteNameDescArray = new ArrayList<>();

            for (int i = 0; i < checked.size(); i++) {

                if (checked.valueAt(i)) {

                    siteNameDescArray.add(sourceOfQuotes.get(i).getSite());
                    siteNameDescArray.add(sourceOfQuotes.get(i).getName());
                    siteNameDescArray.add(sourceOfQuotes.get(i).getDesc());
                }
            }
            if (siteNameDescArray.size() == 0) {
                return null;
            }

            return siteNameDescArray;
        }
    }
}
