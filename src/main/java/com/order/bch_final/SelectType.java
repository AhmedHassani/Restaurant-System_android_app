package com.order.bch_final;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectType extends AppCompatDialogFragment {
    public static String id_table;
    public static String id_order;
    private SharedPreferences sharedOrder;
    private SharedPreferences sharedPreferences;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        View v= LayoutInflater.from(getContext()).inflate(R.layout.order_old,null);
        builder = new AlertDialog.Builder(getContext());
        Button but1 =v.findViewById(R.id.but1);
        Button but2 =v.findViewById(R.id.but2);
        sharedOrder = getActivity().getSharedPreferences("Old_Order", Context.MODE_PRIVATE);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),MainActivity.class);
                String val =sharedOrder.getString(id_table,"0");
                intent.putExtra("TABLE",id_table);
                intent.putExtra("IDORDER",val);
                startActivity(intent);
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerSales.ID_ORDER="";
                sharedPreferences=getActivity().getSharedPreferences("Seting", Context.MODE_PRIVATE);
                ServerSales  serverSales =new ServerSales(getContext());
                serverSales.IP=sharedPreferences.getString("IP","192.168.0.140");
                serverSales.start();
                Toast.makeText(getContext(),"جاري انشاء وصل جديد ..........",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }
                if(ServerSales.ID_ORDER.equals("")){
                    Toast.makeText(getContext(),"تاكد من الاتصال",Toast.LENGTH_LONG).show();

                }else {
                    SharedPreferences.Editor editor = sharedOrder.edit();
                    String id_order = ServerSales.ID_ORDER;
                    editor.putString(id_table, id_order);
                    editor.commit();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("TABLE", id_table);
                    intent.putExtra("IDORDER", id_order);
                    startActivity(intent);
                }
            }
        });

        builder.setView(v);
        return builder.create();
    }

}
