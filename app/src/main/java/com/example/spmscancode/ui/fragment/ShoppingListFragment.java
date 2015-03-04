package com.example.spmscancode.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.example.spmscancode.Data;
import com.example.spmscancode.R;
import com.example.spmscancode.model.ModelContext;
import com.example.spmscancode.model.Order;
import com.example.spmscancode.model.OrderItem;
import com.example.spmscancode.ui.activity.MainActivity;
import com.example.spmscancode.ui.activity.PayActivity;
import com.example.spmscancode.ui.adapter.ItemAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 购物清单界面
 * Created by Andrew on 15/2/14.
 */
public class ShoppingListFragment extends BaseFragment {

    private static final int REQUEST_SCAN = 1;
    private static final int REQUEST_PAY = 2;

    @InjectView(R.id.btn_scan)
    Button mBtnScan;

    @InjectView(R.id.btn_pay)
    Button mBtnPay;

    @InjectView(R.id.lv_shopping_list)
    ListView mLvShoppingList;

    @InjectView(R.id.tv_total)
    TextView mTvTotal;

    private Order mCurrentOrder;
    private ItemAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.inject(this, view);

        MainActivity.mTitleBar.clearButtons();
        MainActivity.mTitleBar.setTitle("购物清单");
        MainActivity.mTitleBar.addRightButton(R.drawable.icon_title_bar_add);
        MainActivity.mTitleBar.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCurrentOrder = new Order();

        mAdapter = new ItemAdapter(mContext, mCurrentOrder);
        mLvShoppingList.setAdapter(mAdapter);
        mLvShoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog delete = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
                delete.setMessage("删除？");
                delete.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderItem orderItem = ((ItemAdapter) parent.getAdapter()).getItem(position);
                        mCurrentOrder.removeOrderItem(orderItem);
                        refresh();
                    }
                });
                delete.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                delete.show();
                return true;
            }
        });
        refresh();
        return view;
    }

    @OnClick({R.id.btn_scan, R.id.btn_pay})
    public void handleClick(Button button) {
        switch (button.getId()) {
            case R.id.btn_scan:
                Log.d(TAG, "scan click");
                Intent intentScan = new Intent(mContext, MipcaActivityCapture.class);
                startActivityForResult(intentScan, REQUEST_SCAN);
                break;
            case R.id.btn_pay:
                Log.d(TAG, "pay click");
                ModelContext.getInstance().setCurrentOrder(mCurrentOrder);
                Intent intentPay = new Intent(mContext, PayActivity.class);
                intentPay.putExtra("qr_code", String.valueOf(mCurrentOrder.getId()));
                startActivityForResult(intentPay, REQUEST_PAY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN) {
            if (resultCode == Activity.RESULT_OK) {
                String code = data.getStringExtra("result");
                Log.d(TAG, "result: " + code);
                addItem(code);
            }
        } else if (requestCode == REQUEST_PAY) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(mContext, "完成支付", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void addItem(String code) {
        if (mCurrentOrder.getOrderItems() != null && mCurrentOrder.getOrderItems().size() > 0) {
            // 查找是否已存在项目
            for (OrderItem orderItem : mCurrentOrder.getOrderItems()) {
                if (code.equals(String.valueOf(orderItem.getItem().getCode()))) {
                    orderItem.setCount(orderItem.getCount() + 1);
                    orderItem.calculate();
                    refresh();
                    return;
                }
            }
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(Data.getItemByCode(code));
        orderItem.setCount(1);
        orderItem.calculate();
        mCurrentOrder.addOrderItem(orderItem);
        refresh();
    }

    private void refresh() {
        mCurrentOrder.calculate();
        mTvTotal.setText(String.format(getString(R.string.current_total), mCurrentOrder.getTotal().toString()));
        mAdapter.notifyDataSetChanged();
    }

}
