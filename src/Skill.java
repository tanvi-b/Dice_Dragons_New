//need to still figure out special skills

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Skill implements Serializable {
    String name;
    ArrayList<Integer> heroClasses;
    ArrayList<Integer> requiredSymbols;
    int amtEffect;
    int skillType; //0: attack, 1: healing, 2: stance, 3: re-roll, 4: ally

    public Skill(String name, ArrayList<Integer> heroClasses, ArrayList<Integer> requiredSymbols, int amtEffect, int skilTtype) {
        this.name = name;
        this.heroClasses = heroClasses;
        this.requiredSymbols = requiredSymbols;
        this.amtEffect = amtEffect;
        this.skillType = skilTtype;
    }

    public static void activate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getHeroClasses() {
        return heroClasses;
    }

    public void setHeroClasses(ArrayList<Integer> heroClasses) {
        this.heroClasses = heroClasses;
    }

    public ArrayList<Integer> getRequiredSymbols() {
        return requiredSymbols;
    }

    public void setRequiredSymbols(ArrayList<Integer> requiredSymbols) {
        this.requiredSymbols = requiredSymbols;
    }

    public int getAmtEffect() {
        return amtEffect;
    }

    public void setAmtEffect(int amtEffect) {
        this.amtEffect = amtEffect;
    }

    public int getSkillType() {
        return skillType;
    }

    public void setSkillType(int skillType) {
        this.skillType = skillType;
    }
}