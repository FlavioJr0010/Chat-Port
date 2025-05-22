package Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        // Cria um Scanner para ler a porta do servidor a partir da entrada do usuário
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a porta do servidor: ");
        int port = scanner.nextInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Cria um ServerSocket na porta especificada e aguarda conexões
            System.out.println("[Servidor] Aguardando conexão na porta " + port);

            // Loop infinito para aceitar múltiplos clientes
            while (true) {
                // Aguarda uma conexão de cliente e retorna um Socket quando conectado
                Socket socket = serverSocket.accept();

                // Lê o nome do cliente enviado pelo cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientName = in.readLine();

                // Registra a conexão do cliente no console e no arquivo de log
                String connectEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + " conectou.";
                System.out.println(connectEntry);
                try (BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))) {
                    log.write(connectEntry); // Escreve no arquivo chat_log.txt
                    log.newLine();
                    log.flush();
                } catch (IOException logException) {
                    System.err.println("Erro ao escrever no log: " + logException.getMessage());
                }

                // Inicia uma nova thread para lidar com o cliente, passando o socket e o nome
                new Thread(new Client.Handler.ClientHandler(socket, clientName)).start();
            }

        } catch (IOException e) {
            // Trata erros relacionados à criação do servidor ou conexão
            e.printStackTrace();
        }
    }
}