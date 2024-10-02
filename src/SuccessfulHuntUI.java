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

public class SuccessfulHuntUI extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Font customFont;
    private Font customBoldFont;
    private BufferedImage background;

    public SuccessfulHuntUI(CardLayout cardLayout, JPanel mainPanel)
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
