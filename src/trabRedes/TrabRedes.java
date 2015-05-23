/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import trabRedes.routing.RouteLine;
import trabRedes.routing.RoutingTable;
import trabRedes.socket.Client;
import trabRedes.socket.Server;

/**
 *
 * @author rodrigo
 */
public class TrabRedes {
    
    public static Server srv;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        srv = new Server();
        srv.setListener(srv);
        srv.start();
        
        int opcao;
        System.out.println("Bem-vindo!");

        while (true) {
//            System.out.println("\nDigite a opção desejada:");
//            System.out.println("1- Adicionar vizinho");
//            System.out.println("2- Exibir tabela de roteamento");
//            System.out.println("3- Sair");
            String result = JOptionPane.showInputDialog("Digite a opção desejada:"
                    + "\n 1- Adicionar vizinho"
                    + "\n 2- Exibir tabela de roteamento"
                    + "\n 3- Sair");
            opcao = Integer.parseInt(result);
            //Scanner sc = new Scanner(System.in);
            //opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    addVizinho();
                    break;
                case 2:
                    exibeTabela();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private static void teste() throws SocketException, UnknownHostException, InterruptedException, IOException {
//        String ipDestino = "ip1";
//        String ipSaida = "10.32.148.121";
//        InetAddress saida = InetAddress.getByName(ipSaida);
//        RouteLine line = new RouteLine(ipDestino, 1);
//
//        Server srv = new Server();
//
//        if (!saida.isAnyLocalAddress()) {
//            srv.table.add(line, saida);
//        } else {
//            System.out.println("Saída é um IP local");
//        }
//        srv.setListener(srv);
//        srv.start();

        /*for (int i = 2; i < 10; i++) {
        srv.broadcast();
        
        String ipDestino2 = "ip" + i;
        String ipSaida2 = "10.32.148.12" + i;
        InetAddress saida2 = InetAddress.getByName(ipSaida2);
        RouteLine line2 = new RouteLine(ipDestino2, 1);
        
        srv.table.add(line2, saida2);
        Thread.sleep(5000);
        }*/
    }

    private static void addVizinho() throws UnknownHostException, SocketException {
        String ipDestino = JOptionPane.showInputDialog("Digite o IP:");
        String ipSaida = ipDestino;
        InetAddress saida = InetAddress.getByName(ipSaida);
        RouteLine line = new RouteLine(ipDestino, 1);

        if (NetworkInterface.getByInetAddress(saida) == null) {
            srv.table.add(line, saida);
        } else {
            System.out.println("Saída é um IP local");
        }
        
    }

    private static void exibeTabela() {
        JOptionPane.showMessageDialog(null, srv.table.toString());
    }
    
    private static void stop() {
        srv.stop();
    }

}
