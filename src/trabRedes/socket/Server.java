/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author rodrigo
 */
public class Server extends DatagramSocket implements Runnable {

    public static final int DEFAULT_PORT = 9876;
    private ServerListener listener;

    public Server(int port) throws SocketException {
        super(port);
    }
    
    public Server() throws SocketException {
        this(DEFAULT_PORT);
    }
    
    public void start() {
        Thread serverThread = new Thread(this);
        serverThread.start();
    }

    public ServerListener getListener() {
        return listener;
    }

    public void setListener(ServerListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        while(true) {
            
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,1024);
            try {
                receive(receivePacket);
                if(listener!=null){
                    listener.onPacketReceived(receivePacket);
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
}
