package com.example.android.simplestorage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

/**
 * Created by new on 23/03/2016.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private Integer id;
    private String name;
    private Integer quantity;
    private String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        if(intent != null) {
            try {
                String idStr = intent.getStringExtra(getResources().getString(R.string.item_id));
                String name = intent.getStringExtra(getResources().getString(R.string.item_name));
                String quantityStr = intent.getStringExtra(getResources().getString(R.string.item_quantity));
                String extra = intent.getStringExtra(getResources().getString(R.string.item_extra));
                Log.v("IntentExtra",idStr+", "+name+", "+quantityStr+", "+extra);
                this.id = Integer.parseInt(idStr);
                this.name = name;
                this.quantity = Integer.parseInt(quantityStr);
                this.extra = extra;
            } catch(NullPointerException e) {
                Log.e("idError","Intent not properly passed");
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO : FIX
        //getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("DetailBack","Back button is pressed");
        finish();
    }

    private void setView() {

    }
}
