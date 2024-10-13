import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ServerListener implements Runnable {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    public int accessCode;
    static Map<String, Game> currentGames = new HashMap<>();

    public ServerListener(ObjectInputStream i, ObjectOutputStream o) {
        this.is = i;
        this.os = o;
    }

    @Override
    public void run() {
        try {
            while (true) {
                CommandFromClient cfc = (CommandFromClient) is.readObject();

                if (cfc.getCommand() == CommandFromClient.JOIN) {
                    String validationResponse = validPlayer(cfc.getData());

                    if (validationResponse.equals("valid")) {
                        String playerEntry = (String) cfc.getData();
                        String[] playerInfo = playerEntry.split(",");
                        int classType = Integer.parseInt(playerInfo[2]);
                        String characterName = playerInfo[1];

                        Game game = currentGames.get(playerInfo[0]);

                        if (game != null) {
                            Hero newHero = new Hero(classType, characterName, os, 0, 0, 0, 0);
                            setHeroValues(newHero);
                            game.getHeroes().add(newHero);
                            sendCommand(new CommandFromServer(CommandFromServer.MAKE_HERO, game, newHero));
                            for (Hero hero : game.getHeroes()) {
                                if (!hero.heroName.equals(newHero.heroName))
                                    sendCommand(new CommandFromServer(CommandFromServer.NEW_PLAYER, game, newHero), hero.getOs());
                            }
                        }
                    } else if (validationResponse.equals("invalidAccessCode")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_ACCESS_CODE, null, null));
                    } else if (validationResponse.equals("invalidName")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_NAME, null, null));
                    } else if (validationResponse.equals("invalidClass")) {
                        sendCommand(new CommandFromServer(CommandFromServer.INVALID_CLASS, null, null));
                    } else {
                        sendCommand(new CommandFromServer(CommandFromServer.MAX_PLAYERS, null, null));
                    }
                }

                if (cfc.getCommand() == CommandFromClient.HOST) {
                    Random random = new Random();
                    boolean pause = false;
                    accessCode = 0;

                    if (!pause) {
                        accessCode = 100000 + random.nextInt(900000);
                        pause = true;
                    }

                    Game newGame = new Game(null, null);
                    newGame.setAccessCode(String.valueOf(accessCode));
                    List<Map.Entry<Boolean, Integer>> diceList = new ArrayList<>(Arrays.asList(
                            new AbstractMap.SimpleEntry<>(false, random.nextInt(6)),
                            new AbstractMap.SimpleEntry<>(false, random.nextInt(6)),
                            new AbstractMap.SimpleEntry<>(false, random.nextInt(6)),
                            new AbstractMap.SimpleEntry<>(false, random.nextInt(6)),
                            new AbstractMap.SimpleEntry<>(false, random.nextInt(6))
                    ));
                    newGame.setDiceRolled(diceList);
                    newGame.setLevel(1);
                    newGame.setDragons(createDragonsList());

                    String playerEntry = (String) cfc.getData();
                    String[] playerInfo = playerEntry.split(",");
                    int classType = Integer.parseInt(playerInfo[2]);
                    String characterName = playerInfo[1];
                    Hero hostHero = new Hero(classType, characterName, os, 0, 0, 0, 0);
                    setHeroValues(hostHero);

                    newGame.getHeroes().add(hostHero);
                    newGame.setMaxPlayers(Integer.parseInt(playerInfo[0]) + 1);
                    currentGames.put(String.valueOf(accessCode), newGame);

                    sendCommand(new CommandFromServer(CommandFromServer.ACCESS_CODE, newGame, hostHero));
                }

                if (cfc.getCommand() == CommandFromClient.SEND_MESSAGE) {
                    String message = (String) cfc.getData();
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    game.getMessagesChat().add(message);
                    for (Hero hero : game.getHeroes()) {
                        sendCommand(new CommandFromServer(CommandFromServer.DISPLAY_MESSAGE, null, game.getMessagesChat()), hero.getOs());
                    }
                }

                if (cfc.getCommand() == CommandFromClient.PASS_DICE) {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    Random random = new Random();
                    List<Map.Entry<Boolean, Integer>> originalDiceList = (List<Map.Entry<Boolean, Integer>>) cfc.getData();
                    List<Map.Entry<Boolean, Integer>> updatedDiceList = new ArrayList<>();
                    for (Map.Entry<Boolean, Integer> entry : originalDiceList) {
                        Boolean shouldKeep = entry.getKey();
                        Integer value = entry.getValue();
                        int newValue = shouldKeep ? value : random.nextInt(6);
                        updatedDiceList.add(new AbstractMap.SimpleEntry<>(false, newValue));
                    }
                    game.setDiceRolled(updatedDiceList);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.GIVE_DICE, game.getDiceRolled(), null), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.SWITCH_TURN)
                {
                    int turn = (Integer) cfc.getData();
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    if (turn < game.maxPlayers - 1)
                        turn++;
                    else
                        turn = 0;
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.SWITCH_TURN, turn, null), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.GAME_TEXT_DISPLAY)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    String messageGame = (String) cfc.getData();
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.SEND_GAME_MESSAGE, messageGame, null), hero.getOs());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Dragon> createDragonsList() {
        ArrayList<Dragon> gameDragons = new ArrayList<>();
        gameDragons.add(new Dragon ("young red", 45, 3, 8, 1));
        gameDragons.add(new Dragon ("pale", 50, 4, 10, 2));
        gameDragons.add(new Dragon ("young black", 55, 5, 12, 3));
        gameDragons.add(new Dragon ("green", 65, 6, 14, 4));
        gameDragons.add(new Dragon ("red", 80, 8, 16, 5));
        gameDragons.add(new Dragon ("blue", 75, 8, 18, 6));
        gameDragons.add(new Dragon ("undead", 75, 10, 20, 7));
        gameDragons.add(new Dragon ("black", 80, 12, 24, 8));
        return gameDragons;
    }

    private void setHeroValues(Hero hero) {
        switch (hero.classType) {
            case 0: //warrior
                hero.setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Strike", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2)), 5, 0),
                        new Skill("Slash", new ArrayList<>(Arrays.asList(0)), new ArrayList<>(Arrays.asList(3, 3)), 4, 0),
                        new Skill("Smashing Blow", new ArrayList<>(Arrays.asList(0)), new ArrayList<>(Arrays.asList(3, 3, 3)), 6, 0),
                        new Skill("Savage Attack", new ArrayList<>(Arrays.asList(0)), new ArrayList<>(Arrays.asList(3, 3, 3, 3)), 9, 0),
                        new Skill("Parry", new ArrayList<>(Arrays.asList(0)), new ArrayList<>(Arrays.asList(3, 3, 6, 6, 6)), 2, 2),
                        new Skill("Critical Hit", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2, 0, 1)), 7, 0))));
                hero.setTokens(new ArrayList<Token>(Arrays.asList(new Token(0, -35, 235),
                        new Token(0, -35, 285), new Token(0, -35, 335))));
                hero.setHitPoints(23);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(5);
                break;
            case 1:  //wizard
                hero.setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Strike", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2)), 5, 0),
                        new Skill("Magic Bolt", new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(5, 5)), 4, 0),
                        new Skill("Fireball", new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(5, 5, 5)), 6, 0),
                        new Skill("Lightning Storm", new ArrayList<>(Arrays.asList(1, 3)), new ArrayList<>(Arrays.asList(5, 5, 0, 0)), 7, 0),
                        new Skill("Shield", new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(5, 5, 1, 1)), 2, 2),
                        new Skill("Critical Hit", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2, 0, 1)), 7, 0))));
                hero.setTokens(new ArrayList<Token>(Arrays.asList(new Token(0, -60, 210),
                        new Token(0, -60, 260), new Token(0, -60, 310))));
                hero.setHitPoints(22);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(4);
                break;
            case 2:  //cleric
                //holy strike wasn't in dragon guide
                hero.setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Holy Strike", new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(1, 3, 5)), 5, 0),
                        new Skill("Blessing", new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(1)), 0, 3),
                        new Skill("Smite", new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(1, 1)), 4, 0),
                        new Skill("Healing Hands", new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(1, 1, 1)), 6, 1),
                        new Skill("Holy Storm", new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(1, 1, 1, 7, 7)), 7, 0),
                        new Skill("Shield", new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(5, 5, 1, 1)), 2, 2))));
                hero.setTokens(new ArrayList<Token>(Arrays.asList(new Token(0, 17, 265),
                        new Token(0, 17, 315), new Token(0, 17, 365),
                        new Token(1, -105, 295), new Token(1, -105, 345),
                        new Token(1, -105, 395), new Token(1, -105, 445))));
                hero.setHitPoints(24);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(6);
                break;
            case 3:  //ranger
                //wild strike wasn't in dragon guide
                hero.setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Wild Strike", new ArrayList<>(Arrays.asList(3)), new ArrayList<>(Arrays.asList(0, 3, 2)), 5, 0),
                        new Skill("Accurate Shot", new ArrayList<>(Arrays.asList(3)), new ArrayList<>(Arrays.asList(0, 0)), 4, 0),
                        new Skill("Dual Shot", new ArrayList<>(Arrays.asList(3)), new ArrayList<>(Arrays.asList(0, 0, 0)), 7, 0),
                        new Skill("Crossfire", new ArrayList<>(Arrays.asList(3)), new ArrayList<>(Arrays.asList(0, 0, 0, 0)), 9, 0),
                        new Skill("Pin Down", new ArrayList<>(Arrays.asList(3, 4)), new ArrayList<>(Arrays.asList(0, 0, 2, 2)), -1, 2),
                        new Skill("Critical Hit", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2, 0, 1)), 7, 0))));
                hero.setTokens(new ArrayList<Token>(Arrays.asList(new Token(0, -25, 250),
                        new Token(0, -25, 300), new Token(0, -25, 350))));
                hero.setHitPoints(21);
                hero.setArmorClass(1);
                hero.setIncentiveOrder(2);
                break;
            case 4:  //rogue
                //flanking strike wasn't in dragon guide
                hero.setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Strike", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2)), 5, 0),
                        new Skill("Stab", new ArrayList<>(Arrays.asList(4)), new ArrayList<>(Arrays.asList(2, 2)), 4, 0),
                        new Skill("Flanking Strike", new ArrayList<>(Arrays.asList(4)), new ArrayList<>(Arrays.asList(2, 2, 2)), 6, 0),
                        new Skill("Sneak Attack", new ArrayList<>(Arrays.asList(4, 0)), new ArrayList<>(Arrays.asList(2, 2, 3, 3)), 6, 0),
                        new Skill("Sudden Death", new ArrayList<>(Arrays.asList(4)), new ArrayList<>(Arrays.asList(2, 2, 2, 7, 7)), 7, 0),
                        new Skill("Critical Hit", new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), new ArrayList<>(Arrays.asList(3, 5, 2, 0, 1)), 8, 0))));
                hero.setTokens(new ArrayList<Token>(Arrays.asList(new Token(0, -30, 300),
                        new Token(0, -30, 350), new Token(0, -30, 400))));
                hero.setHitPoints(19);
                hero.setArmorClass(1);
                hero.setIncentiveOrder(1);
                break;
        }

        hero.setExp(0);
        hero.setGold(0);
        hero.setAlive(true);
    }

    private String validPlayer(Object data) {
        String playerEntry = (String) data;
        String[] playerInputs = playerEntry.split(",");

        for (Map.Entry<String, Game> entry : currentGames.entrySet()) {
            String key = entry.getKey();
            if (key.equals(playerInputs[0])) {
                Game game = entry.getValue();
                ArrayList<Hero> currentHeroes = game.heroes;
                if (currentHeroes.size() == game.maxPlayers)
                    return "maxPlayersReached";
                for (Hero hero : currentHeroes) {
                    if (hero.heroName.equals(playerInputs[1]))
                        return "invalidName";
                    if (hero.classType == Integer.parseInt(playerInputs[2]))
                        return "invalidClass";
                }
                return "valid";
            }
        }
        return "invalidAccessCode";
    }

    private void sendCommand(CommandFromServer cfs) {
        try {
            os.reset();
            os.writeObject(cfs);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(CommandFromServer cfs, ObjectOutputStream hero_Os) {
        try {
            hero_Os.reset();
            hero_Os.writeObject(cfs);
            hero_Os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
