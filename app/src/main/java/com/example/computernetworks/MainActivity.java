package com.example.computernetworks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText startPoint, endPoint;
    TextView tvDisplayPrimes1, tvDisplayTime1, tvDisplayTime2, tvDisplayPrimes2,tvTotal;
    Button btnSend;
    Integer num1, num2, mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPoint = findViewById(R.id.et_A);
        endPoint = findViewById(R.id.et_B);
        btnSend = findViewById(R.id.btnSend);
        tvDisplayTime1 = findViewById(R.id.tvHost1_time);
        tvDisplayTime2 = findViewById(R.id.tvHost2_time);
        tvDisplayPrimes1 = findViewById(R.id.tvHost1_prime);
        tvDisplayPrimes2 = findViewById(R.id.tvHost2_primes);
        tvTotal = findViewById(R.id.tv_totalPrimes);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num1 = Integer.parseInt(startPoint.getText().toString());
                num2 = Integer.parseInt(endPoint.getText().toString());
                valicateInput(num1, num2);
                mid = (num1 + num2) / 2;

                SendData socket1 = new SendData();
                String d1 = num1.toString() + "," + mid.toString();
                mid = mid + 1;
                String d2 = mid.toString() + "," + num2.toString();
                socket1.execute(d1, d2);

            }
        });


    }


    private void valicateInput(Integer num1, Integer num2) {
        String err;
        if (num1 >= num2) {
            /*We will display a toast message for the error*/
            err = "Start Point needs to be less than the end point";
            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            return;
        } else if ((num1 < 0) || (num2 < 0)) {
            err = "Please enter positive integers";
            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            return;
        }
    }


    public class SendData extends AsyncTask<String, Void, String> {

        String num1;
        String num2;
        Socket socket;
        DataOutputStream dataOutputStream;
        PrintWriter printWriter;
        String ip2 = "10.100.6.39";
        String ip1 = "10.100.6.39";

        String Host1 = "", Host2 = "";

        Intent i = new Intent();

        private String host1;
        private String host2;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            /*Server 1: Data*/
            String[] data1 = Host1.split(",");
            tvDisplayPrimes1.setText(data1[0]);
            tvDisplayTime1.setText(data1[1]);
            String[] data2 = Host2.split(",");
            tvDisplayPrimes2.setText(data2[0]);
            tvDisplayTime2.setText(data2[1]);
            Integer primes = Integer.parseInt(data1[0]) + Integer.parseInt(data2[0]);
            tvTotal.setText(primes.toString());

        }

        @Override
        protected String doInBackground(String... voids) {

            num1 = voids[0];
            num2 = voids[1];
            Integer number_of_primes = 0;
            try {
                socket = new Socket(ip1, 25004);

                //Send the message to the server
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                String sendMessage = num1 + "\n";
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server 0 : " + sendMessage);

                //Get the return message from the server
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                //todo get respnose
                String message = br.readLine();
                System.out.println("Message from server 0: " + message + " nanoseconds");
                Host1 = message;
                String[] message1 = message.split(",");
                number_of_primes = Integer.parseInt(message1[0]);
                Long time_taken = Long.parseLong(message1[1]);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                socket = new Socket(ip2, 25005);
                //Send the message to the server
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                String sendMessage = num2 + "\n";
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server 1 : " + sendMessage);

                //Get the return message from the server
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                //todo get respnose
                String message = br.readLine();

                System.out.println("Message from server 1: " + message + " nanoseconds");
                String[] message1 = message.split(",");
                Host2 = message;
                number_of_primes += Integer.parseInt(message1[0]);
                System.out.println("Total Number of primes is " + number_of_primes);
                Long time_taken = Long.parseLong(message1[1]);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

