/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.ServerListener;

/**
 *
 * @author rodrigo
 */
public class Listener implements ServerListener {
    
    RoutingTable table;

    public Listener(RoutingTable table) {
        this.table = table;
    }
    
    

    @Override
    public synchronized void onPacketReceived(DatagramPacket packet) {

        byte[] dados = packet.getData();
        ByteArrayInputStream byteInput = new ByteArrayInputStream(dados);
        /**
         * deserializa os dados do pacote
         */

        ObjectInputStream inStream;
        try {
            inStream = new ObjectInputStream(byteInput);
            RouteLine line = (RouteLine) inStream.readObject();
            inStream.close();
            byteInput.close();

            System.out.println("Tabela Recebida: " + line);
            table.add(line, packet.getAddress());
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}
