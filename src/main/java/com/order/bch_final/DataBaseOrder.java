package com.order.bch_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseOrder extends SQLiteOpenHelper {

    private static  int DATABASE_VERSION = 12;
    String DATABASE_NAME = "Data";
    String TABLE= "tikect2";
    String ID = "id";
    String NAME = "name";
    String PRICE = "price";
    String NUMBER="number";

    public DataBaseOrder( Context context) {
        super(context,"Data",null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT,"
                + NUMBER + " TEXT,"
                + PRICE + " TEXT" + ")");
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public void Drop(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + "tikect");
        db.execSQL("DROP TABLE IF EXISTS " + "tikect1");
        db.execSQL("DROP TABLE IF EXISTS " + "tikect2");
        onCreate(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
    }



    public void upDataNum(String m,String p ,String number){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=  new ContentValues();
        values.put(this.NAME,m);
        values.put(this.PRICE,p);
        values.put(this.NUMBER,number);
        db.update(this.TABLE,values,"name="+"'"+m+"'",null);
        db.close();
    }

    public  void setOrder(String m,String p ,String number){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=  new ContentValues();
        values.put(this.NAME,m);
        values.put(this.PRICE,p);
        values.put(this.NUMBER,number);
        db.insert(this.TABLE,null,values);
        db.close();
    }
    public void DeleteOne(String name){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("delete from "+TABLE +" Where name == '"+name+"'");
        db.close();
    }
    public  String numItem(String Item){
        ArrayList<AdbListOder.Data> list =getOrder();
        for (int i=0;i<list.size();i++){
            if (Item.equals(list.get(i).name)){
                return (list.get(i).num);
            }
        }
        return "0";
    }
    public ArrayList<AdbListOder.Data> getOrder(){
        ArrayList<AdbListOder.Data> data=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM "+TABLE;
        Cursor cursor=null;
        cursor= db.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            String n = cursor.getString(cursor.getColumnIndex("name"));
            String p = cursor.getString(cursor.getColumnIndex("price"));
            String num= cursor.getString(cursor.getColumnIndex("number"));
            data.add(new AdbListOder.Data(n,p,num));
        }
        db.close();
        return data;
    }

    public Double Sumtion(){
        Double sum=0.0;
        ArrayList<AdbListOder.Data> all=getOrder();
        for (int i=0;i<all.size();i++){
           sum=sum+Double.parseDouble(all.get(i).price);
        }
        return  sum;
    }

    public void DeleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("delete from "+TABLE);
        db.close();
    }
}



class SentOrdr{
    public String price;
    public String name_matreal;
    public String number;
    SentOrdr(String name_matreal, String price,String number) {
        this.name_matreal = name_matreal;
        this.price = price;
        this.number=number;
    }
}
