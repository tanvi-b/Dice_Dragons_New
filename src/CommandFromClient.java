import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    //The command being sent to the server
    private int command;
    //Text data for the command
    private Object data = null;

    private String player = "";

    public static final int JOIN=0;
    public static final int HOST=1;

    public static final int PLAY=2;
    public static final int CONNECT = 3;
    public static final int DISCONNECT = 4;

    public static final int ADD_HERO = 5;

    public CommandFromClient(int command, Object data, String player) {
        this.command = command;
        this.data = data;
        this.player = player;
    }

    public int getCommand() {return command;}

    public Object getData() {
        return data;
    }

    public String getPlayer(){
        return player;
    }
}
