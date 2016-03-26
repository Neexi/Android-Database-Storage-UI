package com.example.android.simplestorage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static DefTable table;
    private static int savedParam = 0;
    private TableAdapter tableAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO : DELETE
        // Base Hardcoded table
        if(savedParam == 0) {
            Log.v("Message","Instance is new");
            table = new DefTable();
            table.addEntry("Name1", 1, "");
            table.addEntry("name2name2name2name2name2name2name2name2name2name2name2name2name2", 5, "extra");
            table.addEntry("Name3", 2, "verylongextra");
            table.addEntry("name4", 8, "extra");
            table.addEntry("Name5", 10, "verylongextraverylongextra");
            table.addEntry("name6", 999, "extra");
        } else {
            Log.v("Message","Instance is not new");
        }

        /**
        if(getIntent() != null) {
            Log.v("MAIntent","Intent is not null");
        }**/

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

        ListView itemLv = (ListView) findViewById(R.id.itemListView);
        itemLv.setAdapter(tableAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
