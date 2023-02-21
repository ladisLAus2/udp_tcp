import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class updClient {
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
    public static void main(String[] args) throws IOException {
        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];

            double sentence = 5.3;

            sendBuffer = doubleToByteArray(sentence);
            DatagramPacket sendingPacket = new DatagramPacket(sendBuffer,sendBuffer.length, address,PORT);
            socket.send(sendingPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            double result = convertByteArrayToDouble(receivePacket.getData());
            System.out.println("received from server " + result);

            socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
