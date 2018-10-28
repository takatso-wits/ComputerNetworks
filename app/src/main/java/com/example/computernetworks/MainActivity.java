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

public class MainActivity extends AppCompatActivity/* implements View.OnClickListener*/{
    EditText startPoint, endPoint,num;
    TextView tvDisplay,tvDisplayTime, tvDisplayPrimes;
    Button btnSend;
    Integer num1, num2;
    String n1, n2;
    ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
    long startTime, finishTime,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPoint = (EditText)findViewById(R.id.et_A);
        endPoint = (EditText)findViewById(R.id.et_B);
        tvDisplay = (TextView)findViewById(R.id.tvPrimes);
        btnSend = (Button)findViewById(R.id.btnSend);
        tvDisplayPrimes = (TextView)findViewById(R.id.tv_num_primes);
        tvDisplayTime = (TextView)findViewById(R.id.tv_time);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n1 = startPoint.getText().toString();
                n2 = endPoint.getText().toString().trim();
                num1 = Integer.parseInt(n1);
                num2 = Integer.parseInt(n2);
                valicateInput(num1,num2);
                /*This is the method we should time*/
                startTime = System.nanoTime();
                calculatePrimes(num1,num2);
                finishTime = System.nanoTime();
                duration = (finishTime-startTime);
                printPrimeNumbers(primeNumbers);



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