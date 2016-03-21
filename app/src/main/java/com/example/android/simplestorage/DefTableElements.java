package com.example.android.simplestorage;

/**
 * Created by new on 21/03/2016.
 *  * TODO : DELETE
 * Dummy table element class
 */
public class DefTableElements {
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

    public void addQuantity(int add) {
        quantity += add;
    }

    public boolean removeQuantity(int remove) {
        if(quantity > remove) {
            quantity -= remove;
            return true;
        } else {
            return false;
        }
    }
}
