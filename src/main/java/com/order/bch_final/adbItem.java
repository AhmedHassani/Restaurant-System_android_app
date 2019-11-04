package com.order.bch_final;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;

public class adbItem extends BaseAdapter {
    ArrayList<DataItems> dataItems;
    Context context;
    DataBaseOrder dataBaseOrder;

    public adbItem(ArrayList<DataItems> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItems.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view =LayoutInflater.from(context).inflate(R.layout.view_list,null);
        final TextView name=view.findViewById(R.id.name);
        final TextView price=view.findViewById(R.id.price);
        final TextView type=view.findViewById(R.id.type);
        Button button=view.findViewById(R.id.but);
        name.setText(dataItems.get(position).name);
        price.setText(dataItems.get(position).price);
        type.setText(dataItems.get(position).type);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseOrder=new DataBaseOrder(context);

                if (isFoundItem(name.getText().toString())) {
                    String t=name.getText().toString();
                    String num= dataBaseOrder.numItem(name.getText().toString());
                    int  new_num=Integer.parseInt(num)+1;
                    Double p=Double.parseDouble(price.getText().toString());
                    dataBaseOrder.upDataNum(name.getText().toString(),(p*new_num)+"", new_num+"");
                    Toast.makeText(context, " تم الاظافة "+new_num+" "+t, Toast.LENGTH_SHORT).show();
                }else {
                    String t=name.getText().toString();
                    dataBaseOrder.setOrder(name.getText().toString(),price.getText().toString(),"1");
                    Toast.makeText(context, "تم الاظافة 1 "+t, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public boolean isFoundItem(String Item){

            dataBaseOrder=new DataBaseOrder(context);
            ArrayList<AdbListOder.Data>  list=dataBaseOrder.getOrder();
            for (int i=0;i<list.size();i++){
                if (Item.equals(list.get(i).name))
                    return  true;
            }
        return false;
    }

    public static class DataItems{
        String name,price,type;
        public DataItems(String name, String price,String type) {
            this.name = name;
            this.price = price;
            this.type=type;

        }


    }
}
