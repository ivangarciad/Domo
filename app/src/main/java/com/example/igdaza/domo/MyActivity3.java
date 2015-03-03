package com.example.igdaza.domo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MyActivity3 extends Activity {

    String file_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity3);
        final EditText IP_address_edit_text = (EditText) findViewById(R.id.editText_serverIP);

        String temp="";
        int c;

        Intent intent = getIntent();
        file_name = intent.getStringExtra("file_name");
        try {
            Log.d("FILE-READ", file_name);
            FileInputStream file = openFileInput(file_name);
            while( (c = file.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            IP_address_edit_text.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        final EditText IP_address_edit_text = (EditText) findViewById(R.id.editText_serverIP);

        String IP_address = IP_address_edit_text.getText().toString();

        if (!IP_address.isEmpty()) {
            try {
                FileOutputStream outputStream = openFileOutput(file_name, Context.MODE_PRIVATE);
                outputStream.write(IP_address.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("IP_address", IP_address);
        }
    }
}
