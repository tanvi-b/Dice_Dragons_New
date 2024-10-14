import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PlayingUI extends JPanel {
    private static PlayingUI instance;
    public static Game game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static ArrayList<Hero> gameHeroes = new ArrayList<>(); //use this to acccess hero info
    private ArrayList<BufferedImage> dragonSheets = new ArrayList<>();
    private static ArrayList<Integer> heroSheets = new ArrayList<>();
    private ArrayList<BufferedImage> diceFaces = new ArrayList<>();
    private ArrayList<BufferedImage> dragonTokens = new ArrayList<>();
    private ArrayList<BufferedImage> poisonTokens = new ArrayList<>();
    private ArrayList<BufferedImage> pinnedTokens = new ArrayList<>();
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
    private static JTextField gameMessages;
    private static List<Map.Entry<Boolean, Integer>> diceRolled = new ArrayList<>();
    private static DefaultListModel<String> chatModel = new DefaultListModel<>();
    public static JList<String> chatMessages = new JList<>(chatModel);
    private static String username;
    public static String accessCode;
    public static int level;
    private static int turnTracker = 0;
    private int timesRolled= 1;
    private int turnsPlayed = 0;

    private static List<Map.Entry<Boolean, JButton>> skillButtons = new ArrayList<>();
    //private static ArrayList<JButton> skillButtons = new ArrayList<>();

    public PlayingUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        instance = this;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
        addSkillButtons();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        g.drawImage(dragonSheets.get(level-1), 680, 400, 500, 550, this);
        for (int i = 0; i < diceRolled.size(); i++)
            g.drawImage(diceFaces.get(diceRolled.get(i).getValue()), 450 + i*125, 100, 100, 100, this);

        ArrayList<Token> heroTokens = new ArrayList<>();
        if(heroClass==0) {
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            heroTokens = getClassTokens(0);
            for (int i = 0; i < heroTokens.size(); i++) {
                try {
                    g.drawImage(ImageIO.read(new File("images/WeaponBlue.png")), heroTokens.get(i).xCoordinate,
                            heroTokens.get(i).yCoordinate, 350, 350, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(heroClass==1) {
            try {
                g.drawImage(ImageIO.read(new File("images/wizard.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            heroTokens = getClassTokens(1);
            for (int i = 0; i < heroTokens.size(); i++) {
                try {
                    g.drawImage(ImageIO.read(new File("images/WeaponRed.png")), heroTokens.get(i).xCoordinate,
                            heroTokens.get(i).yCoordinate, 425, 425, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(heroClass==2) {
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            heroTokens = getClassTokens(2);
            for (int i = 0; i < heroTokens.size(); i++) {
                if (heroTokens.get(i).type==0) {
                    try {
                        g.drawImage(ImageIO.read(new File("images/WeaponTeal.png")), heroTokens.get(i).xCoordinate,
                                heroTokens.get(i).yCoordinate, 300, 300, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        g.drawImage(ImageIO.read(new File("images/tokenBlue.png")), heroTokens.get(i).xCoordinate,
                                heroTokens.get(i).yCoordinate, 325, 250, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if(heroClass==3) {
            try {
                g.drawImage(ImageIO.read(new File("images/ranger.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            heroTokens = getClassTokens(3);
            for (int i = 0; i < heroTokens.size(); i++) {
                try {
                    g.drawImage(ImageIO.read(new File("images/WeaponGreen.png")), heroTokens.get(i).xCoordinate,
                            heroTokens.get(i).yCoordinate, 360, 360, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(heroClass==4) {
            try {
                g.drawImage(ImageIO.read(new File("images/rogue.png")), 190, 400, 450, 550, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            heroTokens = getClassTokens(4);
            for (int i = 0; i < heroTokens.size(); i++) {
                try {
                    g.drawImage(ImageIO.read(new File("images/WeaponPurple.png")), heroTokens.get(i).xCoordinate,
                            heroTokens.get(i).yCoordinate, 360, 360, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private ArrayList<Token> getClassTokens (int heroClassNum)
    {
        for (Hero hero : gameHeroes) {
            if (hero.classType == heroClassNum)
                return hero.tokens;
        }
        return null;
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

        JButton keep1 = new JButton("Keep");
        keep1.setFont(customFont.deriveFont(20f));
        keep1.setBounds(450, 205, 100, 25);
        keep1.setBorder(BorderFactory.createLineBorder(Color.black));
        keep1.setOpaque(true);
        keep1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    Integer value = diceRolled.get(0).getValue();
                    Map.Entry<Boolean, Integer> newEntry = null;
                    if (keep1.getBorder() instanceof MatteBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(false, value);
                        keep1.setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    else if (keep1.getBorder() instanceof LineBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(true, value);
                        keep1.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
                    }
                    diceRolled.set(0, newEntry);
                }
            }
        });

        JButton keep2 = new JButton("Keep");
        keep2.setFont(customFont.deriveFont(20f));
        keep2.setBounds(575, 205, 100, 25);
        keep2.setBorder(BorderFactory.createLineBorder(Color.black));
        keep2.setOpaque(true);
        keep2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    Integer value = diceRolled.get(1).getValue();
                    Map.Entry<Boolean, Integer> newEntry = null;
                    if (keep2.getBorder() instanceof MatteBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(false, value);
                        keep2.setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    else if (keep2.getBorder() instanceof LineBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(true, value);
                        keep2.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
                    }
                    diceRolled.set(1, newEntry);
                }
            }
        });

        JButton keep3 = new JButton("Keep");
        keep3.setFont(customFont.deriveFont(20f));
        keep3.setBounds(700, 205, 100, 25);
        keep3.setBorder(BorderFactory.createLineBorder(Color.black));
        keep3.setOpaque(true);
        keep3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    Integer value = diceRolled.get(2).getValue();
                    Map.Entry<Boolean, Integer> newEntry = null;
                    if (keep3.getBorder() instanceof MatteBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(false, value);
                        keep3.setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    else if (keep3.getBorder() instanceof LineBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(true, value);
                        keep3.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
                    }
                    diceRolled.set(2, newEntry);
                }
            }
        });

        JButton keep4 = new JButton("Keep");
        keep4.setFont(customFont.deriveFont(20f));
        keep4.setBounds(825, 205, 100, 25);
        keep4.setBorder(BorderFactory.createLineBorder(Color.black));
        keep4.setOpaque(true);
        keep4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    Integer value = diceRolled.get(3).getValue();
                    Map.Entry<Boolean, Integer> newEntry = null;
                    if (keep4.getBorder() instanceof MatteBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(false, value);
                        keep4.setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    else if (keep4.getBorder() instanceof LineBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(true, value);
                        keep4.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
                    }
                    diceRolled.set(3, newEntry);
                }
            }
        });

        JButton keep5 = new JButton("Keep");
        keep5.setFont(customFont.deriveFont(20f));
        keep5.setBounds(950, 205, 100, 25);
        keep5.setBorder(BorderFactory.createLineBorder(Color.black));
        keep5.setOpaque(true);
        keep5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    Integer value = diceRolled.get(4).getValue();
                    Map.Entry<Boolean, Integer> newEntry = null;
                    if (keep5.getBorder() instanceof MatteBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(false, value);
                        keep5.setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    else if (keep5.getBorder() instanceof LineBorder) {
                        newEntry = new AbstractMap.SimpleEntry<>(true, value);
                        keep5.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
                    }
                    diceRolled.set(4, newEntry);
                }
            }
        });

        JButton roll = new JButton ("Roll");
        roll.setFont(customFont.deriveFont(20f));
        roll.setBounds(515, 240, 225, 30);
        roll.setBorder(BorderFactory.createLineBorder(Color.black));
        roll.setOpaque(true);
        roll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username))
                {
                    if (timesRolled<3) {
                        keep1.setBorder(BorderFactory.createLineBorder(Color.black));
                        keep2.setBorder(BorderFactory.createLineBorder(Color.black));
                        keep3.setBorder(BorderFactory.createLineBorder(Color.black));
                        keep4.setBorder(BorderFactory.createLineBorder(Color.black));
                        keep5.setBorder(BorderFactory.createLineBorder(Color.black));
                        PlayingUI.game.passDice(PlayingUI.game.getOs(), diceRolled);
                        timesRolled++;
                        String text = username + " has rolled the dice combinations above! " + username + " has " + (3-timesRolled) + " turns remaining!";
                        PlayingUI.game.gameMessageText(PlayingUI.game.getOs(), text);
                    }
                }
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
                //if they weren't able to activate a skill put some message in textbox?
                if (turn.getText().substring(6).equals(username) && turnsPlayed>1) {
                    keep1.setBorder(BorderFactory.createLineBorder(Color.black));
                    keep2.setBorder(BorderFactory.createLineBorder(Color.black));
                    keep3.setBorder(BorderFactory.createLineBorder(Color.black));
                    keep4.setBorder(BorderFactory.createLineBorder(Color.black));
                    keep5.setBorder(BorderFactory.createLineBorder(Color.black));
                    PlayingUI.game.passDice(PlayingUI.game.getOs(), diceRolled);
                    PlayingUI.game.switchTurn(PlayingUI.game.getOs(), turnTracker);
                    timesRolled = 1;
                }
            }
        });

        JButton specialSkills = new JButton("Special Skills");
        specialSkills.setFont(customFont.deriveFont(20f));
        specialSkills.setBounds(30, 350, 150, 40);
        specialSkills.setBorder(BorderFactory.createLineBorder(Color.black));
        specialSkills.setOpaque(true);
        specialSkills.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turn.getText().substring(6).equals(username)) {
                    SpecialSkillsUI.getDice(diceRolled);
                    cardLayout.show(mainPanel, "SpecialSkillsScreen");
                }
            }
        });

        gameMessages = new JTextField("Message: ");
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

    private static void addWarriorSkillButtons()
    {
        for (int i  = 0; i<=5; i++)
            skillButtons.get(i).getValue().setVisible(skillButtons.get(i).getKey());
        for (int i = 6; i< skillButtons.size(); i++)
            skillButtons.get(i).getValue().setVisible(false);
    }

    private static void addWizardSkillButtons()
    {
        for (int i = 0; i<=5; i++)
            skillButtons.get(i).getValue().setVisible(false);
        for (int i  = 6; i<=11; i++)
            skillButtons.get(i).getValue().setVisible(skillButtons.get(i).getKey());
        for (int i = 12; i< skillButtons.size(); i++)
            skillButtons.get(i).getValue().setVisible(false);
    }

    private static void addClericSkillButtons()
    {
        for (int i = 0; i<=11; i++)
            skillButtons.get(i).getValue().setVisible(false);
        for (int i = 12; i<=17; i++)
            skillButtons.get(i).getValue().setVisible(skillButtons.get(i).getKey());
        for (int i = 18; i< skillButtons.size(); i++)
            skillButtons.get(i).getValue().setVisible(false);
    }

    private static void addRangerSkillButtons()
    {
        for (int i = 0; i<=17; i++)
            skillButtons.get(i).getValue().setVisible(false);
        for (int i = 18; i<=23; i++)
            skillButtons.get(i).getValue().setVisible(skillButtons.get(i).getKey());
        for (int i = 24; i< skillButtons.size(); i++)
            skillButtons.get(i).getValue().setVisible(false);
    }

    private static void addRogueSkillButtons()
    {
        for (int i = 0; i<=23; i++)
            skillButtons.get(i).getValue().setVisible(false);
        for (int i = 24; i< skillButtons.size(); i++)
            skillButtons.get(i).getValue().setVisible(skillButtons.get(i).getKey());
    }

    private void addSkillButtons()
    {
        //need to do networking
        Color buttonSkillsColor = new Color(237,197,72,255);
        JButton warriorStrike = new JButton("Strike");
        warriorStrike.setFont(customFont.deriveFont(10f));
        warriorStrike.setBounds(262, 620, 80, 20);
        warriorStrike.setBackground(buttonSkillsColor);
        warriorStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(0).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 5
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 450;
                    skillButtons.set(0, new AbstractMap.SimpleEntry<>(false, warriorStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton slash = new JButton("Slash");
        slash.setFont(customFont.deriveFont(10f));
        slash.setBounds(264, 660, 80, 20);
        slash.setBackground(buttonSkillsColor);
        slash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(1).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 4
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 490;
                    skillButtons.set(1, new AbstractMap.SimpleEntry<>(false, slash));
                    turnsPlayed++;
                }
            }
        });

        JButton smashingBlow = new JButton("Smashing Blow");
        smashingBlow.setFont(customFont.deriveFont(7.4f));
        smashingBlow.setBounds(265, 697, 80, 20);
        smashingBlow.setBackground(buttonSkillsColor);
        smashingBlow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(2).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 6
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 530;
                    skillButtons.set(2, new AbstractMap.SimpleEntry<>(false, smashingBlow));
                    turnsPlayed++;
                }
            }
        });

        JButton savageAttack = new JButton("Savage Attack");
        savageAttack.setFont(customFont.deriveFont(7.4f));
        savageAttack.setBounds(262, 735, 80, 20);
        savageAttack.setBackground(buttonSkillsColor);
        savageAttack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(3).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 9
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 560;
                    skillButtons.set(3, new AbstractMap.SimpleEntry<>(false, savageAttack));
                    turnsPlayed++;
                }
            }
        });

        JButton parry = new JButton("Parry");
        parry.setFont(customFont.deriveFont(10f));
        parry.setBounds(265, 772, 80, 20);
        parry.setBackground(buttonSkillsColor);
        parry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(4).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //+2 AC to any chosen hero
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 600;
                    skillButtons.set(4, new AbstractMap.SimpleEntry<>(false, parry));
                    turnsPlayed++;
                }
            }
        });

        JButton warriorCriticalHit = new JButton("Critical Hit");
        warriorCriticalHit.setFont(customFont.deriveFont(10f));
        warriorCriticalHit.setBounds(265, 813, 80, 18);
        warriorCriticalHit.setBackground(buttonSkillsColor);
        warriorCriticalHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(5).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 135;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 640;
                    skillButtons.set(5, new AbstractMap.SimpleEntry<>(false, warriorCriticalHit));
                    turnsPlayed++;
                }
            }
        });

        JButton wizardStrike = new JButton("Strike");
        wizardStrike.setFont(customFont.deriveFont(10f));
        wizardStrike.setBounds(262, 620, 80, 20);
        wizardStrike.setBackground(buttonSkillsColor);
        wizardStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(0).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 5
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 420;
                    skillButtons.set(6, new AbstractMap.SimpleEntry<>(false, wizardStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton magicBolt = new JButton("Magic Bolt");
        magicBolt.setFont(customFont.deriveFont(10f));
        magicBolt.setBounds(264, 658, 80, 20);
        magicBolt.setBackground(buttonSkillsColor);
        magicBolt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(1).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 4
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 460;
                    skillButtons.set(7, new AbstractMap.SimpleEntry<>(false, magicBolt));
                    turnsPlayed++;
                }
            }
        });

        JButton firebolt = new JButton("Fireball");
        firebolt.setFont(customFont.deriveFont(10f));
        firebolt.setBounds(265, 697, 80, 20);
        firebolt.setBackground(buttonSkillsColor);
        firebolt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(2).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 6
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 500;
                    skillButtons.set(8, new AbstractMap.SimpleEntry<>(false, firebolt));
                    turnsPlayed++;
                }
            }
        });

        JButton lightningStorm = new JButton("Lightning Storm");
        lightningStorm.setFont(customFont.deriveFont(6.4f));
        lightningStorm.setBounds(262, 735, 80, 20);
        lightningStorm.setBackground(buttonSkillsColor);
        lightningStorm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(3).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 550;
                    skillButtons.set(9, new AbstractMap.SimpleEntry<>(false, lightningStorm));
                    turnsPlayed++;
                }
            }
        });

        JButton wizardShield = new JButton("Shield");
        wizardShield.setFont(customFont.deriveFont(10f));
        wizardShield.setBounds(263, 770, 80, 20);
        wizardShield.setBackground(buttonSkillsColor);
        wizardShield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(4).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //+2 AC to any chosen hero
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 580;
                    skillButtons.set(10, new AbstractMap.SimpleEntry<>(false, wizardShield));
                    turnsPlayed++;
                }
            }
        });

        JButton wizardCriticalHit = new JButton("Critical Hit");
        wizardCriticalHit.setFont(customFont.deriveFont(10f));
        wizardCriticalHit.setBounds(265, 811, 80, 18);
        wizardCriticalHit.setBackground(buttonSkillsColor);
        wizardCriticalHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(5).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 115;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 620;
                    skillButtons.set(11, new AbstractMap.SimpleEntry<>(false, wizardCriticalHit));
                    turnsPlayed++;
                }
            }
        });

        JButton holyStrike = new JButton("Holy Strike");
        holyStrike.setFont(customFont.deriveFont(10f));
        holyStrike.setBounds(255, 615, 85, 20);
        holyStrike.setBackground(buttonSkillsColor);
        holyStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(0).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 5
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 470;
                    skillButtons.set(12, new AbstractMap.SimpleEntry<>(false, holyStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton blessing = new JButton("Blessing");
        blessing.setFont(customFont.deriveFont(9.4f));
        blessing.setBounds(255, 655, 85, 20);
        blessing.setBackground(buttonSkillsColor);
        blessing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(1).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //reroll dice (maybe u can just subtract 1 from timesRolled?)
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 510;
                    skillButtons.set(13, new AbstractMap.SimpleEntry<>(false, blessing));
                    turnsPlayed++;
                }
            }
        });

        JButton smite = new JButton("Smite");
        smite.setFont(customFont.deriveFont(10f));
        smite.setBounds(255, 695, 85, 20);
        smite.setBackground(buttonSkillsColor);
        smite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(2).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 4
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 550;
                    skillButtons.set(14, new AbstractMap.SimpleEntry<>(false, smite));
                    turnsPlayed++;
                }
            }
        });

        JButton healingHands = new JButton("Healing Hands");
        healingHands.setFont(customFont.deriveFont(10f));
        healingHands.setBounds(255, 735, 85, 20);
        healingHands.setBackground(buttonSkillsColor);
        healingHands.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(3).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //hero HP + 6
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 590;
                    skillButtons.set(15, new AbstractMap.SimpleEntry<>(false, healingHands));
                    turnsPlayed++;
                }
            }
        });

        JButton holyStorm = new JButton("Holy Storm");
        holyStorm.setFont(customFont.deriveFont(10f));
        holyStorm.setBounds(255, 770, 85, 20);
        holyStorm.setBackground(buttonSkillsColor);
        holyStorm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(4).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 630;
                    skillButtons.set(16, new AbstractMap.SimpleEntry<>(false, holyStorm));
                    turnsPlayed++;
                }
            }
        });

        JButton clericShield = new JButton("Critical Hit");
        clericShield.setFont(customFont.deriveFont(10f));
        clericShield.setBounds(265, 812, 85, 18);
        clericShield.setBackground(buttonSkillsColor);
        clericShield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(5).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //+2 AC to any chosen hero
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 180;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 670;
                    skillButtons.set(17, new AbstractMap.SimpleEntry<>(false, clericShield));
                    turnsPlayed++;
                }
            }
        });

        JButton wildStrike = new JButton("Wild Strike");
        wildStrike.setFont(customFont.deriveFont(10f));
        wildStrike.setBounds(255, 620, 85, 20);
        wildStrike.setBackground(buttonSkillsColor);
        wildStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(0).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0)
                {
                    //dragon HP - 5
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 470;
                    skillButtons.set(18, new AbstractMap.SimpleEntry<>(false, wildStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton accurateShot = new JButton("Accurate Shot");
        accurateShot.setFont(customFont.deriveFont(9.4f));
        accurateShot.setBounds(255, 660, 85, 20);
        accurateShot.setBackground(buttonSkillsColor);
        accurateShot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(1).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 4
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 510;
                    skillButtons.set(19, new AbstractMap.SimpleEntry<>(false, accurateShot));
                    turnsPlayed++;
                }
            }
        });

        JButton dualShot = new JButton("Dual Shot");
        dualShot.setFont(customFont.deriveFont(10f));
        dualShot.setBounds(255, 700, 85, 20);
        dualShot.setBackground(buttonSkillsColor);
        dualShot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(2).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 550;
                    skillButtons.set(20, new AbstractMap.SimpleEntry<>(false, dualShot));
                    turnsPlayed++;
                }
            }
        });

        JButton crossfire = new JButton("Crossfire");
        crossfire.setFont(customFont.deriveFont(10f));
        crossfire.setBounds(255, 740, 85, 20);
        crossfire.setBackground(buttonSkillsColor);
        crossfire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(3).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 9
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 590;
                    skillButtons.set(21, new AbstractMap.SimpleEntry<>(false, crossfire));
                    turnsPlayed++;
                }
            }
        });

        JButton pinDown = new JButton("Pin Down");
        pinDown.setFont(customFont.deriveFont(10f));
        pinDown.setBounds(255, 780, 85, 20);
        pinDown.setBackground(buttonSkillsColor);
        pinDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(4).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //-1 AC to any chosen hero
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 630;
                    skillButtons.set(22, new AbstractMap.SimpleEntry<>(false, pinDown));
                    turnsPlayed++;
                }
            }
        });

        JButton rangerCriticalHit = new JButton("Critical Hit");
        rangerCriticalHit.setFont(customFont.deriveFont(10f));
        rangerCriticalHit.setBounds(255, 820, 85, 18);
        rangerCriticalHit.setBackground(buttonSkillsColor);
        rangerCriticalHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(5).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 150;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 670;
                    skillButtons.set(23, new AbstractMap.SimpleEntry<>(false, rangerCriticalHit));
                    turnsPlayed++;
                }
            }
        });

        JButton rogueStrike = new JButton("Strike");
        rogueStrike.setFont(customFont.deriveFont(10f));
        rogueStrike.setBounds(265, 620, 85, 20);
        rogueStrike.setBackground(buttonSkillsColor);
        rogueStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(0).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 5
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 520;
                    skillButtons.set(24, new AbstractMap.SimpleEntry<>(false, rogueStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton stab = new JButton("Stab");
        stab.setFont(customFont.deriveFont(10f));
        stab.setBounds(265, 660, 85, 20);
        stab.setBackground(buttonSkillsColor);
        stab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(1).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 4
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 560;
                    skillButtons.set(25, new AbstractMap.SimpleEntry<>(false, stab));
                    turnsPlayed++;
                }
            }
        });

        JButton flankingStrike = new JButton("Flanking Strike");
        flankingStrike.setFont(customFont.deriveFont(8f));
        flankingStrike.setBounds(265, 695, 85, 20);
        flankingStrike.setBackground(buttonSkillsColor);
        flankingStrike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(2).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 6
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 600;
                    skillButtons.set(26, new AbstractMap.SimpleEntry<>(false, flankingStrike));
                    turnsPlayed++;
                }
            }
        });

        JButton sneakAttack = new JButton("Sneak Attack");
        sneakAttack.setFont(customFont.deriveFont(9f));
        sneakAttack.setBounds(265, 735, 85, 20);
        sneakAttack.setBackground(buttonSkillsColor);
        sneakAttack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(3).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 6
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 630;
                    skillButtons.set(27, new AbstractMap.SimpleEntry<>(false, sneakAttack));
                    turnsPlayed++;
                }
            }
        });

        JButton suddenDeath = new JButton("Sudden Death");
        suddenDeath.setFont(customFont.deriveFont(8f));
        suddenDeath.setBounds(265, 770, 85, 20);
        suddenDeath.setBackground(buttonSkillsColor);
        suddenDeath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(4).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 7
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 670;
                    skillButtons.set(28, new AbstractMap.SimpleEntry<>(false, suddenDeath));
                    turnsPlayed++;
                }
            }
        });

        JButton rogueCriticalHit = new JButton("Critical Hit");
        rogueCriticalHit.setFont(customFont.deriveFont(10f));
        rogueCriticalHit.setBounds(265, 810, 85, 18);
        rogueCriticalHit.setBackground(buttonSkillsColor);
        rogueCriticalHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean state = gameHeroes.get(turnTracker).playerSkills.get(5).checkDiceCombo(diceRolled);
                if (username.equals(turn.getText().substring(6)) && username.equals(characterNameText.getText())
                        && turnsPlayed<3 && state && Integer.parseInt(hitPointsText.getText()) > 0) {
                    //dragon HP - 8
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).xCoordinate = 140;
                    gameHeroes.get(turnTracker).tokens.get(turnsPlayed).yCoordinate = 710;
                    skillButtons.set(29, new AbstractMap.SimpleEntry<>(false, rogueCriticalHit));
                    turnsPlayed++;
                }
            }
        });

        //warrior (0-5)
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, warriorStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, slash));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, smashingBlow));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, savageAttack));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, parry));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, warriorCriticalHit));
        //wizard (6-11)
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, wizardStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, magicBolt));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, firebolt));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, lightningStorm));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, wizardShield));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, wizardCriticalHit));
        //cleric (12-17)
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, holyStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, blessing));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, smite));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, healingHands));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, holyStorm));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, clericShield));
        //ranger (18-23)
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, wildStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, accurateShot));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, dualShot));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, crossfire));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, pinDown));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, rangerCriticalHit));
        //rogue (24-29)
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, rogueStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, stab));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, suddenDeath));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, flankingStrike));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, sneakAttack));
        skillButtons.add(new AbstractMap.SimpleEntry<>(true, rogueCriticalHit));
        for (int i = 0; i< skillButtons.size(); i++) {
            add(skillButtons.get(i).getValue());
            skillButtons.get(i).getValue().setVisible(false);
        }
    }

    public static void updateSkillButtons (List<Map.Entry<Boolean, JButton>> buttons) {
        skillButtons = buttons;
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
            //move later
            dragonTokens.add(ImageIO.read(new File("images/tokenYellow.png")));
            poisonTokens.add(ImageIO.read(new File("images/tokenGreen.png")));
            pinnedTokens.add(ImageIO.read(new File("images/tokenRed.png")));
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
        gameMessages.setText("It is now " + gameHeroes.get(turnNumber).heroName + "'s turn! " + gameHeroes.get(turnNumber).heroName + " has rolled these combinations and has 2 turns remaining.");
    }

    public static void setAccessCode(String num){
        accessCode = num;
    }

    public static String getAccessCode(){
        return accessCode;
    }

    public static void setLevel (int num) {
        level = num;
    }

    public static void setUsername (Hero hero) {
        username = hero.heroName;
    }

    public static void setFields (Hero hero) {
        heroClass = hero.classType;
        currentPlayerSheet.setText(hero.heroName);
        characterNameText.setText(hero.heroName);
        armorClassText.setText(String.valueOf(hero.armorClass));
        hitPointsText.setText(String.valueOf(hero.hitPoints));
        levelText.setText(String.valueOf(hero.level));
        expText.setText(String.valueOf(hero.exp));
        goldText.setText(String.valueOf(hero.gold));
        if (heroClass == 0)
            addWarriorSkillButtons();
        else if (heroClass == 1)
            addWizardSkillButtons();
        else if (heroClass == 2)
            addClericSkillButtons();
        else if (heroClass == 3)
            addRangerSkillButtons();
        else
            addRogueSkillButtons();
    }

    public static void getDice (List<Map.Entry<Boolean, Integer>> dice) {
        diceRolled.clear();
        for (int i = 0; i< dice.size(); i++)
            diceRolled.add(dice.get(i));

        if (instance != null)
            instance.repaint();
    }

    public static void showGameMessage(String text){
        gameMessages.setText(text);
    }
}