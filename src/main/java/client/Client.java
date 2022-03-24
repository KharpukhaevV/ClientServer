package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 80)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String response;
            String nickName = "empty";
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            do {
                if (nickName.equals("empty")) {
                    System.out.print("Enter your name : ");
                    userInput = scanner.nextLine();
                    nickName = userInput;
                    output.println("\nUser " + userInput + " connected.");
                    if (userInput.equals("exit")) {
                        break;
                    }
                } else {
                    Thread.sleep(10);
                    System.out.print(nickName + "-> ");
                    userInput = scanner.nextLine();
                    output.println("\n#" + nickName + ": " + userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
                }
            } while (!userInput.equals("exit"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
