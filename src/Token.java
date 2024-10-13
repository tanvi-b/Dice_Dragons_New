import java.io.Serializable;

public class Token implements Serializable {
    int type; //0: class token, 1: special tokens
    int xCoordinate;
    int yCoordinate;

    public Token(int type, int xCoordinate, int yCoordinate) {
        this.type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public void applyEffect()
    {

    }

    public void removeEffect()
    {

    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
