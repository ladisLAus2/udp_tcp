import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Random;

public class Additional {
    public static byte[] doubleToByteArray ( final double i ) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeDouble(i);
        dos.flush();
        return bos.toByteArray();
    }
    public static double convertByteArrayToDouble(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getDouble();
    }
    public static byte[] longToByteArray ( final long i ) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(i);
        dos.flush();
        return bos.toByteArray();
    }
    public static long convertByteArrayToLong(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getLong();
    }
    public static void generateArrays(double doubleArray[], long longArray[]){
        RandomDouble rd = (max, min) -> {return (new Random().nextDouble() * (max - min)) + min;};
        RandomLong rl = ((max, min) -> {return (min + (long) (Math.random() * (max - min)));});
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = rd.generateDouble(1000,1);
        }
        for (int i = 0; i < longArray.length; i++) {
            longArray[i] = rl.generateLong(1000, 1);
        }

    }
    public static void getInputTCP(double[] doubleArray, long[] longArray, DataInputStream inputStream, String whom) throws IOException{
        int receivedMessages = 0;
        while(receivedMessages < doubleArray.length+ longArray.length){
            if(receivedMessages >= doubleArray.length){
                longArray[receivedMessages% doubleArray.length] = inputStream.readLong();
                System.out.println("received from " + whom + " - " + longArray[receivedMessages% doubleArray.length]);
            }
            else{
                doubleArray[receivedMessages] = inputStream.readDouble();
                System.out.println("received from " + whom + " - " + doubleArray[receivedMessages]);
            }
            receivedMessages++;
        }
    }
    public static void setOutputTCP(double[] doubleArray, long[] longArray, DataOutputStream outputStream)throws IOException{
        for (int i = 0; i < doubleArray.length; i++) {
            outputStream.writeDouble(doubleArray[i]);
        }
        for (int i = 0; i < longArray.length; i++) {
            outputStream.writeLong(longArray[i]);
        }
    }
    public static InetAddress getInputUPD(DatagramSocket server, byte[] receiveBuffer, InetAddress address, int[] port, double[] arrayDouble, long[] arrayLong, String whom ) throws IOException{
        int receivedMessages = 0;
        while(receivedMessages < 7){
            DatagramPacket inputPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            server.receive(inputPacket);
            if(address == null && port[0] == 0) {
                address = inputPacket.getAddress();
                port[0] = inputPacket.getPort();
            }
            if(receivedMessages >= arrayDouble.length){
                arrayLong[(receivedMessages % arrayDouble.length)] = Additional.convertByteArrayToLong(inputPacket.getData());
                System.out.println("sent from "+ whom + " - " + arrayLong[(receivedMessages % arrayDouble.length)]);
            }
            else{
                arrayDouble[receivedMessages] = Additional.convertByteArrayToDouble(inputPacket.getData());
                System.out.println("sent from "+ whom + " - "+ arrayDouble[receivedMessages]);
            }
            receivedMessages++;
        }
        return address;
    }
    public static void setOutputUPD(DatagramSocket server,double[] arrayDouble, long[] arrayLong,byte[] sendBuffer,InetAddress address,int port) throws IOException{
        for (int i = 0; i < arrayDouble.length; i++) {
            sendBuffer = Additional.doubleToByteArray(arrayDouble[i]);
            DatagramPacket sendingPacket = new DatagramPacket(sendBuffer,sendBuffer.length, address,port);
            server.send(sendingPacket);
            sendBuffer = new byte[1024];
        }
        for (int i = 0; i < arrayLong.length; i++) {
            sendBuffer = Additional.longToByteArray(arrayLong[i]);
            DatagramPacket sendingPacket = new DatagramPacket(sendBuffer,sendBuffer.length, address,port);
            server.send(sendingPacket);
            sendBuffer = new byte[1024];
        }
    }
}
