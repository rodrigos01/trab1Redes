/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.Client;
import trabRedes.socket.Server;

/**
 *
 * @author rodrigo
 */
public class Sender {
    
    RoutingTable table;

    public Sender(RoutingTable table) {
        this.table = table;
    }
    
    public void broadcast() throws IOException, InterruptedException {

        while (true) {            
            for (RouteLine line : table) {
                
                InetAddress saida = table.getSaida(line);
                
                InetAddress local = InetAddress.getLocalHost();
                
                if(saida.equals(local))
                    continue;
                
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ObjectOutputStream outStream = new ObjectOutputStream(byteOutput);
                outStream.writeObject(line);
                outStream.close();
                byteOutput.close();
                
                byte[] out = byteOutput.toByteArray();
                
                System.out.println("Enviando string: " + out);
                
                Client.send(out, saida, Server.DEFAULT_PORT);
                
            }
            
            Thread.sleep(5000);
        }
        
    }
    
}
