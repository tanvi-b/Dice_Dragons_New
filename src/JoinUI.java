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
import java.util.ArrayList;

public class JoinUI extends JPanel {
    Game game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BufferedImage loginBackground;
    private Font customFont;
    private Font customBoldFont;

    public JoinUI(CardLayout cardLayout, JPanel mainPanel) {
        this.game = game; //access game object from game ui
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {
        JLabel message = new JLabel("Ask the host for the access code. It is on the top-right of their screen.");
        message.setForeground(Color.white);
        message.setFont(customFont.deriveFont(40f));
        message.setBounds(50, 30, 1300, 100);

        JLabel accessCode = new JLabel("Enter access code:");
        accessCode.setForeground(Color.black);
        accessCode.setFont(customFont.deriveFont(55f));
        accessCode.setBounds(80, 200, 500, 100);
        accessCode.setHorizontalAlignment(SwingConstants.RIGHT);

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

        JTextField accessCodeText = new JTextField();
        accessCodeText.setFont(new Font("Arial", Font.PLAIN, 20));
        accessCodeText.setBounds(600, 200, 350, 75);
        accessCodeText.setEditable(true);

        JLabel invalidAccessCode = new JLabel("Invalid access code");
        invalidAccessCode.setHorizontalAlignment(SwingConstants.CENTER);
        invalidAccessCode.setForeground(Color.red);
        invalidAccessCode.setFont(customFont.deriveFont(20f));
        invalidAccessCode.setBounds(950, 213, 245, 50);
        invalidAccessCode.setOpaque(true);

        JTextField characterNameText = new JTextField();
        characterNameText.setFont(new Font("Arial", Font.PLAIN, 20));
        characterNameText.setBounds(600, 350, 350, 75);
        characterNameText.setEditable(true);

        JLabel duplicateName = new JLabel("Name has already been taken");
        duplicateName.setHorizontalAlignment(SwingConstants.CENTER);
        duplicateName.setForeground(Color.red);
        duplicateName.setFont(customFont.deriveFont(20f));
        duplicateName.setBounds(950, 360, 245, 50);
        duplicateName.setOpaque(true);

        JComboBox<String> heroClassChoice = new JComboBox<>(new String[]{"Warrior", "Wizard", "Cleric", "Ranger", "Rogue"});
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
                removeAll();
                // cardLayout.show(mainPanel, "CustomHeroScreen");
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
                //send message to server and validate input
                //if access code is not valid display jlabel
                //if name is duplicate display jlabel
                if (!accessCodeText.getText().equals("") && !characterNameText.getText().equals("") &&
                        heroClassChoice.getSelectedIndex()!=-1) {
                    removeAll();
                    //cardLayout.show(main Panel, "LobbyScreen")
                    validate();
                    repaint();
                    setVisible(true);
                }
            }
        });

        //jlabel saying max players reached or once game starts, make the access code invalid?

        JLabel customHeroMade = new JLabel("Custom hero has been made");
        customHeroMade.setForeground(Color.red);
        customHeroMade.setFont(customFont.deriveFont(38f));
        customHeroMade.setBounds(600, 500, 700, 100);

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
                accessCodeText.setText("");
                characterNameText.setText("");
                heroClassChoice.setVisible(true);
                custom.setVisible(true);
                customHeroMade.setVisible(false);
                cardLayout.show(mainPanel, "IntroScreen");
            }
        });

        add(message);
        add(accessCode);
        add(accessCodeText);
        add(characterName);
        add(characterNameText);
        add(heroClass);
        add(heroClassChoice);
        heroClassChoice.setSelectedIndex(-1);
        add(custom);
        add(beginGame);
        add(back);
        add(customHeroMade);
        add(invalidAccessCode);
        add(duplicateName);
        customHeroMade.setVisible(false);
        invalidAccessCode.setVisible(false);
        duplicateName.setVisible(false);
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