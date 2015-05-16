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
    
    public void add(RouteLine a, InetAddress saida){
        
        /**
         * Tratar ip destino duplicado na linha
         */
        
        add(a);
        saidas.put(a, saida);
        
    }
    
    public InetAddress getSaida(RouteLine a){
        return saidas.get(a);
    }
    
}
