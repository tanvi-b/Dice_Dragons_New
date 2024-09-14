import javax.swing.*;

//dragon face is on dice side 5

public class GameUI extends JFrame {
    Game game;

    public GameUI(Game game)
    {
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");

        getContentPane().add(new IntroUI());
        validate();
        repaint();
        setVisible(true);
        setSize(1200, 1000);
        setResizable(false);
    }
}