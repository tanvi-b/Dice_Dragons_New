import java.io.Serializable;

public class CommandFromServer implements Serializable {
    private int command;
    private Object data = null;
    private Object player = null;

    public static final int ACCESS_CODE = 0;
    public static final int MAKE_HERO= 1;
    public static final int INVALID_ACCESS_CODE = 2;
    public static final int INVALID_NAME = 3;
    public static final int INVALID_CLASS = 4;
    public static final int MAX_PLAYERS = 5;
    public static final int NEW_PLAYER= 6;
    public static final int DISPLAY_MESSAGE = 7;
    public static final int GIVE_DICE = 8;
    public static final int SWITCH_TURN = 9;
    public static final int SEND_GAME_MESSAGE = 10;
    public static final int PLACE_TOKEN = 11;
    public static final int REMOVE_BUTTON = 12;
    public static final int ATTACK_DRAGON = 13;
    public static final int INCREASE_ARMOR_CLASS = 14;
    public static final int INCREASE_HP = 15;
    public static final int USED_DRAGON_DICE = 16;
    public static final int DRAGON_ATTACK = 17;
    public static final int DRAGON_ATTACK_FINAL = 18;
    public static final int GO_TO_MARKET = 19;
    public static final int FLEE = 20;
    public static final int NO_FLEE = 21;
    public static final int EVERYONE_FLEE = 22;
    public static final int SUCCESSFUL_PURCHASE = 23;
    public static final int NOT_ENOUGH_GOLD = 24;
    public static final int TOO_MANY_ITEMS = 25;
    public static final int EVERYONE_READY_NEXT = 27;

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