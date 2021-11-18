package com.example.learningandroid.booklist.util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LocalDataWR {
    public static void saveData(Activity activity, String fileName, byte[] data) {
        try{
            FileOutputStream fout = activity.openFileOutput(fileName, MODE_PRIVATE);
            fout.write(data);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] loadData(Activity activity, String fileName) {
        byte [] buffer = null;
        try{
            FileInputStream fin = activity.openFileInput(fileName);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);

            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return buffer;
    }
}
