public class Dragon extends Player{
    String dragonName;

    public Dragon (String name, int hitPoints, int gold, int exp, int incentiveOrder)
    {
        super(hitPoints, gold, exp, incentiveOrder, true);
        dragonName = name;
    }

    private void successHunt()
    {

    }

    private void attack()
    {

    }

    private void useSpecialAbility ()
    {

    }

    public String getDragonName() {
        return dragonName;
    }

    public void setDragonName(String dragonName) {
        this.dragonName = dragonName;
    }
}
