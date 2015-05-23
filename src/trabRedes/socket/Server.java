/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabRedes.Listener;
import trabRedes.Sender;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;

/**
 *
 * @author rodrigo
 */
public class Server extends DatagramSocket implements Runnable, ServerListener {

    public static final int DEFAULT_PORT = 9876;
    private ServerListener listener;
    
    Thread serverThread, senderThread;
    boolean isStarted = false;
    
    public final RoutingTable table = new RoutingTable();

    public Server(int port) throws SocketException {
        super(port);
    }
    
    public Server() throws SocketException {
        this(DEFAULT_PORT);
    }
    
    public void start() {
        serverThread = new Thread(this);
        serverThread.start();
        
        Sender sender  = new Sender(table);
        senderThread = new Thread(sender);
        senderThread.start();
        
        isStarted = true;
    }
    
    public void stop() {
        if(isStarted) {
            senderThread.stop();
            serverThread.stop();
        }
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
    
    @Override
    public void onPacketReceived(DatagramPacket packet) {

        byte[] dados = packet.getData();
        ByteArrayInputStream byteInput = new ByteArrayInputStream(dados);

        ObjectInputStream inStream;
        try {
            inStream = new ObjectInputStream(byteInput);
            RouteLine line = (RouteLine) inStream.readObject();
            inStream.close();
            byteInput.close();
            
            InetAddress dest = InetAddress.getByName(line.getDest());
            
            if(NetworkInterface.getByInetAddress(dest) != null) {
                return;
            }
            
            line.setMetric(line.getMetric()+1);
            

            synchronized(table) {
                table.add(line, packet.getAddress());
                System.out.println("Linha Recebida de "+packet.getAddress()+" : " + line);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
