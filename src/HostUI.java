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
import java.io.ObjectOutputStream;

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

        JLabel characterName = new JLabel("Enter character name:");
        characterName.setForeground(Color.black);
        characterName.setFont(customFont.deriveFont(55f));
        characterName.setBounds(80, 350, 500, 100);
        characterName.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel heroClass = new JLabel("Choose your hero class:");
        heroClass.setForeground(Color.black);
        heroClass.setFont(customFont.deriveFont(50f));
        heroClass.setBounds(80, 500, 500, 100);
        heroClass.setHorizontalAlignment(SwingConstants.RIGHT);

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

        JTextField characterNameText = new JTextField();
        characterNameText.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText.setBounds(600, 350, 350, 75);

        String[] heroClasses = {"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"};
        JComboBox<String> heroClassChoice = new JComboBox<String>(heroClasses);
        heroClassChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                }
            }
        });
        heroClassChoice.setSize(350, 75);
        heroClassChoice.setLocation(600, 500);

        JButton custom = new JButton("Custom");
        custom.setForeground(new Color(204, 185, 45));
        custom.setFont(customFont.deriveFont(35f));
        custom.setBounds(1000, 525, 155, 50);
        custom.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(custom);
        custom.addActionListener(new ActionListener() {
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
                if (numbersOfPlayersChoice.getSelectedIndex() != -1 && !characterNameText.getText().equals("") &&
                        heroClassChoice.getSelectedIndex() != -1) {
                    String info = numbersOfPlayersChoice.getSelectedIndex() + "," + characterNameText.getText() + "," + heroClassChoice.getSelectedIndex();
                    LobbyUI.game.playerHost(LobbyUI.game.getOs(), info, characterNameText.getText());
                    cardLayout.show(mainPanel, "LobbyScreen");
                }
            }
        });

        JLabel customHeroMade = new JLabel("Custom hero has been made");
        customHeroMade.setForeground(Color.red);
        customHeroMade.setFont(customFont.deriveFont(38f));
        customHeroMade.setBounds(600, 500, 700, 100);
        customHeroMade.setVisible(false);

        JButton back = new JButton("Back");
        back.setForeground(new Color(204, 185, 45));
        back.setFont(customFont.deriveFont(30f));
        back.setBounds(5, 730, 130, 50);
        back.setBorder(BorderFactory.createLineBorder(Color.white));
        buttonFormatting(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heroClassChoice.setSelectedIndex(-1);
                numbersOfPlayersChoice.setSelectedIndex(-1);
                characterNameText.setText("");
                heroClassChoice.setVisible(true);
                custom.setVisible(true);
                customHeroMade.setVisible(false);
                cardLayout.show(mainPanel, "IntroScreen");
            }
        });

        add(numberOfPlayers);
        add(numbersOfPlayersChoice);
        numbersOfPlayersChoice.setSelectedIndex(-1);
        add(characterName);
        add(characterNameText);
        add(heroClass);
        add(heroClassChoice);
        heroClassChoice.setSelectedIndex(-1);
        add(custom);
        add(createGame);
        add(back);
        add(customHeroMade);
        customHeroMade.setVisible(false);
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
