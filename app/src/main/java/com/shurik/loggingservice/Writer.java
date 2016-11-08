package com.shurik.loggingservice;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Writer {

    public void writeToFile(String string) {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        File logFile = new File(externalStorageDir, "logger.txt");

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fostream = new FileOutputStream(logFile, true);
            OutputStreamWriter oswriter = new OutputStreamWriter(fostream);
            BufferedWriter bwriter = new BufferedWriter(oswriter);
            bwriter.write(string);
            bwriter.close();
            oswriter.close();
            fostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
