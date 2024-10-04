import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpecialSkillsUI extends JPanel {
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;
    public static String heroClass = "";

    //buttons


    public SpecialSkillsUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        readImages();
        loadFonts();
        setLayout(null);
    }
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);

        if(heroClass.equals("Warrior")){
            try {
                g.drawImage(ImageIO.read(new File("images/warrior.png")), 190, 400, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(heroClass.equals("Wizard")){
            try {
                g.drawImage(ImageIO.read(new File("images/wizard.png")), 190, 400, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(heroClass.equals("Cleric")){
            try {
                g.drawImage(ImageIO.read(new File("images/cleric.png")), 190, 400, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(heroClass.equals("Ranger")){
            try {
                g.drawImage(ImageIO.read(new File("images/ranger.png")), 190, 400, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(heroClass.equals("Rogue")){
            try {
                g.drawImage(ImageIO.read(new File("images/rogue.png")), 190, 400, 450, 550, this);
                addComponents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readImages() {
        try {
            background = ImageIO.read(new File("images/backgroundImage.png"));
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
    private void addComponents(){
        JTextField title = new JTextField();
        title.setText("Special Skills");
        title.setEditable(false);
        title.setFont(customFont.deriveFont(25f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(300, 30, 600, 50);
        title.setBorder(BorderFactory.createLineBorder(Color.black));

        Color myGreen = new Color(147, 195, 123);
        Color myRed = new Color(228,99,98);

        JButton blessing = new JButton("Blessing");
        blessing.setFont(customFont.deriveFont(20f));
        blessing.setBounds(150, 110, 130, 50);
        blessing.setBorder(BorderFactory.createLineBorder(Color.black));
        blessing.setBackground(myRed);

        JButton shield = new JButton("Shield");
        shield.setFont(customFont.deriveFont(20f));
        shield.setBounds(300, 110, 130, 50);
        shield.setBorder(BorderFactory.createLineBorder(Color.black));
        shield.setBackground(myRed);

        JButton pinDown = new JButton("Pin Down");
        pinDown.setFont(customFont.deriveFont(20f));
        pinDown.setBounds(450, 110, 130, 50);
        pinDown.setBorder(BorderFactory.createLineBorder(Color.black));
        pinDown.setBackground(myRed);


        JButton allySkills = new JButton("Ally Skills");
        allySkills.setFont(customFont.deriveFont(20f));
        allySkills.setBounds(600, 110, 130, 50);
        allySkills.setBorder(BorderFactory.createLineBorder(Color.black));
        allySkills.setBackground(myRed);

        JButton drainLife = new JButton("Drain Life");
        drainLife.setFont(customFont.deriveFont(20f));
        drainLife.setBounds(750, 110, 130, 50);
        drainLife.setBorder(BorderFactory.createLineBorder(Color.black));
        drainLife.setBackground(myRed);

        JButton healingWave = new JButton("Healing Wave");
        healingWave.setFont(customFont.deriveFont(20f));
        healingWave.setBounds(900, 110, 130, 50);
        healingWave.setBorder(BorderFactory.createLineBorder(Color.black));
        healingWave.setBackground(myRed);

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
        jab.setBorder(BorderFactory.createLineBorder(Color.black));
        jab.setOpaque(true);
        jab.setBackground(myGreen);

        JButton treatWounds = new JButton("Treat Wounds");
        treatWounds.setFont(customFont.deriveFont(20f));
        treatWounds.setBounds(600, 260, 130, 50);
        treatWounds.setBorder(BorderFactory.createLineBorder(Color.black));
        treatWounds.setOpaque(true);
        treatWounds.setBackground(myGreen);

        JButton back = new JButton("Back");
        back.setFont(customFont.deriveFont(20f));
        back.setBounds(50, 850, 130, 50);
        back.setBorder(BorderFactory.createLineBorder(Color.black));
        back.setOpaque(true);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "PlayingScreen");
            }
        });


        //no condition for warrior-- warrior does not get anything
        heroClass = getHeroClass();
        if(heroClass.equals("Wizard")){
            shield.setBackground(myGreen);
            allySkills.setBackground(myGreen);
            drainLife.setBackground(myGreen);
        }
        if(heroClass.equals("Cleric")){
            blessing.setBackground(myGreen);
            shield.setBackground(myGreen);
            healingWave.setBackground(myGreen);
        }

        if(heroClass.equals("Ranger")){
            pinDown.setBackground(myGreen);
            allySkills.setBackground(myGreen);
        }

        if(heroClass.equals("Rogue")){
            pinDown.setBackground(myGreen);
        }


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

    public static void setHeroClass(String hero){
        heroClass = hero;
    }

    public static String getHeroClass(){
        return heroClass;
    }
}
