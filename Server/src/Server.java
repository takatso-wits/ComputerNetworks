import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
 
public class Server
{
 
    private static Socket socket;
    
    private static ArrayList<Integer> calculatePrimes(Integer num1, Integer num2) {
        int i;
        ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
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
        return primeNumbers;
    }

    private static boolean isPrime(int i) {
        boolean isPrime = true;
        for(int j=2; j <= i/2; j++){
            if(i%j == 0){
                isPrime = false;
            }
        }

        return isPrime;
    }
 
    public static void main(String[] args)
    {
        try
        {
 
            int port = 25002;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
 
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String[]number = br.readLine().split(",");
                System.out.println("Message received from client is "+number[0]+" and "+ number[1]);
 
                //Multiplying the number by 2 and forming the return message
                String returnMessage;
                try
                {
                    int numberInIntFormat = Integer.parseInt(number[0]);
                    int numberInIntFormat2 = Integer.parseInt(number[1]);
                    
                    // counting prime numbers
                    long a = System.nanoTime();
                    ArrayList<Integer> primes = calculatePrimes(numberInIntFormat, numberInIntFormat2);
                    int returnValue = primes.size();
                    long b = System.nanoTime();
                    
                    long time_x = b-a;
                    
                    returnMessage = String.valueOf(returnValue)+","+String.valueOf(time_x)+"\n";
                }
                catch(NumberFormatException e)
                {
                    //Input was not a number. Sending proper message back to client.
                    returnMessage = "Please send a proper number\n";
                }
 
                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is "+returnMessage);
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}