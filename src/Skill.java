import java.util.ArrayList;

public class Skill {
    String name;
    ArrayList<Dice> requiredSymbols;
    int typeEffect;
    int amtEffect;
    char type;

    public Skill(String name, ArrayList<Dice> requiredSymbols, int typeEffect, int amtEffect, char type) {
        this.name = name;
        this.requiredSymbols = requiredSymbols;
        this.typeEffect = typeEffect;
        this.amtEffect = amtEffect;
        this.type = type;
    }

    public static void activate() {
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Dice> getRequiredSymbols() {
        return this.requiredSymbols;
    }

    public int getTypeEffect() {
        return this.typeEffect;
    }

    public int getAmtEffect() {
        return this.amtEffect;
    }

    public char getType() {
        return this.type;
    }
}