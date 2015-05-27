/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabRedes;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import trabRedes.routing.RouteLine;
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
    public static void main(String[] args) throws SocketException, UnknownHostException {
        /* 
        * Aqui iniciamos o Servidor. O Método start() inicia uma thread 
        * que aguarda conexões
        */
        srv = new Server();
        srv.setListener(srv);
        srv.start();
        
        int opcao;
        System.out.println("Bem-vindo!");

        /*
          Aqui iniciamos o loop do menu. O Menu ficará voltando até o usuário
          selecionar a opção 3.
        */
        while (true) {
            String result = JOptionPane.showInputDialog("Digite a opção desejada:"
                    + "\n 1- Adicionar vizinho"
                    + "\n 2- Exibir tabela de roteamento"
                    + "\n 3- Sair");
            opcao = Integer.parseInt(result);

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

    /**
     * addVizinho
     * 
     * Este método serve para adicionar um vizinho para o servidor.
     * 
     * @throws UnknownHostException
     * @throws SocketException 
     */
    private static void addVizinho() throws UnknownHostException, SocketException {
        String ipDestino = JOptionPane.showInputDialog("Digite o IP:");
        String ipSaida = ipDestino;
        InetAddress saida = InetAddress.getByName(ipSaida);
        RouteLine line = new RouteLine(ipDestino, 1);

        if (NetworkInterface.getByInetAddress(saida) == null) {
            srv.table.add(line, saida);
        } else {
            JOptionPane.showMessageDialog(null, ipDestino + " é um IP local");
        }
        
    }

    /**
     * Este método exibe a tabela de roteamento.
     */
    private static void exibeTabela() {
        JOptionPane.showMessageDialog(null, srv.table.toString());
    }

}
