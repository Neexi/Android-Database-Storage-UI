package com.example.android.simplestorage;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by new on 22/03/2016.
 */
public class TableAdapter extends BaseAdapter {
    String[] result;
    Context context;
    int[] imageId;
    private Integer[] itemId;
    private String[] itemName;
    private Integer[] itemQuantity;
    private String[] itemExtra;
    private static LayoutInflater inflater = null;

    public TableAdapter(MainActivity mainActivity, Integer[] itemId, String[] itemName, Integer[] itemQuantity, String[] itemExtra) {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemExtra = itemExtra;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemId.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public Integer getItemQuantity(int position) {
        return itemQuantity[position];
    }


    public class Holder {
        LinearLayout ll;
        TextView tvId;
        TextView tvName;
        TextView tvQuantity;
        TextView tvExtra;
        ImageButton ibPlus;
        ImageButton ibMinus;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_list, null);
        holder.ll = (LinearLayout) rowView.findViewById(R.id.itemLayout1);
        holder.tvId = (TextView) rowView.findViewById(R.id.itemId1);
        holder.tvName = (TextView) rowView.findViewById(R.id.itemName1);
        holder.tvQuantity = (TextView) rowView.findViewById(R.id.itemQuantity1);
        holder.tvExtra = (TextView) rowView.findViewById(R.id.itemExtra1);
        holder.ibPlus = (ImageButton) rowView.findViewById(R.id.itemPlus1);
        holder.ibMinus = (ImageButton) rowView.findViewById(R.id.itemMinus1);
        ImageButton ibPlus1 = (ImageButton) rowView.findViewById(R.id.itemPlus1);

        if(itemId[position] % 2 == 0) holder.ll.setBackgroundColor(Color.parseColor("#BDBDBD"));
        else holder.ll.setBackgroundColor(Color.parseColor("#EEEEEE"));

        holder.tvId.setText(itemId[position].toString());
        holder.tvName.setText(itemName[position]);
        holder.tvQuantity.setText(itemQuantity[position].toString());
        holder.tvExtra.setText(itemExtra[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + itemName[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
