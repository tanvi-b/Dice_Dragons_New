import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GameUI extends JFrame implements Serializable {
    Game game;
    public JPanel mainPanel;
    public CardLayout cardLayout;

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

        JPanel lobbyPanel = new LobbyUI(cardLayout, mainPanel, game);
        mainPanel.add(lobbyPanel, "LobbyScreen");

        JPanel playingPanel = new PlayingUI(cardLayout, mainPanel, game);
        mainPanel.add(playingPanel, "PlayingScreen");

        JPanel dragonGuide = new DragonGuideUI(cardLayout, mainPanel);
        mainPanel.add(dragonGuide, "DragonGuideScreen");

        JPanel playerRules = new PlayerRulesUI(cardLayout, mainPanel);
        mainPanel.add(playerRules, "PlayerRulesScreen");

        JPanel specialSkillsPanel = new SpecialSkillsUI(cardLayout, mainPanel);
        mainPanel.add(specialSkillsPanel, "SpecialSkillsScreen");

        JPanel marketPlacePanel = new MarketPlaceUI(cardLayout, mainPanel, game);
        mainPanel.add(marketPlacePanel, "MarketPlaceScreen");

        JPanel defeatedPanel = new DefeatedUI(cardLayout, mainPanel);
        mainPanel.add(defeatedPanel, "DefeatedScreen");

        JPanel wonPanel = new WonUI(cardLayout, mainPanel);
        mainPanel.add(wonPanel, "WonScreen");

        //JPanel customHeroPanel = new CustomHeroUI(cardLayout, mainPanel);
        //mainPanel.add(customHeroPanel, "CustomHeroScreen");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "IntroScreen");
        //cardLayout.show(mainPanel, "MarketPlaceScreen");

        setVisible(true);
        setSize(1200, 1000);
        setResizable(false);
    }
}
