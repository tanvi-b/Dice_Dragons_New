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
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class GameTanvi extends JFrame {
    String accessCode;
    ArrayList<Hero> heroes;
    ArrayList<Dragon> dragons;
    private JList<Hero> heroList;
    private JList<Dragon> dragonList;
    private Font customFont;
    private BufferedImage loginScreenTop, loginScreenBottom, intro, loginScreen;

    public GameTanvi()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");

        try
        {
            loginScreenTop = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/loginScreenTop.png"));
            loginScreenBottom = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/loginScreenBottom.png"));
            intro  = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/introScreen.jpg"));
            loginScreen = ImageIO.read(new File("/Users/tanvibhattad/Downloads/Dice-and-Dragons-Board-Game-main/images/loginScreen.png"));
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
                g.drawImage(loginScreen, 0, 0, 1200, 1000, this);
            }
        };
        joinScreen.setLayout(null);
        JPanel hostScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginScreen, 0, 0, 1200, 1000, this);
            }
        };
        hostScreen.setLayout(null);
        JPanel customHeroScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginScreenBottom, 0, 0, 1400, 1000, this);
            }
        };
        customHeroScreen.setLayout(null);
        JPanel playingScreen = new JPanel();
        playingScreen.setLayout(null);

        //intro screen
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/tanvibhattad/Downloads/Almendra/Almendra-Regular.ttf")).deriveFont(42f);
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
        message.setFont(customFont.deriveFont(40f));
        message.setBounds(50, 30, 1300, 100);

        JLabel accessCode = new JLabel("Enter access code:");
        accessCode.setForeground(new Color(204, 185, 45));
        accessCode.setFont(customFont.deriveFont(55f));
        accessCode.setBounds(80, 200, 500, 100);
        accessCode.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel characterName = new JLabel ("Enter character name:");
        characterName.setForeground(new Color(204, 185, 45));
        characterName.setFont(customFont.deriveFont(55f));
        characterName.setBounds(80, 350, 500, 100);
        characterName.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass = new JLabel("Choose your hero class:");
        heroClass.setForeground(new Color(204, 185, 45));
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
        beginGame.setForeground(new Color(204, 185, 45));
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

        //host screen
        JLabel numberOfPlayers = new JLabel("Number of players:");
        numberOfPlayers.setForeground(new Color(204, 185, 45));
        numberOfPlayers.setFont(customFont.deriveFont(55f));
        numberOfPlayers.setBounds(80, 200, 500, 100);
        numberOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel characterName1 = new JLabel ("Enter character name:");
        characterName1.setForeground(new Color(204, 185, 45));
        characterName1.setFont(customFont.deriveFont(55f));
        characterName1.setBounds(80, 350, 500, 100);
        characterName1.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass1 = new JLabel("Choose your hero class:");
        heroClass1.setForeground(new Color(204, 185, 45));
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
        createGame.setForeground(new Color(204, 185, 45));
        createGame.setFont(customFont.deriveFont(75f));
        createGame.setBounds(400, 650, 500, 100);
        createGame.setBorderPainted(false);
        buttonFormatting(createGame);

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
