import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static String name;

    public static void main(String[] args) {
        try {
            // Created a connection to the server
            Socket socket = new Socket("127.0.0.1", 8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name: ");
            name = scanner.nextLine();

            System.out.println("Enter your IP address: ");
            String ip = scanner.nextLine();

            System.out.println("You are connected as " + name);
            Game gameC = new Game(os, name);

            ClientListener cl = new ClientListener (is, os, gameC);
            Thread t = new Thread(cl);
            t.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
