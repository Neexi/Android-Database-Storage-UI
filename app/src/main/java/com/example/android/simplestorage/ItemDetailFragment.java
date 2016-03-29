package com.example.android.simplestorage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by new on 29/03/2016.
 */
public class ItemDetailFragment extends Fragment {

    private final int idDigit = 6;

    private Integer _ID;
    private String name;
    private Integer quantity;
    private String extra;
    private String full;

    private boolean isNew;
    private Boolean fullMatchesExtra;
    private Boolean oneOnEdit;
    private Boolean nameOnEdit;
    private Boolean extraOnEdit;
    private Boolean fullOnEdit;

    //All view
    TextView tvId;
    EditText etName;
    TextView tvQuantity;
    EditText etExtra;
    EditText etFull;
    ScrollView fullContainer;

    CheckBox isMatchCB;
    ImageButton ibName;
    ImageButton ibExtra;
    ImageButton ibQuantity;
    ImageButton ibFull;
    Button save;

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
        String isNewStr = intent.getStringExtra(getResources().getString(R.string.item_detail_new));
        Log.v("IntentExtra", idStr + ", " + name + ", " + quantityStr + ", " + extra + ", " + isMatch + ", " + full);
        this._ID = Integer.parseInt(idStr);
        this.name = name;
        this.quantity = Integer.parseInt(quantityStr);
        this.extra = extra;
        this.full = full;

