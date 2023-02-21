import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class udpServer {
    private static byte[] doubleToByteArray ( final double i ) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeDouble(i);
        dos.flush();
        return bos.toByteArray();
    }
    private static double convertByteArrayToDouble(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getDouble();
    }
    public static final int PORT = 9999;
    public static void main(String[] args) throws IOException{
        try{
            DatagramSocket socket = new DatagramSocket(PORT);
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];

            DatagramPacket inputPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            System.out.println("waiting client to connect");

            socket.receive(inputPacket);
            String receivedData = new String(inputPacket.getData());
            System.out.println(receivedData);
            double num = convertByteArrayToDouble(inputPacket.getData());
            System.out.println("sent from client " + num);
            num+=10.2;
            sendBuffer = doubleToByteArray(num);
            InetAddress address = inputPacket.getAddress();
            int sendPort = inputPacket.getPort();

            DatagramPacket outputPacket = new DatagramPacket(sendBuffer,sendBuffer.length,address,sendPort);
            socket.send(outputPacket);
            socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
