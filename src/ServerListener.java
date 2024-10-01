import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    public int accessCode;
    static Map<String, Game> currentGames = new HashMap<>();
    public static ArrayList<String>  historyChat = new ArrayList<>();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    public ServerListener(ObjectInputStream i, ObjectOutputStream o) {
        this.is = i;
        this.os = o;
        outs.add(os);
    }

    @Override
    public void run() {
        try {
            while (true) {
                CommandFromClient cfc = (CommandFromClient) is.readObject();
                if (cfc.getCommand() == CommandFromClient.JOIN) {
                    String validationResponse = validPlayer(cfc.getData());

                    if (validationResponse.equals("valid")) {
                        String playerEntry = (String) cfc.getData();
                        String[] playerInfo = playerEntry.split(",");
                        int classType = Integer.parseInt(playerInfo[2]);
                        String characterName = playerInfo[1];

                        Game game = currentGames.get(playerInfo[0]);
                        game.toString();

                        if (game != null) {
                            Hero newHero = new Hero(classType, characterName, os);
                            setHeroValues(newHero);
                            game.getHeroes().add(newHero);
                            System.out.println("Current Games: " + currentGames.toString());
                            sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, game, newHero));
                            for (int i = 0; i<game.getHeroes().size(); i++)
                            {
                                if (!game.getHeroes().get(i).heroName.equals(newHero.heroName))
                                    sendCommand(new CommandFromServer(CommandFromServer.NEW_PLAYER, game, newHero), game.getHeroes().get(i).os);
                            }
                        }
                    } else if (validationResponse.equals("invalidAccessCode")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_ACCESS_CODE, null, null));
                    } else if (validationResponse.equals("invalidName")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_NAME, null, null));
                    } else if (validationResponse.equals("invalidClass")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_CLASS, null, null));
                    } else {
                        sendCommand(new CommandFromServer(CommandFromServer.MAX_PLAYERS, null, null));
                    }
                }

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    Random random = new Random();
                    boolean pause = false;
                    accessCode = 0;

                    if (!pause) {
                        accessCode = 100000 + random.nextInt(900000);
                        pause = true;
                    }

                    //creating game object
                    Game newGame = new Game(null, null);
                    newGame.setAccessCode(String.valueOf(accessCode));
                    newGame.setDiceRolled(new ArrayList<>(Arrays.asList(random.nextInt(6),random.nextInt(6),
                            random.nextInt(6),random.nextInt(6),random.nextInt(6))));

                    String playerEntry = (String) cfc.getData();
                    System.out.println("Player Entry: " + playerEntry);

                    String[] playerInfo = playerEntry.split(",");
                    int classType = Integer.parseInt(playerInfo[2]);
                    String characterName = playerInfo[1];

                    Hero hostHero = new Hero(classType, characterName, os);
                    setHeroValues(hostHero);
                    System.out.println("Host Hero Created: " + hostHero);

                    newGame.getHeroes().add(hostHero);
                    newGame.setMaxPlayers(Integer.parseInt(playerInfo[0]) + 1);
                    System.out.println("Current Games: " + currentGames.toString());
                    currentGames.put(String.valueOf(accessCode), newGame);
                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, newGame, hostHero));
                }

                if(cfc.getCommand() == CommandFromClient.SEND_MESSAGE){
                    String message  = (String) cfc.getData();
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    game.getMessagesChat().add(message);
                    //CommandFromServer cfs = new CommandFromServer(CommandFromServer.DISPLAY_MESSAGE, historyChat, null);
                    if(game == currentGames.get(String.valueOf(cfc.getPlayer()))){
                        for (int i = 0; i < game.getHeroes().size(); i++) {
                            sendCommand(new CommandFromServer(CommandFromServer.DISPLAY_MESSAGE, null, game.getMessagesChat()), game.getHeroes().get(i).os);
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeroValues(Hero hero)
    {
        if (hero.classType==0)
        {
            hero.armorClass=0;
            hero.hitPoints=23;
            hero.level = 1;
            hero.exp = 0;
            hero.gold = 0;
            hero.incentiveOrder = 5;
        }
        if (hero.classType==1)
        {
            hero.armorClass=0;
            hero.hitPoints=22;
            hero.level=1;
            hero.exp = 0;
            hero.gold = 0;
            hero.incentiveOrder = 4;
        }
        if (hero.classType==2)
        {
            hero.armorClass=0;
            hero.hitPoints=24;
            hero.level=1;
            hero.exp = 0;
            hero.gold = 0;
            hero.incentiveOrder = 6;
        }
        if (hero.classType==3)
        {
            hero.armorClass=1;
            hero.hitPoints=21;
            hero.level=1;
            hero.exp = 0;
            hero.gold = 0;
            hero.incentiveOrder = 2;
        }
        if (hero.classType==4)
        {
            hero.armorClass=1;
            hero.hitPoints=19;
            hero.level=1;
            hero.exp = 0;
            hero.gold = 0;
            hero.incentiveOrder = 1;
        }
    }

    private String validPlayer(Object data) {
        String playerEntry = (String) data;
        String[] playerInputs = playerEntry.split(",");

        for (Map.Entry<String, Game> entry : currentGames.entrySet()) {
            String key = entry.getKey();
            if (key.equals(playerInputs[0])) {
                Game game = entry.getValue();
                ArrayList<Hero> currentHeroes = game.heroes;

                if (currentHeroes.size() == game.maxPlayers)
                    return "maxPlayersReached";

                for (int i = 0; i < currentHeroes.size(); i++) {
                    Hero hero = currentHeroes.get(i);
                    if (hero.heroName.equals(playerInputs[1]))
                        return "invalidName";
                    if (hero.classType == Integer.parseInt(playerInputs[2]))
                        return "invalidClass";
                }
                return "valid";
            }
        }
        return "invalidAccessCode";
    }

    private void sendCommand (CommandFromServer cfs)
    {
        //sends to one specific client
        try {
            os.reset();
            os.writeObject(cfs);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand (CommandFromServer cfs, ObjectOutputStream hero_Os)
    {
        //sends to one specific client
        try {
            hero_Os.reset();
            hero_Os.writeObject(cfs);
            hero_Os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommandToChat(CommandFromServer cfs, ObjectOutputStream os){
        try {
            os.reset();
            os.writeObject(cfs);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getHistoryChat(){
        return historyChat;
    }
}

