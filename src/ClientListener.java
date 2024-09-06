public class ClientListener {
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
