import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Game implements Runnable {

    GameUI gameUI;
    int maxPlayers;
    String accessCode;
    int level;
    private ObjectOutputStream os;
    public static ArrayList<ObjectOutputStream> outs = new ArrayList<>();
    private ObjectInputStream is;
    public String currentHero;
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
                CommandFromServer cfs = (CommandFromServer) is.readObject();

                if(cfs.getCommand() == CommandFromServer.ACCESS_CODE) {
                    accessCode = cfs.getData().toString();
                    System.out.println(ServerListener.currentGames);
                    LobbyUI.displayCode(accessCode);
                    LobbyUI.refreshLobby(cfs.getPlayer().toString());
                    PlayingUI.displayPlayerSheet(currentHero);
                }
                else if(cfs.getCommand() == CommandFromServer.MAKE_HERO) {
                    gameUI.cardLayout.show(gameUI.mainPanel, "LobbyScreen");
                    LobbyUI.displayCode(cfs.getData().toString());
                    LobbyUI.refreshLobby(cfs.getPlayer().toString());
                    PlayingUI.displayPlayerSheet(currentHero);
                }
                else if(cfs.getCommand() == CommandFromServer.INVALID_ACCESS_CODE){
                    JoinUI.invalidCodeShow();
                }
                else if(cfs.getCommand() == CommandFromServer.INVALID_NAME){
                    JoinUI.duplicateName();
                }
                else if(cfs.getCommand() == CommandFromServer.INVALID_CLASS){
                    JoinUI.duplicateClass();
                }
                else if(cfs.getCommand() == CommandFromServer.MAX_PLAYERS){
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

    public String toString() {
        return "Game {Access Code: " + accessCode + ", Heroes: " + heroes + ", Players: " + maxPlayers + "}";
    }

    public void playerJoin (ObjectOutputStream os, String info, String name)
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.JOIN, info, name);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void playerHost(ObjectOutputStream os, String info, String player)  {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.HOST, info, player);
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

     public void heroSheetJoin (ObjectOutputStream os, String info, String name)
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.JOIN, info, name);
            currentHero = name;
            os.writeObject(cfc);
            os.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void heroSheetJoinForHost (ObjectOutputStream os, String info, String name)
    {
        try
        {
            CommandFromClient cfc = new CommandFromClient(CommandFromClient.HOST, info, name);
            currentHero = name;
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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }


    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
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
