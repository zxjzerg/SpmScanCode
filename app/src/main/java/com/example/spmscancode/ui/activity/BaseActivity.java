package com.example.spmscancode.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * activity的基类
 * Created by Andrew on 15/1/26.
 */
public class BaseActivity extends Activity {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext = BaseActivity.this;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
