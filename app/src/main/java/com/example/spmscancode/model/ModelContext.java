package com.example.spmscancode.model;

/**
 * Created by Andrew on 15/3/4.
 */
public class ModelContext {

    private Order currentOrder;
    private static ModelContext instance;

    private ModelContext() {

    }

    public static ModelContext getInstance() {
        if (instance == null) {
            instance = new ModelContext();
        }
        return instance;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
}
