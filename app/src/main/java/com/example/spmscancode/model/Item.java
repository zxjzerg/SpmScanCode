package com.example.spmscancode.model;

import java.math.BigDecimal;

/**
 * 商品
 * Created by Andrew on 15/2/20.
 */
public class Item {

    private int id;
    private String code;
    private String name;
    private BigDecimal price;

    public Item() {

    }

    public Item(int id, String code, String name, BigDecimal price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
