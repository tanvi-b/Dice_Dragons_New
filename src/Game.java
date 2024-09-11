import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

//dragon face is on dice side 5

public class Game extends JFrame {
    String accessCode;
    boolean host;
    boolean rollClicked;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;
    private Font customFont;
    private ArrayList<BufferedImage> diceFaces;
    private ArrayList<BufferedImage> playerRules;
    private ArrayList<BufferedImage> dragonGuide;
    private ArrayList<BufferedImage> dragonSheets;
    private ArrayList<BufferedImage> heroSheets;
    private BufferedImage intro, loginBackground, background;
    //playing tokens
    //circular tokens

    public Game()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");
        rollClicked = false;
        heroes = new ArrayList<>();
        diceFaces = new ArrayList<>();
        playerRules = new ArrayList<>();
        dragonGuide = new ArrayList<>();
        dragonSheets = new ArrayList<>();
        heroSheets = new ArrayList<>();
        readImages();
        JPanel introScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(intro, 0, 0, 1200, 1000, this);
            }
        };
        introScreen.setLayout(null);

        JPanel joinScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
            }
        };
        joinScreen.setLayout(null);

        JPanel hostScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
            }
        };
        hostScreen.setLayout(null);

        JPanel customHeroScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginBackground, 0, 0, 1400, 1000, this);
                if (rollClicked==false) {
                    g.drawImage(diceFaces.get(0), 550, 120, 100, 100, this);
                    g.drawImage(diceFaces.get(0), 675, 120, 100, 100, this);
                    g.drawImage(diceFaces.get(0), 800, 120, 100, 100, this);
                    g.drawImage(diceFaces.get(0), 925, 120, 100, 100, this);
                    g.drawImage(diceFaces.get(0), 1050, 120, 100, 100, this);
                }
            }
        };
        customHeroScreen.setLayout(null);

        JPanel playingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, 1400, 1000, this);
            }
        };
        playingScreen.setLayout(null);

        JPanel dragonGuide = new JPanel();
        dragonGuide.setLayout(null);

        JPanel playerRules = new JPanel();
        playerRules.setLayout(null);

        //intro screen
        host = false;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Regular.ttf")).deriveFont(42f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JButton joinGame = new JButton ("Join Game");
        joinGame.setForeground(Color.black);
        joinGame.setFont(customFont);
        joinGame.setBounds(10, 600, 425, 50);
        joinGame.setBorderPainted(false);
        buttonFormatting(joinGame);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(joinScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton hostGame = new JButton ("Host Game");
        hostGame.setForeground(Color.black);
        hostGame.setFont(customFont);
        hostGame.setBounds(10, 675, 425, 50);
        hostGame.setBorderPainted(false);
        buttonFormatting(hostGame);
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                host = true;
                getContentPane().removeAll();
                getContentPane().add(hostScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        //join screen
        JLabel message = new JLabel("Ask the host for the access code. It is on the top-right of their screen.");
        message.setForeground(Color.white);
        message.setFont(customFont.deriveFont(40f));
        message.setBounds(50, 30, 1300, 100);

        JLabel accessCode = new JLabel("Enter access code:");
        accessCode.setForeground(Color.black);
        accessCode.setFont(customFont.deriveFont(55f));
        accessCode.setBounds(80, 200, 500, 100);
        accessCode.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel characterName = new JLabel ("Enter character name:");
        characterName.setForeground(Color.black);
        characterName.setFont(customFont.deriveFont(55f));
        characterName.setBounds(80, 350, 500, 100);
        characterName.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass = new JLabel("Choose your hero class:");
        heroClass.setForeground(Color.black);
        heroClass.setFont(customFont.deriveFont(50f));
        heroClass.setBounds(80, 500, 500, 100);
        heroClass.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField accessCodeText = new JTextField();
        accessCodeText.setFont(new Font("Arial", Font.PLAIN, 20));
        accessCodeText.setBounds(600, 200, 350, 75);
        accessCodeText.setEditable(true);
        accessCodeText.setText("");

        JTextField characterNameText = new JTextField();
        characterNameText.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText.setBounds(600, 350, 350, 75);
        characterNameText.setEditable(true);
        characterNameText.setText("");

        ArrayList<String> availableHeroClasses = new ArrayList<String>();
        availableHeroClasses.add("Warrior");
        availableHeroClasses.add("Wizard");
        availableHeroClasses.add("Cleric");
        availableHeroClasses.add("Ranger");
        availableHeroClasses.add("Rogue");
        for (int i = 4; i>=0; i--)
        {
            for (int j = 0; j<heroes.size(); j++)
            {
                if (heroes.get(j).classType==i)
                    availableHeroClasses.remove(i);
            }
        }
        JComboBox<String> heroClassChoice = new JComboBox<>(availableHeroClasses.toArray(new String[0]));
        heroClassChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice.setSize(350, 75);
        heroClassChoice.setLocation(600, 500);

        JButton custom = new JButton ("Custom");
        custom.setForeground(new Color(204, 185, 45));
        custom.setFont(customFont.deriveFont(35f));
        custom.setBounds(1000, 525, 155, 50);
        custom.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(custom);
        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(customHeroScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton beginGame = new JButton ("Begin Game");
        beginGame.setForeground(Color.white);
        beginGame.setFont(customFont.deriveFont(75f));
        beginGame.setBounds(400, 650, 500, 100);
        beginGame.setBorderPainted(false);
        buttonFormatting(beginGame);
        beginGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!accessCodeText.getText().equals("") && !characterNameText.getText().equals("") &&
                        heroClassChoice.getSelectedIndex()!=-1) {
                    getContentPane().removeAll();
                    getContentPane().add(playingScreen);
                    validate();
                    repaint();
                    setVisible(true);
                }
            }
        });

        JLabel customHeroMade = new JLabel("Custom hero has been made");
        customHeroMade.setForeground(Color.red);
        customHeroMade.setFont(customFont.deriveFont(38f));
        customHeroMade.setBounds(600, 500, 700, 100);

        JButton back = new JButton ("Back");
        back.setForeground(new Color(204, 185, 45));
        back.setFont(customFont.deriveFont(30f));
        back.setBounds(5, 730, 130, 50);
        back.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heroClassChoice.setSelectedIndex(-1);
                heroClassChoice.setVisible(true);
                custom.setVisible(true);
                customHeroMade.setVisible(false);
                getContentPane().removeAll();
                getContentPane().add(introScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        //host screen
        JLabel numberOfPlayers = new JLabel("Number of players:");
        numberOfPlayers.setForeground(Color.black);
        numberOfPlayers.setFont(customFont.deriveFont(55f));
        numberOfPlayers.setBounds(80, 200, 500, 100);
        numberOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel characterName1 = new JLabel ("Enter character name:");
        characterName1.setForeground(Color.black);
        characterName1.setFont(customFont.deriveFont(55f));
        characterName1.setBounds(80, 350, 500, 100);
        characterName1.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass1 = new JLabel("Choose your hero class:");
        heroClass1.setForeground(Color.black);
        heroClass1.setFont(customFont.deriveFont(50f));
        heroClass1.setBounds(80, 500, 500, 100);
        heroClass1.setHorizontalAlignment(SwingConstants.RIGHT);

        Integer[] numbers = {1,2,3,4,5};
        JComboBox<Integer> numbersOfPlayersChoice = new JComboBox<Integer>(numbers);
        numbersOfPlayersChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        numbersOfPlayersChoice.setSize(350, 75);
        numbersOfPlayersChoice.setLocation(600, 200);

        JTextField characterNameText1 = new JTextField();
        characterNameText1.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText1.setBounds(600, 350, 350, 75);
        characterNameText1.setEditable(true);
        characterNameText1.setText("");

        String[] heroClasses1 = {"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"};
        JComboBox<String> heroClassChoice1 = new JComboBox<String>(heroClasses1);
        heroClassChoice1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice1.setSize(350, 75);
        heroClassChoice1.setLocation(600, 500);

        JButton custom1 = new JButton ("Custom");
        custom1.setForeground(new Color(204, 185, 45));
        custom1.setFont(customFont.deriveFont(35f));
        custom1.setBounds(1000, 525, 155, 50);
        custom1.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(custom1);
        custom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(customHeroScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton createGame = new JButton("Create Game");
        createGame.setForeground(Color.white);
        createGame.setFont(customFont.deriveFont(75f));
        createGame.setBounds(400, 650, 500, 100);
        createGame.setBorderPainted(false);
        buttonFormatting(createGame);
        createGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numbersOfPlayersChoice.getSelectedIndex()!=-1 && !characterNameText1.getText().equals("") &&
                        heroClassChoice1.getSelectedIndex()!=-1) {
                    getContentPane().removeAll();
                    getContentPane().add(playingScreen);
                    validate();
                    repaint();
                    setVisible(true);
                }
            }
        });

        JLabel customHeroMade1 = new JLabel("Custom hero has been made");
        customHeroMade1.setForeground(Color.red);
        customHeroMade1.setFont(customFont.deriveFont(38f));
        customHeroMade1.setBounds(600, 500, 700, 100);

        JButton back1 = new JButton ("Back");
        back1.setForeground(new Color(204, 185, 45));
        back1.setFont(customFont.deriveFont(30f));
        back1.setBounds(5, 730, 130, 50);
        back1.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back1);
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heroClassChoice1.setSelectedIndex(-1);
                heroClassChoice1.setVisible(true);
                custom1.setVisible(true);
                customHeroMade1.setVisible(false);
                getContentPane().removeAll();
                getContentPane().add(introScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        //custom hero screen
        DefaultTableModel skillsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        skillsModel.addColumn("Skill");
        skillsModel.addColumn("Class(es)");
        skillsModel.addColumn("Effect");
        JTable skillsTable = new JTable(skillsModel);
        skillsTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        skillsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane skillsScrollPane = new JScrollPane(skillsTable);
        skillsScrollPane.setBounds(20, 35, 500, 680);

        ListSelectionModel selectionModel = skillsTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (skillsTable.getSelectedRowCount() > 6) {
                    //prevent users from selecting more
                }
            }
        });
        skillsTable.setBounds(20, 35, 500, 680);
        skillsTable.setOpaque(true);

        JLabel diceMessage = new JLabel("Roll the dice 3 times to determine your hit points and armor class.");
        diceMessage.setForeground(Color.white);
        diceMessage.setFont(customFont.deriveFont(25f));
        diceMessage.setBounds(530, 30, 700, 100);

        JLabel rollingDice = new JLabel("Rolling dice...");
        rollingDice.setHorizontalAlignment(SwingConstants.CENTER);
        rollingDice.setForeground(Color.red);
        rollingDice.setFont(customFont.deriveFont(45f));
        rollingDice.setBounds(600, 120, 500, 75);
        rollingDice.setOpaque(true);

        JButton roll = new JButton ("Roll");
        roll.setForeground(Color.white);
        roll.setFont(customFont.deriveFont(45f));
        roll.setBounds(630, 250, 425, 50);
        roll.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(roll);
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollClicked = true;
                rollingDice.setVisible(true);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JLabel hitPoints = new JLabel("Hit Points:");
        hitPoints.setForeground(Color.white);
        hitPoints.setFont(customFont.deriveFont(45f));
        hitPoints.setBounds(550, 375, 700, 100);

        JTextField hitPointsText = new JTextField();
        hitPointsText.setFont(new Font("Arial", Font.PLAIN, 20));
        hitPointsText.setBounds(800, 375, 350, 75);
        hitPointsText.setEditable(false);
        hitPointsText.setText("calculate");

        JLabel armorClass = new JLabel("Armor Class:");
        armorClass.setForeground(Color.white);
        armorClass.setFont(customFont.deriveFont(45f));
        armorClass.setBounds(550, 510, 700, 100);

        JTextField armorClassText = new JTextField();
        armorClassText.setFont(new Font("Arial", Font.PLAIN, 20));
        armorClassText.setBounds(800, 510, 350, 75);
        armorClassText.setEditable(false);
        armorClassText.setText("calculate");

        JButton back2 = new JButton ("Back");
        back2.setForeground(new Color(204, 185, 45));
        back2.setFont(customFont.deriveFont(30f));
        back2.setBounds(5, 730, 130, 50);
        back2.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back2);
        back2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                if (host)
                    getContentPane().add(hostScreen);
                else
                    getContentPane().add(joinScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton createHero = new JButton("Create Hero");
        createHero.setForeground(Color.white);
        createHero.setFont(customFont.deriveFont(75f));
        createHero.setBounds(600, 620, 500, 100);
        createHero.setBorderPainted(false);
        buttonFormatting(createHero);
        createHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                if (host) {
                    getContentPane().add(hostScreen);
                    heroClassChoice1.setSelectedIndex(0);
                    heroClassChoice1.setVisible(false);
                    custom1.setVisible(false);
                    customHeroMade1.setVisible(true);
                }
                else {
                    getContentPane().add(joinScreen);
                    heroClassChoice.setSelectedIndex(0);
                    heroClassChoice.setVisible(false);
                    custom.setVisible(false);
                    customHeroMade.setVisible(true);
                }
                validate();
                repaint();
                setVisible(true);
            }
        });

        //playing screen
        JLabel turn = new JLabel();
        turn.setFont(customFont.deriveFont(60f));
        turn.setBounds(450, 75, 500, 90);
        turn.setText("Turn: ");
        turn.setOpaque(true);

        JButton rules = new JButton("Rules");
        rules.setFont(customFont.deriveFont(30f));
        rules.setBounds(970, 80, 80, 80);
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        rules.setOpaque(true);

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(playerRules);
                validate();
                repaint();
                setVisible(true);
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
                getContentPane().removeAll();
                getContentPane().add(dragonGuide);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JLabel pointsText = new JLabel();
        pointsText.setFont(customFont.deriveFont(30f));
        pointsText.setBounds(10, 75, 360, 95);
        pointsText.setText("Hero's Updates");
        pointsText.setHorizontalAlignment(JLabel.CENTER);
        pointsText.setOpaque(true);

        JList<String> heroUpdates = new JList<>();

        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pointsScrollBar.setBounds(10, 190, 360, 280);

        JLabel dragonsText = new JLabel();
        dragonsText.setFont(customFont.deriveFont(30f));
        dragonsText.setBounds(10, 500, 360, 95);
        dragonsText.setText("Dragon's Updates");
        dragonsText.setHorizontalAlignment(JLabel.CENTER);
        dragonsText.setOpaque(true);

        JList<String> dragonUpdates = new JList<>();

        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dragonScrollBar.setBounds(10, 610, 360, 280);

        JLabel accessCodeShow = new JLabel();
        accessCodeShow.setFont(customFont.deriveFont(20f));
        accessCodeShow.setBounds(900,10,250,50);
        accessCodeShow.setText("Access Code: ");
        accessCodeShow.setOpaque(true);
        accessCodeShow.setBackground(Color.black);
        accessCodeShow.setForeground(Color.ORANGE);

        JList<String> messages = new JList<>();
        JScrollPane chatBox = new JScrollPane(messages);
        chatBox.setBounds(870,720, 310,150);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(870, 880, 240, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton();
        send.setFont(customFont.deriveFont(20f));
        send.setBounds(1115, 880, 65, 65);
        send.setText("Send");

        send.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String acc = characterName + ": " + messageText.getText();
                ///will need to implement following method
                //sendMessage(os,name+ ": " + input.getText());
                messageText.setText("");
            }
        });

        //player images and weapons will be decided further through threading

        //attempt to do unique playersheet but not working with draw
        // will attempt threading first and then come back here

       /*BufferedImage warriorSheet, wizardSheet, clericSheet, rangerSheet, rogueSheet;

       try {
           warriorSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\warrior.png"));
           wizardSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\wizard.png"));
           clericSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\cleric.png"));
           rangerSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\ranger.png"));
           rogueSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\rogue.png"));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       protected void paintComponent(Graphics g){
       super.paintComponent(g);
       if (selection1 == 0 || selection2 == 0) {
           g.drawImage(warriorSheet, 100, 100, 200, 300, this);
       }
       if (selection1 == 1 || selection2 == 1) {
           g.drawImage(wizardSheet, 100, 100, 200, 300, this);
       }
       if (selection1 == 2 || selection2 == 2) {
           g.drawImage(clericSheet, 100, 100, 200, 300, this);
       }
       if (selection1 == 3 || selection2 == 3) {
           g.drawImage(rangerSheet, 100, 100, 200, 300, this);
       }
       if (selection1 == 4 || selection2 == 4) {
           g.drawImage(rogueSheet, 100, 100, 200, 300, this);
       }
   }
        */

        introScreen.add(joinGame);
        introScreen.add(hostGame);

        joinScreen.add(message);
        joinScreen.add(accessCode);
        joinScreen.add(accessCodeText);
        joinScreen.add(characterName);
        joinScreen.add(characterNameText);
        joinScreen.add(heroClass);
        joinScreen.add(heroClassChoice);
        heroClassChoice.setSelectedIndex(-1);
        joinScreen.add(custom);
        joinScreen.add(beginGame);
        joinScreen.add(back);
        joinScreen.add(customHeroMade);
        customHeroMade.setVisible(false);

        hostScreen.add(numberOfPlayers);
        hostScreen.add(numbersOfPlayersChoice);
        numbersOfPlayersChoice.setSelectedIndex(-1);
        hostScreen.add(characterName1);
        hostScreen.add(characterNameText1);
        hostScreen.add(heroClass1);
        hostScreen.add(heroClassChoice1);
        heroClassChoice1.setSelectedIndex(-1);
        hostScreen.add(custom1);
        hostScreen.add(createGame);
        hostScreen.add(back1);
        hostScreen.add(customHeroMade1);
        customHeroMade1.setVisible(false);

        customHeroScreen.add(skillsScrollPane);
        customHeroScreen.add(diceMessage);
        customHeroScreen.add(roll);
        customHeroScreen.add(hitPoints);
        customHeroScreen.add(hitPointsText);
        customHeroScreen.add(armorClass);
        customHeroScreen.add(armorClassText);
        customHeroScreen.add(back2);
        customHeroScreen.add(createHero);
        customHeroScreen.add(rollingDice);
        rollingDice.setVisible(false);

        playingScreen.add(turn);
        playingScreen.add(rules);
        playingScreen.add(guide);
        playingScreen.add(pointsText);
        playingScreen.add(pointsScrollBar);
        playingScreen.add(dragonScrollBar);
        playingScreen.add(dragonsText);
        playingScreen.add(chatBox);
        playingScreen.add(messageText);
        playingScreen.add(send);
        playingScreen.add(accessCodeShow);

        getContentPane().add(introScreen);
        setVisible(true);
        setSize(1200, 1000);
        setResizable(false);
    }

    public void buttonFormatting (JButton button)
    {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setVisible(true);
    }

    public void readImages()
    {
        try
        {
            intro = ImageIO.read(new File("images/introScreen.jpg"));
            loginBackground = ImageIO.read(new File("images/loginScreen.jpg"));
            background = ImageIO.read(new File("images/backgroundImage.png"));
            diceFaces.add(ImageIO.read(new File("images/dice side.png")));
            diceFaces.add(ImageIO.read(new File("images/D&D dice_001.png")));
            diceFaces.add(ImageIO.read(new File("images/D&D dice_002.png")));
            diceFaces.add(ImageIO.read(new File("images/D&D dice_004.png")));
            diceFaces.add(ImageIO.read(new File("images/D&D dice_005.png")));
            diceFaces.add(ImageIO.read(new File("images/D&D dice_006.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/1.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/2.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/3.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/4.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/5.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/6.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/7.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/8.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/9.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/page10.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/page11.png")));
            playerRules.add(ImageIO.read(new File("rules/playerRules/page12.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-01.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-02.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-03.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-04.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-05.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-07.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-08.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-09.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-10.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-11.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-12.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-13.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-14.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-15.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-16.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-17.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-18.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-19.png")));
            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-20.png")));
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
