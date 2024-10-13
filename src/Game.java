import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game implements Runnable, Serializable {

    GameUI gameUI;
    int maxPlayers;
    String accessCode;
    int level;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;
    List<Map.Entry<Boolean, Integer>> diceRolled;
    ArrayList<String> messages;

    public Game(ObjectOutputStream os, ObjectInputStream is)
    {
        this.os = os;
        this.is = is;
        heroes = new ArrayList<>();
        diceRolled = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void run() {
        try {
            while (true) {
                CommandFromServer cfs = (CommandFromServer) is.readObject();

                if(cfs.getCommand() == CommandFromServer.ACCESS_CODE) {
                    LobbyUI.displayCode(((Game) cfs.getData()).getAccessCode());
                    LobbyUI.refreshLobby(((Game) cfs.getData()).getHeroes(), ((Game) cfs.getData()).getMaxPlayers());
                    PlayingUI.addHeroes(((Game) cfs.getData()).getHeroes());
                    PlayingUI.setFields((Hero) cfs.getPlayer());
                    PlayingUI.setUsername((Hero) cfs.getPlayer());
                    PlayingUI.setAccessCode(((Game) cfs.getData()).getAccessCode());
                    PlayingUI.setLevel(((Game) cfs.getData()).getLevel());
                    PlayingUI.getDice(((Game) cfs.getData()).getDiceRolled());
                    SpecialSkillsUI.setHeroClass((Hero) cfs.getPlayer());
                    SpecialSkillsUI.getDice(((Game) cfs.getData()).getDiceRolled());
                }
                else if(cfs.getCommand() == CommandFromServer.MAKE_HERO) {
                    gameUI.cardLayout.show(gameUI.mainPanel, "LobbyScreen");
                    LobbyUI.displayCode(((Game) cfs.getData()).getAccessCode());
                    LobbyUI.refreshLobby(((Game) cfs.getData()).getHeroes(), ((Game) cfs.getData()).getMaxPlayers());
                    PlayingUI.addHeroes(((Game) cfs.getData()).getHeroes());
                    PlayingUI.setFields((Hero) cfs.getPlayer());
                    PlayingUI.setUsername((Hero) cfs.getPlayer());
                    PlayingUI.setAccessCode(((Game) cfs.getData()).getAccessCode());
                    PlayingUI.setLevel(((Game) cfs.getData()).getLevel());
                    PlayingUI.getDice(((Game) cfs.getData()).getDiceRolled());
                    SpecialSkillsUI.setHeroClass((Hero) cfs.getPlayer());
                    SpecialSkillsUI.getDice(((Game) cfs.getData()).getDiceRolled());
                }
                else if(cfs.getCommand() == CommandFromServer.NEW_PLAYER) {
                    gameUI.cardLayout.show(gameUI.mainPanel, "LobbyScreen");
                    LobbyUI.refreshLobby(((Game) cfs.getData()).getHeroes(), ((Game) cfs.getData()).getMaxPlayers());
                    PlayingUI.addHeroes(((Game) cfs.getData()).getHeroes());

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
                else if (cfs.getCommand() == CommandFromServer.DISPLAY_MESSAGE) {
                    PlayingUI.refreshChat((ArrayList<String>) cfs.getPlayer());
                }
                else if (cfs.getCommand()==CommandFromServer.GIVE_DICE) {
                    PlayingUI.getDice((List<Map.Entry<Boolean, Integer>>) cfs.getData());
                }
                else if (cfs.getCommand()==CommandFromServer.SWITCH_TURN) {
                    PlayingUI.setTurnText((Integer)cfs.getData());
                }
                else if(cfs.getCommand() == CommandFromServer.SEND_GAME_MESSAGE){
                    PlayingUI.showGameMessage((String) cfs.getData());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "Game {Access Code: " + accessCode + ", Heroes: " + heroes + ", Players: " + maxPlayers + "}";
    }

    public void switchTurn (ObjectOutputStream os, int turnTracker) {
        sendCommand(os, new CommandFromClient(CommandFromClient.SWITCH_TURN, turnTracker, PlayingUI.getAccessCode()));
    }

    public void gameMessageText (ObjectOutputStream os, String text) {
        sendCommand(os, new CommandFromClient(CommandFromClient.GAME_TEXT_DISPLAY, text, PlayingUI.getAccessCode()));
    }

    public void passDice (ObjectOutputStream os, List<Map.Entry<Boolean, Integer>> dice) {
        sendCommand(os, new CommandFromClient(CommandFromClient.PASS_DICE, dice, PlayingUI.getAccessCode()));
    }

    public void playerJoin (ObjectOutputStream os, String info, String name) {
        sendCommand(os, new CommandFromClient(CommandFromClient.JOIN, info, name));
    }

    public void playerHost(ObjectOutputStream os, String info, String player)  {
        sendCommand(os, new CommandFromClient(CommandFromClient.HOST, info, player));
    }

    public void sendMessage (ObjectOutputStream os, String text) {
        sendCommand(os, new CommandFromClient(CommandFromClient.SEND_MESSAGE, text, PlayingUI.getAccessCode()));
    }

    private void sendCommand(ObjectOutputStream os, CommandFromClient cfc) {
        try {
            os.reset();
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

    public ArrayList<String> getMessagesChat(){
        return messages;
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

    public List<Map.Entry<Boolean, Integer>> getDiceRolled() {
        return diceRolled;
    }

    public void setDiceRolled(List<Map.Entry<Boolean, Integer>> diceRolled) { this.diceRolled = diceRolled;}
}