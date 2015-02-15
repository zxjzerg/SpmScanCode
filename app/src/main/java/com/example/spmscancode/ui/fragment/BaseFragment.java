package com.example.spmscancode.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 所有Fragment的基类
 * Created by Andrew on 15/2/14.
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate. " + (getActivity() == null ? "Activity is null." : "Activity is not null."));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView. " + (getActivity() == null ? "Activity is null." : "Activity is not null."));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach. " + (getActivity() == null ? "Activity is null." : "Activity is not null."));
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated. " + (getActivity() == null ? "Activity is null." : "Activity is not null."));
        super.onActivityCreated(savedInstanceState);
    }
}
