package com.order.bch_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseTable extends SQLiteOpenHelper {

   public static int DATABASE_VERSION = 12;
   public static String DATABASE_NAME = "DataTable";
    String TABLE= "MyTable";
    String ID = "id";
    String ID_TABLE = "tabl";

    public DataBaseTable(Context context) {
        super(context,"DATABASE_NAME",null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ID_TABLE + " TEXT" + ")");
        db.execSQL(CREATE_CONTACTS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public ArrayList<TableData> getTables(){
        ArrayList<TableData> data=new ArrayList<TableData>();
        SQLiteDatabase db= this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM "+TABLE;
        Cursor cursor=null;
        cursor= db.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            String n = cursor.getString(cursor.getColumnIndex(ID_TABLE));
            data.add(new TableData(n));
        }
        db.close();
        return data;
    }

    public  void setTables(String m){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=  new ContentValues();
        values.put(this.ID_TABLE,m);
        db.insert(this.TABLE,null,values);
        db.close();
    }


    public void DeleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("delete from "+TABLE);
        db.close();
    }
}
