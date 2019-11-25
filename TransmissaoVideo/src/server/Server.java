/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import transmissaovideo.Requisicao;

/**
 *
 * @author alexh
 */
public class Server {
    public static final int DEPOSITO = 0;
    public static final int RESGATE = 1;
    public static final int SALDO = 2;
    public static final int TED = 3;
    public static final int LOCAL_BANCO = 4;
    public static final int WELCOME = 5;
    public static final int VALIDA_CONTA = 6;
    public static final int REGISTRA_ORDEM = 7;
    public static final int PORTA_PADRAO = 25000;

    
   public static void main(String args[]){   
    int conectados=0;
    
        try
        {
            
            ServerSocket serverSocket;  // este é o socket que vai aguardar pedidos de conexao
            serverSocket = new ServerSocket(25000);
            Socket client; // este é o cara que efetivamente vai se conectar com o cliente
            System.out.println("SERVER alive...");
            while (true){
                System.out.println("SERVER - Aguardando Conexoes...");
                client = serverSocket.accept(); // fico travado aguardando pedidos de conexão
                conectados++;
                System.out.println("Numero de clientes já conectados " +conectados);
                System.out.println("Server conectado");
                                
                // preparando os canais de comunicação
                ObjectInputStream  in  = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                
                // recebendo a requisição
                Requisicao req = (Requisicao)in.readObject(); // receive
                
                //Resposta rep = new Resposta(); // objeto que irá conter a resposta
                
                // processa
                transmissaovideo.Process h = new transmissaovideo.Process();
                
                h.root = req.getObj();
                     
                String exibition = h.DecodeFile(req.getData());
                
                System.out.println("Tamanho do pacote descompactado: "+ exibition.length() );
                // envia resposta
                //out.writeObject(rep);
                
                in.close();
                out.close();
                //client.close(); // fechei o socket do cliente
            }
        }
        catch(Exception ex)
        {
            System.err.println("Deu ruim no servidor...");
            ex.printStackTrace();
        }
    }
  

}