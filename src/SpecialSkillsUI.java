import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpecialSkillsUI extends JPanel {
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;
    public SpecialSkillsUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        readImages();
        loadFonts();
        addComponents();
        setLayout(null);
    }
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
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

        JButton blessing = new JButton("Blessing");
        blessing.setFont(customFont.deriveFont(20f));
        blessing.setBounds(150, 110, 130, 50);
        blessing.setBorder(BorderFactory.createLineBorder(Color.black));
        blessing.setOpaque(true);

        JButton shield = new JButton("Shield");
        shield.setFont(customFont.deriveFont(20f));
        shield.setBounds(300, 110, 130, 50);
        shield.setBorder(BorderFactory.createLineBorder(Color.black));
        shield.setOpaque(true);

        JButton pinDown = new JButton("Pin Down");
        pinDown.setFont(customFont.deriveFont(20f));
        pinDown.setBounds(450, 110, 130, 50);
        pinDown.setBorder(BorderFactory.createLineBorder(Color.black));
        pinDown.setOpaque(true);

        JButton allySkills = new JButton("Ally Skills");
        allySkills.setFont(customFont.deriveFont(20f));
        allySkills.setBounds(600, 110, 130, 50);
        allySkills.setBorder(BorderFactory.createLineBorder(Color.black));
        allySkills.setOpaque(true);

        JButton drainLife = new JButton("Drain Life");
        drainLife.setFont(customFont.deriveFont(20f));
        drainLife.setBounds(750, 110, 130, 50);
        drainLife.setBorder(BorderFactory.createLineBorder(Color.black));
        drainLife.setOpaque(true);

        JButton healingWave = new JButton("Healing Wave");
        healingWave.setFont(customFont.deriveFont(20f));
        healingWave.setBounds(900, 110, 130, 50);
        healingWave.setBorder(BorderFactory.createLineBorder(Color.black));
        healingWave.setOpaque(true);


        add(title);
        add(blessing);
        add(shield);
        add(pinDown);
        add(allySkills);
        add(drainLife);
        add(healingWave);
    }
}
