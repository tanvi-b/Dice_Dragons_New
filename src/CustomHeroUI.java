//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
////take the previous screen in constructor?
//public class CustomHeroUI extends JPanel{
//    boolean rollClicked;
//    boolean host;//figure out to get from host ui later
//    private ArrayList<BufferedImage> diceFaces;
//    private BufferedImage loginBackground;
//    private Font customFont;
//    private Font customBoldFont;
//
//    public CustomHeroUI()
//    {
//        rollClicked = false;
//        host = false; //figure out how to get from into later
//        diceFaces = new ArrayList<>();
//        setLayout(null);
//        readImages();
//        loadFonts();
//        addComponents();
//    }
//
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(loginBackground, 0, 0, 1200, 1000, this);
//        if (rollClicked==false) {
//            g.drawImage(diceFaces.get(0), 550, 120, 100, 100, this);
//            g.drawImage(diceFaces.get(0), 675, 120, 100, 100, this);
//            g.drawImage(diceFaces.get(0), 800, 120, 100, 100, this);
//            g.drawImage(diceFaces.get(0), 925, 120, 100, 100, this);
//            g.drawImage(diceFaces.get(0), 1050, 120, 100, 100, this);
//        }
//    }
//
//    private void addComponents()
//    {
//        DefaultTableModel skillsModel = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        skillsModel.addColumn("Skill");
//        skillsModel.addColumn("Class(es)");
//        skillsModel.addColumn("Effect");
//        JTable skillsTable = new JTable(skillsModel);
//        skillsTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
//        skillsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        JScrollPane skillsScrollPane = new JScrollPane(skillsTable);
//        skillsScrollPane.setBounds(20, 35, 500, 680);
//
//        ListSelectionModel selectionModel = skillsTable.getSelectionModel();
//        selectionModel.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (skillsTable.getSelectedRowCount() > 6) {
//                    //prevent users from selecting more
//                }
//            }
//        });
//        skillsTable.setBounds(20, 35, 500, 680);
//        skillsTable.setOpaque(true);
//
//        JLabel diceMessage = new JLabel("Roll the dice 3 times to determine your hit points and armor class.");
//        diceMessage.setForeground(Color.white);
//        diceMessage.setFont(customFont.deriveFont(25f));
//        diceMessage.setBounds(530, 30, 700, 100);
//
//        JLabel rollingDice = new JLabel("Rolling dice...");
//        rollingDice.setHorizontalAlignment(SwingConstants.CENTER);
//        rollingDice.setForeground(Color.red);
//        rollingDice.setFont(customFont.deriveFont(45f));
//        rollingDice.setBounds(600, 120, 500, 75);
//        rollingDice.setOpaque(true);
//
//        JButton roll = new JButton ("Roll");
//        roll.setForeground(Color.white);
//        roll.setFont(customFont.deriveFont(45f));
//        roll.setBounds(630, 250, 425, 50);
//        roll.setBorder(BorderFactory.createLineBorder(Color.white));
//        buttonFormatting(roll);
//        roll.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                rollClicked = true;
//                rollingDice.setVisible(true);
//                validate();
//                repaint();
//                setVisible(true);
//            }
//        });
//
//        JLabel hitPoints = new JLabel("Hit Points:");
//        hitPoints.setForeground(Color.white);
//        hitPoints.setFont(customFont.deriveFont(45f));
//        hitPoints.setBounds(550, 375, 700, 100);
//
//        JTextField hitPointsText = new JTextField();
//        hitPointsText.setFont(new Font("Arial", Font.PLAIN, 20));
//        hitPointsText.setBounds(800, 375, 350, 75);
//        hitPointsText.setEditable(false);
//        hitPointsText.setText("calculate");
//
//        JLabel armorClass = new JLabel("Armor Class:");
//        armorClass.setForeground(Color.white);
//        armorClass.setFont(customFont.deriveFont(45f));
//        armorClass.setBounds(550, 510, 700, 100);
//
//        JTextField armorClassText = new JTextField();
//        armorClassText.setFont(new Font("Arial", Font.PLAIN, 20));
//        armorClassText.setBounds(800, 510, 350, 75);
//        armorClassText.setEditable(false);
//        armorClassText.setText("calculate");
//
//        JButton back2 = new JButton ("Back");
//        back2.setForeground(new Color(204, 185, 45));
//        back2.setFont(customFont.deriveFont(30f));
//        back2.setBounds(5, 730, 130, 50);
//        back2.setBorder(BorderFactory.createLineBorder(Color.white));
//        buttonFormatting(back2);
//        back2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                removeAll();
//                if (host) {
//                    add(new HostUI()); //dont make new, somehow access previous one
//                    rollClicked=false;
//                    rollingDice.setVisible(false);
//                }
//                else {
//                    add(new JoinUI()); //dont make new, somehow access previous one
//                    rollClicked=false;
//                    rollingDice.setVisible(false);
//                }
//                validate();
//                repaint();
//                setVisible(true);
//            }
//        });
//
//        JButton createHero = new JButton("Create Hero");
//        createHero.setForeground(Color.white);
//        createHero.setFont(customFont.deriveFont(75f));
//        createHero.setBounds(600, 620, 500, 100);
//        createHero.setBorderPainted(false);
//        buttonFormatting(createHero);
//        createHero.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                removeAll();
//                if (host) {
////                    getContentPane().add(hostScreen);
////                    heroClassChoice1.setSelectedIndex(0);
////                    heroClassChoice1.setVisible(false);
////                    custom1.setVisible(false);
////                    customHeroMade1.setVisible(true);
//                }
//                else {
////                    getContentPane().add(joinScreen);
////                    heroClassChoice.setSelectedIndex(0);
////                    heroClassChoice.setVisible(false);
////                    custom.setVisible(false);
////                    customHeroMade.setVisible(true);
//                }
//                validate();
//                repaint();
//                setVisible(true);
//            }
//        });
//
//        add(skillsScrollPane);
//        add(diceMessage);
//        add(roll);
//        add(hitPoints);
//        add(hitPointsText);
//        add(armorClass);
//        add(armorClassText);
//        add(back2);
//        add(createHero);
//        add(rollingDice);
//        rollingDice.setVisible(false);
//    }
//
//    private void buttonFormatting (JButton button)
//    {
//        button.setOpaque(false);
//        button.setContentAreaFilled(false);
//        button.setFocusPainted(false);
//        button.setVisible(true);
//    }
//    private void readImages()
//    {
//        try
//        {
//            loginBackground = ImageIO.read(new File("images/loginScreen.jpg"));
//            diceFaces.add(ImageIO.read(new File("images/dice side.png")));
//            diceFaces.add(ImageIO.read(new File("images/D&D dice_001.png")));
//            diceFaces.add(ImageIO.read(new File("images/D&D dice_002.png")));
//            diceFaces.add(ImageIO.read(new File("images/D&D dice_004.png")));
//            diceFaces.add(ImageIO.read(new File("images/D&D dice_005.png")));
//            diceFaces.add(ImageIO.read(new File("images/D&D dice_006.png")));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadFonts() {
//        try {
//            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Regular.ttf"));
//            customBoldFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Almendra-Bold.ttf"));
//        } catch (IOException | FontFormatException e) {
//            e.printStackTrace();
//        }
//    }
//}