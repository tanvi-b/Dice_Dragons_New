import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpecialSkillsUI extends JPanel {
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;
    public static int heroClass;
    private ArrayList<BufferedImage> diceFaces;
    public static ArrayList<Integer> dice;
    private Color greenBackground = new Color(147, 195, 123);
    private Color greenBorder = new Color(72, 129, 34);
    private Color redBackground = new Color(228, 99, 98);
    private Color redBorder = new Color(139, 0, 0);
    private int borderThickness = 4;

    public SpecialSkillsUI(CardLayout cardLayout, JPanel mainPanel) {
        diceFaces = new ArrayList<>();
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        readImages();
        loadFonts();
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        dice = getDice();
        repaint();

        if (heroClass==0) {
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 30, 320, 450, 550, this);
                for (int i = 0; i < dice.size(); i++)
                    g.drawImage(diceFaces.get(dice.get(i)), 500 + i*125, 450, 100, 100, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==1) {
            try {
                g.drawImage(ImageIO.read(new File("images/wizard.png")), 30, 320, 450, 550, this);
                for (int i = 0; i < dice.size(); i++)
                    g.drawImage(diceFaces.get(dice.get(i)), 500 + i*125, 450, 100, 100, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==2) {
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 30, 320, 450, 550, this);
                for (int i = 0; i < dice.size(); i++)
                    g.drawImage(diceFaces.get(dice.get(i)), 500 + i*125, 450, 100, 100, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==3) {
            try {
                g.drawImage(ImageIO.read(new File("images/ranger.png")), 30, 320, 450, 550, this);
                for (int i = 0; i < dice.size(); i++)
                    g.drawImage(diceFaces.get(dice.get(i)), 500 + i*125, 450, 100, 100, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==4) {
            try {
                g.drawImage(ImageIO.read(new File("images/rogue.png")), 30, 320, 450, 550, this);
                for (int i = 0; i < dice.size(); i++)
                    g.drawImage(diceFaces.get(dice.get(i)), 500 + i*125, 450, 100, 100, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readImages() {
        try {
            background = ImageIO.read(new File("images/backgroundImage.png"));

            diceFaces.add(ImageIO.read(new File("images/dice1.png")));
            diceFaces.add(ImageIO.read(new File("images/dice2.png")));
            diceFaces.add(ImageIO.read(new File("images/dice3.png")));
            diceFaces.add(ImageIO.read(new File("images/dice4.png")));
            diceFaces.add(ImageIO.read(new File("images/dice5.png")));
            diceFaces.add(ImageIO.read(new File("images/dice6.png")));
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

    private void addComponents() {
        JTextField title = new JTextField();
        title.setText("Special Skills");
        title.setEditable(false);
        title.setFont(customFont.deriveFont(25f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(300, 30, 600, 50);
        title.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton blessing = new JButton("Blessing");
        blessing.setFont(customFont.deriveFont(20f));
        blessing.setBounds(150, 110, 130, 50);
        blessing.setOpaque(true);
        if (heroClass==2) {
            blessing.setBackground(greenBackground);
            blessing.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            blessing.setBackground(redBackground);
            blessing.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        blessing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton shield = new JButton("Shield");
        shield.setFont(customFont.deriveFont(20f));
        shield.setBounds(300, 110, 130, 50);
        shield.setOpaque(true);
        if (heroClass==1 || heroClass==2) {
            shield.setBackground(greenBackground);
            shield.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            shield.setBackground(redBackground);
            shield.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        shield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton pinDown = new JButton("Pin Down");
        pinDown.setFont(customFont.deriveFont(20f));
        pinDown.setBounds(450, 110, 130, 50);
        pinDown.setOpaque(true);
        if (heroClass==3 || heroClass==4) {
            pinDown.setBackground(greenBackground);
            pinDown.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            pinDown.setBackground(redBackground);
            pinDown.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        pinDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton allySkills = new JButton("Ally Skills");
        allySkills.setFont(customFont.deriveFont(20f));
        allySkills.setBounds(600, 110, 130, 50);
        allySkills.setOpaque(true);
        if (heroClass==1 || heroClass==3) {
            allySkills.setBackground(greenBackground);
            allySkills.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            allySkills.setBackground(redBackground);
            allySkills.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        allySkills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton drainLife = new JButton("Drain Life");
        drainLife.setFont(customFont.deriveFont(20f));
        drainLife.setBounds(750, 110, 130, 50);
        drainLife.setOpaque(true);
        if (heroClass==1) {
            drainLife.setBackground(greenBackground);
            drainLife.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            drainLife.setBackground(redBackground);
            drainLife.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        drainLife.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton healingWave = new JButton("Healing Wave");
        healingWave.setFont(customFont.deriveFont(20f));
        healingWave.setBounds(900, 110, 130, 50);
        healingWave.setOpaque(true);
        if (heroClass==2) {
            healingWave.setBackground(greenBackground);
            healingWave.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        } else {
            healingWave.setBackground(redBackground);
            healingWave.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, redBorder));
        }
        healingWave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JTextField generalSkills = new JTextField();
        generalSkills.setText("General Skills");
        generalSkills.setEditable(false);
        generalSkills.setFont(customFont.deriveFont(25f));
        generalSkills.setHorizontalAlignment(SwingConstants.CENTER);
        generalSkills.setBounds(300, 180, 600, 50);
        generalSkills.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton jab = new JButton("Jab");
        jab.setFont(customFont.deriveFont(20f));
        jab.setBounds(450, 260, 130, 50);
        jab.setOpaque(true);
        jab.setBackground(greenBackground);
        jab.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        jab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton treatWounds = new JButton("Treat Wounds");
        treatWounds.setFont(customFont.deriveFont(20f));
        treatWounds.setBounds(600, 260, 130, 50);
        treatWounds.setOpaque(true);
        treatWounds.setBackground(greenBackground);
        treatWounds.setBorder(new MatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, greenBorder));
        treatWounds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton back = new JButton("Back");
        back.setFont(customFont.deriveFont(20f));
        back.setBounds(30, 900, 130, 50);
        back.setOpaque(true);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "PlayingScreen");
            }
        });

        add(title);
        add(blessing);
        add(shield);
        add(pinDown);
        add(allySkills);
        add(drainLife);
        add(healingWave);
        add(generalSkills);
        add(jab);
        add(treatWounds);
        add(back);
    }

    public static void setHeroClass(int hero) {
        heroClass = hero;
    }

    public static int getHeroClass() {
        return heroClass;
    }

    public static void setDice(ArrayList<Integer> d){
        dice = d;
    }

    public ArrayList<Integer> getDice(){
        return dice;
    }
}

