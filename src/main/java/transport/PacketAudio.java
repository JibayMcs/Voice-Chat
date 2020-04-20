package transport;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author ZeAmateis
 */
public class PacketAudio implements Serializable {

    private String address;
    private int port;
    private byte[] data;
    private int numBytesRead;

    public PacketAudio() {
    }

    public PacketAudio(String address, int port, byte[] data, int numBytesRead) {
        this.address = address;
        this.port = port;
        this.data = data;
        this.numBytesRead = numBytesRead;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public byte[] getData() {
        return data;
    }

    public int getNumBytesRead() {
        return numBytesRead;
    }

    @Override
    public String toString() {
        return "PacketAudio{" +
                "address=" + address +
                ", port=" + port +
                ", data=" + Arrays.toString(data) +
                ", numBytesRead=" + numBytesRead +
                '}';
    }
}