        this.isNew = Boolean.valueOf(isNewStr);
        this.fullMatchesExtra = Boolean.valueOf(isMatch);
        this.oneOnEdit = false;
        this.nameOnEdit = false;
        this.extraOnEdit = false;
        this.fullOnEdit = false;

    }

    private void setView(View rootView) {
        tvId = (TextView) rootView.findViewById(R.id.itemDetailId);
        etName = (EditText) rootView.findViewById(R.id.itemDetailName);
        tvQuantity = (TextView) rootView.findViewById(R.id.itemDetailQuantity);
        etExtra = (EditText) rootView.findViewById(R.id.itemDetailExtra);
        etFull = (EditText) rootView.findViewById(R.id.itemDetailExtraFull);

        isMatchCB = (CheckBox) rootView.findViewById(R.id.itemDetailIsMatch);

        if(isNew) {
            tvId.setText(formatID(_ID)+" (New)");
        } else {
            tvId.setText(formatID(_ID));
        }
        etName.setText(name);
        tvQuantity.setText(quantity.toString());
        etExtra.setText(extra);
        isMatchCB.setChecked(fullMatchesExtra);
        etFull.setText(full);
        if (fullMatchesExtra) {
            etFull.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        } else {
            etFull.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
    }

    private void setButton(View view) {
        //Prep
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        ibName = (ImageButton) view.findViewById(R.id.itemDetailNameEditButton);
        etName.setInputType(InputType.TYPE_NULL);
        ibExtra = (ImageButton) view.findViewById(R.id.itemDetailExtraEditButton);
        etExtra.setInputType(InputType.TYPE_NULL);
        ibFull = (ImageButton) view.findViewById(R.id.itemDetailFullEditButton);
        if (fullMatchesExtra) { ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight)); }
        else { ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary)); }
        etFull.setInputType(InputType.TYPE_NULL);
        fullContainer = (ScrollView) view.findViewById(R.id.itemDetailFullContainer);
        ibQuantity = (ImageButton) view.findViewById(R.id.itemDetailQuantityEditButton);
        save = (Button) view.findViewById(R.id.itemDetailSave);

        //Setting Button to Edit Name TextField
        //SetNameEdit
        ibName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameOnEdit) {
                    if (!oneOnEdit) {
                        oneOnEdit = !oneOnEdit;
                        nameOnEdit = !nameOnEdit;
                        disableAllEditButton();
                        etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etName.setInputType(InputType.TYPE_CLASS_TEXT);
                        etName.requestFocus();
                        etName.setSelection(etName.getText().length());
                        etName.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibName.setImageResource(R.drawable.ic_done_24dp);
                    }
                } else {
                    name = etName.getText().toString();
                    etName.clearFocus();
                    etName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                    etName.setInputType(InputType.TYPE_NULL);
                    etName.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ibName.setImageResource(R.drawable.ic_edit_24dp);
                    enableAllEditButton();
                    nameOnEdit = !nameOnEdit;
                    oneOnEdit = !oneOnEdit;
                }
            }
        });

        //Setting Button to Edit Extra TextField
        //SetExtraEdit
        ibExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!extraOnEdit) {
                    if (!oneOnEdit) {
                        oneOnEdit = !oneOnEdit;
                        extraOnEdit = !extraOnEdit;
                        disableAllEditButton();
                        etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                        etExtra.setInputType(InputType.TYPE_CLASS_TEXT);
                        etExtra.requestFocus();
                        etExtra.setSelection(etExtra.getText().length());
                        etExtra.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        ibExtra.setImageResource(R.drawable.ic_done_24dp);
                    }
                } else {
                    extra = etExtra.getText().toString();
                    if(fullMatchesExtra) {
                        full = extra;
                        etFull.setText(full);
                    }
                    etExtra.clearFocus();
                    etExtra.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                    etExtra.setInputType(InputType.TYPE_NULL);
                    etExtra.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ibExtra.setImageResource(R.drawable.ic_edit_24dp);
                    enableAllEditButton();
                    extraOnEdit = !extraOnEdit;
                    oneOnEdit = !oneOnEdit;
                }
            }
        });

        //SetFullEdit
        ibFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fullMatchesExtra) {
                    if (!fullOnEdit) {
                        if (!oneOnEdit) {
                            oneOnEdit = !oneOnEdit;
                            fullOnEdit = !fullOnEdit;
                            disableAllEditButton();
                            fullContainer.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
                            etFull.setInputType(InputType.TYPE_CLASS_TEXT);
                            etFull.requestFocus();
                            etFull.setSelection(etFull.getText().length());
                            etFull.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                            ibFull.setImageResource(R.drawable.ic_done_24dp);
                        }
                    } else {
                        full = etFull.getText().toString();
                        etFull.clearFocus();
                        fullContainer.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                        etFull.setInputType(InputType.TYPE_NULL);
                        etFull.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibFull.setImageResource(R.drawable.ic_edit_24dp);
                        enableAllEditButton();
                        fullOnEdit = !fullOnEdit;
                        oneOnEdit = !oneOnEdit;
                    }
                }
            }
        });

        //Setting Button to Edit Quantity TextField
        //SetQuantityEdit
        ibQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!oneOnEdit) {
                    final Dialog d = new Dialog(getContext());
                    d.setContentView(R.layout.dialog_item_detail);
                    d.setTitle("NumberPicker");
                    TextView curQuantity = (TextView) d.findViewById(R.id.itemDetailDialogQuantity);
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
                            Log.v("curVal", curNpVal.toString());
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
                            Log.v("curVal", curNpVal.toString());
                            if (quantity + curNpVal <= getResources().getInteger(R.integer.item_maximum_quantity)) {
                                quantity += curNpVal;
                                tvQuantity.setText(quantity.toString());
                                d.dismiss();
                            } else {
                                Toast toast = Toast.makeText(getContext(),
                                        "Item quantity cannot be more than " + getResources().getInteger(R.integer.item_maximum_quantity),
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                    d.show();
                }
            }
        });

        //SetMatchCheckBox
        isMatchCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fullMatchesExtra = isChecked;
                if (fullMatchesExtra) {
                    if (fullOnEdit) {
                        fullOnEdit = false;
                        etFull.clearFocus();
                        fullContainer.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back_light));
                        etFull.setInputType(InputType.TYPE_NULL);
                        etFull.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        ibFull.setImageResource(R.drawable.ic_edit_24dp);
                    }
                    ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight));
                    full = extra;
                    etFull.setText(etExtra.getText());
                } else {
                    ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            }
        });

        //SetSave
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.trim().length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(getResources().getString(R.string.item_id), _ID.toString());
                    intent.putExtra(getResources().getString(R.string.item_name), name);
                    intent.putExtra(getResources().getString(R.string.item_quantity), quantity.toString());
                    intent.putExtra(getResources().getString(R.string.item_extra), extra);
                    intent.putExtra(getResources().getString(R.string.item_full), full);
                    intent.putExtra(getResources().getString(R.string.item_is_match), fullMatchesExtra.toString());
                    getActivity().setResult(getActivity().RESULT_OK, intent);
                    getActivity().finish();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Name must not be empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void enableAllEditButton() {
        ibName.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ibQuantity.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ibExtra.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        if(!fullMatchesExtra) ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        etName.setFocusableInTouchMode(true);
        etExtra.setFocusableInTouchMode(true);
        etFull.setFocusableInTouchMode(true);
        isMatchCB.setClickable(true);
        save.setClickable(true);
    }

    private void disableAllEditButton() {
        if(!nameOnEdit) {
            ibName.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight));
            etName.setFocusable(false);
        }
        ibQuantity.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight));
        if(!extraOnEdit) {
            ibExtra.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight));
            etExtra.setFocusable(false);
        }
        if(!fullOnEdit) {
            ibFull.setColorFilter(ContextCompat.getColor(getContext(), R.color.greyLight));
            etFull.setFocusable(false);
        }
        isMatchCB.setClickable(false);
        save.setClickable(false);
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
