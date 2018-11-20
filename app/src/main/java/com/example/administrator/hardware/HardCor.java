package com.example.administrator.hardware;

public class HardCor{
    public static native int ledCtrl(int which, int status);
    public static native int ledOpen();
    public static native void ledClose();

    static{
        try {
            System.loadLibrary("hardcor");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String args[]){
//
//    }

}