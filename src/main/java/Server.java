import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public Server() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(Constant.port);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(response);
                serverSocket.send(response);
            }

        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}