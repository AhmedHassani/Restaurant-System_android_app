package com.order.bch_final;

import android.content.Context;
import android.util.Log;

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

public class ServerTable extends Thread{
    public Context context;
    static boolean IS_UPDARA_DATA=false;
    public String PORT;
    public String IP;

    public ServerTable(Context context){
        this.context=context;

    }
    Socket socket;
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
            //192.168.0.109
            Socket sk = new Socket(IP, 7000);

            BufferedReader sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            PrintStream sout = new PrintStream(sk.getOutputStream());
            ObjectOutputStream out = new ObjectOutputStream(sk.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectInputStream cin2 = new ObjectInputStream(sk.getInputStream());
            object.put("PROSSING", "TABLE");
            while (true) {
                System.out.print("Sent Data !");
                out.writeObject(object.toString());
                break;
            }
            DataBaseTable dataBaseMat = new DataBaseTable(context);
            Object i = cin2.readUnshared();
            JSONObject data_rgs = new JSONObject(i.toString());
            String d = data_rgs.getString("TDATA");
            JSONArray obj = new JSONArray(d);
            Log.d("Data :", obj.toString());

            if (obj.length() > 0) {
                dataBaseMat.DeleteAll();
                for (int j = 0; j < obj.length(); j++) {
                    dataBaseMat.setTables(
                            obj.getJSONObject(j).getString("table")
                    );
                }
                IS_UPDARA_DATA=true;
            } else {
                Log.d("Error", "Not Updata ..? ");
            }

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



}
