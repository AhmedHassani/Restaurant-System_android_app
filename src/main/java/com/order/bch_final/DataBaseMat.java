package com.order.bch_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseMat extends SQLiteOpenHelper {

    int DATABASE_VERSION = 1;
    String DATABASE_NAME = "DataMat";
    String TABLE= "matreal";
    String ID = "id";
    String NAME = "name";
    String PRICE = "price";
    String Type="type";
    public DataBaseMat( Context context) {
        super(context,"Data",null,12);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT,"
                + Type + " TEXT,"
                + PRICE + " TEXT" + ")");
        db.execSQL(CREATE_CONTACTS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public ArrayList<adbItem.DataItems> getOrder(){
        ArrayList<adbItem.DataItems> data=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM "+TABLE;
        Cursor cursor=null;
        cursor= db.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            String n = cursor.getString(cursor.getColumnIndex("name"));
            String p = cursor.getString(cursor.getColumnIndex("price"));
            String num= cursor.getString(cursor.getColumnIndex("type"));
            data.add(new adbItem.DataItems(n,p,num));
        }
        db.close();
        return data;
    }

    public  void setOrder(String m,String p ,String number){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=  new ContentValues();
        values.put(this.NAME,m);
        values.put(this.PRICE,p);
        values.put(this.Type,number);
        db.insert(this.TABLE,null,values);
        db.close();
    }

    public double getPriceItem(String Item){
        ArrayList<adbItem.DataItems> list =getOrder();
        for (int i=0;i<list.size();i++){
            if (Item.equals(list.get(i).name)){
                return Double.parseDouble(list.get(i).price);
            }
        }
        return 1.1;
    }

    public void DeleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("delete from "+TABLE);
        db.close();
    }
}
