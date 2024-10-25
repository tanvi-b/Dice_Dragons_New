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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpecialSkillsUI extends JPanel {
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    public static Game game;
    private BufferedImage background;
    private ArrayList<BufferedImage> diceFaces = new ArrayList<>();
    public static List<Map.Entry<Boolean, Integer>> dice = new ArrayList<>();
    private Color greenBackground = new Color(147, 195, 123);
    private Color greenBorder = new Color(72, 129, 34);
    private Color redBackground = new Color(228, 99, 98);
    private Color redBorder = new Color(139, 0, 0);
    public static int heroClass;
    private static int dragonLevel;
    private static Skill jabSkill, treatWoundsSkill;
    private static boolean jabUsed, treatWoundsUsed;
    private static JComboBox<String> nameChoice = new JComboBox<>();
    private static JButton nameSelected = new JButton("OK");
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private static boolean clicked = false;

    public SpecialSkillsUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        readImages();
        loadFonts();
        setLayout(null);
        jabSkill = new Skill("Jab", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(7, 7, 7)), 2, 0);
        treatWoundsSkill = new Skill("Treat Wounds", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(7, 7, 7)), 2, 0);
        addComponents();
        add(nameSelected);
        add(nameChoice);
        nameChoice.setVisible(false);
        nameSelected.setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
        dice = getDice();
        for (int i = 0; i < dice.size(); i++)
            g.drawImage(diceFaces.get(dice.get(i).getValue()), 500 + i*125, 370, 100, 100, this);

        if (heroClass==0) {
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 30, 320, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==1) {
            try {
                g.drawImage(ImageIO.read(new File("images/wizard.png")), 30, 320, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==2) {
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 30, 320, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==3) {
            try {
                g.drawImage(ImageIO.read(new File("images/ranger.png")), 30, 320, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (heroClass==4) {
            try {
                g.drawImage(ImageIO.read(new File("images/rogue.png")), 30, 320, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readImages() {
        try {
            background = ImageIO.read(new File("images/backgroundImage.png"));
            diceFaces.add(ImageIO.read(new File("images/dice0.png")));
            diceFaces.add(ImageIO.read(new File("images/dice1.png")));
            diceFaces.add(ImageIO.read(new File("images/dice2.png")));
            diceFaces.add(ImageIO.read(new File("images/dice3.png")));
            diceFaces.add(ImageIO.read(new File("images/dice4.png")));
            diceFaces.add(ImageIO.read(new File("images/dice5.png")));
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

        nameChoice.setSize(200, 50);
        nameChoice.setLocation(750, 260);

        nameSelected.setFont(customFont.deriveFont(9f));
        nameSelected.setBounds(975, 260, 45, 50);
        nameSelected.setOpaque(true);
        nameSelected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameChoice.setVisible(false);
                nameSelected.setVisible(false);
                clicked = true;
            }
        });

        if(clicked == true){
            for(int i =0; i<heroes.size(); i++){
                if(nameChoice.getSelectedIndex() == i){
                    SpecialSkillsUI.game.treatWounds(SpecialSkillsUI.game.getOs(),heroes.get(i).getClassType());
                }
            }
            clicked = false;
        }
        JButton blessing = new JButton("Blessing");
        blessing.setFont(customFont.deriveFont(20f));
        blessing.setBounds(150, 110, 130, 50);
        blessing.setOpaque(true);
        if (heroClass==2) {
            blessing.setBackground(greenBackground);
            blessing.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            blessing.setBackground(redBackground);
            blessing.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
            shield.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            shield.setBackground(redBackground);
            shield.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
            pinDown.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            pinDown.setBackground(redBackground);
            pinDown.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
            allySkills.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            allySkills.setBackground(redBackground);
            allySkills.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
            drainLife.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            drainLife.setBackground(redBackground);
            drainLife.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
            healingWave.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        } else {
            healingWave.setBackground(redBackground);
            healingWave.setBorder(new MatteBorder(4, 4, 4, 4, redBorder));
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
        jab.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        jab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean canUse = jabSkill.checkDiceCombo(dice);
                if (canUse && !jabUsed) {
                    SpecialSkillsUI.game.jab(SpecialSkillsUI.game.getOs(), dragonLevel);
                    jabUsed = true;
                }
            }
        });

        JButton treatWounds = new JButton("Treat Wounds");
        treatWounds.setFont(customFont.deriveFont(20f));
        treatWounds.setBounds(600, 260, 130, 50);
        treatWounds.setOpaque(true);
        treatWounds.setBackground(greenBackground);
        treatWounds.setBorder(new MatteBorder(4, 4, 4, 4, greenBorder));
        treatWounds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean canUse = treatWoundsSkill.checkDiceCombo(dice);
                if (canUse && !treatWoundsUsed) {
                nameChoice.removeAllItems();
                for (Hero hero : heroes){
                    nameChoice.addItem(hero.heroName);
                }
                treatWoundsUsed = true;
                nameChoice.setVisible(true);
                nameSelected.setVisible(true);
                nameChoice.setSelectedIndex(-1);

            }
        }
        });

        JButton back = new JButton("Back");
        back.setFont(customFont.deriveFont(20f));
        back.setBounds(30, 200, 130, 50);
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

    public static void setHeroClass(Hero hero) {
        heroClass = hero.classType;
    }

    public static int getHeroClass() {
        return heroClass;
    }

    public static void setHeroes(ArrayList<Hero> info){
        heroes = info;
    }
    public static ArrayList<Hero> getHeroes(){
        return heroes;
    }

    public static void getDice(List<Map.Entry<Boolean, Integer>> d){
        dice = d;
    }

    private static void evaluate(){

    }

    public List<Map.Entry<Boolean, Integer>> getDice(){
        return dice;
    }

    public static void setDragonLevel (int level)
    {
        dragonLevel = level;
    }
}

