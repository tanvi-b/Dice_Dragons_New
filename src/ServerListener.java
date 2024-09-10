import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    static HashMap<String, Game> currentGames;

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
                    if (validPlayer(cfc.getData()))
                        sendCommand(new CommandFromServer(CommandFromServer.CONNECT, null, null));
                    else
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_CONNECTION, null, null));
                }

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    Random random = new Random();
                    int accessCode = 100000 + random.nextInt(900000);
                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, String.valueOf(accessCode), null));
                }

                if(cfc.getCommand() == CommandFromClient.CUSTOM_HERO){
                    sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, null, null));
                }

                if(cfc.getCommand() == CommandFromClient.CHAT){
                    sendCommand(new CommandFromServer(CommandFromServer.CHAT, null, cfc.getPlayer()));
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private boolean validPlayer (Object data)
    {
        String playerEntry = (String) data;
        String[] playerInputs = playerEntry.split(",");
        //validate access code
        //validate character name

        return false;
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

//    public void sendCommand(CommandFromServer cfs)
//    {
//        //sends command to everyone
//        for (ObjectOutputStream o : outs) {
//            try {
//                o.writeObject(cfs);
//                o.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
