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
    Context context;
    private Integer[] itemId;
    private String[] itemName;
    private Integer[] itemQuantity;
    private String[] itemExtra;
    private static LayoutInflater inflater = null;

    public TableAdapter(Context context, Integer[] itemId, String[] itemName, Integer[] itemQuantity, String[] itemExtra) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemExtra = itemExtra;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private Integer getTableItemId(int position) {
        return itemId[position];
    }
    private String getTableItemName(int position) { return itemName[position]; }
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

        if (itemId[position] % 2 == 0) holder.ll.setBackgroundColor(Color.parseColor("#BDBDBD"));
        else holder.ll.setBackgroundColor(Color.parseColor("#EEEEEE"));

        holder.tvId.setText(itemId[position].toString());
        holder.tvName.setText(itemName[position]);
        holder.tvQuantity.setText(itemQuantity[position].toString());
        holder.tvExtra.setText(itemExtra[position]);

        //For Item Detail Page
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    MainActivity ma = (MainActivity)context;
                    String itemId = getTableItemId(position).toString();
                    Intent intent = new Intent(context, ItemDetailActivity.class)
                            .putExtra(ma.getResources().getString(R.string.item_id), getTableItemId(position).toString())
                            .putExtra(ma.getResources().getString(R.string.item_name), getTableItemName(position))
                            .putExtra(ma.getResources().getString(R.string.item_quantity), getTableItemQuantity(position).toString())
                            .putExtra(ma.getResources().getString(R.string.item_extra), getTableItemExtra(position));
                    context.startActivity(intent);
                }
            }
        });

        //Button for single addition to item quantity
        ibPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    DefTableElements curEle = ((MainActivity) context).getTable().getElements(itemId[position]);
                    int newQuantity = curEle.addQuantity(1);
                    if (newQuantity != context.getResources().getInteger(R.integer.quantity_error)) {
                        itemQuantity[position] = newQuantity;
                        Log.v("Qty", Arrays.toString(((MainActivity) context).getTable().getQuantityArray()));
                        holder.tvQuantity.setText(itemQuantity[position].toString());
                    }
                }
            }
        });

        //Button for single subtraction to item quantity
        ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    DefTableElements curEle = ((MainActivity) context).getTable().getElements(itemId[position]);
                    int newQuantity = curEle.removeQuantity(1);
                    if (newQuantity != context.getResources().getInteger(R.integer.quantity_error)) {
                        itemQuantity[position] = newQuantity;
                        Log.v("Qty", Arrays.toString(((MainActivity) context).getTable().getQuantityArray()));
                        holder.tvQuantity.setText(itemQuantity[position].toString());
                    }
                }
            }
        });
        return rowView;
    }

}
