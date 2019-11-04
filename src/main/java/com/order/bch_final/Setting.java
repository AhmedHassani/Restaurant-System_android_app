package com.order.bch_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText ip;
    private EditText port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ip=findViewById(R.id.ip);
        port=findViewById(R.id.port);
        sharedPreferences=getSharedPreferences("Seting", Context.MODE_PRIVATE);

    }
    public void SaveSetting(View view){
        String IP=ip.getText().toString();
        String PORT=port.getText().toString();
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("IP",IP);
        editor.putString("PORT",PORT);
        editor.commit();
        Toast.makeText(getApplicationContext(),sharedPreferences.getString("IP","NULL"),
        Toast.LENGTH_LONG).show();

    }
}
