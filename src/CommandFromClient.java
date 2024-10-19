import java.io.Serializable;
import java.util.ArrayList;

public class CommandFromClient implements Serializable
{
    private int command;
    private Object data = null;
    private Object player = null;

    public static final int JOIN=0;
    public static final int HOST=1;
    public static final int SEND_MESSAGE = 2;
    public static final int PASS_DICE = 3;
    public static final int SWITCH_TURN = 4;
    public static final int GAME_TEXT_DISPLAY = 5;
    public static final int PLACE_TOKEN = 6;
    public static final int REMOVE_BUTTON = 7;
    public static final int ATTACK_DRAGON = 8;
    public static final int INCREASE_ARMOR_CLASS = 9;
    public static final int INCREASE_HP = 10;
    public static final int CHECK_DRAGON_DICE = 11;
    public static final int DRAGON_ATTACK = 12;
    public static final int DRAGON_ATTACK_FINAL = 13;

    public CommandFromClient(int command, Object data, Object player) {
        this.command = command;
        this.data = data;
        this.player = player;
    }

    public int getCommand() {return command;}

    public Object getData() {
        return data;
    }

    public Object getPlayer(){
        return player;
    }
}
