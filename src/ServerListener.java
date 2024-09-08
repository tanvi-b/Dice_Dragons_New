import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, "000000", null));
                    //generate random 6-digit number and pass as data
                }

                if(cfc.getCommand() == CommandFromClient.JOIN){
                    sendCommand(new CommandFromServer(CommandFromServer.PLAY, null, null));
                }

                if(cfc.getCommand() == CommandFromClient.CONNECT){
                    sendCommand(new CommandFromServer(CommandFromServer.CONNECT, null, cfc.getPlayer()));
                }

                if(cfc.getCommand() == CommandFromClient.DISCONNECT){
                    sendCommand(new CommandFromServer(CommandFromServer.DISCONNECT, null, cfc.getPlayer()));
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
            sendCommand(new CommandFromServer(CommandFromServer.DISCONNECT, null, null));
        }

    }

    private boolean validPlayer ()
    {
        return false;
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
