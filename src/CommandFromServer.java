import java.io.Serializable;

public class CommandFromServer implements Serializable {
    private int command;
    private Object data = null;
    private String player = "";

    // Command list
    public static final int ACCESS_CODE = 0;
    public static final int CONNECT = 1;

    public static final int MAKE_HERO = 2;

    public static final int CHAT = 3;
    //public static final int DISCONNECT = 3;

    //public static final int ADD_HERO = 4;

    public CommandFromServer(int command, Object data, String player) {
        this.command = command;
        this.data = data;
        this.player = player;
    }

    public int getCommand() {
        return command;
    }

    public Object getData() {
        return data;
    }

    public String getPlayer() {
        return player;
    }
}
