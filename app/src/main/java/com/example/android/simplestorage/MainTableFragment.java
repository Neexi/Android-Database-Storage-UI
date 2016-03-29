package com.example.android.simplestorage;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class)
                        .putExtra(getResources().getString(R.string.item_id), table.getCurrentID().toString())
                        .putExtra(getResources().getString(R.string.item_name), "")
                        .putExtra(getResources().getString(R.string.item_quantity), "0")
                        .putExtra(getResources().getString(R.string.item_extra), "")
                        .putExtra(getResources().getString(R.string.item_is_match), String.valueOf(true))
                        .putExtra(getResources().getString(R.string.item_full), "")
                        .putExtra(getResources().getString(R.string.item_detail_new), String.valueOf(true));
                startActivityForResult(intent,1);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == getActivity().RESULT_OK){
                String idStr = data.getStringExtra(getResources().getString(R.string.item_id));
                String name = data.getStringExtra(getResources().getString(R.string.item_name));
                String quantityStr = data.getStringExtra(getResources().getString(R.string.item_quantity));
                String extra = data.getStringExtra(getResources().getString(R.string.item_extra));
                String isMatchStr = data.getStringExtra(getResources().getString(R.string.item_is_match));
                String full = data.getStringExtra(getResources().getString(R.string.item_full));
                Log.v("IntentReturn", idStr + ", " + name + ", " + quantityStr + ", " + extra + ", " + isMatchStr + ", " + full);
                int id = Integer.parseInt(idStr);
                int quantity = Integer.parseInt(quantityStr);
                Boolean isMatch = Boolean.valueOf(isMatchStr);
                if(table.findEntry(id)) {
                    table.editEntry(id, name, quantity, extra, isMatch, full);
                    tableAdapter.editEntry(Integer.parseInt(idStr), name, Integer.parseInt(quantityStr), extra);
                } else {
                    table.addEntry(name, quantity, extra, isMatch, full);
                    tableAdapter.addEntry(id, name, quantity, extra);
                }
                tableAdapter.notifyDataSetChanged();
            }
        }
    }

    public DefTable getTable() {
        return table;
    }
}
