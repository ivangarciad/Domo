package com.example.igdaza.domo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.SeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import android.widget.CheckBox;

/**
 * Created by igdaza on 8/03/15.
 */
public class riego_activity extends Activity {

    private JSONObject object;
    private JSONObject object_recv;
    private String message;

    private PrintWriter printwriter;
    private BufferedReader bufferedReader;
    private Thread readerThread;

    public void writeJSON_toServer(String id_habitacion, String on_off, String auto, String autoProfile,
                                   String startHour, String stopHour,
                                   String start_state, String stop_state) {
        object = new JSONObject();
        try {
            object.put("Name",        id_habitacion);
            object.put("GetStatus",   "False");
            object.put("Status",      "Not deffined");
            object.put("TimeOpen",    startHour);
            object.put("TimeClose",   stopHour);
            object.put("OnOff",       on_off);
            object.put("Auto",        auto);
            object.put("AutoProfile", autoProfile);
            object.put("StartSector", start_state);
            object.put("StopSector",  stop_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void writeJSON_GetStatusToSever(String id_sector) {
        object = new JSONObject();
        try {
            object.put("Name",        id_sector);
            object.put("GetStatus",   "True");
            object.put("Status",      "Not deffined");
            object.put("TimeOpen",    "None");
            object.put("TimeClose",   "None");
            object.put("OnOff",       "False");
            object.put("Auto",        "False");
            object.put("AutoProfile", "False");
            object.put("StartSector", "False");
            object.put("StopSector",  "False");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void  writeJSON_ProfiletoServer(String id_sector, int value_1, int value_2, int value_3, int value_4, int value_5, int value_6,
                                   boolean monday, boolean thusday, boolean wendsday,
                                   boolean thursday, boolean friday, boolean saterday, boolean sunday) {
        object = new JSONObject();
        try {
            object.put("Name",        id_sector);
            object.put("GetStatus",   "False");
            object.put("Status",      "Not deffined");
            object.put("TimeOpen",    "None");
            object.put("TimeClose",   "None");
            object.put("OnOff",       "None");
            object.put("Auto",        "False");
            object.put("AutoProfile", "True");
            object.put("StartSector", "None");
            object.put("StopSector",  "None");
            object.put("value_1", String.valueOf(value_1));
            object.put("value_2", String.valueOf(value_2));
            object.put("value_3", String.valueOf(value_3));
            object.put("value_4", String.valueOf(value_4));
            object.put("value_5", String.valueOf(value_5));
            object.put("value_6", String.valueOf(value_6));
            object.put("monday",  String.valueOf(monday));
            object.put("thusday", String.valueOf(thusday));
            object.put("wendsday",String.valueOf(wendsday));
            object.put("thursday",String.valueOf(thursday));
            object.put("friday",  String.valueOf(friday));
            object.put("saterday",String.valueOf(saterday));
            object.put("sunday",  String.valueOf(sunday));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector);

        MyApplication myApp = (MyApplication)getApplication();
        printwriter = myApp.getMyApplicationPrintWriter();
        bufferedReader = myApp.getMyApplicationPrintReader();

        Intent intent = getIntent();
        final String id_sector = intent.getStringExtra("id_sector");
        final String sector_name = intent.getStringExtra("sector_name");
        Log.d("Id Room", id_sector);


        final TextView textLabel = (TextView) findViewById(R.id.sector_name);
        final CheckBox on_off = (CheckBox) findViewById(R.id.enable_sector);

        final RadioButton radioButton_Profile = (RadioButton) findViewById(R.id.radioButton_sectorProfile);
        final RadioButton radioButton_Hour = (RadioButton) findViewById(R.id.radioButton_sectorHour);

        final EditText openHour = (EditText) findViewById(R.id.editText_sectorHourStart);
        final EditText closeHour = (EditText) findViewById(R.id.editText_sectorHourStop);
        final Button start_button = (Button)findViewById(R.id.start_sector_button);
        final Button stop_button = (Button)findViewById(R.id.stop_sector_button);

        final SeekBar seekBar1 = (SeekBar)findViewById(R.id.seekBar_round_1);
        final SeekBar seekBar2 = (SeekBar)findViewById(R.id.seekBar_round_2);
        final SeekBar seekBar3 = (SeekBar)findViewById(R.id.seekBar_round_3);
        final SeekBar seekBar4 = (SeekBar)findViewById(R.id.seekBar_round_4);
        final SeekBar seekBar5 = (SeekBar)findViewById(R.id.seekBar_round_5);
        final SeekBar seekBar6 = (SeekBar)findViewById(R.id.seekBar_round_6);

        final CheckBox monday = (CheckBox) findViewById(R.id.checkBox_monday_sector);
        final CheckBox thusday = (CheckBox) findViewById(R.id.checkBox_thusday_sector);
        final CheckBox wendsday = (CheckBox) findViewById(R.id.checkBox_wendsday_sector);
        final CheckBox thursday = (CheckBox) findViewById(R.id.checkBox_thursday_sector);
        final CheckBox friday = (CheckBox) findViewById(R.id.checkBox_friday_sector);
        final CheckBox saterday = (CheckBox) findViewById(R.id.checkBox_saterday_sector);
        final CheckBox sunday = (CheckBox) findViewById(R.id.checkBox_sunday_sector);

        textLabel.setText(sector_name);

        // Petición de estado del blind al servidor
        writeJSON_GetStatusToSever(id_sector);
        printwriter.write(object.toString()); //write the message to output stream
        printwriter.flush();

        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Log.d("NETWORK-RECEIVE", "Trying to read information from the server in riego_activity ...");

                    CharArrayWriter charArray = new CharArrayWriter();
                    do {
                        charArray.reset();
                        int ch = bufferedReader.read();
                        while((char)ch != '}') {
                            // converts int to character
                            char c = (char)ch;
                            charArray.append(c);
                            ch = bufferedReader.read();
                        }
                        charArray.append('}');
                        Log.d("NETWORK-RECEIVE", charArray.toString());
                        object_recv = new JSONObject(charArray.toString());
                        if (object_recv.getString("Name").equals(id_sector))
                            break;
                    }while(true);

                    on_off.setChecked(Boolean.valueOf(object_recv.getString("OnOff")));

                    if (object_recv.getString("Auto").equals("True")) {
                        radioButton_Hour.setChecked(true);
                        openHour.setText(object_recv.getString("TimeOpen"));
                        closeHour.setText(object_recv.getString("TimeClose"));
                    }

                    if (object_recv.getString("AutoProfile").equals("True")) {
                        radioButton_Profile.setChecked(true);
                        openHour.setEnabled(false);
                        closeHour.setEnabled(false);

                        seekBar1.setProgress(object_recv.getInt("value_1"));
                        seekBar2.setProgress(object_recv.getInt("value_2"));
                        seekBar3.setProgress(object_recv.getInt("value_3"));
                        seekBar4.setProgress(object_recv.getInt("value_4"));
                        seekBar5.setProgress(object_recv.getInt("value_5"));
                        seekBar6.setProgress(object_recv.getInt("value_6"));

                        monday.setChecked(Boolean.valueOf(object_recv.getString("Mon")));
                        thusday.setChecked(Boolean.valueOf(object_recv.getString("Tue")));
                        wendsday.setChecked(Boolean.valueOf(object_recv.getString("Wed")));
                        thursday.setChecked(Boolean.valueOf(object_recv.getString("Thu")));
                        friday.setChecked(Boolean.valueOf(object_recv.getString("Fri")));
                        saterday.setChecked(Boolean.valueOf(object_recv.getString("Sat")));
                        sunday.setChecked(Boolean.valueOf(object_recv.getString("Sun")));
                    }

                    if (object_recv.getString("Status").equals("OPEN"))
                    {
                        start_button.setEnabled(false);
                        stop_button.setEnabled(true);
                    }
                    else if (object_recv.getString("Status").equals("CLOSE"))
                    {
                        stop_button.setEnabled(false);
                        start_button.setEnabled(true);
                    }

                } catch (IOException e) {
                    System.err.println("Couldn't read.");
                    System.err.println(e);
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON");
                }
            }
        };

        readerThread = new Thread(runnable, "SocketThread");
        readerThread.start();

        on_off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the solar button
                writeJSON_toServer(id_sector, "True", "None", "None",
                        "None", "None",
                        "None", "None");

                message = object.toString();

                printwriter.write(message);
                printwriter.flush();
            }
        });

