package Client.Handler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientName;

    public ClientHandler(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                String logEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + ": " + message;
                System.out.println(logEntry);
                log.write(logEntry);
                log.newLine();
                log.flush();
            }
            // Mensagem de desconexão
            String disconnectEntry = "[" + java.time.LocalDateTime.now() + "] " + clientName + " desconectou.";
            System.out.println(disconnectEntry);
            log.write(disconnectEntry);
            log.newLine();
            log.flush();
        } catch (IOException e) {
            String errorEntry = "[" + java.time.LocalDateTime.now() + "] Erro com cliente " + clientName + ": " + e.getMessage();
            System.err.println(errorEntry);
            try (BufferedWriter log = new BufferedWriter(new FileWriter("chat_log.txt", true))) {
                log.write(errorEntry);
                log.newLine();
                log.flush();
            } catch (IOException logException) {
                System.err.println("Erro ao escrever no log: " + logException.getMessage());
            }
        }
    }
}