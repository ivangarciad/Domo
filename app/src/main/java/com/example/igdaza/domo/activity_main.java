package com.example.igdaza.domo;

/**
 * Created by igdaza on 4/03/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class activity_main extends Activity
{
    private TabHost mTabHost;
    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader reader;
    private Thread socketThread;

    private Socket client_riego;
    private PrintWriter printwriter_riego;
    private BufferedReader reader_riego;
    private Thread socketThread_riego;

    private String filename = "ip_host.txt";
    private String temp="";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mTabHost =(TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();

        // Adding tabs
        TabHost.TabSpec tab0 = mTabHost.newTabSpec("tab_creation");
        //tab0.setIndicator("TAB1").setContent(new Intent(this, MyActivity2.class));
		tab0.setIndicator("Persianas", getResources().getDrawable(android.R.drawable.ic_menu_add));
		tab0.setContent(R.id.tab0);
        mTabHost.addTab(tab0);

        // Adding tabs
        TabHost.TabSpec tab1 = mTabHost.newTabSpec("tab_creation");
        tab1.setIndicator("Riego", getResources().getDrawable(android.R.drawable.ic_menu_add));
        tab1.setContent(R.id.tab1);
        mTabHost.addTab(tab1);

        // Adding tabs
        TabHost.TabSpec tab2 = mTabHost.newTabSpec("tab_creation");
        tab2.setIndicator("Clima", getResources().getDrawable(android.R.drawable.ic_menu_add));
        tab2.setContent(R.id.tab2);
        mTabHost.addTab(tab2);

        //String string = "192.168.0.100";
        try {
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
                    client = new Socket(temp, 5002);
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

        Runnable runnable_riego = new Runnable(){
            public void run() {
                try {
                    Log.d("FILE-CONTENT_IN THREAD RIEGO", temp);
                    client_riego = new Socket(temp, 5001);
                    printwriter_riego = new PrintWriter(client_riego.getOutputStream());
                    reader_riego = new BufferedReader(new InputStreamReader(client_riego.getInputStream()));

                    MyApplication myApp_riego = (MyApplication)getApplication();
                    myApp_riego.setMyApplicationPrintWriterRiego(printwriter_riego);
                    myApp_riego.setMyApplicationPrintReaderRiego(reader_riego);

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
        socketThread_riego = new Thread(runnable_riego, "SocketThreadRiego");
        socketThread_riego.start();

        final Button button_cocina = (Button)findViewById(R.id.cocina_button);
        final Button button_salon = (Button)findViewById(R.id.salon_button);
        final Button button_estudio = (Button)findViewById(R.id.estudio);
        final Button sector_1 = (Button)findViewById(R.id.sector_1);
        final Button sector_2 = (Button)findViewById(R.id.sector_2);
        final Button sector_3 = (Button)findViewById(R.id.sector_3);
        final Button sector_4 = (Button)findViewById(R.id.sector_4);
        final Button sector_5 = (Button)findViewById(R.id.sector_5);
        final Button sector_6 = (Button)findViewById(R.id.sector_6);
        final Button sector_7 = (Button)findViewById(R.id.sector_7);

        // Blind
        button_cocina.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_1");
                intent.putExtra("room_name", "Caldera");
                startActivity(intent);
            }
        });

        button_salon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_7");
                intent.putExtra("room_name", "Salón");
                startActivity(intent);
            }
        });

        button_estudio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, MyActivity2.class);
                intent.putExtra("id_room", "ID_HABITACION_8");
                intent.putExtra("room_name", "Estudio");
                startActivity(intent);
            }
        });

        //Riego Sector
        sector_1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, riego_activity.class);
                intent.putExtra("id_sector", "ID_SECTOR_1");
                intent.putExtra("sector_name", "Sector 1");
                startActivity(intent);
            }
        });

        sector_2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, riego_activity.class);
                intent.putExtra("id_sector", "ID_SECTOR_2");
                intent.putExtra("sector_name", "Sector 2");
                startActivity(intent);
            }
        });

        sector_3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, riego_activity.class);
                intent.putExtra("id_sector", "ID_SECTOR_3");
                intent.putExtra("sector_name", "Sector 3");
                startActivity(intent);
            }
        });

        sector_7.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity_main.this, riego_activity.class);
                intent.putExtra("id_sector", "ID_SECTOR_7");
                intent.putExtra("sector_name", "Sector 7");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("CreateOptionsMenu", "Menu");
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("OptionsItemSelected", "MenuAction");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Setting activity must be called.
            Intent intent = new Intent(activity_main.this, MyActivity3.class);
            intent.putExtra("file_name", filename);
            startActivity(intent);
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
            printwriter_riego.close();
            reader_riego.close();
            client_riego.close();
            socketThread_riego.interrupt();
        } catch (IOException e) {
            //System.err.println("Exit button exception.");
            //System.err.println(e);
        }
    }
}