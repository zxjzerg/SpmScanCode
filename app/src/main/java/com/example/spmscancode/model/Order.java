package com.example.spmscancode.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by Andrew on 15/2/20.
 */
public class Order {

    private int id;
    private List<OrderItem> orderItems;
    private BigDecimal total;

    public Order() {
        orderItems = new ArrayList<OrderItem>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void caculate() {
        total = new BigDecimal(0);
        for (OrderItem item : orderItems) {
            total = total.add(item.getTotal());
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
