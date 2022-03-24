import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static void main(String[] args) throws IOException {
        int count = 0;
        ServerSocket serverSocket = new ServerSocket(80);
        ThreadPoolExecutor threads = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


        threads.execute(
                () -> {
                    while (true) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Client accepted ");

                            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

                            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                            BufferedReader reader = new BufferedReader(isr);

                            String request = reader.readLine();
                            String response = "#" + ", your message length is " + request.length() + "\n";

                            writer.write(response);
                            writer.flush();

                            writer.close();
                            reader.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
