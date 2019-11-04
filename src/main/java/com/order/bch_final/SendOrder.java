package com.order.bch_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SendOrder extends Thread {
    public String name_cst;
    public String table_id;
    public  String RESUILT="";
    public String IP;
    public String PORT;
    public Context context;
    static boolean SENT_OK=false;
    public  String id_order;
    DataBaseOrder dataBaseOrder;
    Socket socket;

    public SendOrder(Context context){
        this.context=context;
    }


    @Override
    public void run() {
        try {
            this.Conection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  void Conection() throws IOException, JSONException {
        try {
            JSONObject object = new JSONObject();
            JSONObject object2 = new JSONObject();
            //sharedPreferences.getString("IP","null");
            Socket sk = new Socket(IP, 7000);

            BufferedReader sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            PrintStream sout = new PrintStream(sk.getOutputStream());
            ObjectOutputStream out = new ObjectOutputStream(sk.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectInputStream cin2=new  ObjectInputStream(sk.getInputStream());
            dataBaseOrder=new DataBaseOrder(context);
            ArrayList<AdbListOder.Data> sorder=dataBaseOrder.getOrder();
            String json = new Gson().toJson(sorder);
            object2.put("DATA",json);
            object2.put("IDORDER",id_order);
            object2.put("TIME",getCurrentTime());
            object2.put("TATBLE",table_id);
            object2.put("NAME",name_cst);
            object.put("PROSSING",object2);
            while (true) {
                out.writeObject(object.toString());
                break;
            }
            Object i =cin2.readUnshared();
            JSONObject data_rgs = new JSONObject(i.toString());
            String d = data_rgs.getString("RESILT");
            this.setRESUILT(d);
            Log.d("RUSILT_OF_SOKET",d);
            sk.close();
            sin.close();
            sout.close();
            stdin.close();
        }catch (ConnectException e){
            Log.d("ErroCooonection","Error !!!..");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String getCurrentTime() {
        String DATE_FORMAT_1 = "hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public String getRESUILT() {
        return RESUILT;
    }

    public void setRESUILT(String RESUILT) {
        this.RESUILT = RESUILT;
    }
}
