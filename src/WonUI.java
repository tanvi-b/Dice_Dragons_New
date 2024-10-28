import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WonUI extends JPanel {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private Font customFont;
    private Font customBoldFont;
    private BufferedImage background;

    public WonUI(CardLayout cardLayout, JPanel mainPanel)
    {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
    }

    private void addComponents()
    {
        JLabel successfulHunt = new JLabel ("YOUR HUNTS HAVE BEEN SUCCESSFUL");
        successfulHunt.setFont(customBoldFont.deriveFont(40f));
        successfulHunt.setForeground(new Color(145, 0, 0));
        successfulHunt.setBounds(300, 10, 700, 50);
        successfulHunt.setOpaque(false);

//        JButton homeScreen = new JButton ("New Game");
//        homeScreen.setForeground(Color.white);
//        homeScreen.setFont(customFont.deriveFont(50f));
//        homeScreen.setBounds(330, 175, 300, 100);
//        homeScreen.setBorderPainted(false);
//        homeScreen.setOpaque(false);
//        homeScreen.setContentAreaFilled(false);
//        homeScreen.setFocusPainted(false);
//        homeScreen.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(mainPanel, "IntroScreen");
//            }
//        });

//        JButton exit = new JButton ("Exit");
//        exit.setForeground(Color.white);
//        exit.setFont(customFont.deriveFont(50f));
//        exit.setBounds(765, 175, 300, 100);
//        exit.setBorderPainted(false);
//        exit.setOpaque(false);
//        exit.setContentAreaFilled(false);
//        exit.setFocusPainted(false);
//        exit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });

        add(successfulHunt);
//        add(homeScreen);
//        add(exit);
    }

    private void loadFonts()
    {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Regular.ttf"));
            customBoldFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void readImages()
    {
        try {
            background = ImageIO.read(new File("images/wonTesting.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}