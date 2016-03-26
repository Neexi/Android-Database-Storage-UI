package com.example.android.simplestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by new on 23/03/2016.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ItemDetailFragment())
                    .commit();
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
        Log.v("DetailBack", "Back button is pressed");
        finish();
    }

    public static class ItemDetailFragment extends Fragment {

        private final int idDigit = 6;

        private Integer _ID;
        private String name;
        private Integer quantity;
        private String extra;
        private Boolean isMatch;
        private String full;

        TextView idView;
        TextView nameView;
        TextView quantityView;
        TextView extraView;
        CheckBox isMatchCB;
        TextView fullView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                try {
                    setVariable(intent);
                    setView(rootView);
                } catch (NullPointerException e) {
                    Log.e("idError", "Intent not properly passed");
                    getActivity().finish();
                }
            }
            return rootView;
        }

        private void setVariable(Intent intent) throws NullPointerException {
            String idStr = intent.getStringExtra(getResources().getString(R.string.item_id));
            String name = intent.getStringExtra(getResources().getString(R.string.item_name));
            String quantityStr = intent.getStringExtra(getResources().getString(R.string.item_quantity));
            String extra = intent.getStringExtra(getResources().getString(R.string.item_extra));
            String isMatch = intent.getStringExtra(getResources().getString(R.string.item_is_match));
            String full = intent.getStringExtra(getResources().getString(R.string.item_full));
            Log.v("IntentExtra", idStr + ", " + name + ", " + quantityStr + ", " + extra + ", " + isMatch + ", " + full);
            this._ID = Integer.parseInt(idStr);
            this.name = name;
            this.quantity = Integer.parseInt(quantityStr);
            this.extra = extra;
            this.isMatch = Boolean.valueOf(isMatch);
            this.full = full;
        }

        private void setView(View rootView) {
            idView = (TextView) rootView.findViewById(R.id.itemDetailId);
            nameView = (TextView) rootView.findViewById(R.id.itemDetailName);
            quantityView = (TextView) rootView.findViewById(R.id.itemDetailQuantity);
            extraView = (TextView) rootView.findViewById(R.id.itemDetailExtra);
            isMatchCB = (CheckBox) rootView.findViewById(R.id.itemDetailIsMatch);
            fullView = (TextView) rootView.findViewById(R.id.itemDetailExtraFull);
            idView.setText(formatID(_ID));
            nameView.setText(name);
            quantityView.setText(quantity.toString());
            extraView.setText(extra);
            isMatchCB.setChecked(isMatch);
            fullView.setText(full);
            if (isMatch) {
                fullView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            } else {
                fullView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
        }

        private String formatID(Integer _ID) {
            String result = "#";
            int numDigit = String.valueOf(_ID).length();
            while (numDigit < idDigit) {
                result += "0";
                numDigit++;
            }
            result += _ID;
            return result;
        }
    }
}
