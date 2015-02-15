package com.example.spmscancode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spmscancode.R;

/**
 * 购物清单界面
 * Created by Andrew on 15/2/14.
 */
public class ShoppingListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        return view;
    }
}
