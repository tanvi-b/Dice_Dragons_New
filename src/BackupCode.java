//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.ArrayList;
//
//public class BackupCode {
//    private BufferedImage background;
//    private ArrayList<BufferedImage> playerRules;
//    private ArrayList<BufferedImage> dragonGuide;
//    playerRules = new ArrayList<>();
//    dragonGuide = new ArrayList<>();
//}
//
//    public void readImages()
//    {
//        try
//        {
//            background = ImageIO.read(new File("images/backgroundImage.png"));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/1.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/2.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/3.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/4.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/5.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/6.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/7.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/8.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/9.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/page10.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/page11.png")));
//            playerRules.add(ImageIO.read(new File("rules/playerRules/page12.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-01.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-02.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-03.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-04.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-05.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-07.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-08.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-09.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-10.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-11.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-12.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-13.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-14.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-15.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-16.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-17.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-18.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-19.png")));
//            dragonGuide.add(ImageIO.read(new File("rules/dragonRules/D&D dragons guide-20.png")));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

//playing ui
//        JLabel pointsText = new JLabel("Hero's Updates");
//        pointsText.setFont(customFont.deriveFont(30f));
//        pointsText.setBounds(10, 75, 360, 95);
//        pointsText.setHorizontalAlignment(JLabel.CENTER);
//        pointsText.setOpaque(true);

//        JList<String> heroUpdates = new JList<>();
//
//        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
//        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        pointsScrollBar.setBounds(10, 190, 360, 280);

//        JLabel dragonsText = new JLabel("Dragon's Updates");
//        dragonsText.setFont(customFont.deriveFont(30f));
//        dragonsText.setBounds(10, 500, 360, 95);
//        dragonsText.setHorizontalAlignment(JLabel.CENTER);
//        dragonsText.setOpaque(true);

//        JList<String> dragonUpdates = new JList<>();
//
//        JScrollPane dragonScrollBar = new JScrollPane(dragonUpdates);
//        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        dragonScrollBar.setBounds(10, 610, 360, 290);

//add(pointsText);
//add(pointsScrollBar);
//add(dragonScrollBar);
//add(dragonsText);

//    private void sendCommand (CommandFromServer cfs) {
//        //sends to all clients
//        for (ObjectOutputStream out : outs) {
//            try {
//                out.writeObject(cfs);
//                out.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }