import java.net.*;
import java.io.*;
public class TCPClient {
    public static void main(String[] args) {
        double [] doubleArray = new double[5];
        long [] longArray = new long[2];
        Additional.generateArrays(doubleArray,longArray);
        try{
            Socket client = new Socket("localhost", 50001);

            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());

            Additional.setOutputTCP(doubleArray,longArray,outputStream);
            Additional.getInputTCP(doubleArray,longArray,inputStream,"server");

            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
