import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VoiceClient {
    private AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
    private TargetDataLine microphone;
    private SourceDataLine speakers;

    public VoiceClient() throws IOException {

        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);


            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();


            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            InetAddress address = InetAddress.getByName(Constant.hostname);

            DatagramSocket socket = new DatagramSocket();
            byte[] receiveBuffer = new byte[1024];

            while (true) {

                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                //  bytesRead += numBytesRead;
                // write the mic data to a stream for use later
                //out.write(data, 0, numBytesRead);
                // write mic data to stream for immediate playback

                DatagramPacket request = new DatagramPacket(data, numBytesRead, address, Constant.port);
                socket.send(request);

                DatagramPacket response = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(response);

                System.out.println("From : " + response.getAddress() + ":" + response.getPort());

                speakers.write(response.getData(), 0, response.getData().length);
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        new VoiceClient();
    }

}