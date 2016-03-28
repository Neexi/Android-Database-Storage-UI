package com.example.android.simplestorage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    public void onDestroy() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onDestroy();
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

        //All view
        TextView tvId;
        EditText etName;
        TextView tvQuantity;
        EditText etExtra;
        TextView tvFull;

        CheckBox isMatchCB;

        ImageButton ibName;
        ImageButton ibExtra;
        ImageButton ibQuantity;

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
            tvId = (TextView) rootView.findViewById(R.id.itemDetailId);
            etName = (EditText) rootView.findViewById(R.id.itemDetailName);
            tvQuantity = (TextView) rootView.findViewById(R.id.itemDetailQuantity);
            etExtra = (EditText) rootView.findViewById(R.id.itemDetailExtra);
            tvFull = (TextView) rootView.findViewById(R.id.itemDetailExtraFull);

            isMatchCB = (CheckBox) rootView.findViewById(R.id.itemDetailIsMatch);

            tvId.setText(formatID(_ID));
            etName.setText(name);
            tvQuantity.setText(quantity.toString());
            etExtra.setText(extra);
            isMatchCB.setChecked(fullMatchesExtra);
            tvFull.setText(full);
            if (fullMatchesExtra) {
                tvFull.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            } else {
                tvFull.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
        }

        private void setButton(View view) {
            //Prep
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            ibName = (ImageButton) view.findViewById(R.id.itemDetailNameEditButton);
            etName.setInputType(InputType.TYPE_NULL);
            ibExtra = (ImageButton) view.findViewById(R.id.itemDetailExtraEditButton);
            etExtra.setInputType(InputType.TYPE_NULL);
            ibQuantity = (ImageButton) view.findViewById(R.id.itemDetailQuantityEditButton);

            //Setting Button to Edit Name TextField
            ibName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!nameOnEdit) {
                        if (extraOnEdit) {
                            extraOnEdit = false;
                            etExtra.clearFocus();
                            etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                            etExtra.setInputType(InputType.TYPE_NULL);
                            etExtra.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            ibExtra.setImageResource(R.drawable.ic_edit_24dp);
                        }
                        etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etName.setInputType(InputType.TYPE_CLASS_TEXT);
                        etName.requestFocus();
                        etName.setSelection(etName.getText().length());
                        etName.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibName.setImageResource(R.drawable.ic_done_24dp);
                    } else {
                        etName.clearFocus();
                        etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                        etName.setInputType(InputType.TYPE_NULL);
                        etName.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibName.setImageResource(R.drawable.ic_edit_24dp);
                    }
                    nameOnEdit = !nameOnEdit;
                }
            });

            //Setting Button to Edit Extra TextField
            ibExtra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!extraOnEdit) {
                        if (nameOnEdit) {
                            nameOnEdit = false;
                            etName.clearFocus();
                            etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                            etName.setInputType(InputType.TYPE_NULL);
                            etName.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            ibName.setImageResource(R.drawable.ic_edit_24dp);
                        }
                        etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etExtra.setInputType(InputType.TYPE_CLASS_TEXT);
                        etExtra.requestFocus();
                        etExtra.setSelection(etExtra.getText().length());
                        etExtra.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibExtra.setImageResource(R.drawable.ic_done_24dp);
                    } else {
                        etExtra.clearFocus();
                        etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                        etExtra.setInputType(InputType.TYPE_NULL);
                        etExtra.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibExtra.setImageResource(R.drawable.ic_edit_24dp);
                    }
                    extraOnEdit = !extraOnEdit;
                }
            });

            //Setting Button to Edit Quantity TextField
            ibQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = new Dialog(getContext());
                    d.setContentView(R.layout.dialog_item_detail);
                    d.setTitle("NumberPicker");
                    TextView curQuantity= (TextView) d.findViewById(R.id.itemDetailDialogQuantity);
                    curQuantity.setText(quantity.toString());
                    ImageButton cancelButton = (ImageButton) d.findViewById(R.id.itemDetailDialogCancel);
                    Button removeButton = (Button) d.findViewById(R.id.itemDetailDialogRemove);
                    Button addButton = (Button) d.findViewById(R.id.itemDetailDialogAdd);
                    final NumberPicker[] np = new NumberPicker[3];
                    np[0] = (NumberPicker) d.findViewById(R.id.itemDetailNumberPicker1);
                    np[1] = (NumberPicker) d.findViewById(R.id.itemDetailNumberPicker2);
                    np[2] = (NumberPicker) d.findViewById(R.id.itemDetailNumberPicker3);
                    for (int i = 0; i < 3; i++) {
                        np[i].setMaxValue(9);
                        np[i].setMinValue(0);
                        setDividerColor(np[i], ContextCompat.getColor(getContext(), R.color.colorPrimaryLight));
                        np[i].setWrapSelectorWheel(false);
                    }
                    np[2].setValue(1);

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                    removeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer curNpVal = getNumberPickerValue(np);
                            Log.v("curVal",curNpVal.toString());
                            if (quantity - curNpVal >= getResources().getInteger(R.integer.item_minimum_quantity)) {
                                quantity -= curNpVal;
                                tvQuantity.setText(quantity.toString());
                                d.dismiss();
                            } else {
                                Toast toast = Toast.makeText(getContext(),
                                        "Item quantity cannot be less than " + getResources().getInteger(R.integer.item_minimum_quantity),
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer curNpVal = getNumberPickerValue(np);
                            Log.v("curVal",curNpVal.toString());
                            if(quantity + curNpVal <= getResources().getInteger(R.integer.item_maximum_quantity)) {
                                quantity += curNpVal;
                                tvQuantity.setText(quantity.toString());
                                d.dismiss();
                            } else {
                                Toast toast = Toast.makeText(getContext(),
                                        "Item quantity cannot be more than "+getResources().getInteger(R.integer.item_maximum_quantity),
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                    d.show();
                }
            });
        }

        private int getNumberPickerValue(NumberPicker[] picker) {
            return picker[0].getValue() * 100 + picker[1].getValue() * 10 + picker[2].getValue();
        }

        private void setDividerColor(NumberPicker picker, int color) {

            java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (java.lang.reflect.Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        ColorDrawable colorDrawable = new ColorDrawable(color);
                        pf.set(picker, colorDrawable);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
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
