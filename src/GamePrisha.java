import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;




public class GamePrisha extends JFrame {
    private JPanel introScreen;
    private JPanel joinScreen;
    private JPanel hostScreen;
    private JPanel customHeroScreen;
    private JPanel playingScreen;
    private JPanel dragonGuide;
    private JPanel playerRules;
    private JList<Hero> heroList;
    private JList<Dragon> dragonList;
    private BufferedImage loginScreenTop, loginScreenBottom, background, dragonSheet, heroClassSheet, weapon;
    private JList<BufferedImage> playerRulesImages;
    private BufferedImage prPage1, prPage2, prPage3, prPage4, prPage5, prPage6, prPage7, prPage8, prPage9, prPage10, prPage11, prPage12;



    public GamePrisha()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");


        try
        {
            loginScreenTop = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\loginScreenTop.png"));
            loginScreenBottom = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\loginScreenBottom.png"));
            background = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\backgroundImage.png"));
            dragonSheet = ImageIO.read(new File("C:\\Users\\rishi\\IdeaProjects\\Dice_Dragons_New\\images\\young black dragon.png"));
            prPage1 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\1.png"));
            prPage2 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\2.png"));
            prPage3 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\3.png"));
            prPage4 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\4.png"));
            prPage5 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\5.png"));
            prPage6 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\6.png"));
            prPage7 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\7.png"));
            prPage8 = ImageIO.read(new File("C:\\Users\\K1334989\\IdeaProjects\\Dice_Dragons_New\\rules\\playerRules\\8.png"));


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        introScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginScreenTop, 0, 0, 1400, 500, this);
                g.drawImage(loginScreenBottom, 0, 500, 1400, 500, this);
            }
        };
        introScreen.setLayout(null);
        joinScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginScreenBottom, 0, 0, 1400, 1000, this);
            }
        };
        joinScreen.setLayout(null);
        hostScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginScreenBottom, 0, 0, 1400, 1000, this);
            }
        };
        hostScreen.setLayout(null);
        customHeroScreen = new JPanel();
        customHeroScreen.setLayout(null);
        playingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, 1400, 1000, this);
                g.drawImage(dragonSheet, 970, 140, 400, 550, this);


            }
        };
        playingScreen.setLayout(null);




        //intro screen
        JButton joinGame = new JButton ("Join Game");
        joinGame.setForeground(new Color(204, 185, 45));
        joinGame.setFont(new Font("Times New Roman", Font.BOLD, 75));
        joinGame.setBounds(100, 575, 500, 100);
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
        hostGame.setForeground(new Color(204, 185, 45));
        hostGame.setFont(new Font("Times New Roman", Font.BOLD, 75));
        hostGame.setBounds(750, 575, 500, 100);
        hostGame.setBorderPainted(false);
        buttonFormatting(hostGame);
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(hostScreen);
                validate();
                repaint();
                setVisible(true);
            }
        });


        //join screen
        JLabel message = new JLabel("Ask the host for the access code. It is on the top-right of their screen.");
        message.setForeground(new Color(204, 185, 45));
        message.setFont(new Font("Times New Roman", Font.PLAIN, 45));
        message.setBounds(50, 30, 1300, 100);


        JLabel accessCode = new JLabel("Enter access code:");
        accessCode.setForeground(new Color(204, 185, 45));
        accessCode.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        accessCode.setBounds(100, 200, 500, 100);
        accessCode.setHorizontalAlignment(SwingConstants.RIGHT);


        JLabel characterName = new JLabel ("Enter character name:");
        characterName.setForeground(new Color(204, 185, 45));
        characterName.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        characterName.setBounds(100, 350, 500, 100);
        characterName.setHorizontalAlignment(SwingConstants.RIGHT);


        JLabel heroClass = new JLabel("Choose your hero class:");
        heroClass.setForeground(new Color(204, 185, 45));
        heroClass.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        heroClass.setBounds(100, 500, 500, 100);
        heroClass.setHorizontalAlignment(SwingConstants.RIGHT);


        JTextField accessCodeText = new JTextField();
        accessCodeText.setFont(new Font("Arial", Font.PLAIN, 20));
        accessCodeText.setBounds(700, 200, 350, 75);
        accessCodeText.setEditable(true);
        accessCodeText.setText("");


        JTextField characterNameText = new JTextField();
        characterNameText.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText.setBounds(700, 350, 350, 75);
        characterNameText.setEditable(true);
        characterNameText.setText("");


        //need to change and only show available classes
        //ArrayList<String> availableHeroClasses = new ArrayList<String>();
        //JComboBox<String> heroClassChoice = new JComboBox<String>((ComboBoxModel<String>) availableHeroClasses);
        String[] heroClasses = {"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"};
        JComboBox<String> heroClassChoice = new JComboBox<String>(heroClasses);
        int selection1 = heroClassChoice.getSelectedIndex();
        heroClassChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice.setSize(350, 100); //see if you can increase height itself
        heroClassChoice.setLocation(700, 500);


        JButton custom = new JButton ("Custom");
        custom.setForeground(new Color(204, 185, 45));
        custom.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        custom.setBounds(1100, 525, 155, 50);
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
        beginGame.setForeground(new Color(204, 185, 45));
        beginGame.setFont(new Font("Times New Roman", Font.BOLD, 75));
        beginGame.setBounds(455, 650, 500, 100);
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
        back.setFont(new Font("Times New Roman", Font.BOLD, 30));
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


        //host screen
        JLabel numberOfPlayers = new JLabel("Number of players:");
        numberOfPlayers.setForeground(new Color(204, 185, 45));
        numberOfPlayers.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        numberOfPlayers.setBounds(100, 200, 500, 100);
        numberOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);


        JLabel characterName1 = new JLabel ("Enter character name:");
        characterName1.setForeground(new Color(204, 185, 45));
        characterName1.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        characterName1.setBounds(100, 350, 500, 100);
        characterName1.setHorizontalAlignment(SwingConstants.RIGHT);


        JLabel heroClass1 = new JLabel("Choose your hero class:");
        heroClass1.setForeground(new Color(204, 185, 45));
        heroClass1.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        heroClass1.setBounds(100, 500, 500, 100);
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
        numbersOfPlayersChoice.setSize(350, 75); //see if you can increase height itself
        numbersOfPlayersChoice.setLocation(700, 200);


        JTextField characterNameText1 = new JTextField();
        characterNameText1.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText1.setBounds(700, 350, 350, 75);
        characterNameText1.setEditable(true);
        characterNameText1.setText("");


        String[] heroClasses1 = {"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"};
        JComboBox<String> heroClassChoice1 = new JComboBox<String>(heroClasses1);
        int selection2 = heroClassChoice1.getSelectedIndex();
        heroClassChoice1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice1.setSize(350, 100); //see if you can increase height itself
        heroClassChoice1.setLocation(700, 500);


        JButton custom1 = new JButton ("Custom");
        custom1.setForeground(new Color(204, 185, 45));
        custom1.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        custom1.setBounds(1100, 525, 155, 50);
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
        createGame.setForeground(new Color(204, 185, 45));
        createGame.setFont(new Font("Times New Roman", Font.BOLD, 75));
        createGame.setBounds(455, 650, 500, 100);
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
        back1.setFont(new Font("Times New Roman", Font.BOLD, 30));
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


        ////playing screen
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
        pointsText.setBounds(20, 30, 360, 100);
        pointsText.setVisible(true);
        pointsText.setText("        Hero's Updates  ");


        JList<String> heroUpdates = new JList<>();


        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pointsScrollBar.setBounds(20, 150, 360, 300);


        JTextField dragonsText = new JTextField();
        dragonsText.setForeground(new Color(0, 0, 0));
        dragonsText.setFont(new Font("Arial", Font.BOLD, 30));
        dragonsText.setBounds(20, 490, 360, 100);
        dragonsText.setVisible(true);
        dragonsText.setText("      Dragon's Updates  ");


        JList<String> dragonUpdates = new JList<>();


        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dragonScrollBar.setBounds(20, 620, 360, 300);


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

        //custom hero screen


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
        setSize(1400, 1000);
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

