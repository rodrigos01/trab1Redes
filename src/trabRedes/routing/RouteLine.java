/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.routing;

import java.io.Serializable;

/**
 *
 * @author rodrigo
 */
public class RouteLine implements Serializable {
    
    private String dest;
    private String gateway;
    private int metric;

    public RouteLine(String dest, String gateway, int metric) {
        this.dest = dest;
        this.gateway = gateway;
        this.metric = metric;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public int getMetric() {
        return metric;
    }

    public void setMetric(int metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return dest+"|"+gateway+"|"+metric;
    }
    
    
    
}
