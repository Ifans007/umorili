package com.ifansdev.umorili.commonfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifansdev.umorili.R;

import java.net.UnknownHostException;

public class ErrorsFragment extends Fragment {

    private final String TAG = "ErrorsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_errors, container, false);

        new TextViewError(view);
        new SwipeRefresh(view);

        return view;
    }

    private class TextViewError {
        private TextViewError(View view) {
            TextView textViewError = view.findViewById(R.id.textError);
            Throwable e;
            Bundle bundle = getArguments();

            if (bundle != null) {
                e = (Throwable) bundle.getSerializable("Throwable");
                if (e instanceof UnknownHostException) {
                    textViewError.setText(R.string.network_error);
                } else {
                    Log.e(TAG, String.valueOf(e));
                    textViewError.setText(R.string.unknown_error);
                }
            } else {
                textViewError.setText(R.string.unknown_error);
            }
        }
    }

    private class SwipeRefresh {
        private SwipeRefresh(View view) {
            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.timeline_swipe_layout);
            swipeRefreshLayout.setOnRefreshListener(this::refresh);
        }

        private void refresh() {
            if (getActivity() != null) {
                ((HandlerRefresher)getActivity()).refresh();
            }
        }
    }

    public interface HandlerRefresher {
        void refresh();
    }

}
