package Client.Handler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket; // Socket do cliente conectado
    private final String clientName; // Nome do cliente

    // Construtor que recebe o socket e o nome do cliente
    public ClientHandler(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try (
                // Cria um BufferedReader para ler mensagens do cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Cria um BufferedWriter para registrar mensagens no arquivo de log
                BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))
        ) {
            String message;
            // Lê mensagens do cliente até que a conexão seja fechada
            while ((message = in.readLine()) != null) {
                // Formata a mensagem com timestamp e nome do cliente
                String logEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + ": " + message;
                System.out.println(logEntry);
                // Escreve a mensagem no arquivo de log
                log.write(logEntry);
                log.newLine();
                log.flush();
            }
            // Registra a desconexão do cliente no console e no log
            String disconnectEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + " desconectou.";
            System.out.println(disconnectEntry);
            log.write(disconnectEntry);
            log.newLine();
            log.flush();
        } catch (IOException e) {
            // Trata erros de leitura/escrita com o cliente
            String errorEntry = "[" + java.time.LocalDateTime.now() + "] Erro com cliente " + clientName + ": " + e.getMessage();
            System.err.println(errorEntry);
            try (BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))) {
                // Registra o erro no arquivo de log
                log.write(errorEntry);
                log.newLine();
                log.flush();
            } catch (IOException logException) {
                System.err.println("Erro ao escrever no log: " + logException.getMessage());
            }
        }
    }
}