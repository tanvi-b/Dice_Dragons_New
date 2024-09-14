import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LobbyUI extends JPanel {
    private BufferedImage loginBackground;
    private Font customFont;
    private Font customBoldFont;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LobbyUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {
        JLabel accessCodeShow = new JLabel();
        accessCodeShow.setFont(customFont.deriveFont(20f));
        accessCodeShow.setBounds(900, 10, 250, 50);
        accessCodeShow.setText("Access Code: ");
        accessCodeShow.setOpaque(true);
        accessCodeShow.setBackground(Color.black);
        accessCodeShow.setForeground(Color.ORANGE);

        JLabel playersJoined = new JLabel();
        playersJoined.setFont(customFont.deriveFont(60f));
        playersJoined.setBounds(350, 200, 500, 75);
        playersJoined.setText("Players Joined");
        playersJoined.setHorizontalAlignment(SwingConstants.CENTER);
        playersJoined.setOpaque(true);

        //in this table list the usernames of players
        DefaultTableModel playersModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        playersModel.addColumn("Username");
        JTable playersTable = new JTable(playersModel);
        playersTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        JScrollPane playersScrollPane = new JScrollPane(playersTable);
        playersScrollPane.setBounds(450, 300, 300, 400);

        //timer - after all players have joined show a jlabel saying starting game in 3 2 1
        //lead to playing screen

        add(playersJoined);
        add(accessCodeShow);
        add(playersScrollPane);
    }

    private void buttonFormatting(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setVisible(true);
    }

    private void readImages() {
        try {
            loginBackground = ImageIO.read(new File("images/loginScreen.jpg"));
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
