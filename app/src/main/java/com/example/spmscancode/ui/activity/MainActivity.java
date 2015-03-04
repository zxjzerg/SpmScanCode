package com.example.spmscancode.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.spmscancode.Data;
import com.example.spmscancode.R;
import com.example.spmscancode.ui.fragment.ShoppingListFragment;
import com.example.spmscancode.ui.view.PlatoTitleBar;

public class MainActivity extends ActionBarActivity {

    public static PlatoTitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 准备数据
        Data.prepareData();

        mTitleBar = (PlatoTitleBar) findViewById(R.id.view_title);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_content, new ShoppingListFragment());
        fragmentTransaction.commit();
    }

}
