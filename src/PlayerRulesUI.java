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

public class PlayerRulesUI extends JPanel {
    private ArrayList<BufferedImage> playerRules;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Font customFont;
    private Font customBoldFont;
    private BufferedImage background;
    private static int indexPage1;
    private static int indexPage2;

    public PlayerRulesUI(CardLayout cardLayout, JPanel mainPanel)
    {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        playerRules = new ArrayList<>();
        indexPage1 = 0;
        indexPage2 = 1;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        g.drawImage(playerRules.get(indexPage1), 10, 10, 595, 900, this);
        g.drawImage(playerRules.get(indexPage2), 605, 10, 595, 900, this);
    }

    private void addComponents()
    {
        JButton done = new JButton ("Done");
        done.setFont(customFont.deriveFont(20f));
        done.setBounds(1045, 5, 130, 40);
        done.setBorder(BorderFactory.createLineBorder(Color.black));
        done.setOpaque(true);
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "PlayingScreen");

            }
        });

        BasicArrowButton previous = new BasicArrowButton(BasicArrowButton.WEST);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexPage1-1>=0 && indexPage2-2>=0)
                {
                    indexPage1-=2;
                    indexPage2-=2;
                    repaint();
                }
            }
        });
        previous.setBounds(10, 915, 130, 40);

        BasicArrowButton next = new BasicArrowButton(BasicArrowButton.EAST);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexPage1+2<playerRules.size() && indexPage2+2<playerRules.size())
                {
                    indexPage1+=2;
                    indexPage2+=2;
                    repaint();
                }
            }
        });
        next.setBounds(1045, 915, 130, 40);

       add(done);
       add(previous);
       add(next);
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
            background = ImageIO.read(new File("images/backgroundImage.png"));
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
