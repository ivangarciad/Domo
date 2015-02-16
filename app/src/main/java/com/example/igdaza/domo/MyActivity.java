package com.example.igdaza.domo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.*;
import java.io.*;
import java.lang.Thread;

import org.json.*;

public class MyActivity extends Activity {
    //private TextView output;
    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader reader;

    private Thread socketThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Runnable runnable = new Runnable(){
            public void run() {
                try {
                    client = new Socket("192.168.0.100", 5000);
                    printwriter = new PrintWriter(client.getOutputStream());
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    MyApplication myApp = (MyApplication)getApplication();
                    myApp.setMyApplicationPrintWriter(printwriter);
                    myApp.setMyApplicationPrintReader(reader);

                } catch (UnknownHostException e) {
                    System.err.println("Unknown Host.");
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection.");
                    System.err.println(e);
                }
            }
        };

        socketThread = new Thread(runnable, "SocketThread");
        socketThread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final Button button_cocina = (Button)findViewById(R.id.cocina_button);
        final Button button_salon = (Button)findViewById(R.id.salon_button);
        final Button button_estudio = (Button)findViewById(R.id.estudio);

        button_cocina.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_1");
                startActivity(intent);
            }
        });

        button_salon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_7");
                startActivity(intent);
            }
        });

        button_estudio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_8");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("NETWORK-RECEIVE", "optiansmenu");
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("NETWORK-RECEIVE", "optiansitem");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            printwriter.close();
            reader.close();
            client.close();
            socketThread.interrupt();
        } catch (IOException e) {
            System.err.println("Exit button exception.");
            System.err.println(e);
        }
    }
}
