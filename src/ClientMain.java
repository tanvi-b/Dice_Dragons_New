import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {


    public static void main(String[] args) {
        try {
            // Created a connection to the server
            //Pass the ip address and port number in edit config
            if (args==null || args.length<2)
            {
                System.out.println("Please provide IP Address and Port Number of the game server.");
                System.exit(0);
            }
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            Game game = new Game(os, is);
            Thread t = new Thread(game);
            t.start();

            GameUI gameUI = new GameUI(game);
            game.setGameUI(gameUI);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
