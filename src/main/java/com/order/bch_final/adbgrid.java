package com.order.bch_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.ArrayList;

public class adbgrid extends BaseAdapter {
    Context context;

    ArrayList<TableData> list;

    public adbgrid(Context context, ArrayList<TableData> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position).id;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.gradlayout,null);
        TextView textid =view.findViewById(R.id.idtable);
        TextView texttable =view.findViewById(R.id.table);
        textid.setText((list.get(position).id)+"");
        texttable.setText("الطاولة");
        return view;
    }

}
class TableData{
    public String name;
    public String id;
    public TableData(String name, String  id) {
        this.name = name;
        this.id = id;
    }
    public TableData(String  id) {
        this.id = id;
    }
}