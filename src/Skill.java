//need to still figure out special skills

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Skill implements Serializable {
    String name;
    ArrayList<Integer> heroClasses;
    ArrayList<Integer> requiredSymbols; //6: not equal to, 7: equal to
    int amtEffect;
    int skillType; //0: attack (HP), 1: healing (+HP), 2: stance (AC), 3: re-roll, 4: ally

    public Skill(String name, ArrayList<Integer> heroClasses, ArrayList<Integer> requiredSymbols, int amtEffect, int skilType) {
        this.name = name;
        this.heroClasses = heroClasses;
        this.requiredSymbols = requiredSymbols;
        this.amtEffect = amtEffect;
        this.skillType = skilType;
    }

    public boolean checkDiceCombo (List<Map.Entry<Boolean, Integer>> playerDice)
    {
        HashMap<Integer, Integer> attributeCount = new HashMap<>();
        for (Integer value : requiredSymbols)
            attributeCount.put(value, attributeCount.getOrDefault(value, 0) + 1);

        HashMap<Integer, Integer> paramCount = new HashMap<>();
        for (Map.Entry<Boolean, Integer> entry : playerDice)
            paramCount.put(entry.getValue(), paramCount.getOrDefault(entry.getValue(), 0) + 1);

        for (Map.Entry<Integer, Integer> entry : attributeCount.entrySet()) {
            Integer requiredCount = entry.getValue();

            if (entry.getKey() == 6) {
                int uniqueCount = 0;
                for (Integer count : paramCount.values()) {
                    if (count > 0)
                        uniqueCount++;
                }
                if (uniqueCount < requiredCount)
                    return false;
            } else if (entry.getKey() == 7) {
                boolean hasRequiredSymbol = false;
                for (Integer count : paramCount.values()) {
                    if (count >= requiredCount) {
                        hasRequiredSymbol = true;
                        break;
                    }
                }
                if (!hasRequiredSymbol)
                    return false;
            } else {
                Integer availableCount = paramCount.getOrDefault(entry.getKey(), 0);
                if (availableCount < requiredCount)
                    return false;
            }
        }
        return true;
    }

    public void setToken(){

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