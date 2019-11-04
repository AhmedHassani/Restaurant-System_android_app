package com.order.bch_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class oldorder extends AppCompatActivity {
    GridView gridView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldorder);
        gridView=findViewById(R.id.grid_tables);
        sharedPreferences=getSharedPreferences("Seting", Context.MODE_PRIVATE);
        ArrayList<TableData> list =new DataBaseTable(this).getTables();
        gridView.setAdapter(new adbgrid(this,list));
        sharedOrder=getSharedPreferences("Old_Order", Context.MODE_PRIVATE);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor =sharedOrder.edit();
                TextView textView =view.findViewById(R.id.idtable);
                //Go(textView.getText().toString());
                String val =sharedOrder.getString(textView.getText().toString(),"0");
                Log.d("sharedOrder=>>>>>>>>>>:",textView.getText().toString()+":"+val);
                Toast.makeText(getBaseContext(),"جاري انشاء وصل جديد ..........",Toast.LENGTH_LONG).show();
                if(val.equals("0")){
                    ServerSales.ID_ORDER="";
                    ServerSales  serverSales =new ServerSales(getBaseContext());
                    serverSales.IP=sharedPreferences.getString("IP","192.168.0.140");
                    serverSales.start();

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                    if(ServerSales.ID_ORDER.equals("")) {
                        Toast.makeText(getBaseContext(),"تاكد من الاتصال",Toast.LENGTH_LONG).show();
                    }else {
                        String id_order = ServerSales.ID_ORDER;
                        String g = textView.getText().toString();
                        editor.putString(g, id_order);
                        editor.commit();
                        Go(textView.getText().toString(), id_order);
                    }
                }else{
                    SelectType  selectType =new SelectType();
                    SelectType.id_table=textView.getText().toString();
                    selectType.show(getSupportFragmentManager(),"اختار نوع الوصل");

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.oldorder,menu);
        MenuItem up=menu.findItem(R.id.update_table);
        MenuItem seting_mnue=menu.findItem(R.id.seting_mnue);
        seting_mnue.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                GoSetting();
                return false;
            }
        });
        up.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ServerTable server =new ServerTable(getBaseContext());
                server.IP=sharedPreferences.getString("IP","192.168.0.140");
                server.start();
                try {
                    Toast.makeText(getBaseContext(),"جاري تحديث المواد.......",Toast.LENGTH_LONG).show();
                    Thread.sleep(2000);
                    if(ServerTable.IS_UPDARA_DATA){
                        ArrayList<TableData> list =new DataBaseTable(getBaseContext()).getTables();
                        gridView.setAdapter(new adbgrid(getBaseContext(),list));
                        ServerTable.IS_UPDARA_DATA=false;
                        Toast.makeText(getBaseContext(),"تم التحديث بنجاح",Toast.LENGTH_LONG).show();
                    }else {
                        ServerTable.IS_UPDARA_DATA=false;
                        Toast.makeText(getBaseContext(),"لم يتم التحديث تاكد من الاتصال",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public void Go(String value,String id){
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("TABLE",value);
        intent.putExtra("IDORDER",id);
        startActivity(intent);
    }
    public void GoSetting(){
        Intent intent =new Intent(this,Setting.class);
        startActivity(intent);
    }

}
