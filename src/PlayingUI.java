import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayingUI extends JPanel {
    Game game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<BufferedImage> dragonSheets;
    private ArrayList<BufferedImage> heroSheets;
    private BufferedImage background;
    private Font customFont;
    private Font customBoldFont;

    public PlayingUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        dragonSheets = new ArrayList<>();
        heroSheets = new ArrayList<>();
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {

        JLabel turn = new JLabel("Turn: ");
        turn.setFont(customFont.deriveFont(60f));
        turn.setBounds(450, 75, 500, 90);
        turn.setOpaque(true);

        JButton rules = new JButton("Rules");
        rules.setFont(customFont.deriveFont(30f));
        rules.setBounds(970, 80, 80, 80);
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        rules.setOpaque(true);

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(mainPanel, "PlayerRulesScreen");
            }
        });

        JButton guide = new JButton("Guide");
        guide.setFont(customFont.deriveFont(30f));
        guide.setBounds(1070, 80, 80, 80);
        guide.setBorder(BorderFactory.createLineBorder(Color.black));
        guide.setOpaque(true);

        guide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(mainPanel, "DragonGuideScreen");
            }
        });

        JLabel pointsText = new JLabel("Hero's Updates");
        pointsText.setFont(customFont.deriveFont(30f));
        pointsText.setBounds(10, 75, 360, 95);
        pointsText.setHorizontalAlignment(JLabel.CENTER);
        pointsText.setOpaque(true);

        JList<String> heroUpdates = new JList<>();

        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pointsScrollBar.setBounds(10, 190, 360, 280);

        JLabel dragonsText = new JLabel("Dragon's Updates");
        dragonsText.setFont(customFont.deriveFont(30f));
        dragonsText.setBounds(10, 500, 360, 95);
        dragonsText.setHorizontalAlignment(JLabel.CENTER);
        dragonsText.setOpaque(true);

        JList<String> dragonUpdates = new JList<>();

        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dragonScrollBar.setBounds(10, 610, 360, 280);

        JList<String> messages = new JList<>();
        JScrollPane chatBox = new JScrollPane(messages);
        chatBox.setBounds(870, 720, 310, 150);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(870, 880, 240, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton("Send");
        send.setFont(customFont.deriveFont(20f));
        send.setBounds(1115, 880, 65, 65);

        //chat method
        /*
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String acc = characterName + ": " + messageText.getText();
                ///will need to implement following method
                //sendMessage(os,name+ ": " + input.getText());
                messageText.setText("");
            }
        });
         */

        add(turn);
        add(rules);
        add(guide);
        add(pointsText);
        add(pointsScrollBar);
        add(dragonScrollBar);
        add(dragonsText);
        add(chatBox);
        add(messageText);
        add(send);

    }

    private void loadFonts() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Regular.ttf"));
            customBoldFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void readImages() {
        try {
            background = ImageIO.read(new File("images/backgroundImage.png"));
            dragonSheets.add(ImageIO.read(new File("images/black dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/blue dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/green dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/undead dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/young black dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/young red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/pale dragon.png")));
            heroSheets.add(ImageIO.read(new File("images/cleric.png")));
            heroSheets.add(ImageIO.read(new File("images/custom hero.png")));
            heroSheets.add(ImageIO.read(new File("images/ranger.png")));
            heroSheets.add(ImageIO.read(new File("images/rogue.png")));
            heroSheets.add(ImageIO.read(new File("images/warrior.png")));
            heroSheets.add(ImageIO.read(new File("images/wizard.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
