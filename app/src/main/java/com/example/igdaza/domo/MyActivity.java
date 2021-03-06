package com.example.igdaza.domo;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.*;
import java.io.*;
import java.lang.Thread;

import android.content.Context;
import android.widget.TabHost;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyActivity extends Activity {
    //private TextView output;
    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader reader;

    private Thread socketThread;
    private String filename = "ip_host.txt";
    private String temp="";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //String string = "192.168.0.100";

        try {
            //FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            //outputStream.write(string.getBytes());
            //outputStream.close();

            FileInputStream fin = openFileInput(filename);
            int c;
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Log.d("FILE-READ", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = new Runnable(){
            public void run() {
                try {
                    Log.d("FILE-CONTENT_IN THREAD", temp);
                    client = new Socket(temp, 5000);
                    printwriter = new PrintWriter(client.getOutputStream());
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    MyApplication myApp = (MyApplication)getApplication();
                    myApp.setMyApplicationPrintWriter(printwriter);
                    myApp.setMyApplicationPrintReader(reader);

                } catch (UnknownHostException e) {
                    //System.err.println("Unknown Host.");
                } catch (IOException e) {
                    //System.err.println("Couldn't get I/O for the connection.");
                    //System.err.println(e);
                }
            }
        };

        socketThread = new Thread(runnable, "SocketThread");
        socketThread.start();

        /*Resources res = getResources();
        //setContentView(R.layout.activity_my);


        final Button button_cocina = (Button)findViewById(R.id.cocina_button);
        final Button button_salon = (Button)findViewById(R.id.salon_button);
        final Button button_estudio = (Button)findViewById(R.id.estudio);

        button_cocina.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_1");
                intent.putExtra("room_name", "Caldera");
                startActivity(intent);
            }
        });

        button_salon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_7");
                intent.putExtra("room_name", "Salón");
                startActivity(intent);
            }
        });

        button_estudio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_8");
                intent.putExtra("room_name", "Estudio");
                startActivity(intent);
            }
        });*/
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my, container, false);
        //TextView tv = (TextView) v.findViewById(R.id.text);
        //tv.setText(this.getTag() + " Content");
        return v;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("CreateOptionsMenu", "Menu");
        //getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("OptionsItemSelected", "MenuAction");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Setting activity must be called.
            /*Intent intent = new Intent(MyActivity.this, MyActivity3.class);
            intent.putExtra("file_name", filename);
            startActivity(intent);*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            printwriter.close();
            reader.close();
            client.close();
            socketThread.interrupt();
        } catch (IOException e) {
            //System.err.println("Exit button exception.");
            //System.err.println(e);
        }
    }
}
