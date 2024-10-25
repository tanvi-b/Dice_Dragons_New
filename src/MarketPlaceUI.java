import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MarketPlaceUI extends JPanel{
    private Font customFont;
    private Font customBoldFont;
    public static Game game;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;
    private BufferedImage marketPic;
    private Color greenBackground = new Color(147, 195, 123);
    private Color goldColor = new Color(212, 175, 55);
    private static int typeDragon = 1;
    private static String username;
    private static JLabel goldText, xpText, tooManyItemsError, notEnoughGoldError, successfulPurchaseMessage, startingNewGame,
    typeMarket;
    private static Timer countdownTimer;
    private static int countdown = 3;
    private static DefaultTableModel data;
    private static JTable marketTable;

    public MarketPlaceUI(CardLayout cardLayout, JPanel mainPanel, Game game) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.game = game;
        setLayout(null);
        readImages();
        loadFonts();
        addComponents();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1200, 1000, this);
    }

    private void addComponents() {
        JLabel title = new JLabel("Market Place");
        title.setForeground(Color.black);
        title.setFont(customFont.deriveFont(55f));
        title.setBounds(400, 20, 500, 70);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(Color.white);

        JLabel amountOfGold = new JLabel("Amount of Gold You Have: ");
        amountOfGold.setForeground(Color.black);
        amountOfGold.setFont(customFont.deriveFont(20f));
        amountOfGold.setBounds(250, 120, 250, 50);
        amountOfGold.setHorizontalAlignment(SwingConstants.CENTER);
        amountOfGold.setOpaque(true);
        amountOfGold.setBackground(Color.white);

        goldText = new JLabel();
        goldText.setForeground(Color.black);
        goldText.setFont(customFont.deriveFont(20f));
        goldText.setBounds(550, 120, 400, 50);
        goldText.setHorizontalAlignment(SwingConstants.CENTER);
        goldText.setOpaque(true);
        goldText.setBackground(goldColor);

        JLabel amountOfXP = new JLabel("Amount of XP You Have: ");
        amountOfXP.setForeground(Color.black);
        amountOfXP.setFont(customFont.deriveFont(20f));
        amountOfXP.setBounds(250, 190, 250, 50);
        amountOfXP.setHorizontalAlignment(SwingConstants.CENTER);
        amountOfXP.setOpaque(true);
        amountOfXP.setBackground(Color.white);

        xpText = new JLabel();
        xpText.setForeground(Color.black);
        xpText.setFont(customFont.deriveFont(20f));
        xpText.setBounds(550, 190, 400, 50);
        xpText.setHorizontalAlignment(SwingConstants.CENTER);
        xpText.setOpaque(true);
        xpText.setBackground(goldColor);

        typeMarket = new JLabel("");
        typeMarket.setForeground(Color.black);
        typeMarket.setFont(customFont.deriveFont(55f));
        typeMarket.setBounds(350, 310, 550, 70);
        typeMarket.setHorizontalAlignment(SwingConstants.CENTER);
        typeMarket.setOpaque(true);
        typeMarket.setBackground(Color.white);

        JLabel directions = new JLabel("Based on your gold and XP, select items which you think will be useful to combat the next dragon!");
        directions.setForeground(Color.black);
        directions.setFont(customFont.deriveFont(20f));
        directions.setBounds(190, 400, 850, 50);
        directions.setHorizontalAlignment(SwingConstants.CENTER);
        directions.setOpaque(true);

        data = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        data.setRowCount(0);

        data.addColumn("Item");
        data.addColumn("Type");
        data.addColumn("Effect");
        data.addColumn("Cost");
        data.addColumn("Quantity");

        marketTable = new JTable(data);
        setTableData();
        marketTable.getTableHeader().setBounds(345, 480, 750, 20);
        for(int i =0; i<marketTable.getRowCount(); i++){
            marketTable.setRowHeight(25);
        }
        marketTable.setBounds(345, 500, 750, 25* marketTable.getRowCount());
        marketTable.setCellSelectionEnabled(false);
        marketTable.setColumnSelectionAllowed(false);
        marketTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        marketTable.getColumnModel().getColumn(0).setPreferredWidth(125);
        marketTable.setPreferredSize(new Dimension(900, 400));

        marketTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
            }
        });

        JButton buy = new JButton ("Buy");
        buy.setFont(customFont.deriveFont(20f));
        buy.setBounds(615, 710, 175, 50);
        buy.setOpaque(true);
        buy.setBackground(new Color(147, 195, 123));
        buy.setBorder(new MatteBorder(4, 4, 4, 4, new Color(72, 129, 34)));
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = marketTable.getSelectedRow();
                if (selectedRow != -1) {
                    tooManyItemsError.setVisible(false);
                    notEnoughGoldError.setVisible(false);
                    successfulPurchaseMessage.setVisible(false);
                    MarketPlaceUI.game.buyItem(MarketPlaceUI.game.getOs(), username, selectedRow, typeDragon);
                    String item = (String) marketTable.getValueAt(marketTable.getSelectedRow(), 0);
                    MarketPlaceUI.game.setItemButtons(MarketPlaceUI.game.getOs(), item);
                }
            }
        });

        tooManyItemsError = new JLabel("You already have 2 items.");
        tooManyItemsError.setHorizontalAlignment(SwingConstants.CENTER);
        tooManyItemsError.setForeground(Color.red);
        tooManyItemsError.setFont(customFont.deriveFont(20f));
        tooManyItemsError.setBounds(580, 775, 245, 50);
        tooManyItemsError.setOpaque(true);

        notEnoughGoldError = new JLabel("You do not have enough gold.");
        notEnoughGoldError.setHorizontalAlignment(SwingConstants.CENTER);
        notEnoughGoldError.setForeground(Color.red);
        notEnoughGoldError.setFont(customFont.deriveFont(20f));
        notEnoughGoldError.setBounds(570, 775, 260, 50);
        notEnoughGoldError.setOpaque(true);

        successfulPurchaseMessage = new JLabel("Successful purchase!");
        successfulPurchaseMessage.setHorizontalAlignment(SwingConstants.CENTER);
        successfulPurchaseMessage.setForeground(Color.red);
        successfulPurchaseMessage.setFont(customFont.deriveFont(20f));
        successfulPurchaseMessage.setBounds(570, 775, 260, 50);
        successfulPurchaseMessage.setOpaque(true);

        JButton done = new JButton ("Done");
        done.setFont(customFont.deriveFont(20f));
        done.setBounds(1050, 730, 130, 50);
        done.setOpaque(true);
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tooManyItemsError.setVisible(false);
                notEnoughGoldError.setVisible(false);
                successfulPurchaseMessage.setVisible(false);
                MarketPlaceUI.game.readyToMoveOn(MarketPlaceUI.game.getOs(), username);
            }
        });

        startingNewGame = new JLabel("Next hunt begins in 3...");
        startingNewGame.setHorizontalAlignment(SwingConstants.CENTER);
        startingNewGame.setForeground(Color.red);
        startingNewGame.setFont(customFont.deriveFont(20f));
        startingNewGame.setBounds(550, 775, 300, 50);
        startingNewGame.setOpaque(true);

        add(amountOfGold);
        add(goldText);
        add(title);
        add(amountOfXP);
        add(xpText);
        add(marketTable.getTableHeader());
        add(marketTable);
        add(typeMarket);
        add(directions);
        add(buy);
        add(tooManyItemsError);
        add(notEnoughGoldError);
        add(done);
        add(startingNewGame);
        add(successfulPurchaseMessage);
        tooManyItemsError.setVisible(false);
        notEnoughGoldError.setVisible(false);
        successfulPurchaseMessage.setVisible(false);
        startingNewGame.setVisible(false);
    }

    public static void setTableData()
    {
        data.setRowCount(0);
        if(typeDragon == 1) {
            typeMarket.setText("Bearwood Market");
            data.addRow(new Object[]{"Small Healing Potion", "Instant", "Heals +4 HP", "1 Gold", "2"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Haste Potion", "Instant", "Re-roll up to 2 Dragon Dice", "1 Gold", "1"});
            data.addRow(new Object[]{"Holy Water", "Instant", "Add a Hammer Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Mana Potion", "Instant", "Add a Magic Symbol", "1 Gold", "1"});
        }

        else if(typeDragon == 2){
            typeMarket.setText("Agelos Market");
            data.addRow(new Object[]{"Small Healing Potion", "Instant", "Heals +4 HP", "1 Gold", "1" });
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "1 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Haste Potion", "Instant", "Re-roll up to 2 Dragon Dice", "1 Gold", "2"});
            data.addRow(new Object[]{"Vision Portion", "Instant", "Add a Crossbow Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Mana Portion", "Instant", "Add a Magic Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Stealth Potion", "Instant", "Add a Daggers Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Steel Shield", "Durable", "+1 AC", "2 Gold", "1"});
        }

        else if(typeDragon == 3){
            typeMarket.setText("Raindrop Keep Market");
            data.addRow(new Object[]{"Small Healing Potion", "Instant", "Heals +4 HP", "1 Gold", "1" });
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "1 Gold", "2"});
            data.addRow(new Object[]{"Holy Water", "Instant", "Add a Hammer Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Stealth Potion", "Instant", "Add a Daggers Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Steel Shield", "Durable", "+1 AC", "2 Gold", "1"});
            data.addRow(new Object[]{"Magic Shield", "Durable", "+2 AC", "4 Gold", "1"});
            data.addRow(new Object[]{"Magic Sword", "Durable", "Add a Sword Symbol each turn", "5 Gold", "1"});
            data.addRow(new Object[]{"Pinpoint Crossbow", "Durable", "Add a Crossbow Symbol each turn", "5 Gold", "1"});
        }

        else if(typeDragon == 4){
            typeMarket.setText("Deepridge Burrow Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "1"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Strength Potion", "Instant", "Add a Sword Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Blessed Hammer", "Durable", "Add a Hammer Symbol each turn", "1 Gold", "1"});
            data.addRow(new Object[]{"Gauntlets of Power", "Durable", "1 HP extra damage when activating a Strike Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Magic Staff", "Durable", "Add a Magic Symbol each turn", "2 Gold", "1"});
        }

        else if(typeDragon == 5){
            typeMarket.setText("Bearwood Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "1"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Strength Potion", "Instant", "Add a Sword Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Blessed Hammer", "Durable", "Add a Hammer Symbol each turn", "1 Gold", "1"});
            data.addRow(new Object[]{"Gauntlets of Power", "Durable", "1 HP extra damage when activating a Strike Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Magic Staff", "Durable", "Add a Magic Symbol each turn", "2 Gold", "1"});
        }
        else if(typeDragon == 6){
            typeMarket.setText("Kemora Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "1"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Holy Water", "Instant", "Add a Hammer Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Magic Sword", "Durable", "Add a Sword Symbol each turn", "1 Gold", "1"});
        }
        else if(typeDragon == 7){
            typeMarket.setText("Jowryk Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "2"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "2"});
            data.addRow(new Object[]{"Stealth Potion", "Instant", "Add a Daggers Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Vision Portion", "Instant", "Add a Crossbow Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Gauntlets of Power", "Durable", "1 HP extra damage when activating a Strike Skill", "4 Gold", "1"});
            data.addRow(new Object[]{"Staff of Healing", "Durable", "+1 HP extra healing when activating a Healing Skill", "4 Gold", "1"});

        }
        else{
            //no marketplace for black dragon
            typeMarket.setText("No more market!");
        }
        marketTable.setModel(data);
        marketTable.setBounds(345, 500, 750, 25* marketTable.getRowCount());
    }

    public static void readyToMoveOn()
    {
        startingNewGame.setVisible(true);
        countdown = 3;
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdown > 0) {
                    startingNewGame.setText("Next hunt begins in " + countdown + "...");
                    countdown--;
                } else {
                    countdownTimer.stop();
                    startingNewGame.setVisible(false);
                    typeDragon++;
                    PlayingUI.reset();
                    SpecialSkillsUI.reset();
                    cardLayout.show(mainPanel, "PlayingScreen");
                }
            }
        });
        countdownTimer.start();
    }

    public static void tooManyItems ()
    {
        tooManyItemsError.setVisible(true);
    }

    public static void notEnoughGold ()
    {
        notEnoughGoldError.setVisible(true);
    }

    public static void successfulPurchase()
    {
        successfulPurchaseMessage.setVisible(true);
    }

    public static void setTypeDragon (int level) {
        typeDragon = level;
        setTableData();
    }

    public static void setGoldAndXpText (Hero hero)
    {
        goldText.setText(String.valueOf(hero.gold));
        xpText.setText(String.valueOf(hero.exp));
    }

    public static void setUsername (Hero hero) {
        username = hero.heroName;
    }

    private void readImages() {
        try {
            background = ImageIO.read(new File("images/marketOption3.png"));
            marketPic = ImageIO.read(new File("images/items.png"));

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
