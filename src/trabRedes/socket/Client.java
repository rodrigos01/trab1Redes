/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author rodrigo
 */
public class Client extends DatagramSocket {

    private InetAddress serverAddress;
    private int serverPort;

    public Client(InetAddress serverAddress, int serverPort) throws SocketException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }
    
    public void send(String message) throws IOException {
        byte[] data = message.getBytes();
        send(data);
    }
    
    public void send(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
        this.send(packet);
    }
    
    public static void send(String message, InetAddress serverAddress, int serverPort) throws IOException {
        new Client(serverAddress, serverPort).send(message);
    }
    
    public static void send(byte[] data, InetAddress serverAddress, int serverPort) throws IOException {
        new Client(serverAddress, serverPort).send(data);
    }
    

    
}
