package com.example.android.simplestorage;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by new on 21/03/2016.
 * TODO : DELETE
 * Dummy table class
 */
public class DefTable {
    private int currentID;
    private HashMap<Integer, DefTableEntry> elementsList;
    private int numElements;

    public DefTable() {
        currentID = 1;
        elementsList = new HashMap<>();
        numElements = 0;
    }

    /**
     * Add certain number of item into entry based on entry ID
     * @param _ID
     * @param quantity
     * @return
     */
    public boolean addToEntry(int _ID, int quantity) {
        if(elementsList.containsKey(_ID)) {
            DefTableEntry cur = elementsList.get(_ID);
            cur.addQuantity(quantity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove certain number of item into entry based on entry ID
     * @param _ID
     * @param quantity
     * @return
     */
    public boolean removeFromEntry(int _ID, int quantity) {
        if(elementsList.containsKey(_ID)) {
            DefTableEntry cur = elementsList.get(_ID);
            if(cur.removeQuantity(quantity) > -1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Add an entry to table
     * @param name
     * @param quantity
     * @param extra
     * @return
     */
    public int addEntry(String name, int quantity, String extra) {
        int duplicate = findEntryByName(name);
        if(duplicate == 0) {
            elementsList.put(currentID, new DefTableEntry(currentID, name, quantity, extra));
            currentID++;
            numElements++;
        } else {
            elementsList.get(duplicate).addQuantity(quantity);
        }
        return duplicate;
    }

    /**
     * Add an entry to table
     * @param name
     * @param quantity
     * @param extra
     * @param fullMatchExtra
     * @param full
     * @return
     */
    public int addEntry(String name, int quantity, String extra, boolean fullMatchExtra, String full) {
        int duplicate = findEntryByName(name);
        if(duplicate == 0) {
            elementsList.put(currentID, new DefTableEntry(currentID, name, quantity, extra, fullMatchExtra, full));
            currentID++;
            numElements++;
        } else {
            elementsList.get(duplicate).addQuantity(quantity);
        }
        return duplicate;
    }

    public int editEntry(int _ID, String name, int quantity, String extra, boolean fullMatchExtra, String full) {
        if(elementsList.containsKey(_ID)) {
            elementsList.remove(_ID);
            elementsList.put(_ID, new DefTableEntry(_ID, name, quantity, extra, fullMatchExtra, full));
            return _ID;
        } else {
            return -1;
        }
    }

    /**
     * Find entry from table by the item name
     * @param name
     * @return
     */
    public int findEntryByName(String name) {
        for(Integer _ID : elementsList.keySet()) {
            DefTableEntry cur = elementsList.get(_ID);
            if(cur.getName().equals(name)) return _ID;
        }
        return 0;
    }

    /**
     * Get entry from table by ID
     * @param id
     * @return
     */
    public DefTableEntry getEntry(int id) {
        return elementsList.get(id);
    }

    //get array function for table adapter
    public Integer[] getIdArray() {
        SortedSet<Integer> sortedId = new TreeSet<>(elementsList.keySet());
        return sortedId.toArray(new Integer[0]);
    }

    public String[] getNameArray() {
        String[] nameArray = new String[numElements];
        int index = 0;
        SortedSet<Integer> sortedId = new TreeSet<>(elementsList.keySet());
        for(Integer id : sortedId) {
            DefTableEntry ele = elementsList.get(id);
            nameArray[index] = ele.getName();
            index++;
        }
        return nameArray;
    }

    public Integer[] getQuantityArray() {
        Integer[] quantityArray = new Integer[numElements];
        int index = 0;
        SortedSet<Integer> sortedId = new TreeSet<>(elementsList.keySet());
        for(Integer id : sortedId) {
            DefTableEntry ele = elementsList.get(id);
            quantityArray[index] = ele.getQuantity();
            index++;
        }
        return quantityArray;
    }

    public String[] getExtraArray() {
        String[] extraArray = new String[numElements];
        int index = 0;
        SortedSet<Integer> sortedId = new TreeSet<>(elementsList.keySet());
        for(Integer id : sortedId) {
            DefTableEntry ele = elementsList.get(id);
            extraArray[index] = ele.getExtra();
            index++;
        }
        return extraArray;
    }
}
