import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    int hitPoints;
    int gold;
    int exp;
    int incentiveOrder;
    ArrayList<Skill> playerSkills;
    ArrayList<MarketItem> items;
    boolean alive;

    public Player(int hitPoints, int gold, int exp, int incentiveOrder, boolean alive)
    {
        this.hitPoints = hitPoints;
        this.gold = gold;
        this.exp = exp;
        this.incentiveOrder = incentiveOrder;
        this.alive = alive;
    }

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

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getIncentiveOrder() {
        return incentiveOrder;
    }

    public void setIncentiveOrder(int incentiveOrder) {
        this.incentiveOrder = incentiveOrder;
    }

    public ArrayList<Skill> getPlayerSkills() {
        return playerSkills;
    }

    public void setPlayerSkills(ArrayList<Skill> playerSkills) {
        this.playerSkills = playerSkills;
    }

    public ArrayList<MarketItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MarketItem> items) {
        this.items = items;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
