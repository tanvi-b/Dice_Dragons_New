import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Game implements Runnable {

    GameUI gameUI;
    int maxPlayers;
    String accessCode;
    String username;
    int level;
    private ObjectOutputStream os;
    public static ArrayList<ObjectOutputStream> outs = new ArrayList<>();
    private ObjectInputStream is;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;
    public static String codeAll;

    public static ArrayList<String> players;

    public Game(ObjectOutputStream os, ObjectInputStream is)
    {
        this.os = os;
        this.is = is;
        heroes = new ArrayList<>();
        players = new ArrayList<>();

    }

    public void run() {
        try {
            while (true) {
                // Read message from the server
                CommandFromServer cfs = (CommandFromServer) is.readObject();
                // Process the received message

                if(cfs.getCommand() == CommandFromServer.ACCESS_CODE) {
                    SwingUtilities.invokeLater(() -> {
                        maxPlayers++;
                        players.add(cfs.getPlayer());
                        LobbyUI.updatePlayersinLobby(cfs.getPlayer());
                        setAccessCode(cfs.getData().toString());
                        LobbyUI.displayCode(getAccessCode());
                    });
                }
                if(cfs.getCommand() == CommandFromServer.MAKE_HERO) {
                    SwingUtilities.invokeLater(() -> {
                    maxPlayers++;
                    players.add(cfs.getPlayer());
                    LobbyUI.updatePlayersinLobby(cfs.getPlayer());
                    setAccessCode(cfs.getData().toString());
                    System.out.println(cfs.getData().toString());
                    LobbyUI.displayCode(getAccessCode());
                    });
                }
                if(cfs.getCommand() == CommandFromServer.INVALID_ACCESS_CODE){
                    JoinUI.invalidCodeShow();
                }
                if(cfs.getCommand() == CommandFromServer.INVALID_NAME){
                    JoinUI.duplicateName();
                }
                if(cfs.getCommand() == CommandFromServer.INVALID_CLASS){
                    JoinUI.duplicateClass();
                }
                if(cfs.getCommand() == CommandFromServer.MAX_PLAYERS){
                    JoinUI.maxPlayers();
                }
//                if(cfs.getCommand() == CommandFromServer.CHAT)
//                {
//                    game.addToChat(cfs.getMessage());
//                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendJoinUserLobby (ObjectOutputStream os, Object Data, String name)
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.JOIN, "valid", name);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendUserLobby(ObjectOutputStream os, String player)  {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.HOST, null, player);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendCode(ObjectOutputStream os, Object data, String player){
        try {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.JOIN, data, player);
            os.writeObject(cfc);
            os.flush();
        } catch (IOException e) {
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
    public ArrayList<String> allPlayers(){
        return players;
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

    public ArrayList<String> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<String> players){
        this.players = players;
    }

    public ArrayList<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(ArrayList<Dragon> dragons) {
        this.dragons = dragons;
    }

}
