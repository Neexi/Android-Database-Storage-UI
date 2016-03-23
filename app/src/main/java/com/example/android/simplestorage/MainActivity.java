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
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private DefTable table;
    private TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO : DELETE
        // Base Hardcoded table
        table = new DefTable();
        table.addElements("Name1", 1, "");
        table.addElements("name2name2name2name2name2name2name2name2name2name2name2name2name2", 5, "extra");
        table.addElements("Name3", 2, "verylongextra");
        table.addElements("name4", 8, "extra");
        table.addElements("Name5", 10, "verylongextraverylongextra");
        table.addElements("name6", 500, "extra");


        Integer[] idArray = table.getIdArray();
        Log.v("ID", Arrays.toString(idArray));
        String[] nameArray = table.getNameArray();
        Log.v("Name",Arrays.toString(nameArray));
        Integer[] quantityArray = table.getQuantityArray();
        Log.v("Qty",Arrays.toString(quantityArray));
        String[] extraArray = table.getExtraArray();
        Log.v("Extra", Arrays.toString(extraArray));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tableAdapter = new TableAdapter(this, idArray, nameArray, quantityArray, extraArray);

        ListView itemLv= (ListView) findViewById(R.id.itemListView);
        itemLv.setAdapter(tableAdapter);
        /** TODO : DELETE
        ImageButton button1 = (ImageButton) itemLv.findViewById(R.id.itemPlus1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curQuantity = tableAdapter.getItemQuantity()
            }
        });**/
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

    public DefTable getTable() {
        return table;
    }
}
