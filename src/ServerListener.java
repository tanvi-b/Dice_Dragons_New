import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private int accessCode;
    public static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    static Map<String, Game> currentGames = new HashMap<>();

    public ServerListener(ObjectInputStream i, ObjectOutputStream o) {
        this.is = i;
        this.os = o;
        synchronized (outs) {
            outs.add(os);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                CommandFromClient cfc = (CommandFromClient) is.readObject();
                if (cfc.getCommand() == CommandFromClient.JOIN) {
                    String validationResponse = validPlayer(cfc.getData());
                    System.out.println(validationResponse);

                    if (validationResponse.equals("valid")) {
                        String playerEntry = (String) cfc.getData();
                        String[] playerInfo = playerEntry.split(",");
                        int classType = Integer.parseInt(playerInfo[2]);
                        String characterName = playerInfo[1];

                        Game game = currentGames.get(playerInfo[0]);
                        game.toString();

                        if (game != null) {
                            Hero newHero = new Hero(classType, characterName);
                            game.getHeroes().add(newHero);
                            //LobbyUI.refreshLobby(game);
                            sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, playerInfo[0], cfc.getPlayer()));
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
                    Game newGame = new Game(os, is);
                    newGame.setAccessCode(String.valueOf(accessCode));

                    String playerEntry = (String) cfc.getData();
                    System.out.println("Player Entry: " + playerEntry);

                    String[] playerInfo = playerEntry.split(",");
                    int classType = Integer.parseInt(playerInfo[2]);
                    String characterName = playerInfo[1];

                    Hero hostHero = new Hero(classType, characterName);
                    System.out.println("Host Hero Created: " + hostHero);

                    newGame.getHeroes().add(hostHero);
                    newGame.setMaxPlayers(Integer.parseInt(playerInfo[0]) + 1);
                    currentGames.put(String.valueOf(accessCode), newGame);
                    //LobbyUI.refreshLobby(newGame);
                    System.out.println("Current Games: " + currentGames);

                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, String.valueOf(accessCode), cfc.getPlayer()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            os.writeObject(cfs);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void sendCommand (CommandFromServer cfs) {
//        //sends to all clients
//        for (ObjectOutputStream out : outs) {
//            try {
//                out.writeObject(cfs);
//                out.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
