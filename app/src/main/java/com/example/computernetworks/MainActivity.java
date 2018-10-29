package com.example.computernetworks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText startPoint, endPoint,num, dat;
    TextView tvDisplay,tvDisplayTime, tvDisplayPrimes;
    Button btnSend;
    Integer num1, num2, mid, b;
    String n1, n2;
    ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
    long startTime, finishTime,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPoint = (EditText)findViewById(R.id.et_A);
        endPoint = (EditText)findViewById(R.id.et_B);
        dat = (EditText)findViewById(R.id.et_Text);
        tvDisplay = (TextView)findViewById(R.id.tvPrimes);
        btnSend = (Button)findViewById(R.id.btnSend);
        tvDisplayPrimes = (TextView)findViewById(R.id.tv_num_primes);
        tvDisplayTime = (TextView)findViewById(R.id.tv_time);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num1 = Integer.parseInt(startPoint.getText().toString());
                num2 = Integer.parseInt(endPoint.getText().toString());
                valicateInput(num1,num2);
                mid = (num1+num2)/2;

                /*Here I am creating sockets for Host 1*/
              SendData socket1 = new SendData();
//              SendData socket2 = new SendData();
              String d1 = num1.toString()+","+mid.toString();
                socket1.execute(d1, "0");
//                socket2.execute(mid.toString(), "0");
                mid = mid+1;
                /*Here I am creating sockets for Host 2*/
                SendData socket3 = new SendData();
//                SendData socket4 = new SendData();
                String d2 = mid.toString() + "," + num2.toString();
                socket3.execute(d2, "1");
//                socket4.execute(num2.toString(), "1");

                /*primeNumbers.clear();
                duration =0;
                tvDisplay.setText("");
                tvDisplayTime.setText("");
                tvDisplayPrimes.setText("");
                n1 = startPoint.getText().toString();
                n2 = endPoint.getText().toString().trim();
                num1 = Integer.parseInt(n1);
                num2 = Integer.parseInt(n2);

                *//*This is the method we should time*//*
                startTime = System.nanoTime();
                calculatePrimes(num1,num2);
                finishTime = System.nanoTime();
                duration = (finishTime-startTime);
                printPrimeNumbers(primeNumbers);*/



            }
        });
    }
    private void printPrimeNumbers(ArrayList<Integer> primeNumbers) {
        for(int i = 0; i < primeNumbers.size(); i++){
            tvDisplay.append(primeNumbers.get(i).toString());
            tvDisplay.append(" ");
        }
        tvDisplayTime.setText(Double.toString(duration));
        tvDisplayPrimes.setText(Integer.toString(primeNumbers.size()));
    }

    private void calculatePrimes(Integer num1, Integer num2) {
        int i;
        if((num1 == 0) || (num1 == 1)){
            i = 2;
        }else{
            i = num1;
        }

        for( ; i <= num2; i++){
            if(isPrime(i) == true){
                primeNumbers.add(i);
            }
        }

    }

    private boolean isPrime(int i) {
        boolean isPrime = true;
        for(int j=2; j <= i/2; j++){
            if(i%j == 0){
                isPrime = false;
            }
        }

        return isPrime;
    }

    private void valicateInput(Integer num1, Integer num2) {
        String err;
        if(num1 >= num2){
            /*We will display a toast message for the error*/
            err = "Start Point needs to be less than the end point";
            Toast.makeText(getApplicationContext(),err,Toast.LENGTH_LONG).show();
            return;
        }else if((num1 < 0)  || (num2 < 0)){
            err = "Please enter positive integers";
            Toast.makeText(getApplicationContext(),err,Toast.LENGTH_LONG).show();
            return;
        }
    }


}
