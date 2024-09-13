//eventually delete

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener implements Runnable {
  private Socket socket;
  private ObjectInputStream is;
  private ObjectOutputStream os;
  private Game game; 

  public ClientListener(ObjectInputStream is, Game game){
    this.is = is;
    this.game = game; 
  }
  public void run() {
        try {
            while (true) {
                // Read message from the server
                CommandFromServer cfs = (CommandFromServer) is.readObject();

                // Process the received message
                if(cfs.getCommand() == CommandFromServer.ACCESS_CODE) {
                   //inside code will follow this syntax
                  // game.method(cfs.getVariable()); 
                }
                if(cfs.getCommand() == CommandFromServer.CONNECT){
                    game.addPlayerToLobby(cfs.getPlayer());
                }
                if(cfs.getCommand() == CommandFromServer.INVALID_ACCESS_CODE){

                }
                if(cfs.getCommand() == CommandFromServer.INVALID_NAME){

                }
                if(cfs.getCommand() == CommandFromServer.MAKE_HERO)
                {

                }
                if(cfs.getCommand() == CommandFromServer.CHAT)
                {
                game.addToChat(cfs.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
