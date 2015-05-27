/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.routing;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author rodrigo
 */
public class RouteLine implements Serializable, Comparable<RouteLine> {
    
    private String dest;
    private int metric;

    public RouteLine(String dest, int metric) {
        this.dest = dest;
        this.metric = metric;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getMetric() {
        return metric;
    }

    public void setMetric(int metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return dest+"|"+metric;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.dest);
        hash = 71 * hash + this.metric;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RouteLine other = (RouteLine) obj;
        if (!Objects.equals(this.dest, other.dest)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public int compareTo(RouteLine o) {
        if(o.getDest().equals(dest)){
            return o.getMetric() - metric;
        }
        return 1;
    }
    
    
    
    
}
