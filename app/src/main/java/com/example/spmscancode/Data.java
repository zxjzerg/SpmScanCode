package com.example.spmscancode;

import com.example.spmscancode.model.Item;
import com.example.spmscancode.model.Order;
import com.example.spmscancode.model.OrderItem;

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

    public static Order prepareTestData() {
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
