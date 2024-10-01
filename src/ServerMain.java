import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;

public class ServerMain {

    public static void main(String[] args) {
        try {
            // Create a ServerSocket to listen for client connections
            ServerSocket serverSocket = new ServerSocket(8001);

            System.out.println("Created by Tanvi Bhattad and Prisha Singh - 2024");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread to handle each client
                ServerListener serverListener = new ServerListener(is, os);
                Thread thread = new Thread(serverListener);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
