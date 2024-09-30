import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PassDiceUI extends JPanel {
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;

    public PassDiceUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        readImages();
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
}