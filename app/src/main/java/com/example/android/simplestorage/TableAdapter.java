package com.example.android.simplestorage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by new on 22/03/2016.
 */
public class TableAdapter extends BaseAdapter {
    MainTableFragment mtf;
    private Integer[] itemId;
    private String[] itemName;
    private Integer[] itemQuantity;
    private String[] itemExtra;
    private static LayoutInflater inflater = null;

    public TableAdapter(MainTableFragment mtf, Integer[] itemId, String[] itemName, Integer[] itemQuantity, String[] itemExtra) {
        // TODO Auto-generated constructor stub
        this.mtf = mtf;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemExtra = itemExtra;
        inflater = (LayoutInflater) mtf.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void editEntry(Integer itemId, String itemName, Integer itemQuantity, String itemExtra) {
        int position = getPositionOfId(itemId);
        this.itemName[position] = itemName;
        this.itemQuantity[position] = itemQuantity;
        this.itemExtra[position] = itemExtra;
    }

    @Override
    public int getCount() {
        return itemId.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Integer getPositionOfId(int _ID) {
        return Arrays.asList(itemId).indexOf(_ID);
    }

    private Integer getTableItemId(int position) {
        return itemId[position];
    }

    private String getTableItemName(int position) {
        return itemName[position];
    }

    private Integer getTableItemQuantity(int position) {
        return itemQuantity[position];
    }

    private String getTableItemExtra(int position) {
        return itemExtra[position];
    }


    public class Holder {
        LinearLayout ll;
        TextView tvId;
        TextView tvName;
        TextView tvQuantity;
        TextView tvExtra;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_list, null);
        holder.ll = (LinearLayout) rowView.findViewById(R.id.itemLayout1);
        holder.tvId = (TextView) rowView.findViewById(R.id.itemId1);
        holder.tvName = (TextView) rowView.findViewById(R.id.itemName1);
        holder.tvQuantity = (TextView) rowView.findViewById(R.id.itemQuantity1);
        holder.tvExtra = (TextView) rowView.findViewById(R.id.itemExtra1);
        ImageButton ibPlus = (ImageButton) rowView.findViewById(R.id.itemPlus1);
        ImageButton ibMinus = (ImageButton) rowView.findViewById(R.id.itemMinus1);

        if (itemId[position] % 2 == 0) holder.ll.setBackgroundColor(Color.parseColor("#BBDEFB"));
        else holder.ll.setBackgroundColor(Color.parseColor("#E3F2FD"));

        holder.tvId.setText(itemId[position].toString());
        holder.tvName.setText(itemName[position]);
        holder.tvQuantity.setText(itemQuantity[position].toString());
        holder.tvExtra.setText(itemExtra[position]);

        //For Item Detail Page
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer itemId = getTableItemId(position);
                DefTableEntry entry = mtf.getTable().getEntry(itemId);
                Intent intent = new Intent(mtf.getActivity(), ItemDetailActivity.class)
                        .putExtra(mtf.getResources().getString(R.string.item_id), itemId.toString())
                        .putExtra(mtf.getResources().getString(R.string.item_name), getTableItemName(position))
                        .putExtra(mtf.getResources().getString(R.string.item_quantity), getTableItemQuantity(position).toString())
                        .putExtra(mtf.getResources().getString(R.string.item_extra), getTableItemExtra(position))
                        .putExtra(mtf.getResources().getString(R.string.item_is_match), String.valueOf(entry.getIsMatch()))
                        .putExtra(mtf.getResources().getString(R.string.item_full), entry.getFull());
                mtf.startActivityForResult(intent,1);
            }
        });

        //Button for single addition to item quantity
        ibPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefTableEntry curEle = mtf.getTable().getEntry(itemId[position]);
                int newQuantity = curEle.addQuantity(1);
                if (newQuantity != mtf.getResources().getInteger(R.integer.quantity_error)) {
                    itemQuantity[position] = newQuantity;
                    Log.v("Qty", Arrays.toString(mtf.getTable().getQuantityArray()));
                    holder.tvQuantity.setText(itemQuantity[position].toString());
                }
            }
        });

        //Button for single subtraction to item quantity
        ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefTableEntry curEle = mtf.getTable().getEntry(itemId[position]);
                int newQuantity = curEle.removeQuantity(1);
                if (newQuantity != mtf.getResources().getInteger(R.integer.quantity_error)) {
                    itemQuantity[position] = newQuantity;
                    Log.v("Qty", Arrays.toString(mtf.getTable().getQuantityArray()));
                    holder.tvQuantity.setText(itemQuantity[position].toString());
                }
            }
        });
        return rowView;
    }

}
