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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabRedes.Listener;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;

/**
 *
 * @author rodrigo
 */
public class Server extends DatagramSocket implements Runnable, ServerListener {

    public static final int DEFAULT_PORT = 9876;
    private ServerListener listener;
    
    public final RoutingTable table = new RoutingTable();

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

            synchronized(table) {
                table.add(line, packet.getAddress());
            }
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void broadcast() throws IOException, InterruptedException {
        synchronized(table) {
            for (RouteLine line : table) {
                if(line.getMetric() == 1)  {
                    InetAddress saida = table.getSaida(line);
                    String ipSaida = saida.getHostAddress();
                    sendMe(saida);
                }
            }
        }
        
    }
    
    private void sendMe(InetAddress saida) throws IOException {
        for (RouteLine line : table) {   
            sendLine(line, saida);
        }
    }
    
    private void sendLine(RouteLine line, InetAddress saida) throws IOException {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOutput);
        outStream.writeObject(line);
        outStream.close();
        byteOutput.close();

        byte[] out = byteOutput.toByteArray();

        //System.out.println("Enviando string: " + out);
        
        Client.send(out, saida, Server.DEFAULT_PORT);
    }
    
}
