package com.example.android.simplestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by new on 23/03/2016.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private Integer _ID;
    private String name;
    private Integer quantity;
    private String extra;
    private Boolean isMatch;
    private String full;

    private final int idDigit = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        if(intent != null) {
            try {
                setVariable(intent);
                setView();
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

    private void setVariable(Intent intent) throws NullPointerException {
        String idStr = intent.getStringExtra(getResources().getString(R.string.item_id));
        String name = intent.getStringExtra(getResources().getString(R.string.item_name));
        String quantityStr = intent.getStringExtra(getResources().getString(R.string.item_quantity));
        String extra = intent.getStringExtra(getResources().getString(R.string.item_extra));
        String isMatch = intent.getStringExtra(getResources().getString(R.string.item_is_match));
        String full = intent.getStringExtra(getResources().getString(R.string.item_full));
        Log.v("IntentExtra",idStr+", "+name+", "+quantityStr+", "+extra+", "+isMatch+", "+full);
        this._ID = Integer.parseInt(idStr);
        this.name = name;
        this.quantity = Integer.parseInt(quantityStr);
        this.extra = extra;
        this.isMatch = Boolean.valueOf(isMatch);
        this.full = full;
    }

    private void setView() {
        TextView idView = (TextView)findViewById(R.id.itemDetailId);
        TextView nameView = (TextView)findViewById(R.id.itemDetailName);
        TextView quantityView = (TextView)findViewById(R.id.itemDetailQuantity);
        TextView extraView = (TextView)findViewById(R.id.itemDetailExtra);
        CheckBox isMatchCB = (CheckBox)findViewById(R.id.itemDetailIsMatch);
        TextView fullView = (TextView)findViewById(R.id.itemDetailExtraFull);
        idView.setText(formatID(_ID));
        nameView.setText(name);
        quantityView.setText(quantity.toString());
        extraView.setText(extra);
        isMatchCB.setChecked(isMatch);
        fullView.setText(full);
        if(isMatch) {
            fullView.setTextColor(ContextCompat.getColor(this, R.color.grey));
        } else {
            fullView.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    private String formatID(Integer _ID) {
        String result = "#";
        int numDigit = String.valueOf(_ID).length();
        while(numDigit < idDigit) {
            result += "0";
            numDigit++;
        }
        result += _ID;
        return result;
    }
}
