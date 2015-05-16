/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.io.IOException;
import java.net.InetAddress;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.Client;

/**
 *
 * @author rodrigo
 */
public class teste {
    
    public void doa() throws IOException {
        
        RoutingTable table = new RoutingTable();
        
        for(RouteLine linha: table) {
            
            InetAddress saida = table.getSaida(linha);
            
            /**
             * Serializa a linha
             */
            
            Client.send(null, saida, 8080);
            
        }
        
    }
    
}
