import java.util.ArrayList;

public class Player {
    String name;
    int hitPoints;
    int armorClass;
    int gold;
    int exp;
    int incentiveOrder;
    ArrayList<Skill> playerSkills;
    ArrayList<MarketItem> items;
    boolean alive;

    private boolean checkCombination()
    {
        return false;
    }

    private void takeDamage()
    {

    }

    private ArrayList<Dice> rollDice()
    {
        return null;
    }
}
