import java.net.*;
import java.io.*;
public class TCPServer {
    public static final int PORT = 50001;
    public static void main(String[] args) {
        double[] doubleArray = new double[5];
        long [] longArray = new long[2];
        try{
            ServerSocket server = new ServerSocket(PORT);
            Socket client = server.accept();

            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());

            Additional.getInputTCP(doubleArray,longArray,inputStream, "client");
            Additional.setOutputTCP(doubleArray,longArray,outputStream);

            server.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
