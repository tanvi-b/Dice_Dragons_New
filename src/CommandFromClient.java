import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    //The command being sent to the server
    private int command;
    //Text data for the command
    private String data = "";

    private String playerName = "";

    public static final int PLAY=0;
    public static final int CONNECT = 1;
    public static final int DISCONNECT = 2;

    public static final int ADD_HERO = 3;

    public CommandFromClient(int command, String data, String playerName) {
        this.command = command;
        this.data = data;
        this.playerName = playerName;
    }

    public int getCommand() {return command;}

    public String getData() {
        return data;
    }

    public String getPlayerName(){
        return playerName;
    }
}
