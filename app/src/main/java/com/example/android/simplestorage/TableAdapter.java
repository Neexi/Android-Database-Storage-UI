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
    private Integer[] itemIds;
    private String[] itemNames;
    private Integer[] itemQuantities;
    private String[] itemExtras;
    private static LayoutInflater inflater = null;

    public TableAdapter(MainTableFragment mtf, Integer[] itemId, String[] itemName, Integer[] itemQuantity, String[] itemExtra) {
        // TODO Auto-generated constructor stub
        this.mtf = mtf;
        this.itemIds = itemId;
        this.itemNames = itemName;
        this.itemQuantities = itemQuantity;
        this.itemExtras = itemExtra;
        inflater = (LayoutInflater) mtf.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //TODO : Implement better method
    public int addEntry(Integer itemId, String itemName, Integer itemQuantity, String itemExtra) {
        int position = getPositionOfId(itemId);
        if(position == -1) {
            int newSize = this.itemIds.length + 1;
            Integer[] itemIds = new Integer[newSize];
            String[] itemNames = new String[newSize];
            Integer[] itemQuantities = new Integer[newSize];
            String[] itemExtras = new String[newSize];
            for(int i = 0; i < newSize - 1; i++) {
                itemIds[i] = this.itemIds[i];
                itemNames[i] = this.itemNames[i];
                itemQuantities[i] = this.itemQuantities[i];
                itemExtras[i] = this.itemExtras[i];
            }
            itemIds[newSize - 1] = itemId;
            itemNames[newSize - 1] = itemName;
            itemQuantities[newSize - 1] = itemQuantity;
            itemExtras[newSize - 1] = itemExtra;
            this.itemIds = itemIds;
            this.itemNames = itemNames;
            this.itemQuantities = itemQuantities;
            this.itemExtras = itemExtras;

            return itemId;
        }
        return -1;
    }

    public int editEntry(Integer itemId, String itemName, Integer itemQuantity, String itemExtra) {
        int position = getPositionOfId(itemId);
        if(position != -1) {
            this.itemNames[position] = itemName;
            this.itemQuantities[position] = itemQuantity;
            this.itemExtras[position] = itemExtra;
        }
        return position;
    }

    @Override
    public int getCount() {
        return itemIds.length;
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
        return Arrays.asList(itemIds).indexOf(_ID);
    }

    private Integer getTableItemId(int position) {
        return itemIds[position];
    }

    private String getTableItemName(int position) {
        return itemNames[position];
    }

    private Integer getTableItemQuantity(int position) {
        return itemQuantities[position];
    }

    private String getTableItemExtra(int position) {
        return itemExtras[position];
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

        if (itemIds[position] % 2 == 0) holder.ll.setBackgroundColor(Color.parseColor("#BBDEFB"));
        else holder.ll.setBackgroundColor(Color.parseColor("#E3F2FD"));

        holder.tvId.setText(itemIds[position].toString());
        holder.tvName.setText(itemNames[position]);
        holder.tvQuantity.setText(itemQuantities[position].toString());
        holder.tvExtra.setText(itemExtras[position]);

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
                        .putExtra(mtf.getResources().getString(R.string.item_full), entry.getFull())
                        .putExtra(mtf.getResources().getString(R.string.item_detail_new), String.valueOf(false));
                mtf.startActivityForResult(intent,1);
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
        return rowView;
    }

}
