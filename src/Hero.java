import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Hero extends Player {
    int level;
    boolean flee;
    int classType; //warrior = 0, wizard = 1, cleric = 2, ranger = 3, rogue = 4
    String heroName;
    ArrayList<Token> tokens;
    transient ObjectOutputStream os;

    public Hero(int classType, String characterName, ObjectOutputStream os)
    {
        this.classType = classType;
        heroName = characterName;
        this.os = os;
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

    private void chat ()
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