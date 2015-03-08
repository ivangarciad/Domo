package com.example.igdaza.domo;

import android.app.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MyApplication extends Application {
    private PrintWriter printwriter;
    private BufferedReader streamReader;
    private PrintWriter printwriter_riego;
    private BufferedReader streamReader_riego;

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

    public PrintWriter getMyApplicationPrintWriterRiego(){
        return printwriter_riego;
    }

    public void setMyApplicationPrintWriterRiego(PrintWriter input){
        this.printwriter_riego = input;
    }

    public BufferedReader getMyApplicationPrintReaderRiego(){
        return streamReader_riego;
    }

    public void setMyApplicationPrintReaderRiego(BufferedReader input){
        this.streamReader_riego = input;
    }
}