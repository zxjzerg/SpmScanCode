package com.example.spmscancode.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spmscancode.R;

/**
 * Created by Andrew on 15/3/4.
 */
public class ViewUtil {

    public static View createHeaderView(Context context, ViewGroup root) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_shopping_list, root, false);
        ((TextView) view.findViewById(R.id.tv_id)).setText("NO.");
        ((TextView) view.findViewById(R.id.tv_name)).setText("名称");
        ((TextView) view.findViewById(R.id.tv_price)).setText("单价");
        ((TextView) view.findViewById(R.id.tv_count)).setText("数量");
        ((TextView) view.findViewById(R.id.tv_total)).setText("总价");
        view.setBackgroundColor(context.getResources().getColor(R.color.gray));
        return view;
    }
}
