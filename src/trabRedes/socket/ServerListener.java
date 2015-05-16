/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes.socket;

import java.net.DatagramPacket;

/**
 *
 * @author rodrigo
 */
public interface ServerListener {
    
    public void onPacketReceived(DatagramPacket packet);
    
}
