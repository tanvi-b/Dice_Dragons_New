import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//dragon face is on dice side 5
//create separate class for each screen

public class GameUI extends JFrame {
    Game game;
    private ArrayList<BufferedImage> playerRules;
    private ArrayList<BufferedImage> dragonGuide;
    private ArrayList<BufferedImage> dragonSheets;
    private ArrayList<BufferedImage> heroSheets;
    private BufferedImage intro, loginBackground, background;
    //playing tokens
    //circular tokens

    public GameUI(Game game)
    {
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dice and Dragons Board Game");
        playerRules = new ArrayList<>();
        dragonGuide = new ArrayList<>();
        dragonSheets = new ArrayList<>();
        heroSheets = new ArrayList<>();

        JPanel lobbyScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(loginBackground, 0, 0, 1400, 1000, this);
            }
        };
        lobbyScreen.setLayout(null);

        JPanel playingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, 1400, 1000, this);
            }
        };
        playingScreen.setLayout(null);

        JPanel dragonGuide = new JPanel();
        dragonGuide.setLayout(null);

        JPanel playerRules = new JPanel();
        playerRules.setLayout(null);

        //lobby screen
//        JLabel accessCodeShow = new JLabel();
//        accessCodeShow.setFont(customFont.deriveFont(20f));
//        accessCodeShow.setBounds(900,10,250,50);
//        accessCodeShow.setText("Access Code: ");
//        accessCodeShow.setOpaque(true);
//        accessCodeShow.setBackground(Color.black);
//        accessCodeShow.setForeground(Color.ORANGE);
//
//        JLabel playersJoined = new JLabel();
//        playersJoined.setFont(customFont.deriveFont(60f));
//        playersJoined.setBounds(350, 200, 500, 75);
//        playersJoined.setText("Players Joined");
//        playersJoined.setHorizontalAlignment(SwingConstants.CENTER);
//        playersJoined.setOpaque(true);
//
//        //in this table list the usernames of players
//        DefaultTableModel playersModel = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        playersModel.addColumn("Username");
//        JTable playersTable = new JTable(playersModel);
//        playersTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
//        JScrollPane playersScrollPane = new JScrollPane(playersTable);
//        playersScrollPane.setBounds(450, 300, 300, 400);

        //timer - after all players have joined show a jlabel saying starting game in 3 2 1
        //lead to playing screen

        //playing screen
//        JLabel turn = new JLabel("Turn: ");
//        turn.setFont(customFont.deriveFont(60f));
//        turn.setBounds(450, 75, 500, 90);
//        turn.setOpaque(true);
//
//        JButton rules = new JButton("Rules");
//        rules.setFont(customFont.deriveFont(30f));
//        rules.setBounds(970, 80, 80, 80);
//        rules.setBorder(BorderFactory.createLineBorder(Color.black));
//        rules.setOpaque(true);
//
//        rules.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                getContentPane().removeAll();
//                getContentPane().add(playerRules);
//                validate();
//                repaint();
//                setVisible(true);
//            }
//        });
//
//        JButton guide = new JButton("Guide");
//        guide.setFont(customFont.deriveFont(30f));
//        guide.setBounds(1070, 80, 80, 80);
//        guide.setBorder(BorderFactory.createLineBorder(Color.black));
//        guide.setOpaque(true);
//
//        guide.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                getContentPane().removeAll();
//                getContentPane().add(dragonGuide);
//                validate();
//                repaint();
//                setVisible(true);
//            }
//        });
//
//        JLabel pointsText = new JLabel("Hero's Updates");
//        pointsText.setFont(customFont.deriveFont(30f));
//        pointsText.setBounds(10, 75, 360, 95);
//        pointsText.setHorizontalAlignment(JLabel.CENTER);
//        pointsText.setOpaque(true);
//
//        JList<String> heroUpdates = new JList<>();
//
//        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
//        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        pointsScrollBar.setBounds(10, 190, 360, 280);
//
//        JLabel dragonsText = new JLabel("Dragon's Updates");
//        dragonsText.setFont(customFont.deriveFont(30f));
//        dragonsText.setBounds(10, 500, 360, 95);
//        dragonsText.setHorizontalAlignment(JLabel.CENTER);
//        dragonsText.setOpaque(true);
//
//        JList<String> dragonUpdates = new JList<>();
//
//        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
//        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        dragonScrollBar.setBounds(10, 610, 360, 280);
//
//        JList<String> messages = new JList<>();
//        JScrollPane chatBox = new JScrollPane(messages);
//        chatBox.setBounds(870,720, 310,150);
//
//        JTextField messageText = new JTextField();
//        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
//        messageText.setBounds(870, 880, 240, 65);
//        messageText.setBackground(Color.white);
//        messageText.setOpaque(true);
//
//        JButton send = new JButton("Send");
//        send.setFont(customFont.deriveFont(20f));
//        send.setBounds(1115, 880, 65, 65);
//
//        send.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                String acc = characterName + ": " + messageText.getText();
//                ///will need to implement following method
//                //sendMessage(os,name+ ": " + input.getText());
//                messageText.setText("");
//            }
//        });

//        lobbyScreen.add(playersJoined);
//        lobbyScreen.add(accessCodeShow);
//        lobbyScreen.add(playersScrollPane);
//        //add starting game label but make visible false
//
//        playingScreen.add(turn);
//        playingScreen.add(rules);
//        playingScreen.add(guide);
//        playingScreen.add(pointsText);
//        playingScreen.add(pointsScrollBar);
//        playingScreen.add(dragonScrollBar);
//        playingScreen.add(dragonsText);
//        playingScreen.add(chatBox);
//        playingScreen.add(messageText);
//        playingScreen.add(send);

        getContentPane().add(new IntroUI());
        validate();
        repaint();
        setVisible(true);
        setSize(1200, 1000);
        setResizable(false);
    }

    public void buttonFormatting (JButton button)
    {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setVisible(true);
    }

    public void readImages()
    {
        try
        {
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
            dragonSheets.add(ImageIO.read(new File("images/black dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/blue dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/green dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/undead dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/young black dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/young red dragon.png")));
            dragonSheets.add(ImageIO.read(new File("images/pale dragon.png")));
            heroSheets.add(ImageIO.read(new File("images/cleric.png")));
            heroSheets.add(ImageIO.read(new File("images/custom hero.png")));
            heroSheets.add(ImageIO.read(new File("images/ranger.png")));
            heroSheets.add(ImageIO.read(new File("images/rogue.png")));
            heroSheets.add(ImageIO.read(new File("images/warrior.png")));
            heroSheets.add(ImageIO.read(new File("images/wizard.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}