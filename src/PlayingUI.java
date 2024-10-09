import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayingUI extends JPanel {
    private static PlayingUI instance;
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
    private static int heroClass; //changes as person flips through sheets
    private static JLabel turn;
    private static JLabel currentPlayerSheet;
    private static JLabel characterNameText;
    private static JLabel armorClassText;
    private static JLabel hitPointsText;
    private static JLabel levelText;
    private static JLabel expText;
    private static JLabel goldText;
    private static ArrayList<Integer> diceRolled;
    private static String username;
    private static DefaultListModel<String> chatModel = new DefaultListModel<>();
    public static JList<String> chatMessages = new JList<>(chatModel);
    public static String accessCode;
    private static int turnTracker = 0;
    private int timesRolled;

    public PlayingUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        instance = this;
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
        diceRolled = new ArrayList<>();
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        g.drawImage(dragonSheets.get(0), 680, 400, 500, 550, this);
        for (int i = 0; i < diceRolled.size(); i++)
            g.drawImage(diceFaces.get(diceRolled.get(i)), 450 + i*125, 100, 100, 100, this);

        if(heroClass==0) {
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 3; i++) {
                g.drawImage(warriorTokens.get(0), -35, 235 + (i*50), 350, 350, this);
            }
            addWarriorSkillButtons();
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
            addWizardSkillButtons();
        }
        if(heroClass==2) {
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 4; i++)
                g.drawImage(blessedTokens.get(0), -105, 295 + (i*50), 325, 250, this);
            for (int i = 0; i < 3; i++) {
                g.drawImage(clericTokens.get(0), 17, 265 + (i*50), 300, 300, this);
            }
            addClericSkillButtons();
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
            addRangerSkillButtons();
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
            addRogueSkillButtons();
        }
    }

    private void addComponents() {
        turn = new JLabel("Turn: ");
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
                cardLayout.show(mainPanel, "PlayerRulesScreen");
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
                cardLayout.show(mainPanel, "DragonGuideScreen");
            }
        });

        JScrollPane chatBox = new JScrollPane(chatMessages);
        chatBox.setVisible(true);
        chatBox.setBounds(20, 20, 270, 200);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(20, 230, 200, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton("Send");
        send.setFont(customFont.deriveFont(15f));
        send.setBounds(225, 230, 65, 65);
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (messageText.getText().length()!=0)
                    PlayingUI.game.sendMessage(PlayingUI.game.getOs(), username+ ": " + messageText.getText());
                messageText.setText("");
            }
        });

        JButton roll = new JButton ("Roll");
        roll.setFont(customFont.deriveFont(20f));
        roll.setBounds(515, 240, 225, 30);
        roll.setBorder(BorderFactory.createLineBorder(Color.black));
        roll.setOpaque(true);
        roll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(characterNameText.getText()))
                {
                    if (timesRolled<3) {
                        PlayingUI.game.passDice(PlayingUI.game.getOs());
                        timesRolled++;
                    }
                }
                repaint();
            }
        });

        JButton doneWithTurn = new JButton ("Done with Turn");
        doneWithTurn.setBackground(new Color(228, 99, 98));
        doneWithTurn.setFont(customFont.deriveFont(20f));
        doneWithTurn.setBounds(755, 240, 225, 30);
        doneWithTurn.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0)));
        doneWithTurn.setOpaque(true);
        doneWithTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timesRolled>0) {
                    PlayingUI.game.switchTurn(PlayingUI.game.getOs(), turnTracker);
                    timesRolled = 0;
                }
            }
        });

        JButton keep1 = new JButton("Keep");
        keep1.setFont(customFont.deriveFont(20f));
        keep1.setBounds(450, 210, 100, 20);
        keep1.setBorder(BorderFactory.createLineBorder(Color.black));
        keep1.setOpaque(true);
        keep1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
            }
        });

        JButton keep2 = new JButton("Keep");
        keep2.setFont(customFont.deriveFont(20f));
        keep2.setBounds(575, 210, 100, 20);
        keep2.setBorder(BorderFactory.createLineBorder(Color.black));
        keep2.setOpaque(true);
        keep2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
            }
        });

        JButton keep3 = new JButton("Keep");
        keep3.setFont(customFont.deriveFont(20f));
        keep3.setBounds(700, 210, 100, 20);
        keep3.setBorder(BorderFactory.createLineBorder(Color.black));
        keep3.setOpaque(true);
        keep3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
            }
        });

        JButton keep4 = new JButton("Keep");
        keep4.setFont(customFont.deriveFont(20f));
        keep4.setBounds(825, 210, 100, 20);
        keep4.setBorder(BorderFactory.createLineBorder(Color.black));
        keep4.setOpaque(true);
        keep4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
            }
        });

        JButton keep5 = new JButton("Keep");
        keep5.setFont(customFont.deriveFont(20f));
        keep5.setBounds(950, 210, 100, 20);
        keep5.setBorder(BorderFactory.createLineBorder(Color.black));
        keep5.setOpaque(true);
        keep5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
            }
        });

        //special skills
        JButton specialSkills = new JButton("Special Skills");
        specialSkills.setFont(customFont.deriveFont(20f));
        specialSkills.setBounds(30, 350, 150, 40);
        specialSkills.setBorder(BorderFactory.createLineBorder(Color.black));
        specialSkills.setOpaque(true);

        specialSkills.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if (turn.getText().substring(6).equals(characterNameText.getText()))
                SpecialSkillsUI.getDice(diceRolled);
                cardLayout.show(mainPanel, "SpecialSkillsScreen");
            }
        });

        JTextField gameMessages = new JTextField("Message: ");
        gameMessages.setEditable(false);
        gameMessages.setFont(customFont.deriveFont(17f));
        gameMessages.setBounds(450, 280, 600, 50);
        gameMessages.setBorder(BorderFactory.createLineBorder(Color.black));

        currentPlayerSheet = new JLabel();
        currentPlayerSheet.setFont(customFont.deriveFont(30f));
        currentPlayerSheet.setBounds(160, 355, 500, 50);
        currentPlayerSheet.setHorizontalAlignment(SwingConstants.CENTER);
        currentPlayerSheet.setOpaque(false);

        characterNameText = new JLabel();
        characterNameText.setFont(customFont.deriveFont(17f));
        characterNameText.setBounds(375, 440, 100, 30);
        characterNameText.setHorizontalAlignment(SwingConstants.CENTER);
        characterNameText.setOpaque(false);

        armorClassText = new JLabel();
        armorClassText.setFont(customFont.deriveFont(17f));
        armorClassText.setBounds(258, 505, 35, 45);
        armorClassText.setHorizontalAlignment(SwingConstants.CENTER);
        armorClassText.setOpaque(false);
        //for now box use x = 295

        hitPointsText = new JLabel();
        hitPointsText.setFont(customFont.deriveFont(17f));
        hitPointsText.setBounds(348, 505, 35, 45);
        hitPointsText.setHorizontalAlignment(SwingConstants.CENTER);
        hitPointsText.setOpaque(false);

        levelText = new JLabel("0");
        levelText.setFont(customFont.deriveFont(15f));
        levelText.setBounds(455, 505, 20, 20);
        levelText.setHorizontalAlignment(SwingConstants.CENTER);
        levelText.setOpaque(false);

        expText = new JLabel("0");
        expText.setFont(customFont.deriveFont(15f));
        expText.setBounds(455, 545, 20, 20);
        expText.setHorizontalAlignment(SwingConstants.CENTER);
        expText.setOpaque(false);

        goldText = new JLabel("0");
        goldText.setFont(customFont.deriveFont(15f));
        goldText.setBounds(455, 582, 20, 20);
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
                        setFields(hero);
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
                        setFields(hero);
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
        add(roll);
        add(doneWithTurn);
        add(keep1);
        add(keep2);
        add(keep3);
        add(keep4);
        add(keep5);
        add(specialSkills);
        add(gameMessages);
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

    private void addWarriorSkillButtons()
    {
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton strike = new JButton("Strike");
        strike.setFont(customFont.deriveFont(10f));
        strike.setBounds(262, 620, 80, 20);
        strike.setBackground(buttonSkillsColor);
        strike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton slash = new JButton("Slash");
        slash.setFont(customFont.deriveFont(10f));
        slash.setBounds(264, 660, 80, 20);
        slash.setBackground(buttonSkillsColor);

        JButton smashingBlow = new JButton("Smashing Blow");
        smashingBlow.setFont(customFont.deriveFont(7.4f));
        smashingBlow.setBounds(265, 697, 80, 20);
        smashingBlow.setBackground(buttonSkillsColor);

        JButton savageAttack = new JButton("Savage   Attack");
        savageAttack.setFont(customFont.deriveFont(7.4f));
        savageAttack.setBounds(262, 735, 80, 20);
        savageAttack.setBackground(buttonSkillsColor);

        JButton parry = new JButton("Parry");
        parry.setFont(customFont.deriveFont(10f));
        parry.setBounds(265, 772, 80, 20);
        parry.setBackground(buttonSkillsColor);

        JButton criticalHit = new JButton("Critical Hit");
        criticalHit.setFont(customFont.deriveFont(10f));
        criticalHit.setBounds(265, 813, 80, 18);
        criticalHit.setBackground(buttonSkillsColor);

        add(strike);
        add(slash);
        add(smashingBlow);
        add(savageAttack);
        add(parry);
        add(criticalHit);
    }

    private void addWizardSkillButtons()
    {
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton strike = new JButton("Strike");
        strike.setFont(customFont.deriveFont(10f));
        strike.setBounds(262, 620, 80, 20);
        strike.setBackground(buttonSkillsColor);
        strike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton magicBolt = new JButton("Magic Bolt");
        magicBolt.setFont(customFont.deriveFont(10f));
        magicBolt.setBounds(264, 658, 80, 20);
        magicBolt.setBackground(buttonSkillsColor);

        JButton firebolt = new JButton("Firebolt");
        firebolt.setFont(customFont.deriveFont(10f));
        firebolt.setBounds(265, 697, 80, 20);
        firebolt.setBackground(buttonSkillsColor);

        JButton lightningStorm = new JButton("Lightning Storm");
        lightningStorm.setFont(customFont.deriveFont(6.4f));
        lightningStorm.setBounds(262, 735, 80, 20);
        lightningStorm.setBackground(buttonSkillsColor);

        JButton shield = new JButton("Shield");
        shield.setFont(customFont.deriveFont(10f));
        shield.setBounds(263, 770, 80, 20);
        shield.setBackground(buttonSkillsColor);

        JButton criticalHit = new JButton("Critical Hit");
        criticalHit.setFont(customFont.deriveFont(10f));
        criticalHit.setBounds(263, 811, 80, 18);
        criticalHit.setBackground(buttonSkillsColor);

        add(strike);
        add(magicBolt);
        add(firebolt);
        add(lightningStorm);
        add(shield);
        add(criticalHit);
    }

    private void addClericSkillButtons()
    {
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton testing1 = new JButton("Testing");
        testing1.setFont(customFont.deriveFont(9.4f));
        testing1.setBounds(255, 620, 85, 20);
        testing1.setBackground(buttonSkillsColor);

        JButton testing2 = new JButton("Testing");
        testing2.setFont(customFont.deriveFont(10f));
        testing2.setBounds(255, 660, 85, 20);
        testing2.setBackground(buttonSkillsColor);

        add(testing2);
        add(testing1);
    }

    private void addRangerSkillButtons()
    {
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton wildStrike = new JButton("Wild Strike");
        wildStrike.setFont(customFont.deriveFont(10f));
        wildStrike.setBounds(255, 620, 85, 20);
        wildStrike.setBackground(buttonSkillsColor);
        wildStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton accurateShot = new JButton("Accurate Shot");
        accurateShot.setFont(customFont.deriveFont(9.4f));
        accurateShot.setBounds(255, 660, 85, 20);
        accurateShot.setBackground(buttonSkillsColor);

        JButton dualShot = new JButton("Dual Shot");
        dualShot.setFont(customFont.deriveFont(10f));
        dualShot.setBounds(255, 700, 85, 20);
        dualShot.setBackground(buttonSkillsColor);

        JButton crossfire = new JButton("Crossfire");
        crossfire.setFont(customFont.deriveFont(10f));
        crossfire.setBounds(255, 740, 85, 20);
        crossfire.setBackground(buttonSkillsColor);

        JButton pinDown = new JButton("Pin Down");
        pinDown.setFont(customFont.deriveFont(10f));
        pinDown.setBounds(255, 780, 85, 20);
        pinDown.setBackground(buttonSkillsColor);

        JButton criticalHit = new JButton("Critical Hit");
        criticalHit.setFont(customFont.deriveFont(10f));
        criticalHit.setBounds(255, 820, 85, 18);
        criticalHit.setBackground(buttonSkillsColor);

        add(wildStrike);
        add(accurateShot);
        add(dualShot);
        add(crossfire);
        add(pinDown);
        add(criticalHit);
    }

    private void addRogueSkillButtons()
    {
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton strike = new JButton("Strike");
        strike.setFont(customFont.deriveFont(10f));
        strike.setBounds(265, 620, 85, 20);
        strike.setBackground(buttonSkillsColor);
        strike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton stab = new JButton("Stab");
        stab.setFont(customFont.deriveFont(10f));
        stab.setBounds(265, 660, 85, 20);
        stab.setBackground(buttonSkillsColor);

        JButton flankingStrike = new JButton("Flanking Strike");
        flankingStrike.setFont(customFont.deriveFont(8f));
        flankingStrike.setBounds(265, 695, 85, 20);
        flankingStrike.setBackground(buttonSkillsColor);

        JButton sneakAttack = new JButton("Sneak Attack");
        sneakAttack.setFont(customFont.deriveFont(9f));
        sneakAttack.setBounds(265, 735, 85, 20);
        sneakAttack.setBackground(buttonSkillsColor);

        JButton suddenDeath = new JButton("Sudden Death");
        suddenDeath.setFont(customFont.deriveFont(10f));
        suddenDeath.setBounds(265, 780, 85, 20);
        suddenDeath.setBackground(buttonSkillsColor);


        JButton criticalHit = new JButton("Critical Hit");
        criticalHit.setFont(customFont.deriveFont(10f));
        criticalHit.setBounds(265, 820, 85, 18);
        criticalHit.setBackground(buttonSkillsColor);

        add(strike);
        add(stab);
        add(suddenDeath);
        add(flankingStrike);
        add(sneakAttack);
        add(criticalHit);
    }

    public static void refreshChat(ArrayList<String> text) throws IOException {
        chatModel.clear();
        for(int i =0; i<text.size(); i++){
            chatModel.addElement(text.get(i));
        }
        chatMessages.ensureIndexIsVisible(chatModel.getSize()-1);
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

            diceFaces.add(ImageIO.read(new File("images/dice0.png")));
            diceFaces.add(ImageIO.read(new File("images/dice1.png")));
            diceFaces.add(ImageIO.read(new File("images/dice2.png")));
            diceFaces.add(ImageIO.read(new File("images/dice3.png")));
            diceFaces.add(ImageIO.read(new File("images/dice4.png")));
            diceFaces.add(ImageIO.read(new File("images/dice5.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addHeroes (ArrayList<Hero> currentHeroes)
    {
        gameHeroes.clear();
        heroSheets.clear();
        for (int i = 0; i<currentHeroes.size(); i++) {
            heroSheets.add(currentHeroes.get(i).classType);
            gameHeroes.add(currentHeroes.get(i));
        }
        gameHeroes.sort(Comparator.comparingInt(h -> h.incentiveOrder));
        setTurnText(0);
    }

    public static void setTurnText(int turnNumber)
    {
        turn.setText("Turn: " + gameHeroes.get(turnNumber).heroName);
        turnTracker = turnNumber;
    }

    public static void setAccessCode(String num){
        accessCode = num;
    }

    public static String getAccessCode(){
        return accessCode;
    }

    public static void setFields (Hero hero)
    {
        heroClass = hero.classType;
        currentPlayerSheet.setText(hero.heroName);
        characterNameText.setText(hero.heroName);
        username = hero.heroName;
        armorClassText.setText(String.valueOf(hero.armorClass));
        hitPointsText.setText(String.valueOf(hero.hitPoints));
        levelText.setText(String.valueOf(hero.level));
        expText.setText(String.valueOf(hero.exp));
        goldText.setText(String.valueOf(hero.gold));
    }

    public static void getDice (ArrayList<Integer> dice)
    {
        diceRolled.clear();
        for (int i = 0; i< dice.size(); i++)
            diceRolled.add(dice.get(i));

        if (instance != null) {
            instance.repaint();
        }
    }
}

//roll - y = 210
//keep - y = 235

  
