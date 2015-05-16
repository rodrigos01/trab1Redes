/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.net.DatagramPacket;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.ServerListener;

/**
 *
 * @author rodrigo
 */
public class listener implements ServerListener {

    @Override
    public void onPacketReceived(DatagramPacket packet) {
        
        RoutingTable table = new RoutingTable();
        
        byte[] dados= packet.getData();
        /**
         * deserializa os dados do pacote
         */
        
        table.add(null, packet.getAddress());
        
    }
    
}
