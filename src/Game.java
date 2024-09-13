import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Game implements Runnable {

    GameUI gameUI;
    String accessCode;
    String username;
    int level;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;

    public Game(ObjectOutputStream os, ObjectInputStream is)
    {
        this.os = os;
        this.is = is;
        heroes = new ArrayList<>();
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

    public void playerHost ()
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.HOST, null, username);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void playerJoin (ObjectOutputStream os, String info)
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.JOIN, "access code,character name", username);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ObjectOutputStream getOs() {
        return os;
    }

    public void setOs(ObjectOutputStream os) {
        this.os = os;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public ArrayList<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(ArrayList<Dragon> dragons) {
        this.dragons = dragons;
    }
}
