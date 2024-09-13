//public class LobbyUI extends JFrame {
//  public LobbyUI(){
//    JPanel lobbyScreen = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.drawImage(loginBackground, 0, 0, 1400, 1000, this);
//            }
//        };
//        lobbyScreen.setLayout(null);
//
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
//
//        playersModel.addColumn("Username");
//        JTable playersTable = new JTable(playersModel);
//        playersTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
//        JScrollPane playersScrollPane = new JScrollPane(playersTable);
//        playersScrollPane.setBounds(450, 300, 300, 400);
//
//        //timer - after all players have joined show a jlabel saying starting game in 3 2 1
//        //lead to playing screen
//
//        lobbyScreen.add(playersJoined);
//        lobbyScreen.add(accessCodeShow);
//        lobbyScreen.add(playersScrollPane);
//
//  }
//}
