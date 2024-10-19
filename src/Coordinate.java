import java.io.Serializable;

public class Coordinate implements Serializable {
    private int x;
    private int y;
    private int hero;

    public Coordinate(){

    }
    public Coordinate(int xCoor, int yCoor, int heroC){
        x = xCoor;
        y = yCoor;
        hero = heroC;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getHero() {
        return hero;
    }

    public void setHero(int hero) {
        this.hero = hero;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

}
