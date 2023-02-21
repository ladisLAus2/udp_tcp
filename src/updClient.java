import java.net.*;
import java.io.*;

public class updClient {

    public static final int PORT = 9999;
    public static void main(String[] args) throws IOException {
        double [] arrayDouble = new double[5];
        long [] arrayLong = new long[2];
        Additional.generateArrays(arrayDouble,arrayLong);
        try{
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];

            Additional.setOutputUPD(client,arrayDouble,arrayLong,sendBuffer,address,PORT);

            Additional.getInputUPD(client,receiveBuffer,address,null, arrayDouble,arrayLong,"server");
            client.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
