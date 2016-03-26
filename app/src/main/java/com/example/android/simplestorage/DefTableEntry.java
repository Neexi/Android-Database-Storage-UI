package com.example.android.simplestorage;

/**
 * Created by new on 21/03/2016.
 *  * TODO : DELETE
 * Dummy table element class
 */
public class DefTableEntry {
    final int itemMaximumQuantity = 999;
    final int itemMinimumQuantity = 0;
    final int inputError = -1;

    private int _ID;
    private String name;
    private int quantity;
    private String extra;
    private boolean isMatch;
    private String full;

    public DefTableEntry(int _ID, String name, int quantity, String extra) {
        this._ID = _ID;
        this.name = name;
        this.quantity = quantity;
        this.extra = extra;
        this.isMatch = true;
        this.full = extra;
    }

    public DefTableEntry(int _ID, String name, int quantity, String extra, boolean fullMatchExtra, String full) {
        this._ID = _ID;
        this.name = name;
        this.quantity = quantity;
        this.extra = extra;
        this.isMatch = fullMatchExtra;
        this.full = full;
    }

    public int getID() { return _ID; }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExtra() {
        return extra;
    }

    public Boolean getIsMatch() { return isMatch; }

    public String getFull() { return full; }

    public int addQuantity(int add) {
        if(quantity + add <= itemMaximumQuantity) {
            quantity += add;
            return quantity;
        } else {
            return inputError;
        }
    }

    public int removeQuantity(int remove) {
        if(quantity - remove >= itemMinimumQuantity) {
            quantity -= remove;
            return quantity;
        } else {
            return inputError;
        }
    }
}
