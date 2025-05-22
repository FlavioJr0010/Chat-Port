package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o host do servidor: ");
        String host = scanner.nextLine();

        System.out.print("Digite a porta do servidor: ");
        int port = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer

        System.out.print("Digite seu nome de usuário: ");
        String clientName = scanner.nextLine();

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Conectado ao servidor!");
            out.println(clientName);
            System.out.println("Nome enviado ao servidor!");

            Thread sender = new Thread(() -> {
                try {
                    String input;
                    while ((input = userInput.readLine()) != null) {
                        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("sair")) {
                            System.out.println("Encerrando conexão...");
                            break; // Sai do loop para encerrar a thread
                        }
                        out.println(input);
                        System.out.println("[Você]: " + input);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao enviar mensagem.");
                    e.printStackTrace();
                }
            });

            Thread receiver = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Conexão com o servidor encerrada.");
                }
            });

            sender.start();
            receiver.start();
            sender.join(); // Aguarda a thread de envio terminar
            socket.close(); // Fecha o socket após o término da thread de envio
            System.out.println("Conexão encerrada.");

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}