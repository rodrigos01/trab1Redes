/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.socket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**
     * Este método inicia a thread de recebimento de pacotes.
     */
    public void start() {
        serverThread = new Thread(this);
        serverThread.start();
        
        Sender sender  = new Sender(table);
        senderThread = new Thread(sender);
        senderThread.start();
        
        isStarted = true;
    }

    public ServerListener getListener() {
        return listener;
    }

    public void setListener(ServerListener listener) {
        this.listener = listener;
    }

    /**
     * Este método espera em uma thread separada pelos pacotes enviados 
     * por clientes.
     */
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
    
    /**
     * Este método é chamado quando um pacote é recebido. Ele extrai 
     * a informação de roteamento do pacote e adiciona à tabela.
     * @param packet 
     */
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
                NetworkInterface iface = NetworkInterface.getByInetAddress(dest);
                //System.out.println(line.getDest()+" é o ip da interface "+iface.getDisplayName());
                return;
            }
            
            line.setMetric(line.getMetric()+1);
            
            

            synchronized(table) {
                if(table.add(line, packet.getAddress())) {
                    System.out.println("Linha Recebida de "+packet.getAddress()+" : " + line);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
