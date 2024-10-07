import java.io.*;
import java.net.Socket;
import java.util.*;

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
                    newGame.setDiceRolled(new ArrayList<>(Arrays.asList(random.nextInt(6), random.nextInt(6),
                            random.nextInt(6), random.nextInt(6), random.nextInt(6))));
                    newGame.setLevel(0);
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
                    game.setDiceRolled(new ArrayList<>(Arrays.asList(random.nextInt(6), random.nextInt(6),
                            random.nextInt(6), random.nextInt(6), random.nextInt(6))));
                    for (Hero hero : game.getHeroes()) {
                        sendCommand(new CommandFromServer(CommandFromServer.GIVE_DICE, game.getDiceRolled(), null), hero.getOs());
                    }
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
                //hero.setPlayerSkills(new Skill());
                hero.setHitPoints(23);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(5);
                break;
            case 1:  //wizard
                //hero.setPlayerSkills();
                hero.setHitPoints(22);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(4);
                break;
            case 2:  //cleric
                //hero.setPlayerSkills();
                hero.setHitPoints(24);
                hero.setArmorClass(0);
                hero.setIncentiveOrder(6);
                break;
            case 3:  //ranger
                //hero.setPlayerSkills();
                hero.setHitPoints(21);
                hero.setArmorClass(1);
                hero.setIncentiveOrder(2);
                break;
            case 4:  //rogue
                //hero.setPlayerSkills();
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
