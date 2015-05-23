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
public class Sender implements Runnable {
    
    private final RoutingTable table;

    public Sender(RoutingTable table) {
        this.table = table;
    }
    
    public void broadcast() throws IOException, InterruptedException {

        while (true) {            
            synchronized(table) {
                System.out.println("enviando tabela:");
                System.out.println(table);
                for (RouteLine line : table) {
                    if(line.getMetric() == 1)  {
                        InetAddress saida = table.getSaida(line);
                        String ipSaida = saida.getHostAddress();
                        sendMe(saida);
                    }
                }
            }
            
            Thread.sleep(5000);
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

    @Override
    public void run() {
        try {
            broadcast();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
