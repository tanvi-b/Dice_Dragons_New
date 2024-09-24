import java.io.Serializable;
import java.util.ArrayList;

public class CommandFromClient implements Serializable
{
    //The command being sent to the server
    private int command;
    private Object data = null;
    private String player = "";

    public static final int JOIN=0;
    public static final int HOST=1;
    public static final int SEND_MESSAGE = 2;

    //public static final int CUSTOM_HERO;
    //public static final int DISCONNECT;

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
