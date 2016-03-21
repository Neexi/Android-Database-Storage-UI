package com.example.android.simplestorage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by new on 21/03/2016.
 * TODO : DELETE
 * Dummy table class
 */
public class DefTable {
    private int currentID;
    private HashMap<Integer, DefTableElements> elementsList;
    private int numElements;

    public DefTable() {
        currentID = 1;
        elementsList = new HashMap<>();
        numElements = 0;
    }

    public boolean addToElements(int _ID, int quantity) {
        if(elementsList.containsKey(_ID)) {
            DefTableElements cur = elementsList.get(_ID);
            cur.addQuantity(quantity);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFromElements(int _ID, int quantity) {
        if(elementsList.containsKey(_ID)) {
            DefTableElements cur = elementsList.get(_ID);
            if(cur.removeQuantity(quantity)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int addElements(String name, int quantity, String extra) {
        int duplicate = findElementsByName(name);
        if(duplicate == 0) {
            elementsList.put(currentID, new DefTableElements(name, quantity, extra));
            currentID++;
            numElements++;
        } else {
            elementsList.get(duplicate).addQuantity(quantity);
        }
        return duplicate;
    }

    private int findElementsByName(String name) {
        for(Integer _ID : elementsList.keySet()) {
            DefTableElements cur = elementsList.get(_ID);
            if(cur.getName().equals(name)) return _ID;
        }
        return 0;
    }
}
