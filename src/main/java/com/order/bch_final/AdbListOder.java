package com.order.bch_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdbListOder extends BaseAdapter {
    TextView name,num,price;
    Context context;
    DataBaseOrder dataBaseOrder;
    ArrayList<Data> list;
    AdbListOder adbListOder;




    public AdbListOder(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
        this.adbListOder=this;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final View view= LayoutInflater.from(context).inflate(R.layout.show_order_final,null);
        name=view.findViewById(R.id.or_name_text);
        price=view.findViewById(R.id.or_price);
        num=view.findViewById(R.id.or_num);
        ImageView but=view.findViewById(R.id.but_del);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseOrder=new DataBaseOrder(context);
                dataBaseOrder.DeleteOne(name.getText().toString());

            }
        });
        name.setText(list.get(position).name);
        price.setText(list.get(position).price);
        num.setText(list.get(position).num);
        return view;
    }

   static class Data {
        String name,num,price;
        public Data(String name, String price, String num) {
            this.name = name;
            this.num = num;
            this.price = price;
        }
    }

}
