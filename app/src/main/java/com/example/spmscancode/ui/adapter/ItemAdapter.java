package com.example.spmscancode.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spmscancode.R;
import com.example.spmscancode.model.Order;
import com.example.spmscancode.model.OrderItem;

/**
 * Created by Andrew on 15/3/4.
 */
public class ItemAdapter extends BaseAdapter {

    private Context mContext;
    private Order mCurrentOrder;

    public ItemAdapter(Context context, Order currentOrder) {
        mContext = context;
        mCurrentOrder = currentOrder;
    }

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
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.item_shopping_list, parent, false);
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
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
        } else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
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
