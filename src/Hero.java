import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Hero extends Player {
    int level;
    int armorClass;
    boolean flee;
    boolean readyForNextDragon;
    int classType; //warrior = 0, wizard = 1, cleric = 2, ranger = 3, rogue = 4
    String heroName;
    ArrayList<Token> tokens;
    transient ObjectOutputStream os;

    public Hero(int classType, String heroName, ObjectOutputStream os, int hitPoints, int gold, int exp, int incentiveOrder)
    {
        super(hitPoints, gold, exp, incentiveOrder, true);
        this.classType = classType;
        this.heroName = heroName;
        this.os = os;
        this.level = 1;
        this.flee = false;
        this.readyForNextDragon = false;
    }

    public String toString() {
        return "Hero {Class Type: " + classType + ", Character Name: '" + heroName + "'}";
    }

    private void passDice()
    {

    }

    private void placingClassToken()
    {

    }

    private void heal()
    {

    }

    private void flee()
    {

    }

    private void activateSpecialToken()
    {

    }

    private void attack()
    {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public boolean isFlee() {
        return flee;
    }

    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public ObjectOutputStream getOs() {
        return os;
    }

    public void setOs(ObjectOutputStream os) {
        this.os = os;
    }
}