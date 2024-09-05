import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
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

                if (cfc.getCommand() == CommandFromClient.PLAY) {

                    sendCommand(new CommandFromServer(CommandFromServer.PLAY, cfc.getData(), ClientMain.name));
                    //might need to change name
                }

                if(cfc.getCommand() == CommandFromClient.ADD_HERO){
                    sendCommand(new CommandFromServer(CommandFromServer.ADD_HERO, null, cfc.getPlayer()));
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

    public void sendCommand(CommandFromServer cfs)
    {
        // Sends command to everyone
        for (ObjectOutputStream o : outs) {
            try {
                o.writeObject(cfs);
                o.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
