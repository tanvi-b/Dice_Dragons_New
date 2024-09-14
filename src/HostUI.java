import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HostUI extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BufferedImage loginBackground;
    private Font customFont;
    private Font customBoldFont;

    public HostUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {
        JLabel numberOfPlayers = new JLabel("Number of players:");
        numberOfPlayers.setForeground(Color.black);
        numberOfPlayers.setFont(customFont.deriveFont(55f));
        numberOfPlayers.setBounds(80, 200, 500, 100);
        numberOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel characterName1 = new JLabel("Enter character name:");
        characterName1.setForeground(Color.black);
        characterName1.setFont(customFont.deriveFont(55f));
        characterName1.setBounds(80, 350, 500, 100);
        characterName1.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass1 = new JLabel("Choose your hero class:");
        heroClass1.setForeground(Color.black);
        heroClass1.setFont(customFont.deriveFont(50f));
        heroClass1.setBounds(80, 500, 500, 100);
        heroClass1.setHorizontalAlignment(SwingConstants.RIGHT);

        Integer[] numbers = {1, 2, 3, 4, 5};
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

        JButton custom1 = new JButton("Custom");
        custom1.setForeground(new Color(204, 185, 45));
        custom1.setFont(customFont.deriveFont(35f));
        custom1.setBounds(1000, 525, 155, 50);
        custom1.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(custom1);
        custom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(mainPanel, "CustomHeroScreen");
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
                //send message to server and receive access code
                //get number of players, class type, and username
                if (numbersOfPlayersChoice.getSelectedIndex() != -1 && !characterNameText1.getText().equals("") &&
                        heroClassChoice1.getSelectedIndex() != -1) {
                    //call game.playerHost;
                    cardLayout.show(mainPanel, "LobbyScreen");
                }
            }
        });

        JLabel customHeroMade1 = new JLabel("Custom hero has been made");
        customHeroMade1.setForeground(Color.red);
        customHeroMade1.setFont(customFont.deriveFont(38f));
        customHeroMade1.setBounds(600, 500, 700, 100);
        customHeroMade1.setVisible(false);

        JButton back1 = new JButton("Back");
        back1.setForeground(new Color(204, 185, 45));
        back1.setFont(customFont.deriveFont(30f));
        back1.setBounds(5, 730, 130, 50);
        back1.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back1);
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heroClassChoice1.setSelectedIndex(-1);
                numbersOfPlayersChoice.setSelectedIndex(-1);
                characterNameText1.setText("");
                heroClassChoice1.setVisible(true);
                custom1.setVisible(true);
                customHeroMade1.setVisible(false);
                cardLayout.show(mainPanel, "IntroScreen");
            }
        });

        add(numberOfPlayers);
        add(numbersOfPlayersChoice);
        numbersOfPlayersChoice.setSelectedIndex(-1);
        add(characterName1);
        add(characterNameText1);
        add(heroClass1);
        add(heroClassChoice1);
        heroClassChoice1.setSelectedIndex(-1);
        add(custom1);
        add(createGame);
        add(back1);
        add(customHeroMade1);
        customHeroMade1.setVisible(false);
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
}
