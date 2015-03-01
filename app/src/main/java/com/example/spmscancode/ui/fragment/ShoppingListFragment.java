package com.example.spmscancode.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qr_codescan.MipcaActivityCapture;
import com.example.spmscancode.R;
import com.example.spmscancode.model.Order;
import com.example.spmscancode.model.OrderItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 购物清单界面
 * Created by Andrew on 15/2/14.
 */
public class ShoppingListFragment extends BaseFragment {

    private static final int REQUEST_SCAN = 1;

    @InjectView(R.id.btn_scan)
    Button mBtnScan;

    @InjectView(R.id.btn_pay)
    Button mBtnPay;

    @InjectView(R.id.lv_shopping_list)
    ListView mLvShoppingList;

    private Order mCurrentOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick({R.id.btn_scan, R.id.btn_pay})
    public void handleClick(Button button) {
        switch (button.getId()) {
            case R.id.btn_scan:
                Log.d(TAG, "scan click");
                Intent intentScan = new Intent(mContext, MipcaActivityCapture.class);
                startActivity(intentScan);
                break;
            case R.id.btn_pay:
                Log.d(TAG, "pay click");
                break;
            default:
                break;
        }
    }

    class ShoppingListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCurrentOrder.getOrderItems().size();
        }

        @Override
        public OrderItem getItem(int position) {
            return mCurrentOrder.getOrderItems().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
