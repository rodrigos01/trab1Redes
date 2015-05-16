/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.Client;
import trabRedes.socket.Server;

/**
 *
 * @author rodrigo
 */
public class TrabRedes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Server srv = new Server();
        srv.setListener((DatagramPacket packet) -> {
            byte[] data = packet.getData();
            ByteArrayInputStream byteInput = new ByteArrayInputStream(data);
            try {
                System.out.println("Recebido "+new String(data));
                ObjectInputStream inStream = new ObjectInputStream(byteInput);
                RoutingTable table = (RoutingTable) inStream.readObject();
                inStream.close();
                byteInput.close();
                
                System.out.println("Tabela Recebida: "+table);
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(TrabRedes.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        });
        srv.start();
        
        Client client = new Client(InetAddress.getByName("localhost"), Server.DEFAULT_PORT);
        
        while(true) {
            
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(byteOutput);
            RouteLine table = new RouteLine("a", "b", 1);
            outStream.writeObject(table);
            outStream.close();
            byteOutput.close();
            
            byte[] out = byteOutput.toByteArray();
            
            System.out.println("Enviando string: "+out);
            
                        
            ObjectInputStream inStream = new ObjectInputStream(new ByteArrayInputStream(out));
            RoutingTable table2 = (RoutingTable) inStream.readObject();
            inStream.close();
                
            client.send(out);
            
            Thread.sleep(5000);
        }
        
    }
    
}
