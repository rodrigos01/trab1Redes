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
    
    
    public boolean add(RouteLine a, InetAddress saida){
        if(!contains(a)){
            add(a);
            saidas.put(a, saida);
            return true;
        } else {
            RouteLine i = get(indexOf(a));
            if(a.getMetric() < i.getMetric()) {
                remove(i);
                add(a);
                saidas.put(a, saida);
                return true;
            }
            //System.out.println("Line ja existe");
        }
        return false;
        
    }
    
    public InetAddress getSaida(RouteLine a){
        return saidas.get(a);
    }

    @Override
    public String toString() {
        String table = "";
            for(RouteLine line: this) {
                table += "\n" + line.getDest() + "|" 
                        + getSaida(line).getHostAddress() + "|" 
                        + line.getMetric();
            }
        return table;
    }
    
    
    
}
