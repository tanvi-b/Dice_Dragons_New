import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LobbyUI extends JPanel {
    private BufferedImage loginBackground;
    private Font customFont;
    private Font customBoldFont;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public static Game game;
    private static DefaultListModel<String> playersModel = new DefaultListModel<>();
    public static JList<String> userNames = new JList<>(playersModel);
    private static JLabel accessCodeShow;

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
        playersJoined.setBounds(350, 200, 500, 75);
        playersJoined.setHorizontalAlignment(SwingConstants.CENTER);
        playersJoined.setOpaque(true);

        JScrollPane playersScrollPane = new JScrollPane(userNames);
        playersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        playersScrollPane.setBounds(450, 300, 300, 400);
        playersScrollPane.setVisible(true);

        //timer - after all players have joined show a jlabel saying starting game in 3 2 1
        //lead to playing screen

        //for testing purposes
        JButton goToPlaying=  new JButton("Go to Playing Screen");
        goToPlaying.setFont(customFont.deriveFont(28f));
        goToPlaying.setBounds(450, 710, 300, 75);
        goToPlaying.setHorizontalAlignment(SwingConstants.CENTER);
        goToPlaying.setOpaque(true);

        add(playersJoined);
        add(accessCodeShow);
        add(playersScrollPane);
        add(goToPlaying);
        //add starting game label but make visible false
    }

    private void buttonFormatting(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setVisible(true);
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

    public static void refreshLobby(String username){
        System.out.println(username);
        playersModel.addElement(username);
//        playersModel.clear();
//        for (int i = 0; i<currentGame.heroes.size(); i++)
//            playersModel.addElement(currentGame.heroes.get(i).heroName);
    }

    public static void displayCode(String code){
        accessCodeShow.setText("Access Code: " + code);
    }
}