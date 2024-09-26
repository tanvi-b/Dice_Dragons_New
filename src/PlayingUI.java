import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayingUI extends JPanel {
    public static Game game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static ArrayList<Hero> gameHeroes;
    private ArrayList<BufferedImage> dragonSheets;
    private static ArrayList<Integer> heroSheets = new ArrayList<>();
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
    private static int heroClass;

    private static JLabel currentPlayerSheet;
    private static JLabel characterNameText;
    private static JLabel armorClassText;
    private static JLabel hitPointsText;
    private static JLabel levelText;
    private static JLabel expText;
    private static JLabel goldText;

    public PlayingUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        gameHeroes = new ArrayList<>();
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
        if(heroClass==0) {
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 3; i++) {
                g.drawImage(warriorTokens.get(0), -35, 235 + (i*50), 350, 350, this);
            }
        }
        if(heroClass==1) {
            try {
                g.drawImage(ImageIO.read(new File("images/wizard.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 3; i++) {
                g.drawImage(wizardTokens.get(0), -60, 210 + (i*50), 425, 425, this);
            }
        }
        if(heroClass==2) {
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 5; i++)
                g.drawImage(blessedTokens.get(0), -105, 295 + (i*50), 325, 250, this);
            for (int i = 0; i < 3; i++) {
                g.drawImage(clericTokens.get(0), 17, 265 + (i*50), 300, 300, this);
            }
        }
       if(heroClass==3) {
           try {
               g.drawImage(ImageIO.read(new File("images/ranger.png")), 190, 400, 450, 550, this);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           for (int i = 0; i < 3; i++) {
               g.drawImage(rangerTokens.get(0), -25, 250 + (i*50), 360, 360, this);
           }
       }
        if(heroClass==4) {
            try {
                g.drawImage(ImageIO.read(new File("images/rogue.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 3; i++) {
                g.drawImage(rogueTokens.get(0), -30, 300 + (i*50), 360, 360, this);
            }
        }
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
        chatBox.setBounds(20, 20, 270, 200);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(20, 230, 200, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton("Send");
        send.setFont(customFont.deriveFont(15f));
        send.setBounds(225, 230, 65, 65);

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

        currentPlayerSheet = new JLabel("Player");
        currentPlayerSheet.setFont(customFont.deriveFont(30f));
        currentPlayerSheet.setBounds(160, 355, 500, 50);
        currentPlayerSheet.setHorizontalAlignment(SwingConstants.CENTER);
        currentPlayerSheet.setOpaque(false);

        characterNameText = new JLabel("Testing");
        characterNameText.setFont(customFont.deriveFont(17f));
        //characterNameText.setBounds(160, 355, 500, 50);
        characterNameText.setHorizontalAlignment(SwingConstants.CENTER);
        characterNameText.setOpaque(false);

        armorClassText = new JLabel("0");
        armorClassText.setFont(customFont.deriveFont(17f));
        //armorClassText.setBounds(160, 355, 500, 50);
        armorClassText.setHorizontalAlignment(SwingConstants.CENTER);
        armorClassText.setOpaque(false);

        hitPointsText = new JLabel("0");
        hitPointsText.setFont(customFont.deriveFont(17f));
        //hitPointsText.setBounds(160, 355, 500, 50);
        hitPointsText.setHorizontalAlignment(SwingConstants.CENTER);
        hitPointsText.setOpaque(false);

        levelText = new JLabel("0");
        levelText.setFont(customFont.deriveFont(15f));
        //levelText.setBounds(160, 355, 500, 50);
        levelText.setHorizontalAlignment(SwingConstants.CENTER);
        levelText.setOpaque(false);

        expText = new JLabel("0");
        expText.setFont(customFont.deriveFont(15f));
        //expText.setBounds(160, 355, 500, 50);
        expText.setHorizontalAlignment(SwingConstants.CENTER);
        expText.setOpaque(false);

        goldText = new JLabel("0");
        goldText.setFont(customFont.deriveFont(15f));
        //goldText.setBounds(160, 355, 500, 50);
        goldText.setHorizontalAlignment(SwingConstants.CENTER);
        goldText.setOpaque(false);

        BasicArrowButton next = new BasicArrowButton(BasicArrowButton.EAST);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = heroSheets.indexOf(heroClass);
                if (i+1<=heroSheets.size()-1)
                {
                    heroClass = heroSheets.get(i+1);
                    repaint();
                }
                for (Hero hero: gameHeroes)
                {
                    if (hero.classType==heroClass)
                        setNameTopOfSheet(hero);
                }
            }
        });
        next.setBounds(525, 370, 100, 25);

        BasicArrowButton previous = new BasicArrowButton(BasicArrowButton.WEST);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = heroSheets.indexOf(heroClass);
                if (i-1>=0)
                {
                    heroClass = heroSheets.get(i-1);
                    repaint();
                }
                for (Hero hero: gameHeroes)
                {
                    if (hero.classType==heroClass)
                        setNameTopOfSheet(hero);
                }
            }
        });
        previous.setBounds(200, 370, 100, 25);

        add(turn);
        add(rules);
        add(guide);
        add(chatBox);
        add(messageText);
        add(send);
        add(currentPlayerSheet);
        add(characterNameText);
        add (armorClassText);
        add(hitPointsText);
        add(levelText);
        add(expText);
        add(goldText);
        add(next);
        add(previous);
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
        heroClass = selectionOfHero;
    }

    public static void addPlayerSheet (ArrayList<Hero> currentHeroes)
    {
        heroSheets.clear();
        for (int i = 0; i<currentHeroes.size(); i++)
            heroSheets.add(currentHeroes.get(i).classType);
    }

    public static void addHeroes (ArrayList<Hero> currentHeroes)
    {
        gameHeroes.clear();
        for (int i = 0; i<currentHeroes.size(); i++)
            gameHeroes.add(currentHeroes.get(i));
    }

    public static void setNameTopOfSheet (Hero hero)
    {
        currentPlayerSheet.setText(hero.heroName);
    }

    public static void setFields (Hero hero)
    {
        currentPlayerSheet.setText(hero.heroName);
        characterNameText.setText(hero.heroName);
        armorClassText.setText(String.valueOf(hero.armorClass));
        hitPointsText.setText(String.valueOf(hero.hitPoints));
        levelText.setText(String.valueOf(hero.level));
        expText.setText(String.valueOf(hero.exp));
        goldText.setText(String.valueOf(hero.gold));
    }
}
