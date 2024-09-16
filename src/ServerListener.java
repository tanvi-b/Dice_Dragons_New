import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private int code;
    private String accessCode;
    public static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    static Map<String, Game> currentGames;

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
                    if (cfc.getData().equals("valid")) {;
                        sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, accessCode, cfc.getPlayer()));
                    }
                    else if (cfc.getData().equals("invalidAccessCode"))
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_ACCESS_CODE, null, null));
                    else if (cfc.getData().equals("invalidName"))
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_NAME, null, null));
                    else if (cfc.getData().equals("invalidClass"))
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_CLASS, null, null));
                    else
                        sendCommand(new CommandFromServer(CommandFromServer.MAX_PLAYERS, null, null));
                }

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    Random random = new Random();
                    boolean pause = false;
                    code = 0;
                    if(pause == false){
                        code = 100000 + random.nextInt(900000);
                        pause = true;
                    }
                    accessCode  = String.valueOf(code);
                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE,accessCode, cfc.getPlayer()));
                    ///hostUsed = true; -- condition if only one game allowed
                    //might have to add the game into hash map?
                }
                /// code needed only if one game allowed to be host
                //if(hostUsed == true){
                    //IntroUI.noHost();
               // }

//                if(cfc.getCommand() == CommandFromClient.CUSTOM_HERO){
//                    sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, null, null));
//                }
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
                    if (hero.name.equals(playerInputs[1]))
                        return "invalidName";
                   if (hero.classType == Integer.parseInt(playerInputs[2]))
                        return "invalidClass";
                }
                return "valid";
            }
        }
            return "invalidAccessCode";
        }

        private void sendCommand (CommandFromServer cfs) {
            //sends to all clients
            for (ObjectOutputStream out : outs) {
                try {
                    out.writeObject(cfs);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



}