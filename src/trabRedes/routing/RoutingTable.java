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
            add(a);
            saidas.put(a, saida);
        } else {
            //System.out.println("Line ja existe");
        }
        
    }
    
    public InetAddress getSaida(RouteLine a){
        return saidas.get(a);
    }

    @Override
    public String toString() {
        String table = "";
            for(RouteLine line: this) {
                table += line + "|" + getSaida(line).getHostAddress();
            }
        return table;
    }
    
    
    
}
