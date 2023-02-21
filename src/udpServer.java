import java.net.*;
import java.io.*;

public class udpServer {

    public static final int PORT = 9999;
    public static void main(String[] args) throws IOException{
        try{
            DatagramSocket server = new DatagramSocket(PORT);
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];
            double [] arrayDouble = new double[5];
            long [] arrayLong = new long[2];
            InetAddress address = null;
            int port[] = {0};
            address = Additional.getInputUPD(server,receiveBuffer,address,port,arrayDouble,arrayLong,"client");

            Additional.setOutputUPD(server,arrayDouble,arrayLong,sendBuffer,address,port[0]);
            server.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
