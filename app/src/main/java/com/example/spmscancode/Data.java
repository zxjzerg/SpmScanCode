package com.example.spmscancode;

import com.example.spmscancode.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 15/3/3.
 */
public class Data {

    public static List<Item> sItems;

    public static void prepareData() {
        Item coke = new Item(1, "10000001", "可乐", new BigDecimal(2.5));
        Item noodle = new Item(1, "10000002", "方便面", new BigDecimal(4));
        Item chocolate = new Item(2, "10000003", "巧克力", new BigDecimal(6));
        Data.sItems = new ArrayList<Item>();
        Data.sItems.add(coke);
        Data.sItems.add(noodle);
        Data.sItems.add(chocolate);
    }

    public static Item getItemByCode(String code) {
        for (Item item : sItems) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
