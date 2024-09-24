import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class PlayingUI extends JPanel {
    public static Game game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<BufferedImage> dragonSheets;
    private static ArrayList<BufferedImage> heroSheets;
    private ArrayList<BufferedImage> diceFaces;
    private ArrayList<BufferedImage> warriorTokens;
    private ArrayList<BufferedImage> wizardTokens;
    private ArrayList<BufferedImage> clericTokens;
    private ArrayList<BufferedImage> rangerTokens;
    private ArrayList<BufferedImage> rogueTokens;
    private ArrayList<BufferedImage> dragonTokens;
    private ArrayList<BufferedImage> poisonTokens;
    private ArrayList<BufferedImage> pinnedTokens;
    private ArrayList<BufferedImage> blessedTokens;
    private BufferedImage background;
    private Font customFont;
    private Font customBoldFont;
    private static boolean wizardState;
    private static boolean clericState;
    private static boolean warriorState;
    private static boolean rangerState;
    private static boolean rogueState;

    public PlayingUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        dragonSheets = new ArrayList<>();
        heroSheets = new ArrayList<>();
        diceFaces = new ArrayList<>();
        warriorTokens = new ArrayList<>();
        wizardTokens = new ArrayList<>();
        clericTokens = new ArrayList<>();
        rangerTokens = new ArrayList<>();
        rogueTokens = new ArrayList<>();
        dragonTokens = new ArrayList<>();
        poisonTokens = new ArrayList<>();
        pinnedTokens = new ArrayList<>();
        blessedTokens = new ArrayList<>();
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

   public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        g.drawImage(dragonSheets.get(0), 680, 400, 500, 550, this);
        if(warriorState == true)
            g.drawImage(heroSheets.get(4), 190, 400, 450, 550, this);
        if(wizardState == true)
            g.drawImage(heroSheets.get(5), 190, 400, 450, 550, this);
        if(clericState == true)
            g.drawImage(heroSheets.get(0), 190, 400, 450, 550, this);
        if(rogueState == true)
            g.drawImage(heroSheets.get(3), 190, 400, 450, 550, this);
        if(rangerState == true)
            g.drawImage(heroSheets.get(2), 190, 400, 450, 550, this);
    }

    private void addComponents() {

        JLabel turn = new JLabel("Turn: ");
        turn.setFont(customFont.deriveFont(28f));
        turn.setBounds(450, 20, 400, 60);
        turn.setOpaque(true);

        JButton rules = new JButton("Rules");
        rules.setFont(customFont.deriveFont(20f));
        rules.setBounds(900, 20, 60, 60);
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        rules.setOpaque(true);

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(mainPanel, "PlayerRulesScreen");
            }
        });

        JButton guide = new JButton("Guide");
        guide.setFont(customFont.deriveFont(20f));
        guide.setBounds(990, 20, 60, 60);
        guide.setBorder(BorderFactory.createLineBorder(Color.black));
        guide.setOpaque(true);

        guide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(mainPanel, "DragonGuideScreen");
            }
        });

        JList<String> messages = new JList<>();
        JScrollPane chatBox = new JScrollPane(messages);
        chatBox.setBounds(20, 20, 270, 150);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(20, 180, 200, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton("Send");
        send.setFont(customFont.deriveFont(15f));
        send.setBounds(225, 180, 65, 65);

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
        add(chatBox);
        add(messageText);
        add(send);
    }

    private static void refreshChat(String message)
    {

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
            dragonSheets.add(ImageIO.read(new File("images/young red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/pale dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/young black dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/green dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/blue dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/undead dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/black dragon.png")));

            //for now, only added one image into arraylist - can change later
            warriorTokens.add(ImageIO.read(new File("images/WeaponBlue.png")));
            wizardTokens.add(ImageIO.read(new File("images/WeaponRed.png")));
            clericTokens.add(ImageIO.read(new File("images/WeaponTeal.png")));
            rangerTokens.add(ImageIO.read(new File("images/WeaponGreen.png")));
            rogueTokens.add(ImageIO.read(new File("images/WeaponPurple.png")));
            dragonTokens.add(ImageIO.read(new File("images/tokenYellow.png")));
            poisonTokens.add(ImageIO.read(new File("images/tokenGreen.png")));
            pinnedTokens.add(ImageIO.read(new File("images/tokenRed.png")));
            blessedTokens.add(ImageIO.read(new File("images/tokenBlue.png")));

            diceFaces.add(ImageIO.read(new File("images/dice1.png")));
            diceFaces.add(ImageIO.read(new File("images/dice2.png")));
            diceFaces.add(ImageIO.read(new File("images/dice3.png")));
            diceFaces.add(ImageIO.read(new File("images/dice4.png")));
            diceFaces.add(ImageIO.read(new File("images/dice5.png")));
            diceFaces.add(ImageIO.read(new File("images/dice6.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayPlayerSheet(String heroes){
        int selectionOfHero = Integer.parseInt(heroes);
        if(selectionOfHero == 0)
            warriorState = true;
        if(selectionOfHero == 1)
            wizardState = true;
        if(selectionOfHero == 2)
            clericState = true;
        if(selectionOfHero == 3)
            rangerState = true;
        if(selectionOfHero == 4)
            rogueState = true;
    }

    public static void addPlayerSheet (String currentHero)
    {
        int selectionOfHero = Integer.parseInt(currentHero);
        if (selectionOfHero==0) {
            try {
                heroSheets.add(ImageIO.read(new File("images/warrior.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (selectionOfHero==1) {
            try {
                heroSheets.add(ImageIO.read(new File("images/wizard.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (selectionOfHero==2) {
            try {
                heroSheets.add(ImageIO.read(new File("images/cleric.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (selectionOfHero==3) {
            try {
                heroSheets.add(ImageIO.read(new File("images/ranger.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (selectionOfHero==4) {
            try {
                heroSheets.add(ImageIO.read(new File("images/rogue.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
