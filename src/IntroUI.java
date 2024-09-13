import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IntroUI extends JPanel {
    private boolean host;
    private BufferedImage intro;
    private Font customFont;
    private Font customBoldFont;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public IntroUI() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel introPanel = createIntroPanel();
        mainPanel.add(introPanel, "IntroScreen");

        // Pass CardLayout and mainPanel references to JoinUI and HostUI
        JPanel joinPanel = new JoinUI(cardLayout, mainPanel);
        mainPanel.add(joinPanel, "JoinScreen");

        JPanel hostPanel = new HostUI(cardLayout, mainPanel);
        mainPanel.add(hostPanel, "HostScreen");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "IntroScreen");
    }

    private JPanel createIntroPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(intro, 0, 0, 1200, 1000, this);
            }
        };

        readImages();
        loadFonts();

        host = false;
        JLabel gameTitleLine1 = new JLabel("Dice &");
        gameTitleLine1.setForeground(Color.white);
        gameTitleLine1.setFont(customBoldFont.deriveFont(75f));
        gameTitleLine1.setBounds(260, 12, 700, 100);

        JLabel gameTitleLine2 = new JLabel("Dragons");
        gameTitleLine2.setForeground(Color.white);
        gameTitleLine2.setFont(customBoldFont.deriveFont(75f));
        gameTitleLine2.setBounds(230, 80, 700, 100);

        JButton joinGame = new JButton("Join Game");
        joinGame.setForeground(Color.black);
        joinGame.setFont(customFont.deriveFont(42f));
        joinGame.setBounds(10, 600, 425, 50);
        joinGame.setBorderPainted(false);
        buttonFormatting(joinGame);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "JoinScreen");
            }
        });

        JButton hostGame = new JButton("Host Game");
        hostGame.setForeground(Color.black);
        hostGame.setFont(customFont.deriveFont(42f));
        hostGame.setBounds(10, 665, 425, 50);
        hostGame.setBorderPainted(false);
        buttonFormatting(hostGame);
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                host = true;
                cardLayout.show(mainPanel, "HostScreen");
            }
        });

        panel.add(gameTitleLine1);
        panel.add(gameTitleLine2);
        panel.add(joinGame);
        panel.add(hostGame);

        return panel;
    }

    private void buttonFormatting(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setVisible(true);
    }

    private void readImages() {
        try {
            intro = ImageIO.read(new File("images/introScreen.jpg"));
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
