package com.order.bch_final;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;

public class Order extends AppCompatDialogFragment {
    ArrayList<AdbListOder.Data> data;
    SharedPreferences sharedPreferences;
    ListView listView;
    TextView Totle;
    EditText or_number;
    AutoCompleteTextView or_name;
    DataBaseMat dataBaseMat;
    Order.AdbListOder1 adbListOder;
    public String IP_SER;
    public static String id_table;
    public static String id_order;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        sharedPreferences=getActivity().getSharedPreferences("DATAORDER", Context.MODE_PRIVATE);

        adbListOder=new Order.AdbListOder1(getContext(),data);
        View v= LayoutInflater.from(getContext()).inflate(R.layout.order,null);
        listView=v.findViewById(R.id.list);
        Totle=v.findViewById(R.id.Totle_price);
        or_name=v.findViewById(R.id.or_name_text);
        or_number=v.findViewById(R.id.or_number_table);
        Totle.setText(new DataBaseOrder(getContext()).Sumtion()+"");
        listView.setAdapter(adbListOder);
        or_number.setText(id_table);
        or_number.setEnabled(false);

        builder = new AlertDialog.Builder(getContext());
        builder.setView(v);

        builder.setPositiveButton("خروج", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Do something here
            }
        });
        builder.setNegativeButton("ارسال", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SendOrder sendOrder = new SendOrder(getContext());
                sendOrder.name_cst = or_name.getText().toString();
                sendOrder.table_id = or_number.getText().toString();
                sendOrder.id_order=id_order;
                sendOrder.IP=IP_SER;
                if (!sendOrder.table_id.equals("")) {
                    sendOrder.start();
                    Toast.makeText(getContext(),"جاري ارسال الوصل............",Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                        if (sendOrder.getRESUILT().equals("Ok")) {
                            Toast.makeText(getContext(), "تم الارسال بنجاح", Toast.LENGTH_LONG).show();
                            DataBaseOrder dataBaseOrder = new DataBaseOrder(getContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            dataBaseOrder.DeleteAll();
                        } else {
                            Toast.makeText(getContext(), "لم يتم الارسال", Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(),"ادخال رقم الطاولة ",Toast.LENGTH_LONG).show();
                }

            }

        });
        return builder.create();
    }
    public ArrayList<AdbListOder.Data> getData() {
        return data;
    }

    public void setData(ArrayList<AdbListOder.Data> data) {
        this.data = data;
    }

    //adbter

    public class AdbListOder1 extends BaseAdapter {

        Context context;
        DataBaseOrder dataBaseOrder;
        ArrayList<com.order.bch_final.AdbListOder.Data> list;
        public AdbListOder1(Context context, ArrayList<com.order.bch_final.AdbListOder.Data> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {

            final TextView name,num,price,totle_price;
            final View view= LayoutInflater.from(context).inflate(R.layout.show_order_final,null);
            name=view.findViewById(R.id.or_name_text);
            price=view.findViewById(R.id.or_price);
            num=view.findViewById(R.id.or_num);
            totle_price=view.findViewById(R.id.Totle_price);
            ImageView but=view.findViewById(R.id.but_del);
            ImageView add=view.findViewById(R.id.but_add);

            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseOrder=new DataBaseOrder(context);
                    dataBaseMat=new DataBaseMat(context);
                    String num= dataBaseOrder.numItem(name.getText().toString());
                    int  new_num=Integer.parseInt(num);
                    if(new_num>1){
                        Double p_old =dataBaseMat.getPriceItem(name.getText().toString());
                        Double p=Double.parseDouble(price.getText().toString());
                        dataBaseOrder.upDataNum(name.getText().toString(),(p-p_old)+"", (new_num-1)+"");
                        Toast.makeText(context, (new_num-1)+"", Toast.LENGTH_SHORT).show();
                        adbListOder=new Order.AdbListOder1(getContext(),dataBaseOrder.getOrder());
                        listView.setAdapter(adbListOder);
                        Totle.setText(new DataBaseOrder(getContext()).Sumtion()+"");

                    }else {
                        dataBaseOrder.DeleteOne(name.getText().toString());
                        adbListOder = new Order.AdbListOder1(getContext(), dataBaseOrder.getOrder());
                        listView.setAdapter(adbListOder);
                        Totle.setText(new DataBaseOrder(getContext()).Sumtion()+"");
                    }

                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseOrder=new DataBaseOrder(context);
                    dataBaseMat=new DataBaseMat(context);
                    String t=name.getText().toString();
                    Double p_old =dataBaseMat.getPriceItem(name.getText().toString());
                    String num= dataBaseOrder.numItem(name.getText().toString());
                    int  new_num=Integer.parseInt(num);
                    Double p=Double.parseDouble(price.getText().toString());
                    dataBaseOrder.upDataNum(name.getText().toString(),(p+p_old)+"", (new_num+1)+"");
                    Toast.makeText(context, (new_num+1)+" "+t, Toast.LENGTH_SHORT).show();
                    adbListOder=new Order.AdbListOder1(getContext(),dataBaseOrder.getOrder());
                    listView.setAdapter(adbListOder);
                    Totle.setText(new DataBaseOrder(getContext()).Sumtion()+"");

                }
            });

            name.setText(list.get(position).name);
            price.setText(list.get(position).price);
            num.setText(list.get(position).num);
            return view;
        }


        class Data {
            String name,num,price;
            public Data(String name, String price, String num) {
                this.name = name;
                this.num = num;
                this.price = price;
            }
        }

    }



}
