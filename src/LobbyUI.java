import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LobbyUI extends JPanel {
    private BufferedImage loginBackground;
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    public static Game game;
    private static DefaultListModel<String> playersModel = new DefaultListModel<>();
    public static JList<String> userNames = new JList<>(playersModel);
    private static JLabel accessCodeShow;
    private static JLabel countdownLabel;
    private static Timer countdownTimer;
    private static int countdown = 3;

    public LobbyUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {
        accessCodeShow = new JLabel("Access Code: ");
        accessCodeShow.setFont(customFont.deriveFont(20f));
        accessCodeShow.setBounds(900, 10, 250, 50);
        accessCodeShow.setOpaque(true);
        accessCodeShow.setBackground(Color.black);
        accessCodeShow.setForeground(Color.ORANGE);

        JLabel playersJoined = new JLabel("Players Joined");
        playersJoined.setFont(customFont.deriveFont(60f));
        playersJoined.setBounds(350, 150, 500, 75);
        playersJoined.setHorizontalAlignment(SwingConstants.CENTER);
        playersJoined.setOpaque(true);

        JScrollPane playersScrollPane = new JScrollPane(userNames);
        playersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        playersScrollPane.setBounds(450, 250, 300, 400);
        playersScrollPane.setVisible(true);

        countdownLabel = new JLabel("");
        countdownLabel.setFont(customFont.deriveFont(40f));
        countdownLabel.setBounds(450, 660, 300, 75);
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countdownLabel.setOpaque(true);
        countdownLabel.setBackground(Color.BLACK);
        countdownLabel.setForeground(Color.ORANGE);
        countdownLabel.setVisible(false);

        add(playersJoined);
        add(accessCodeShow);
        add(playersScrollPane);
        add(countdownLabel);
    }

    private void readImages() {
        try {
            loginBackground = ImageIO.read(new File("images/loginScreen.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFonts() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Regular.ttf"));
            customBoldFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static void refreshLobby(ArrayList<Hero> currentHeroes, int maxPlayers) {
        playersModel.clear();
        for (int i = 0; i < currentHeroes.size(); i++)
            playersModel.addElement(currentHeroes.get(i).heroName);

        if (playersModel.size() == maxPlayers)
            startCountdown();
    }

    private static void startCountdown() {
        countdownLabel.setVisible(true);
        countdown = 3;
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdown > 0) {
                    countdownLabel.setText("Starting in " + countdown + "...");
                    countdown--;
                } else {
                    countdownTimer.stop();
                    countdownLabel.setVisible(false);
                    cardLayout.show(mainPanel, "PlayingScreen");
                }
            }
        });
        countdownTimer.start();
    }

    public static void displayCode(String code) {
        accessCodeShow.setText("Access Code: " + code);
    }
}
