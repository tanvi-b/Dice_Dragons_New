import java.io.Serializable;

public class CommandFromServer implements Serializable {
    private int command;
    private Object data = null;
    private Object player = null;

    // Command list
    public static final int ACCESS_CODE = 0;
    public static final int MAKE_HERO= 1;
    public static final int INVALID_ACCESS_CODE = 2;
    public static final int INVALID_NAME = 3;
    public static final int INVALID_CLASS = 4;
    public static final int MAX_PLAYERS = 5;
    public static final int NEW_PLAYER= 6;
    public static final int DISPLAY_MESSAGE = 7;
    public static final int GIVE_DICE = 8;

    public CommandFromServer(int command, Object data, Object player) {
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

    public Object getPlayer() {
        return player;
    }
}