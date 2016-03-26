package com.example.android.simplestorage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

/**
 * Created by new on 26/03/2016.
 */
public class MainTableFragment extends Fragment {
    private static DefTable table;
    private static int savedParam = 0;
    private TableAdapter tableAdapter;

    public MainTableFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        //TODO : DELETE
        // Base Hardcoded table
        if(savedParam == 0) {
            Log.v("Message", "Instance is new");
            table = new DefTable();
            table.addEntry("Name1", 1, "");
            table.addEntry("name2name2name2name2name2name2name2", 5, "extra");
            table.addEntry("Name3", 2, "verylongextra");
            table.addEntry("name4", 8, "extra");
            table.addEntry("Name5", 10, "verylongextraverylongextra");
            table.addEntry("name6", 999, "extra");
        } else {
            Log.v("Message","Instance is not new");
        }

        //Prepare the Listview
        Integer[] idArray = table.getIdArray();
        Log.v("ID", Arrays.toString(idArray));
        String[] nameArray = table.getNameArray();
        Log.v("Name", Arrays.toString(nameArray));
        Integer[] quantityArray = table.getQuantityArray();
        Log.v("Qty", Arrays.toString(quantityArray));
        String[] extraArray = table.getExtraArray();
        Log.v("Extra", Arrays.toString(extraArray));

        tableAdapter = new TableAdapter(this, idArray, nameArray, quantityArray, extraArray);

        ListView itemLv = (ListView) rootView.findViewById(R.id.itemListView);
        itemLv.setAdapter(tableAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Set the table state to saved, so it won't be restarted
        savedParam = 1;
        Log.v("Save", "State Saved");

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public DefTable getTable() {
        return table;
    }
}
