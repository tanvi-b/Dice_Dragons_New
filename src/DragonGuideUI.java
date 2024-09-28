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

public class DragonGuideUI extends JPanel {
    private ArrayList<BufferedImage> dragonGuide;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Font customFont;
    private Font customBoldFont;
    private BufferedImage background;
    private static int indexPage1;
    private static int indexPage2;

    public DragonGuideUI(CardLayout cardLayout, JPanel mainPanel)
    {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        dragonGuide = new ArrayList<>();
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
        g.drawImage(dragonGuide.get(indexPage1), 10, 10, 595, 900, this);
        g.drawImage(dragonGuide.get(indexPage2), 605, 10, 595, 900, this);
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
                    //cardLayout.show(mainPanel, "Playing Screen"); --> somehow need to see current playing screen, not new
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
                if (indexPage1+2<dragonGuide.size() && indexPage2+2<dragonGuide.size())
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
