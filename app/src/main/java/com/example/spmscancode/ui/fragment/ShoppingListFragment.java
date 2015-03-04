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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qr_codescan.MipcaActivityCapture;
import com.example.spmscancode.Data;
import com.example.spmscancode.R;
import com.example.spmscancode.model.Item;
import com.example.spmscancode.model.Order;
import com.example.spmscancode.model.OrderItem;
import com.example.spmscancode.ui.activity.MainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private ShoppingListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.inject(this, view);

        MainActivity.mTitleBar.clearButtons();
        MainActivity.mTitleBar.addRightButton(R.drawable.icon_title_bar_add);
        MainActivity.mTitleBar.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCurrentOrder = new Order();
        mAdapter = new ShoppingListAdapter();
        mLvShoppingList.setAdapter(mAdapter);
        mLvShoppingList.addHeaderView(createHeaderView());
        mLvShoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog delete = new AlertDialog.Builder(mContext,AlertDialog.THEME_HOLO_LIGHT).create();
                delete.setMessage("确定删除？");
                delete.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
                break;
            default:
                break;
        }
    }

    private View createHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_shopping_list, mLvShoppingList, false);
        ((TextView) view.findViewById(R.id.tv_id)).setText("NO.");
        ((TextView) view.findViewById(R.id.tv_name)).setText("名称");
        ((TextView) view.findViewById(R.id.tv_price)).setText("单价");
        ((TextView) view.findViewById(R.id.tv_count)).setText("数量");
        ((TextView) view.findViewById(R.id.tv_total)).setText("总价");
        view.setBackgroundColor(getResources().getColor(R.color.gray));
        return view;
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
        }

    }

    private void addItem(String code) {
        if (mCurrentOrder.getOrderItems() != null && mCurrentOrder.getOrderItems().size() > 0) {
            // 查找是否已存在项目
            for (OrderItem orderItem : mCurrentOrder.getOrderItems()) {
                if (code.equals(String.valueOf(orderItem.getItem().getCode()))) {
                    orderItem.setCount(orderItem.getCount() + 1);
                    orderItem.calculate();
                    mCurrentOrder.caculate();
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(Data.getItemByCode(code));
        orderItem.setCount(1);
        orderItem.calculate();
        mCurrentOrder.addOrderItem(orderItem);
        mCurrentOrder.caculate();
        mAdapter.notifyDataSetChanged();
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
            ViewHolder holder;

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_shopping_list, parent, false);
                holder = new ViewHolder();
                holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
                holder.tvTotal = (TextView) convertView.findViewById(R.id.tv_total);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position % 2 == 0) {
                convertView.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                convertView.setBackgroundColor(getResources().getColor(R.color.gray));
            }

            OrderItem orderItem = getItem(position);
            holder.tvId.setText(String.valueOf(position + 1));
            holder.tvName.setText(orderItem.getItem().getName());
            holder.tvCount.setText(String.valueOf(orderItem.getCount()));
            holder.tvPrice.setText(orderItem.getItem().getPrice().toString());
            holder.tvTotal.setText(orderItem.getTotal().toString());

            return convertView;
        }

        class ViewHolder {

            TextView tvId;
            TextView tvName;
            TextView tvPrice;
            TextView tvCount;
            TextView tvTotal;
        }
    }

    private Order prepareTestData() {
        Order order = new Order();

        Item coke = new Item(1, "10000001", "可乐", new BigDecimal(2.5));
        OrderItem orderItem_1 = new OrderItem();
        orderItem_1.setItem(coke);
        orderItem_1.setCount(1);
        orderItem_1.calculate();

        Item chocolate = new Item(2, "10000003", "巧克力", new BigDecimal(6));
        OrderItem orderItem_2 = new OrderItem();
        orderItem_2.setItem(chocolate);
        orderItem_2.setCount(10);
        orderItem_2.calculate();

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(orderItem_1);
        orderItems.add(orderItem_2);
        order.setOrderItems(orderItems);

        return order;
    }
}
