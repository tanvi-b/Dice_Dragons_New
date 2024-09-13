public class PlayingUI extends JFrame {
  Game game; 
  private ArrayList<BufferedImage> playerRules;
  private ArrayList<BufferedImage> dragonGuide;
  
  public PlayingUI(){
    JPanel playingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, 1400, 1000, this);
            }
        };
        playingScreen.setLayout(null);
    JLabel turn = new JLabel("Turn: ");
        turn.setFont(customFont.deriveFont(60f));
        turn.setBounds(450, 75, 500, 90);
        turn.setOpaque(true);

        JButton rules = new JButton("Rules");
        rules.setFont(customFont.deriveFont(30f));
        rules.setBounds(970, 80, 80, 80);
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        rules.setOpaque(true);

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(playerRules);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JButton guide = new JButton("Guide");
        guide.setFont(customFont.deriveFont(30f));
        guide.setBounds(1070, 80, 80, 80);
        guide.setBorder(BorderFactory.createLineBorder(Color.black));
        guide.setOpaque(true);

        guide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(dragonGuide);
                validate();
                repaint();
                setVisible(true);
            }
        });

        JLabel pointsText = new JLabel("Hero's Updates");
        pointsText.setFont(customFont.deriveFont(30f));
        pointsText.setBounds(10, 75, 360, 95);
        pointsText.setHorizontalAlignment(JLabel.CENTER);
        pointsText.setOpaque(true);

        JList<String> heroUpdates = new JList<>();

        JScrollPane pointsScrollBar = new JScrollPane(heroUpdates);
        pointsScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pointsScrollBar.setBounds(10, 190, 360, 280);

        JLabel dragonsText = new JLabel("Dragon's Updates");
        dragonsText.setFont(customFont.deriveFont(30f));
        dragonsText.setBounds(10, 500, 360, 95);
        dragonsText.setHorizontalAlignment(JLabel.CENTER);
        dragonsText.setOpaque(true);

        JList<String> dragonUpdates = new JList<>();

        JScrollPane dragonScrollBar = new JScrollPane(heroUpdates);
        dragonScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dragonScrollBar.setBounds(10, 610, 360, 280);

        JList<String> messages = new JList<>();
        JScrollPane chatBox = new JScrollPane(messages);
        chatBox.setBounds(870,720, 310,150);

        JTextField messageText = new JTextField();
        messageText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        messageText.setBounds(870, 880, 240, 65);
        messageText.setBackground(Color.white);
        messageText.setOpaque(true);

        JButton send = new JButton("Send");
        send.setFont(customFont.deriveFont(20f));
        send.setBounds(1115, 880, 65, 65);

        send.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String acc = characterName + ": " + messageText.getText();
                ///will need to implement following method
                //sendMessage(os,name+ ": " + input.getText());
                messageText.setText("");
            }
        });

        playingScreen.add(turn);
        playingScreen.add(rules);
        playingScreen.add(guide);
        playingScreen.add(pointsText);
        playingScreen.add(pointsScrollBar);
        playingScreen.add(dragonScrollBar);
        playingScreen.add(dragonsText);
        playingScreen.add(chatBox);
        playingScreen.add(messageText);
        playingScreen.add(send);
    
  }
}
