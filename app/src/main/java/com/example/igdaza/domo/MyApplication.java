package com.example.igdaza.domo;

import android.app.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MyApplication extends Application {
    private PrintWriter printwriter;
    private BufferedReader streamReader;

    public PrintWriter getMyApplicationPrintWriter(){
        return printwriter;
    }

    public void setMyApplicationPrintWriter(PrintWriter input){
        this.printwriter = input;
    }

    public BufferedReader getMyApplicationPrintReader(){
        return streamReader;
    }

    public void setMyApplicationPrintReader(BufferedReader input){
        this.streamReader = input;
    }
}