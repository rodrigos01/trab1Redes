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
        
        String ipDestino = "ip1";
        String ipSaida = "127.0.0.1";
        InetAddress saida = InetAddress.getByName(ipSaida);
        RouteLine line = new RouteLine(ipDestino, 1);
        
        Server srv = new Server();
        
        if(!saida.isAnyLocalAddress()) {
            srv.table.add(line, saida);
        } else {
            System.out.println("Saída é um IP local");
        }
        srv.setListener(srv);
        srv.start();
        
        for(int i = 2; i < 10; i++) {       
            srv.broadcast();
            
            String ipDestino2 = "ip"+i;
            String ipSaida2 = "127.0.0."+i;
            InetAddress saida2 = InetAddress.getByName(ipSaida2);
            RouteLine line2 = new RouteLine(ipDestino2, 1);
            
            srv.table.add(line2, saida2);
            Thread.sleep(5000);
        }
        
    }
    
}
