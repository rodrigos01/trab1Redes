/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.routing;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rodrigo
 */
public class RoutingTable extends ArrayList<RouteLine>{
    
    HashMap<RouteLine, InetAddress> saidas;

    public RoutingTable() {
        this.saidas = new HashMap<>();
    }
    
    
    public void add(RouteLine a, InetAddress saida){
        
        
        
        if(!contains(a)){
            System.out.println("Linha Recebida de "+saida.getHostAddress()+" : " + a);
            add(a);
            saidas.put(a, saida);
        } else {
            //System.out.println("Line ja existe");
        }
        
    }
    
    public InetAddress getSaida(RouteLine a){
        return saidas.get(a);
    }
    
}
