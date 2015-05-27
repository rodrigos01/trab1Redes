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
 * Esta classe envia a tabela de roteamento para os vizihos.
 * 
 * @author rodrigo
 */
public class Sender implements Runnable {
    
    private final RoutingTable table;

    public Sender(RoutingTable table) {
        this.table = table;
    }
    
    /**
     * Este método envia a tabela de roteamento para os vizihos 
     * à cada 5 segundos.
     * @throws IOException
     * @throws InterruptedException 
     */
    public void broadcast() throws IOException, InterruptedException {

        while (true) {            
            synchronized(table) {
                if(!table.isEmpty()) {
                    //System.out.println("enviando tabela:");
                    //System.out.println(table);
                    for (RouteLine line : table) {
                        if(line.getMetric() == 1)  {
                            InetAddress saida = table.getSaida(line);
                            String ipSaida = saida.getHostAddress();
                            sendMe(saida);
                        }
                    }
                }
            }
            
            Thread.sleep(5000);
        }
        
    }
    
    /**
     * Este método envia a tabela de roteamento para um viziho.
     * @param saida o IP do vizinho
     * @throws IOException 
     */
    private void sendMe(InetAddress saida) throws IOException {
        for (RouteLine line : table) {   
            sendLine(line, saida);
        }
    }
    
    /**
     * Este método envia uma linha de roteamento para um viziho.
     * @param line a linha a er enviada
     * @param saida o IP do vizinho
     * @throws IOException 
     */
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

    /**
     * Esta é uma thread que faz o broadcast em segundo plano
     */
    @Override
    public void run() {
        try {
            broadcast();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
