package com.example.android.simplestorage;

import android.util.Log;

/**
 * Created by new on 21/03/2016.
 *  * TODO : DELETE
 * Dummy table element class
 */
public class DefTableElements {
    final int itemMaximumQuantity = 999;
    final int itemMinimumQuantity = 0;
    final int quantityError = -1;

    private String name;
    private int quantity;
    private String extra;

    public DefTableElements(String name, int quantity, String extra) {
        this.name = name;
        this.quantity = quantity;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExtra() {
        return extra;
    }

    public int addQuantity(int add) {
        if(quantity + add <= itemMaximumQuantity) {
            quantity += add;
            return quantity;
        } else {
            return quantityError;
        }
    }

    public int removeQuantity(int remove) {
        if(quantity - remove >= itemMinimumQuantity) {
            quantity -= remove;
            return quantity;
        } else {
            return quantityError;
        }
    }
}
