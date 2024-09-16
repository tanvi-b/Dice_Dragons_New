import java.util.ArrayList;

public class Hero extends Player{
    boolean flee;
    int classType; //warrior = 0, wizard = 1, cleric = 2, ranger = 3, rogue = 4
    String heroName;
    ArrayList<Token> tokens;

    public Hero(int classType, String characterName)
    {
        this.classType = classType;
        heroName = characterName;
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
 }
