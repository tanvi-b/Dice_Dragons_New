import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MarketPlaceUI extends JPanel{
    private Font customFont;
    private Font customBoldFont;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private BufferedImage background;
    private BufferedImage marketPic;
    private Color greenBackground = new Color(147, 195, 123);
    private Color goldColor = new Color(212, 175, 55);
    //for now, this will be modified with networking
    private static int typeDragon = 0;


    public MarketPlaceUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
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

        // will change number
        JLabel gold = new JLabel("0");
        gold.setForeground(Color.black);
        gold.setFont(customFont.deriveFont(20f));
        gold.setBounds(550, 120, 400, 50);
        gold.setHorizontalAlignment(SwingConstants.CENTER);
        gold.setOpaque(true);
        gold.setBackground(goldColor);

        JLabel amountOfXP = new JLabel("Amount of XP You Have: ");
        amountOfXP.setForeground(Color.black);
        amountOfXP.setFont(customFont.deriveFont(20f));
        amountOfXP.setBounds(250, 190, 250, 50);
        amountOfXP.setHorizontalAlignment(SwingConstants.CENTER);
        amountOfXP.setOpaque(true);
        amountOfXP.setBackground(Color.white);

        // will change number depending on player
        JLabel xp = new JLabel("0");
        xp.setForeground(Color.black);
        xp.setFont(customFont.deriveFont(20f));
        xp.setBounds(550, 190, 400, 50);
        xp.setHorizontalAlignment(SwingConstants.CENTER);
        xp.setOpaque(true);
        xp.setBackground(goldColor);

        JLabel typeMarket = new JLabel("");
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
        typeMarket.setBackground(Color.white);

        DefaultTableModel data = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // put this line of code as it will clear previous dragon marketplace data
        data.setRowCount(0);

        data.addColumn("Item");
        data.addColumn("Type");
        data.addColumn("Effect");
        data.addColumn("Cost");
        data.addColumn("Quantity");

        if(typeDragon == 0) {
            typeMarket.setText("Bearwood Market");
            data.addRow(new Object[]{"Small Healing Potion", "Instant", "Heals +4 HP", "1 Gold", "2"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Haste Potion", "Instant", "Re-roll up to 2 Dragon Dice", "1 Gold", "1"});
            data.addRow(new Object[]{"Holy Water", "Instant", "Add a Hammer Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Mana Potion", "Instant", "Add a Magic Symbol", "1 Gold", "1"});
        }

        else if(typeDragon == 1){
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

        else if(typeDragon == 2){
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

        else if(typeDragon == 3){
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

        else if(typeDragon == 4){
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
        else if(typeDragon == 5){
            typeMarket.setText("Kemora Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "1"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "1"});
            data.addRow(new Object[]{"Holy Water", "Instant", "Add a Hammer Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Magic Sword", "Durable", "Add a Sword Symbol each turn", "1 Gold", "1"});
        }
        else if(typeDragon == 6){
            typeMarket.setText("Jowryk Market");
            data.addRow(new Object[]{"Healing Potion", "Instant", "Heals +7 HP", "2 Gold", "2"});
            data.addRow(new Object[]{"Great Healing Potion", "Instant", "Heals +9 HP", "3 Gold", "1"});
            data.addRow(new Object[]{"Scroll of Knowledge", "Instant", "Re-use a Skill", "1 Gold", "2"});
            data.addRow(new Object[]{"Stealth Potion", "Instant", "Add a Daggers Symbol", "1 Gold", "2"});
            data.addRow(new Object[]{"Great Haste Portion", "Instant", "Re-roll up to 3 Dragon Dice", "2 Gold", "1"});
            data.addRow(new Object[]{"Vision Portion", "Instant", "Add a Crossbow Symbol", "1 Gold", "1"});
            data.addRow(new Object[]{"Gauntlets of Power", "Durable", "1 HP extra damage when activating a Strike Skill", "4 Gold", "1"});
            data.addRow(new Object[]{"Staff of Healing", "Durable", "+1HP extra healing when activating a Healing Skill", "4 Gold", "1"});

        }
        else{
            //no marketplace for black dragon
            typeMarket.setText("No more market!");
        }

        JTable marketTable = new JTable(data);
        marketTable.getTableHeader().setBounds(370, 480, 750, 20);
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
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = marketTable.getSelectedRow();
                    System.out.println("Testing Row" + selectedRow);
                }
            }
        });

        add(amountOfGold);
        add(gold);
        add(title);
        add(amountOfXP);
        add(xp);
        add(marketTable.getTableHeader());
        add(marketTable);
        add(typeMarket);
        add(directions);
    }
    private void readImages() {
        try {
            background = ImageIO.read(new File("images/marketOption3.png"));
            marketPic = ImageIO.read(new File("images/items.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToMarketPlace(){
        cardLayout.show(mainPanel, "MarketPlaceScreen");
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
