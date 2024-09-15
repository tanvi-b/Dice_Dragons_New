import javax.swing.*;
import java.awt.*;

//dragon face is on dice side 5

public class GameUI extends JFrame {
    //might need to make the JPanels attributes to get access in other classes?
    Game game;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public GameUI(Game game)
    {
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");

        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel introPanel = new IntroUI(cardLayout, mainPanel);
        mainPanel.add(introPanel, "IntroScreen");

        JPanel joinPanel = new JoinUI(cardLayout, mainPanel);
        mainPanel.add(joinPanel, "JoinScreen");

        JPanel hostPanel = new HostUI(cardLayout, mainPanel);
        mainPanel.add(hostPanel, "HostScreen");

        JPanel lobbyPanel = new LobbyUI(cardLayout, mainPanel);
        mainPanel.add(lobbyPanel, "LobbyScreen");

        //JPanel customHeroPanel = new CustomHeroUI(cardLayout, mainPanel);
//        mainPanel.add(customHeroPanel, "CustomHeroScreen");
//
//        JPanel playingPanel = new PlayingUI(cardLayout, mainPanel);
//        mainPanel.add(playingPanel, "PlayingScreen");

//        JPanel dragonGuide = new DragonGuideUI(cardLayout, mainPanel);
//        mainPanel.add(dragonGuide, "DragonGuideScreen");
//
//        JPanel playerRules = new PlayerRulesUI(cardLayout, mainPanel);
//        mainPanel.add(playerRules, "PlayerRulesScreen");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "IntroScreen");

        setVisible(true);
        setSize(1200, 1000);
        setResizable(false);
    }
}