        radioButton_Hour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the hour button
                writeJSON_toServer(id_sector, "None", "True", "False",
                        openHour.getText().toString(), closeHour.getText().toString(),
                        "None", "None");

                message = object.toString();

                printwriter.write(message);
                printwriter.flush();

                openHour.setEnabled(true);
                closeHour.setEnabled(true);
            }
        });

        radioButton_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeJSON_ProfiletoServer(id_sector, seekBar1.getProgress(), seekBar2.getProgress(), seekBar3.getProgress(),
                        seekBar4.getProgress(), seekBar5.getProgress(), seekBar6.getProgress(),
                        monday.isChecked(), thusday.isChecked(), wendsday.isChecked(), thursday.isChecked(),
                        friday.isChecked(), saterday.isChecked(), sunday.isChecked());
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                openHour.setEnabled(false);
                closeHour.setEnabled(false);
            }
        });

        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the open hour
                writeJSON_toServer(id_sector, "None", "None", "None",
                        "None", "None",
                        "True", "None");

                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                start_button.setEnabled(false);
                stop_button.setEnabled(true);
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the closed hour
                writeJSON_toServer(id_sector, "None", "None", "None",
                        "None", "None",
                        "None", "True");

                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                start_button.setEnabled(true);
                stop_button.setEnabled(false);
            }
        });

    }
}
