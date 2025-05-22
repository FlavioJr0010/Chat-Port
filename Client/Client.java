package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Cria um Scanner para ler entradas do usuário (host, porta, nome)
        Scanner scanner = new Scanner(System.in);

        // Solicita o host do servidor
        System.out.print("Digite o host do servidor: ");
        String host = scanner.nextLine();

        // Solicita a porta do servidor
        System.out.print("Digite a porta do servidor: ");
        int port = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do Scanner após ler o número

        // Solicita o nome de usuário do cliente
        System.out.print("Digite seu nome de usuário: ");
        String clientName = scanner.nextLine();

        // Cria um BufferedReader para ler entradas do usuário durante o chat
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try (
                // Cria um Socket para conectar ao servidor no host e porta especificados
                Socket socket = new Socket(host, port);
                // Cria um BufferedReader para ler mensagens do servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Cria um PrintWriter para enviar mensagens ao servidor
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Confirma a conexão com o servidor
            System.out.println("Conectado ao servidor!");
            // Envia o nome do cliente ao servidor
            out.println(clientName);
            System.out.println("Nome enviado ao servidor!");

            // Thread para enviar mensagens do cliente
            Thread sender = new Thread(() -> {
                try {
                    String input;
                    // Lê entradas do usuário até que sejam nulas ou "exit"/"sair"
                    while ((input = userInput.readLine()) != null) {
                        // Verifica se o usuário quer encerrar a conexão
                        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("sair")) {
                            System.out.println("Encerrando conexão...");
                            break; // Sai do loop para encerrar a thread
                        }
                        // Envia a mensagem ao servidor e exibe no console do cliente
                        out.println(input);
                        System.out.println("[Você]: " + input);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao enviar mensagem.");
                    e.printStackTrace();
                }
            });

            // Thread para receber mensagens do servidor
            Thread receiver = new Thread(() -> {
                try {
                    String message;
                    // Lê mensagens do servidor até que a conexão seja fechada
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Conexão com o servidor encerrada.");
                }
            });

            // Inicia as threads de envio e recebimento
            sender.start();
            receiver.start();
            // Aguarda a thread de envio terminar (quando o usuário digita "exit" ou "sair")
            sender.join();
            // Fecha o socket após o término da thread de envio
            socket.close();
            System.out.println("Conexão encerrada.");

        } catch (IOException | InterruptedException e) {
            // Trata erros de conexão ou interrupção de threads
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}