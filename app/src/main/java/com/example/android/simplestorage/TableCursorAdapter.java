package com.example.android.simplestorage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by new on 8/04/2016.
 */
public class TableCursorAdapter extends CursorAdapter {
    private MainTableFragment mtf;

    public TableCursorAdapter(MainTableFragment mtf, Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mtf = mtf;
    }

    public class Holder {
        LinearLayout ll;
        TextView tvId;
        TextView tvName;
        TextView tvQuantity;
        TextView tvExtra;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Holder holder = new Holder();
        final Integer curId = cursor.getInt(MainTableFragment.COL_ID);
        final String curName = cursor.getString(MainTableFragment.COL_NAME);
        final Integer curQuantity = cursor.getInt(MainTableFragment.COL_QUANTITY);
        final String curExtra = cursor.getString(MainTableFragment.COL_EXTRA);
        final String curFull = cursor.getString(MainTableFragment.COL_FULL);
        final Integer curFullMatchesExtra = cursor.getInt(MainTableFragment.COL_FULL_MATCHES_EXTRA);
        holder.ll = (LinearLayout) view.findViewById(R.id.itemLayout1);
        holder.tvId = (TextView) view.findViewById(R.id.itemId1);
        holder.tvName = (TextView) view.findViewById(R.id.itemName1);
        holder.tvQuantity = (TextView) view.findViewById(R.id.itemQuantity1);
        holder.tvExtra = (TextView) view.findViewById(R.id.itemExtra1);

        ImageButton ibPlus = (ImageButton) view.findViewById(R.id.itemPlus1);
        ImageButton ibMinus = (ImageButton) view.findViewById(R.id.itemMinus1);

        if (curId % 2 == 0) holder.ll.setBackgroundColor(Color.parseColor("#BBDEFB"));
        else holder.ll.setBackgroundColor(Color.parseColor("#E3F2FD"));

        holder.tvId.setText(curId.toString());
        holder.tvName.setText(curName);
        holder.tvQuantity.setText(curQuantity.toString());
        holder.tvExtra.setText(curExtra);

        /**
        //For Item Detail Page
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDetailActivity.class)
                        .putExtra(v.getResources().getString(R.string.item_id), curId.toString())
                        .putExtra(v.getResources().getString(R.string.item_name), curName)
                        .putExtra(v.getResources().getString(R.string.item_quantity), curQuantity.toString())
                        .putExtra(v.getResources().getString(R.string.item_extra), curExtra)
                        .putExtra(v.getResources().getString(R.string.item_is_match), curFullMatchesExtra.toString())
                        .putExtra(v.getResources().getString(R.string.item_full), curFull)
                        .putExtra(v.getResources().getString(R.string.item_detail_new), String.valueOf(false));
                mtf.startActivityForResult(intent, 1);
            }
        });

        //Button for single addition to item quantity
        ibPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefTableEntry curEle = mtf.getTable().getEntry(itemIds[position]);
                int newQuantity = curEle.addQuantity(1);
                if (newQuantity != mtf.getResources().getInteger(R.integer.quantity_error)) {
                    itemQuantities[position] = newQuantity;
                    Log.v("Qty", Arrays.toString(mtf.getTable().getQuantityArray()));
                    holder.tvQuantity.setText(itemQuantities[position].toString());
                }
            }
        });

        //Button for single subtraction to item quantity
        ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefTableEntry curEle = mtf.getTable().getEntry(itemIds[position]);
                int newQuantity = curEle.removeQuantity(1);
                if (newQuantity != mtf.getResources().getInteger(R.integer.quantity_error)) {
                    itemQuantities[position] = newQuantity;
                    Log.v("Qty", Arrays.toString(mtf.getTable().getQuantityArray()));
                    holder.tvQuantity.setText(itemQuantities[position].toString());
                }
            }
        });
         **/
    }
}
