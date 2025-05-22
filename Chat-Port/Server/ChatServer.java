package Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a porta do servidor: ");
        int port = scanner.nextInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Servidor] Aguardando conexão na porta " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientName = in.readLine();

                String connectEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + " conectou.";
                System.out.println(connectEntry);
                try (BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))) {
                    log.write(connectEntry);
                    log.newLine();
                    log.flush();
                } catch (IOException logException) {
                    System.err.println("Erro ao escrever no log: " + logException.getMessage());
                }

                new Thread(new Client.Handler.ClientHandler(socket, clientName)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}