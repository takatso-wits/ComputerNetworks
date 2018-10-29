package com.example.computernetworks;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendData extends AsyncTask<String,Void,Void> {

    String num1;
    Socket socket;
    Socket socket2;
    DataOutputStream dataOutputStream;
    PrintWriter printWriter;
   String ip1 = "10.100.6.39";
    String ip2 = "10.100.6.37";
    int count = 0;

    @Override
    protected Void doInBackground(String... voids) {

        num1 = voids[0];
        // num2 = voids[1];
        if (count == 0 || count == 1) {
            count++;
            try {
                socket = new Socket(ip1, 25002);
                printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.write(num1);
                printWriter.flush();
                printWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
