package transport;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Network {

    public static void sendTo(DatagramSocket socket, PacketAudio o, InetAddress address, int desPort) {
        try {
            ByteArrayOutputStream byteStream = new
                    ByteArrayOutputStream(o.getNumBytesRead());
            ObjectOutputStream os = new ObjectOutputStream(new
                    BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(o);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(
                    sendBuf, sendBuf.length, address, desPort);
            socket.send(packet);
            os.close();
        } catch (UnknownHostException e) {
            System.err.println("Exception:  " + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PacketAudio receive(DatagramSocket socket, byte[] recvBuf) {
        try {
            DatagramPacket packet = new DatagramPacket(recvBuf,
                    recvBuf.length);
            socket.receive(packet);
            int byteCount = packet.getLength();
            ByteArrayInputStream byteStream = new
                    ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new
                    ObjectInputStream(new BufferedInputStream(byteStream));
            PacketAudio o = (PacketAudio) is.readObject();
            is.close();
            return (o);
        } catch (IOException e) {
            System.err.println("Exception:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (null);
    }
}