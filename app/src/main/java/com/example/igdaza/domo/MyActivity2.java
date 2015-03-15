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


public class MyActivity2 extends Activity {

    private JSONObject object;
    private JSONObject object_recv;
    private String message;

    private PrintWriter printwriter;
    private BufferedReader bufferedReader;
    private Thread readerThread;

    public void writeJSON(String id_habitacion, String open_state, String close_state) {
        object = new JSONObject();
        try {
            object.put("Name", id_habitacion);
            object.put("GetStatus", "False");
            object.put("Status", "Not deffined");
            object.put("TimeOpen", "None");
            object.put("TimeClose", "None");
            object.put("Auto", "False");
            object.put("AutoSolar", "False");
            object.put("AutoProfile", "False");
            object.put("OpenBlind", open_state);
            object.put("CloseBlind", close_state);

            //object.put("score", new Integer(200));
            //object.put("current", new Double(152.32));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void writeJSON_GetStatus(String id_habitacion) {
        object = new JSONObject();
        try {
            object.put("Name", id_habitacion);
            object.put("GetStatus", "True");
            object.put("Status", "Not deffined");
            object.put("TimeOpen", "None");
            object.put("TimeClose", "None");
            object.put("Auto", "False");
            object.put("AutoSolar", "False");
            object.put("AutoProfile", "False");
            object.put("OpenBlind", "False");
            object.put("CloseBlind", "False");

            //object.put("score", new Integer(200));
            //object.put("current", new Double(152.32));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void writeJSON_Solar(String id_habitacion) {
        object = new JSONObject();
        try {
            object.put("Name", id_habitacion);
            object.put("GetStatus", "False");
            object.put("Status", "Not deffined");
            object.put("TimeOpen", "None");
            object.put("TimeClose", "None");
            object.put("Auto", "False");
            object.put("AutoSolar", "True");
            object.put("AutoProfile", "False");
            object.put("OpenBlind", "False");
            object.put("CloseBlind", "False");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void writeJSON_Hour(String id_habitacion, String openHour, String closeHour) {
        object = new JSONObject();
        try {
            object.put("Name", id_habitacion);
            object.put("GetStatus", "False");
            object.put("Status", "Not deffined");
            object.put("TimeOpen", openHour);
            object.put("TimeClose", closeHour);
            object.put("Auto", "True");
            object.put("AutoSolar", "False");
            object.put("AutoProfile", "False");
            object.put("OpenBlind", "False");
            object.put("CloseBlind", "False");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public void  writeJSON_Profile(String id_habitacion, int value_1, int value_2, int value_3, int value_4, int value_5, int value_6,
                                   int value_7, int value_8, int value_9, boolean monday, boolean thusday, boolean wendsday,
                                   boolean thursday, boolean friday, boolean saterday, boolean sunday) {
        object = new JSONObject();
        try {
            object.put("Name", id_habitacion);
            object.put("GetStatus", "False");
            object.put("Status", "Not deffined");
            object.put("TimeOpen", "NO_HOUR");
            object.put("TimeClose", "NO_HOUR");
            object.put("Auto", "False");
            object.put("AutoSolar", "False");
            object.put("AutoProfile", "True");
            object.put("OpenBlind", "False");
            object.put("CloseBlind", "False");
            object.put("value_1", String.valueOf(value_1));
            object.put("value_2", String.valueOf(value_2));
            object.put("value_3", String.valueOf(value_3));
            object.put("value_4", String.valueOf(value_4));
            object.put("value_5", String.valueOf(value_5));
            object.put("value_6", String.valueOf(value_6));
            object.put("value_7", String.valueOf(value_7));
            object.put("value_8", String.valueOf(value_8));
            object.put("value_9", String.valueOf(value_9));
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
        setContentView(R.layout.activity_my_activity2);

        MyApplication myApp = (MyApplication)getApplication();
        printwriter = myApp.getMyApplicationPrintWriter();
        bufferedReader = myApp.getMyApplicationPrintReader();

        Intent intent = getIntent();
        final String id_room = intent.getStringExtra("id_room");
        final String room_name = intent.getStringExtra("room_name");
        Log.d("Id Room", id_room);


        final TextView textLabel = (TextView) findViewById(R.id.textlabel);
        final RadioButton radioButton_Solar = (RadioButton) findViewById(R.id.radioButton_Solar);
        final RadioButton radioButton_Profile = (RadioButton) findViewById(R.id.radioButton_Profile);
        final RadioButton radioButton_Hour = (RadioButton) findViewById(R.id.radioButton_Hour);
        final EditText openHour = (EditText) findViewById(R.id.editText_HourOpen);
        final EditText closeHour = (EditText) findViewById(R.id.editText_HourClose);
        final Button open_button = (Button)findViewById(R.id.open_blind_button);
        final Button close_button = (Button)findViewById(R.id.close_blind_button);

        final SeekBar seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        final SeekBar seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        final SeekBar seekBar3 = (SeekBar)findViewById(R.id.seekBar3);
        final SeekBar seekBar4 = (SeekBar)findViewById(R.id.seekBar4);
        final SeekBar seekBar5 = (SeekBar)findViewById(R.id.seekBar5);
        final SeekBar seekBar6 = (SeekBar)findViewById(R.id.seekBar6);
        final SeekBar seekBar7 = (SeekBar)findViewById(R.id.seekBar7);
        final SeekBar seekBar8 = (SeekBar)findViewById(R.id.seekBar8);

        final CheckBox monday = (CheckBox) findViewById(R.id.checkBox_monday);
        final CheckBox thusday = (CheckBox) findViewById(R.id.checkBox_thusday);
        final CheckBox wendsday = (CheckBox) findViewById(R.id.checkBox_wendsday);
        final CheckBox thursday = (CheckBox) findViewById(R.id.checkBox_thursday);
        final CheckBox friday = (CheckBox) findViewById(R.id.checkBox_friday);
        final CheckBox saterday = (CheckBox) findViewById(R.id.checkBox_saterday);
        final CheckBox sunday = (CheckBox) findViewById(R.id.checkBox_sunday);

        textLabel.setText(room_name);

        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Log.d("NETWORK-RECEIVE", "Trying to read information from the server ...");

                    CharArrayWriter charArray = new CharArrayWriter();


                    do {
                        charArray.reset();
                        int ch = bufferedReader.read();
                        while ((char) ch != '}') {
                            // converts int to character
                            char c = (char) ch;
                            charArray.append(c);
                            ch = bufferedReader.read();
                        }
                        charArray.append('}');
                        Log.d("NETWORK-RECEIVE", charArray.toString());
                        object_recv = new JSONObject(charArray.toString());
                        if (object_recv.getString("Name").equals(id_room))
                            break;
                    } while (true);

                    if (object_recv.getString("Auto").equals("True")) {
                        radioButton_Hour.setChecked(true);
                        openHour.setText(object_recv.getString("TimeOpen"));
                        closeHour.setText(object_recv.getString("TimeClose"));
                    }
                    if (object_recv.getString("AutoSolar").equals("True")) {
                        radioButton_Solar.setChecked(true);
                        openHour.setEnabled(false);
                        closeHour.setEnabled(false);
                    }

                    if (object_recv.getString("AutoProfile").equals("True")) {
                        radioButton_Profile.setChecked(true);
                        openHour.setEnabled(false);
                        closeHour.setEnabled(false);
                    }

                    seekBar1.setProgress(object_recv.getInt("value_1"));
                    seekBar2.setProgress(object_recv.getInt("value_2"));
                    seekBar3.setProgress(object_recv.getInt("value_3"));
                    seekBar4.setProgress(object_recv.getInt("value_4"));
                    seekBar5.setProgress(object_recv.getInt("value_5"));
                    seekBar6.setProgress(object_recv.getInt("value_6"));
                    seekBar7.setProgress(object_recv.getInt("value_7"));
                    seekBar8.setProgress(object_recv.getInt("value_8"));

                    monday.setChecked(Boolean.valueOf(object_recv.getString("Mon")));
                    thusday.setChecked(Boolean.valueOf(object_recv.getString("Tue")));
                    wendsday.setChecked(Boolean.valueOf(object_recv.getString("Wed")));
                    thursday.setChecked(Boolean.valueOf(object_recv.getString("Thu")));
                    friday.setChecked(Boolean.valueOf(object_recv.getString("Fri")));
                    saterday.setChecked(Boolean.valueOf(object_recv.getString("Sat")));
                    sunday.setChecked(Boolean.valueOf(object_recv.getString("Sun")));


                    if (object_recv.getString("Status").equals("OPEN"))
                    {
                        open_button.setEnabled(false);
                        close_button.setEnabled(true);
                    }
                    else if (object_recv.getString("Status").equals("CLOSE"))
                    {
                        close_button.setEnabled(false);
                        open_button.setEnabled(true);
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

        // Petición de estado del blind al servidor
        writeJSON_GetStatus(id_room);
        printwriter.write(object.toString()); //write the message to output stream
        printwriter.flush();

        radioButton_Solar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the solar button
                writeJSON_Solar(id_room);
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                radioButton_Solar.setChecked(true);
                radioButton_Hour.setChecked(false);
                radioButton_Profile.setChecked(false);

                openHour.setEnabled(false);
                closeHour.setEnabled(false);
            }
        });

        radioButton_Hour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the hour button
                writeJSON_Hour(id_room, openHour.getText().toString(), closeHour.getText().toString());
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                radioButton_Solar.setChecked(false);
                radioButton_Hour.setChecked(true);
                radioButton_Profile.setChecked(false);

                openHour.setEnabled(true);
                closeHour.setEnabled(true);
            }
        });

        radioButton_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeJSON_Profile(id_room, seekBar1.getProgress(), seekBar2.getProgress(), seekBar3.getProgress(), seekBar4.getProgress(),
                        seekBar5.getProgress(), seekBar6.getProgress(), seekBar7.getProgress(),seekBar8.getProgress(),
                        0, monday.isChecked(), thusday.isChecked(), wendsday.isChecked(), thursday.isChecked(),
                        friday.isChecked(), saterday.isChecked(), sunday.isChecked());
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                radioButton_Solar.setChecked(false);
                radioButton_Hour.setChecked(false);
                radioButton_Profile.setChecked(true);

                openHour.setEnabled(false);
                closeHour.setEnabled(false);
            }
        });

        open_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the open hour
                writeJSON(id_room, "True", "False");
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                open_button.setEnabled(false);
                close_button.setEnabled(true);
            }
        });

        close_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The server changes the closed hour
                writeJSON(id_room, "False", "True");
                message = object.toString();

                printwriter.write(message); //write the message to output stream
                printwriter.flush();

                open_button.setEnabled(true);
                close_button.setEnabled(false);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity2, menu);
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
        readerThread.interrupt();
    }
}
