package com.order.bch_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    boolean  is_opend_order=false;
    DataBaseOrder dataBaseOrder;
    private SharedPreferences sharedPreferences;

    adbItem adbitem;
    FloatingActionButton fb;
    ArrayList<adbItem.DataItems> data;
    DataBaseMat dataBaseMat;
    String id_table ;
    String ID_Order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseMat=new DataBaseMat(this);
        new DataBaseOrder(getBaseContext()).Drop();
        new DataBaseMat(this).DeleteAll();
        data=dataBaseMat.getOrder();
        adbitem=new adbItem(data,this);
        listView=findViewById(R.id.list_item_view);
        listView.setAdapter(adbitem);
        fb=findViewById(R.id.ic_mnue);

        id_table =getIntent().getStringExtra("TABLE");
        ID_Order =getIntent().getStringExtra("IDORDER");
        Log.d("+++++++ID_Order+++++++:","====================>"+ID_Order);
        sharedPreferences=getSharedPreferences("Seting", Context.MODE_PRIVATE);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrder();
            }
        });

    }
    public void MakeOrder(){
        dataBaseOrder=new DataBaseOrder(getBaseContext());
        Order order =new Order();
        Toast.makeText(getApplicationContext(),sharedPreferences.getString("IP","192.168.0.140"),Toast.LENGTH_LONG).show();
        order.IP_SER=sharedPreferences.getString("IP","192.168.0.140");
        Order.id_table=id_table;
        Order.id_order=ID_Order;
        order.show(getSupportFragmentManager(),"استمارة الارسال");
        try {
            ArrayList<AdbListOder.Data> d=dataBaseOrder.getOrder();
            order.setData(d);
        }catch (Exception e){
            Log.d("Error in main ","open Order !");
        }

    }
    public void OpenSetting(){
        Intent intent =new Intent(this,Setting.class);
        startActivity(intent);
    }
    public void OpenTable(){
        Intent intent =new Intent(this,oldorder.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other,menu);
        MenuItem Search=menu.findItem(R.id.Search);
        MenuItem Other=menu.findItem(R.id.other);
        MenuItem update=menu.findItem(R.id.updata_matreal);
        MenuItem setting_but=menu.findItem(R.id.setting);//ic_new_order
        MenuItem table=menu.findItem(R.id.table_icon);
        MenuItem makeOrder=menu.findItem(R.id.make_order);
        makeOrder.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MakeOrder();
                return false;
            }
        });
        table.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                OpenTable();
                return false;
            }
        });

        final SearchView searchView =(SearchView)Search.getActionView();
        setting_but.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                OpenSetting();
                return true;
            }
        });
        update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                 Updata up=new Updata();
                 up.show(getSupportFragmentManager(),"تحديث المواد");
                 ConnectionServer server =new ConnectionServer(getBaseContext());
                 server.IP=sharedPreferences.getString("IP","192.168.0.140");
                 server.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                up.dismiss();
                 if(ConnectionServer.IS_UPDARA_DATA){
                     DataBaseMat dataBaseMat=new DataBaseMat(getBaseContext());
                     ArrayList<adbItem.DataItems> dd=dataBaseMat.getOrder();
                     adbItem adbitem1=new adbItem(dd,getBaseContext());
                     listView.setAdapter(adbitem1);
                     Toast.makeText(getBaseContext(),"تم التحديث",Toast.LENGTH_LONG).show();
                     ConnectionServer.IS_UPDARA_DATA=false;
                 }else{
                     Toast.makeText(getBaseContext(),"لم يتم التحديث تاكد من الاتصال ",Toast.LENGTH_LONG).show();
                 }
                return true;
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    ArrayList<adbItem.DataItems> new_data = Search(data, newText);
                    adbitem = new adbItem(new_data, getBaseContext());
                    listView.setAdapter(adbitem);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public ArrayList<adbItem.DataItems>Search(ArrayList<adbItem.DataItems> items,String value){
        ArrayList<adbItem.DataItems> data=new ArrayList<adbItem.DataItems>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).name.contains(value)){
                data.add(new adbItem.DataItems(items.get(i).name,items.get(i).price,items.get(i).type));
            }
        }
      return data;
    }
    public ArrayList<adbItem.DataItems>SearchRST(ArrayList<adbItem.DataItems> items,String value){
        ArrayList<adbItem.DataItems> data=new ArrayList<adbItem.DataItems>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).type.contains(value)){
                data.add(new adbItem.DataItems(items.get(i).name,items.get(i).price,items.get(i).type));
            }
        }
        return data;
    }
}
