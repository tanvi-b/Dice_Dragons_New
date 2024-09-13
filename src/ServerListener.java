import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    static Map<String, Game> currentGames;

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

                if(cfc.getCommand() == CommandFromClient.JOIN){
                    if (cfc.getData().equals("valid"))
                        sendCommand(new CommandFromServer(CommandFromServer.CONNECT, null, cfc.getPlayer(),null));
                    else if (cfc.getData().equals("invalidAccessCode"))
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_ACCESS_CODE, null, null,null));
                    else
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_NAME, null, null,null));
                }

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    Random random = new Random();
                    int accessCode = 100000 + random.nextInt(900000);
                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, String.valueOf(accessCode), null,null));
                }

                if(cfc.getCommand() == CommandFromClient.CUSTOM_HERO){
                    sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, null, null, null));
                }

                if(cfc.getCommand() == CommandFromClient.CHAT){
                   sendCommand(new CommandFromServer(CommandFromServer.CHAT, null, cfc.getPlayer(), cfc.getMessage()));
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private String validPlayer (Object data)
    {
        String playerEntry = (String) data;
        String[] playerInputs = playerEntry.split(",");
        for (Map.Entry<String, Game> entry : currentGames.entrySet())
        {
            String key = entry.getKey();
            if (key.equals(playerInputs[0])) {
                Game value = entry.getValue();
                ArrayList<Hero> currentHeroes = value.heroes;
                for (int i = 0; i<currentHeroes.size(); i++)
                {
                    Hero hero = currentHeroes.get(i);
                    if (hero.name.equals(playerInputs[1]))
                        return "invalidName";
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

}

