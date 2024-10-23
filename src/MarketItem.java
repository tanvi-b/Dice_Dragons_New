public class MarketItem {
    String name;
    int type; //0: heals hp, 1: +AC, 2: re use skill, 3: reroll dice, 4: add symbols, 5: extra damage/healing w/ skill
    boolean instant;
    int amtEffect;
    int gold;
    int quantity;

    public MarketItem (String name, int type, boolean instant, int amtEffect, int gold, int quantity)
    {
        this.name = name;
        this.type = type;
        this.instant = instant;
        this.amtEffect = amtEffect;
        this.gold = gold;
        this.quantity = quantity;
    }

    private void use()
    {

    }

    private void remove()
    {

    }
}
