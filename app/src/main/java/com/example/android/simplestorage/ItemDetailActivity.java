package com.example.android.simplestorage;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    public static class ItemDetailFragment extends Fragment {

        private final int idDigit = 6;

        private Integer _ID;
        private String name;
        private Integer quantity;
        private String extra;
        private String full;

        private Boolean fullMatchesExtra;
        private Boolean nameOnEdit;
        private Boolean extraOnEdit;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                try {
                    setVariable(intent);
                    setView(rootView);
                    setButton(rootView);
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
            this.full = full;

            this.fullMatchesExtra = Boolean.valueOf(isMatch);
            this.nameOnEdit = false;
            this.extraOnEdit = false;
        }

        private void setView(View rootView) {
            TextView idView = (TextView) rootView.findViewById(R.id.itemDetailId);
            TextView nameView = (TextView) rootView.findViewById(R.id.itemDetailName);
            TextView quantityView = (TextView) rootView.findViewById(R.id.itemDetailQuantity);
            TextView extraView = (TextView) rootView.findViewById(R.id.itemDetailExtra);
            TextView fullView = (TextView) rootView.findViewById(R.id.itemDetailExtraFull);

            CheckBox isMatchCB = (CheckBox) rootView.findViewById(R.id.itemDetailIsMatch);

            idView.setText(formatID(_ID));
            nameView.setText(name);
            quantityView.setText(quantity.toString());
            extraView.setText(extra);
            isMatchCB.setChecked(fullMatchesExtra);
            fullView.setText(full);
            if (fullMatchesExtra) {
                fullView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            } else {
                fullView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
        }

        private void setButton(View view) {
            //Setting Name TextField
            final EditText etName = (EditText) view.findViewById(R.id.itemDetailName);
            final ImageButton ibName = (ImageButton) view.findViewById(R.id.itemDetailNameEditButton);
            etName.setInputType(InputType.TYPE_NULL);
            final EditText etExtra = (EditText) view.findViewById(R.id.itemDetailExtra);
            final ImageButton ibExtra = (ImageButton) view.findViewById(R.id.itemDetailExtraEditButton);
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            etExtra.setInputType(InputType.TYPE_NULL);
            ibName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!nameOnEdit) {
                        /**
                        if (extraOnEdit) {
                            extraOnEdit = false;
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            etExtra.setBackground(null);
                            etExtra.setFocusable(false);
                            etExtra.setInputType(InputType.TYPE_NULL);
                            ibExtra.setImageResource(R.drawable.ic_edit_24dp);
                        }**/
                        etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etName.setInputType(InputType.TYPE_CLASS_TEXT);
                        etName.requestFocus();
                        etName.setFocusable(true);
                        etName.setSelection(etName.getText().length());
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibName.setImageResource(R.drawable.ic_done_24dp);
                    } else {
                        etName.setBackground(null);
                        etName.setFocusable(false);
                        etName.setInputType(InputType.TYPE_NULL);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibName.setImageResource(R.drawable.ic_edit_24dp);
                    }
                    nameOnEdit = !nameOnEdit;
                }
            });

            //Setting Extra TextField
            ibExtra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!extraOnEdit) {
                        /**
                        if (nameOnEdit) {
                            nameOnEdit = false;
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            etName.setBackground(null);
                            etName.setFocusable(false);
                            etName.setInputType(InputType.TYPE_NULL);
                            ibName.setImageResource(R.drawable.ic_edit_24dp);
                        }**/
                        etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etExtra.setInputType(InputType.TYPE_CLASS_TEXT);
                        etExtra.requestFocus();
                        etExtra.setFocusable(true);
                        etExtra.setSelection(etExtra.getText().length());
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibExtra.setImageResource(R.drawable.ic_done_24dp);
                    } else {
                        etExtra.setBackground(null);
                        etExtra.setFocusable(false);
                        etExtra.setInputType(InputType.TYPE_NULL);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibExtra.setImageResource(R.drawable.ic_edit_24dp);
                    }
                    extraOnEdit = !extraOnEdit;
                }
            });
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
