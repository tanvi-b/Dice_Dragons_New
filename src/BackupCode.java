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