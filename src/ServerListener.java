import javax.swing.*;
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
                            newHero.items = new ArrayList<MarketItem>();
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
                    hostHero.items = new ArrayList<MarketItem>();

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

                if (cfc.getCommand()==CommandFromClient.REMOVE_BUTTON)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.REMOVE_BUTTON, cfc.getData(), null), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.PLACE_TOKEN)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getData();
                    int heroClass = data.get(0);
                    int tokenNumber = data.get(1);
                    int xCoor = data.get(2);
                    int yCoor = data.get(3);
                    Hero playingHero = null;
                    for (Hero hero: game.getHeroes())
                    {
                        if (hero.classType==heroClass)
                            playingHero = hero;
                    }
                    playingHero.tokens.get(tokenNumber).xCoordinate = xCoor;
                    playingHero.tokens.get(tokenNumber).yCoordinate = yCoor;

                    ArrayList<Hero> updatedHeroes = game.getHeroes();
                    for (int i = 0; i < updatedHeroes.size(); i++) {
                        if (updatedHeroes.get(i).getClassType() == heroClass) {
                            updatedHeroes.set(i, playingHero);
                            break;
                        }
                    }
                    game.setHeroes(updatedHeroes);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.PLACE_TOKEN, game, null), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.ATTACK_DRAGON)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getData();
                    int attackPoints = data.get(0);
                    int level = data.get(1);

                    List<Dragon> dragons = game.getDragons();
                    Dragon dragon = dragons.get(level - 1);
                    int newHitPoints = dragon.hitPoints - attackPoints;
                    if (newHitPoints <= 0) {
                        dragon.setHitPoints(0);
                        dragon.alive=false;
                        //send command for dead dragon
                    } else {
                        dragon.hitPoints = newHitPoints;
                    }
                    dragons.set(level - 1, dragon);
                    game.setDragons((ArrayList<Dragon>) dragons);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.ATTACK_DRAGON, game, dragon), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.INCREASE_ARMOR_CLASS)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getData();
                    int points = data.get(0);
                    int classType = data.get(1);
                    Hero playingHero = null;
                    for (Hero hero: game.getHeroes())
                    {
                        if (hero.classType==classType)
                            playingHero = hero;
                    }
                    playingHero.armorClass += points;
                    ArrayList<Hero> updatedHeroes = game.getHeroes();
                    for (int i = 0; i < updatedHeroes.size(); i++) {
                        if (updatedHeroes.get(i).getClassType() == classType) {
                            updatedHeroes.set(i, playingHero);
                            break;
                        }
                    }
                    game.setHeroes(updatedHeroes);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.INCREASE_ARMOR_CLASS, game, hero), hero.getOs());
                }
                if (cfc.getCommand()==CommandFromClient.INCREASE_HP)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getData();
                    int points = data.get(0);
                    int classType = data.get(1);
                    Hero playingHero = null;
                    for (Hero hero: game.getHeroes())
                    {
                        if (hero.classType==classType)
                            playingHero = hero;
                    }
                    playingHero.hitPoints += points;
                    ArrayList<Hero> updatedHeroes = game.getHeroes();
                    for (int i = 0; i < updatedHeroes.size(); i++) {
                        if (updatedHeroes.get(i).getClassType() == classType) {
                            updatedHeroes.set(i, playingHero);
                            break;
                        }
                    }
                    game.setHeroes(updatedHeroes);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.INCREASE_HP, game, hero), hero.getOs());
                }
                if (cfc.getCommand()==CommandFromClient.CHECK_DRAGON_DICE)
                {
                    ArrayList<String> data = (ArrayList<String>) cfc.getPlayer();
                    Game game = currentGames.get(data.get(0));
                    String playerName = data.get(1);
                    int gameLevel = Integer.parseInt(data.get(2)) - 1;
                    List<Map.Entry<Boolean, Integer>> diceList = (List<Map.Entry<Boolean, Integer>>) cfc.getData();
                    int amtDragonDice = 0;
                    for (int i = 0; i< diceList.size(); i++)
                        if (diceList.get(i).getValue()==4)
                            amtDragonDice++;
                    int dragonAttackPoints = 0;
                    if (amtDragonDice==1)
                        dragonAttackPoints = game.getDragons().get(gameLevel).playerSkills.get(0).amtEffect;
                    if (amtDragonDice==2)
                        dragonAttackPoints = game.getDragons().get(gameLevel).playerSkills.get(1).amtEffect;
                    if (amtDragonDice>=3)
                        dragonAttackPoints = game.getDragons().get(gameLevel).playerSkills.get(2).amtEffect;

                    Hero playingHero = null;
                    for (Hero hero: game.getHeroes())
                    {
                        if (hero.heroName.equals(playerName))
                            playingHero = hero;
                    }
                    dragonAttackPoints -= playingHero.armorClass;
                    playingHero.hitPoints -= dragonAttackPoints;
                    ArrayList<Hero> updatedHeroes = game.getHeroes();
                    for (int i = 0; i < updatedHeroes.size(); i++) {
                        if (updatedHeroes.get(i).getHeroName().equals(playerName)) {
                            updatedHeroes.set(i, playingHero);
                            break;
                        }
                    }
                    game.setHeroes(updatedHeroes);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.USED_DRAGON_DICE, game, hero), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.DRAGON_ATTACK)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    Random random = new Random();
                    List<Map.Entry<Boolean, Integer>> originalDiceList = (List<Map.Entry<Boolean, Integer>>) cfc.getData();
                    List<Map.Entry<Boolean, Integer>> updatedDiceList = new ArrayList<>();

                    for (Map.Entry<Boolean, Integer> entry : originalDiceList) {
                        Boolean shouldKeep = entry.getKey();
                        Integer value = entry.getValue();
                        int newValue = shouldKeep ? value : random.nextInt(6);
                        if (newValue == 4)
                            shouldKeep = true;
                        updatedDiceList.add(new AbstractMap.SimpleEntry<>(shouldKeep, newValue));
                    }
                    game.setDiceRolled(updatedDiceList);
                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.DRAGON_ATTACK, game, null), hero.getOs());
                }

                if (cfc.getCommand()==CommandFromClient.DRAGON_ATTACK_FINAL)
                {
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getPlayer();
                    Game game = currentGames.get(String.valueOf(data.get(0)));

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

                    int amtDragonDice = 0;
                    for (int i = 0; i< updatedDiceList.size(); i++)
                        if (updatedDiceList.get(i).getValue()==4)
                            amtDragonDice++;

                    int dragonAttackPoints = 0;
                    if (amtDragonDice==1)
                        dragonAttackPoints = game.getDragons().get(data.get(1)-1).playerSkills.get(0).amtEffect;
                    if (amtDragonDice==2)
                        dragonAttackPoints = game.getDragons().get(data.get(1)-1).playerSkills.get(1).amtEffect;
                    if (amtDragonDice>=3)
                        dragonAttackPoints = game.getDragons().get(data.get(1)-1).playerSkills.get(2).amtEffect;

                    for (Hero hero: game.getHeroes()) {
                        dragonAttackPoints -= hero.armorClass;
                        hero.hitPoints -= dragonAttackPoints;
                    }

                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.DRAGON_ATTACK_FINAL, game, hero), hero.getOs());
                }

                if(cfc.getCommand() == CommandFromClient.GO_TO_MARKET){
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getPlayer();
                    Game game = currentGames.get(String.valueOf(data.get(0)));
                    Dragon defeatedDragon = game.dragons.get(data.get(1)-1);
                    int totalGold = defeatedDragon.gold;
                    int totalXp = defeatedDragon.exp;
                    ArrayList<Hero> heroes = game.getHeroes();
                    int numHeroes = heroes.size();
                    int goldPerHero = totalGold / numHeroes;
                    int xpPerHero = totalXp / numHeroes;
                    for (Hero hero : heroes) {
                        hero.gold += goldPerHero;
                        hero.exp += xpPerHero;
                    }
                    int remainingGold = totalGold % numHeroes;
                    int remainingXp = totalXp % numHeroes;

                    for (int i = 0; i < remainingGold; i++)
                        heroes.get(i).gold++;

                    for (int i = 0; i < remainingXp; i++)
                        heroes.get(i).exp++;

                    for (Hero hero : game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.GO_TO_MARKET, data.get(1), hero), hero.getOs());
                }

                if(cfc.getCommand() == CommandFromClient.FLEE){
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    for (Hero hero : game.getHeroes()) {
                        if (hero.heroName.equals(cfc.getData())) {
                            hero.flee= true;
                            break;
                        }
                    }

                    boolean everyoneFled = true;
                    for (Hero hero : game.getHeroes()) {
                        if (hero.flee==false) {
                            everyoneFled = false;
                            break;
                        }
                    }

                    if (everyoneFled) {
                        for(Hero hero: game.getHeroes())
                            sendCommand(new CommandFromServer(CommandFromServer.EVERYONE_FLEE, null, null), hero.getOs());
                    } else {
                        for(Hero hero: game.getHeroes())
                            sendCommand(new CommandFromServer(CommandFromServer.FLEE, game, null), hero.getOs());
                    }
                }

                if(cfc.getCommand() == CommandFromClient.NO_FLEE){
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    for(Hero hero: game.getHeroes()) {
                        hero.flee = false;
                    }
                    for(Hero hero: game.getHeroes()) {
                        sendCommand(new CommandFromServer(CommandFromServer.NO_FLEE, game, null), hero.getOs());
                    }
                }
                if (cfc.getCommand() == CommandFromClient.BUY_ITEM) {
                    ArrayList<Integer> data = (ArrayList<Integer>) cfc.getPlayer();
                    Game game = currentGames.get(String.valueOf(data.get(0)));
                    Dragon dragon = game.dragons.get(data.get(1)-1);
                    MarketItem item = dragon.items.get(data.get(2));
                    Hero playingHero = null;
                    for (Hero hero: game.getHeroes())
                    {
                        if (hero.heroName.equals(cfc.getData()))
                            playingHero = hero;
                    }
                    if (playingHero.items!=null && playingHero.items.size()==2)
                        sendCommand(new CommandFromServer(CommandFromServer.TOO_MANY_ITEMS, null, null), playingHero.getOs());
                    else if (playingHero.gold<item.gold)
                        sendCommand(new CommandFromServer(CommandFromServer.NOT_ENOUGH_GOLD, null, null), playingHero.getOs());
                    else
                    {
                        playingHero.items.add(item);
                        playingHero.gold-=item.gold;
                        sendCommand(new CommandFromServer(CommandFromServer.SUCCESSFUL_PURCHASE, null, playingHero), playingHero.getOs());
                    }
                }
                if (cfc.getCommand()==CommandFromClient.READY_NEXT){
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    for (Hero hero : game.getHeroes()) {
                        if (hero.heroName.equals(cfc.getData())) {
                            hero.readyForNextDragon= true;
                            break;
                        }
                    }

                    boolean everyoneReady = true;
                    for (Hero hero : game.getHeroes()) {
                        if (hero.readyForNextDragon==false) {
                            everyoneReady = false;
                            break;
                        }
                    }

                    if (everyoneReady) {
                        for(Hero hero: game.getHeroes())
                            sendCommand(new CommandFromServer(CommandFromServer.EVERYONE_READY_NEXT, null, null), hero.getOs());
                    }
                }
                if (cfc.getCommand()==CommandFromClient.JAB)
                {
                    Game game = currentGames.get(String.valueOf(cfc.getPlayer()));
                    List<Dragon> dragons = game.getDragons();
                    int level = (Integer) cfc.getData();
                    Dragon dragon = game.dragons.get(level-1);
                    int newHitPoints = dragon.hitPoints - 2;
                    if (newHitPoints <= 0) {
                        dragon.setHitPoints(0);
                        dragon.alive=false;
                        //send command for dead dragon
                    } else {
                        dragon.hitPoints = newHitPoints;
                    }
                    dragons.set(level - 1, dragon);
                    game.setDragons((ArrayList<Dragon>) dragons);
                    for(Hero hero: game.getHeroes())
                        sendCommand(new CommandFromServer(CommandFromServer.JAB_USED, null, dragon), hero.getOs());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Dragon> createDragonsList() {
        ArrayList<Dragon> gameDragons = new ArrayList<>();
        gameDragons.add(new Dragon ("young red", 45, 3, 8, 1));
        gameDragons.get(0).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Slashing Claws", null, null, 5, 0),
                new Skill("Tail Strike", null, null, 7, 0),
                new Skill("Fire Breath", null, null, 10, 0))));
        gameDragons.get(0).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Small Healing Potion", 0, true, 4, 1, 2),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 1),
                new MarketItem("Haste Potion", 3, true, -1, 1, 1),
                new MarketItem("Holy Water", 4, true, -1, 1, 1),
                new MarketItem("Mana Potion", 4, true, -1, 1, 1))));

        gameDragons.add(new Dragon ("pale", 50, 4, 10, 2));
        gameDragons.get(1).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Brutal Stomp", null, null, 6, 0),
                new Skill("Winged Attack", null, null, 9, 0),
                new Skill("White Inferno", null, null, 12, 0))));
        gameDragons.get(1).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Small Healing Potion", 0, true, 4, 1, 1),
                new MarketItem("Healing Potion", 0, true, 7, 1, 1),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 1),
                new MarketItem("Haste Potion", 3, true, -1, 1, 2),
                new MarketItem("Vision Portion", 4, true, -1, 1, 1),
                new MarketItem("Mana Portion", 4, true, -1, 1, 1),
                new MarketItem("Stealth Potion", 4, true, -1, 1, 1),
                new MarketItem("Steel Shield", 1, false, 1, 2, 1))));

        gameDragons.add(new Dragon ("young black", 55, 5, 12, 3));
        gameDragons.get(2).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Reaping Jaws", null, null, 6, 0),
                new Skill("Tail Strike", null, null, 10, 0),
                new Skill("Strike From Above", null, null, 13, 0))));
        gameDragons.get(2).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Small Healing Potion", 0, true, 4, 1, 1),
                new MarketItem("Healing Potion", 0, true, 7, 1, 2),
                new MarketItem("Holy Water", 4, true, -1, 1, 1),
                new MarketItem("Stealth Potion", 4, true, -1, 1, 1),
                new MarketItem("Steel Shield", 1, false, 1, 2, 1),
                new MarketItem("Magic Shield", 1, false, 2, 4, 1),
                new MarketItem("Magic Sword", 4, false, -1, 5, 1),
                new MarketItem("Pinpoint Crossbow", 4, false, -1, 5, 1))));

        gameDragons.add(new Dragon ("green", 65, 6, 14, 4));
        gameDragons.get(3).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Bite Attack", null, null, 6, 0),
                new Skill("Slashing Claws", null, null, 10, 0),
                new Skill("Green Inferno", null, null, 13, 0))));
        gameDragons.get(3).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Healing Potion", 0, true, 7, 2, 1),
                new MarketItem("Great Healing Potion", 0, true, 9, 3, 1),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 1),
                new MarketItem("Strength Potion", 4, true, -1, 1, 2),
                new MarketItem("Great Haste Portion", 3, true, -1, 2, 1),
                new MarketItem("Blessed Hammer", 4, false, -1, 1, 1),
                new MarketItem("Gauntlets of Power", 5, false, 1, 1, 1),
                new MarketItem("Magic Staff", 4, false, -1, 2, 1))));

        gameDragons.add(new Dragon ("red", 80, 8, 16, 5));
        gameDragons.get(4).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Slashing Claws", null, null, 7, 0),
                new Skill("Tail Strike", null, null, 11, 0),
                new Skill("Red Inferno", null, null, 15, 0))));
        gameDragons.get(4).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Healing Potion", 0, true, 7, 2, 1),
                new MarketItem("Great Healing Potion", 0, true, 9, 3, 1),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 1),
                new MarketItem("Strength Potion", 4, true, -1, 1, 2),
                new MarketItem("Great Haste Portion", 3, true, -1, 2, 1),
                new MarketItem("Blessed Hammer", 4, false, -1, 1, 1),
                new MarketItem("Gauntlets of Power", 5, false, 1, 1, 1),
                new MarketItem("Magic Staff", 4, false, -1, 2, 1))));

        gameDragons.add(new Dragon ("blue", 75, 8, 18, 6));
        gameDragons.get(5).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("A Cold One", null, null, 6, 0),
                new Skill("Winged Attack", null, null, 10, 0),
                new Skill("Cold Inferno", null, null, 13, 0))));
        gameDragons.get(5).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Healing Potion", 0, true, 7, 2, 1),
                new MarketItem("Great Healing Potion", 0, true, 9, 3, 1),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 1),
                new MarketItem("Holy Water", 4, true, -1, 1, 2),
                new MarketItem("Great Haste Portion", 3, true, -1, 2, 1),
                new MarketItem("Magic Sword", 4, false, -1, 1, 1))));

        gameDragons.add(new Dragon ("undead", 75, 10, 20, 7));
        gameDragons.get(6).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Brutal Stomp", null, null, 7, 0),
                new Skill("Winged Attack", null, null, 11, 0),
                new Skill("Death From Above", null, null, 14, 0))));
        gameDragons.get(6).setItems(new ArrayList<MarketItem>(Arrays.asList(
                new MarketItem("Healing Potion", 0, true, 7, 2, 2),
                new MarketItem("Great Healing Potion", 0, true, 9, 3, 1),
                new MarketItem("Scroll of Knowledge", 2, true, -1, 1, 2),
                new MarketItem("Stealth Potion", 4, true, -1, 1, 2),
                new MarketItem("Great Haste Portion", 3, true, -1, 2, 1),
                new MarketItem("Vision Portion", 4, true, -1, 1, 1),
                new MarketItem("Gauntlets of Power", 5, false, 1, 4, 1),
                new MarketItem("Staff of Healing", 5, false, 1, 4, 1))));

        gameDragons.add(new Dragon ("black", 80, 12, 24, 8));
        gameDragons.get(7).setPlayerSkills(new ArrayList<Skill>(Arrays.asList(new Skill("Reaping Jaws", null, null, 7, 0),
                new Skill("Tail Strike", null, null, 13, 0),
                new Skill("Black Inferno", null, null, 18, 0))));
        //no market items

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
