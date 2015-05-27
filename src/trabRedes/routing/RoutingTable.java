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
 * Esta classe representa a tabela de roteamento.
 * Ela é um Lista dos IPs, suas saídas e métricas.
 * 
 * @author rodrigo
 */
public class RoutingTable extends ArrayList<RouteLine>{
    
    /**
     * Este atibuto é um mapa das saídas vinculadas 
     */
    HashMap<RouteLine, InetAddress> saidas;

    public RoutingTable() {
        this.saidas = new HashMap<>();
    }
    
    /**
     * 
     * Este método é responsável por adicionar linhas à tabela de roteamento.
     * Se a linha já existir com a mesma métrica, esta é descartada. Se a linha
     * recebida já existe mas tem uma métrica menor do que a atual, 
     * a atual é atualizada.
     * 
     * @param line a linha à ser adiciona à tabela.
     * @param saida o gateway da linha recebida.
     * @return 
     */
    public boolean add(RouteLine line, InetAddress saida){
        if(!contains(line)){
            add(line);
            saidas.put(line, saida);
            return true;
        } else {
            RouteLine i = get(indexOf(line));
            if(line.getMetric() < i.getMetric()) {
                remove(i);
                add(line);
                saidas.put(line, saida);
                return true;
            }
            //System.out.println("Line ja existe");
        }
        return false;
        
    }
    
    /**
     * Este método retorna o ip de saída de uma determinada linha da tabela.
     * @param line
     * @return 
     */
    public InetAddress getSaida(RouteLine line){
        return saidas.get(line);
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
