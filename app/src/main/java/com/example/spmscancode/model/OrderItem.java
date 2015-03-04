package com.example.spmscancode.model;

import java.math.BigDecimal;

/**
 * 订单里的商品
 * Created by Andrew on 15/2/20.
 */
public class OrderItem {

    private int id;
    private Item item;
    private int count;
    private BigDecimal total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void calculate() {
        total = item.getPrice().multiply(new BigDecimal(count));
    }
}
