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


public class GameTanvi extends JFrame {
    String accessCode;
    boolean host;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;
    private JList<Hero> heroList;
    private JList<Dragon> dragonList;
    private Font customFont;
    private BufferedImage intro, loginBackground, diceFace1, diceFace2, diceFace3, diceFace4, diceFace5, diceFace6,
            background, weapon, prPage1, prPage2, prPage3, prPage4, prPage5, prPage6,
            prPage7, prPage8, prPage9, prPage10, prPage11, prPage12;
    private JList<BufferedImage> playerRulesImages;
    private JList<BufferedImage> dragonSheets;
    private JList<BufferedImage> heroSheets;

    public GameTanvi()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");

        try
        {
            /*
            intro  = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/introScreen.jpg"));
            loginBackground = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/loginScreen.jpg"));
            background = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/backgroundImage.png"));
            diceFace1 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/dice side.png"));
            diceFace2 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/D&D dice_001.png"));
            diceFace3 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/D&D dice_002.png"));
            diceFace4 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/D&D dice_004.png"));
            diceFace5 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/D&D dice_005.png"));
            diceFace6 = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/D&D dice_006.png"));

             */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
                g.drawImage(diceFace1, 550, 120, 100, 100, this);
                g.drawImage(diceFace1, 675, 120, 100, 100, this);
                g.drawImage(diceFace1, 800, 120, 100, 100, this);
                g.drawImage(diceFace1, 925, 120, 100, 100, this);
                g.drawImage(diceFace1, 1050, 120, 100, 100, this);
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
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\K1334989\\Desktop\\GitHub\\Dice_Dragons_New\\images\\Almendra-Regular.ttf")).deriveFont(42f);
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

        //need to change and only show available classes
        //ArrayList<String> availableHeroClasses = new ArrayList<String>();
        //JComboBox<String> heroClassChoice = new JComboBox<String>((ComboBoxModel<String>) availableHeroClasses);
        String[] heroClasses = {"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"};
        JComboBox<String> heroClassChoice = new JComboBox<String>(heroClasses);
        heroClassChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice.setSize(350, 100); //see if you can increase height itself
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
                getContentPane().removeAll();
                getContentPane().add(playingScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton back = new JButton ("Back");
        back.setForeground(new Color(204, 185, 45));
        back.setFont(customFont.deriveFont(30f));
        back.setBounds(5, 730, 130, 50);
        back.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(introScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JLabel customHeroMade = new JLabel("Custom hero has been made");
        customHeroMade.setForeground(Color.red);
        customHeroMade.setFont(customFont.deriveFont(38f));
        customHeroMade.setBounds(600, 500, 700, 100);

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
        numbersOfPlayersChoice.setSize(350, 100); //see if you can increase height itself
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
        heroClassChoice1.setSize(350, 100); //see if you can increase height itself
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
                getContentPane().removeAll();
                getContentPane().add(playingScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton back1 = new JButton ("Back");
        back1.setForeground(new Color(204, 185, 45));
        back1.setFont(customFont.deriveFont(30f));
        back1.setBounds(5, 730, 130, 50);
        back1.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back1);
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(introScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JLabel customHeroMade1 = new JLabel("Custom hero has been made");
        customHeroMade1.setForeground(Color.red);
        customHeroMade1.setFont(customFont.deriveFont(38f));
        customHeroMade1.setBounds(600, 500, 700, 100);

        //custom hero screen
        DefaultTableModel skillsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        skillsModel.addColumn("Skill");
        skillsModel.addColumn("Class(es)");
        skillsModel.addColumn("Symbol 1");
        skillsModel.addColumn("Symbol 2");
        skillsModel.addColumn("Symbol 3");
        skillsModel.addColumn("Symbol 4");
        skillsModel.addColumn("Symbol 5");
        skillsModel.addColumn("Effect");
        JTable skillsTable = new JTable(skillsModel);
        skillsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ListSelectionModel selectionModel = skillsTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (skillsTable.getSelectedRowCount() > 6) {
                    //prevent users from selecting more
                }
            }
        });
        skillsTable.setBounds(10, 35, 500, 680);
        JScrollPane scrollPane = new JScrollPane(skillsTable);
        scrollPane.setBounds(20, 35, 500, 680);

        JLabel diceMessage = new JLabel("Roll the dice 3 times to determine your hit points and armor class.");
        diceMessage.setForeground(Color.white);
        diceMessage.setFont(customFont.deriveFont(25f));
        diceMessage.setBounds(530, 30, 700, 100);

        JButton roll = new JButton ("Roll");
        roll.setForeground(Color.white);
        roll.setFont(customFont.deriveFont(45f));
        roll.setBounds(630, 250, 425, 50);
        roll.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(roll);
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    hostScreen.remove(heroClassChoice1);
                    hostScreen.remove(custom1);
                    hostScreen.add(customHeroMade1);
                }
                else {
                    getContentPane().add(joinScreen);
                    joinScreen.remove(heroClassChoice);
                    joinScreen.remove(custom);
                    joinScreen.add(customHeroMade);
                }
                validate();
                repaint();
                setVisible(true);
            }
        });

        //playing screen
        JTextField turn = new JTextField();
        turn.setForeground(new Color(0, 0, 0));
        turn.setFont(new Font("Times New Roman", Font.BOLD, 60));
        turn.setBounds(450, 30, 500, 100);
        turn.setVisible(true);
        turn.setText("TURN: ");

        JButton rules = new JButton("Rules");
        rules.setFont(new Font("Times New Roman", Font.BOLD, 30));
        rules.setBounds(1000, 30, 120, 100);
        rules.setEnabled(true);
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonFormatting(rules);

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

        JButton guide = new JButton("Dragon Guide");
        guide.setFont(new Font("Times New Roman", Font.BOLD, 30));
        guide.setBounds(1150, 30, 200, 100);
        guide.setEnabled(true);
        guide.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonFormatting(guide);

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
        JTextField pointsText = new JTextField();
        pointsText.setForeground(new Color(0, 0, 0));
        pointsText.setFont(new Font("Arial", Font.BOLD, 30));
        pointsText.setBounds(10, 50, 360, 90);
        pointsText.setVisible(true);
        pointsText.setText("        Hero's Updates  ");
        pointsText.setEditable(false);
        pointsText.setBackground(Color.white);


        JList<String> heroUpdates = new JList<>();

        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pointsScrollBar.setBounds(10, 160, 360, 250);


        JTextField dragonsText = new JTextField();
        dragonsText.setForeground(new Color(0, 0, 0));
        dragonsText.setFont(new Font("Arial", Font.BOLD, 30));
        dragonsText.setBounds(10, 500, 360, 90);
        dragonsText.setVisible(true);
        dragonsText.setText("      Dragon's Updates  ");
        dragonsText.setEditable(false);
        dragonsText.setBackground(Color.white);

        JList<String> dragonUpdates = new JList<>();

        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dragonScrollBar.setBounds(10, 610, 360, 280);

        JList<String> messages = new JList<>();
        JScrollPane chatBox = new JScrollPane(messages);
        chatBox.setBounds(970,700, 400,200);

        JTextField textMessage = new JTextField();
        textMessage.setBounds(970, 1000, 300, 50);
        textMessage.setEditable(true);

        JButton send = new JButton();
        send.setBounds(1300, 1000, 80, 20);
        send.setText("Send");

        send.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String acc = characterName + ": " + textMessage.getText();
                ///will need to implement following method
                //sendMessage(os,name+ ": " + input.getText());
                textMessage.setText("");
            }
        });

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

        customHeroScreen.add(skillsTable);
        customHeroScreen.add(scrollPane);
        customHeroScreen.add(diceMessage);
        customHeroScreen.add(roll);
        customHeroScreen.add(hitPoints);
        customHeroScreen.add(hitPointsText);
        customHeroScreen.add(armorClass);
        customHeroScreen.add(armorClassText);
        customHeroScreen.add(back2);
        customHeroScreen.add(createHero);

        playingScreen.add(turn);
        playingScreen.add(rules);
        playingScreen.add(guide);
        playingScreen.add(pointsText);
        playingScreen.add(pointsScrollBar);
        playingScreen.add(dragonScrollBar);
        playingScreen.add(dragonsText);
        playingScreen.add(chatBox);
        playingScreen.add(textMessage);
        playingScreen.add(send);

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
}
