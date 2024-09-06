import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener implements Runnable {
  private Socket socket;
  private ObjectInputStream inputStream; 
  private ObjectOutputStream outputStream;
  private Game game; 

  public ClientListener(ObjectInputStream is, ObjectOutputStream os, Game game){
    this.inputStream = is; 
    this.outputStream = os; 
    this.game = game; 
  }
  public void run() {
        try {
            while (true) {
                // Read message from the server
                CommandFromServer cfs = (CommandFromServer) inputStream.readObject();

                // Process the received message
                if(cfs.getCommand() == CommandFromServer.PLAY) {
                   //inside code will follow this syntax
                  // game.method(cfs.getVariable()); 
                }
                if(cfs.getCommand() == CommandFromServer.ADD_HERO){
                    
                }
                if(cfs.getCommand() == CommandFromServer.DISCONNECT){
                    
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
