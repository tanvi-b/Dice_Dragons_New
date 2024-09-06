import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Game extends JFrame {
    private JPanel introScreen;
    private JPanel joinScreen;
    private JPanel hostScreen;
    private JPanel customHeroScreen;
    private JPanel playingScreen;
    private JList<Hero> heroList;
    private JList<Dragon> dragonList;

    public Game()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");

        introScreen = new JPanel();
        introScreen.setLayout(null);
        joinScreen = new JPanel();
        joinScreen.setLayout(null);
        hostScreen = new JPanel();
        hostScreen.setLayout(null);
        customHeroScreen = new JPanel();
        customHeroScreen.setLayout(null);
        playingScreen = new JPanel();
        playingScreen.setLayout(null);

    //intro screen
        JButton joinGame = new JButton ("Join Game");
        //set font and size
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        //set bounds

        JButton hostGame = new JButton ("Host Game");
        //set font and size
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        //set bounds

    //join screen
        JLabel message = new JLabel("Ask for host for the access code. It is on the top-right of their screen");
        JLabel accessCode = new JLabel("Enter access code:");
        JLabel characterName = new JLabel ("Enter character name:");
        JLabel heroClass = new JLabel("Choose your hero class:");
        //text fields
        JButton beginGame = new JButton ("Begin Game");

    //host screen
        JLabel numberPlayers = new JLabel("Numbers of Players");
        JLabel characterName1 = new JLabel("Enter character name:");
        JLabel heroClass1 = new JLabel("Choose your hero class:");
        JButton createGame = new JButton("Create Game");

    //custom hero screen


        getContentPane().add(introScreen);
        setVisible(true);
        setSize(1000, 700);
        setResizable(false);
    }
}
