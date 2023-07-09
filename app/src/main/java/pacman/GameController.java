package pacman;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import pacman.monsters.Monster;
import pacman.models.MonsterCreator;
import pacman.monsters.Tx5;
import pacman.utility.GameCallback;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class GameController {
    private int nbHorzCells = 20;
    private int nbVertCells = 11;
    protected PacManGameGrid grid = new PacManGameGrid(nbHorzCells, nbVertCells);
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
    private ArrayList<Actor> iceCubes = new ArrayList<Actor>();
    private ArrayList<Actor> goldPieces = new ArrayList<Actor>();

    private boolean hasPacmanBeenHit = false;
    private boolean hasPacmanEatAllPills = false;

    private Properties properties;
    private int seed = 30006;
    private ArrayList<Location> propertyPillLocations = new ArrayList<>();
    private ArrayList<Location> propertyGoldLocations = new ArrayList<>();

    protected PacActor pacActor;

    private GameCallback gameCallback;
    private List<Monster> monsters = new ArrayList<>();
    private List<ActorLocation> monstersLocation = new ArrayList<>();

    public GameController(int nbHorzCells, int nbVertCells, Properties properties, GameCallback gameCallback) {
        this.properties = properties;
        this.nbHorzCells = nbHorzCells;
        this.nbVertCells = nbVertCells;
        this.gameCallback = gameCallback;

        pacActor = new PacActor(this);

        //Setup for auto test
        pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
        pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
        loadPillAndItemsLocations();

        MonsterCreator monsterCreator = new MonsterCreator();
        this.monstersLocation = monsterCreator.createMonsters(this, properties);
        for (ActorLocation monsterLocation: this.monstersLocation) {
            Actor actor = monsterLocation.getActor();
            if (actor instanceof Monster) {
                Monster monster = (Monster) actor;
                this.monsters.add(monster);
            }
        }
        //Setup Random seeds
        seed = Integer.parseInt(properties.getProperty("seed"));
        pacActor.setSeed(seed);
        for (Monster monster: monsters) {
            monster.setSeed(seed);
            monster.setSlowDown(3);
            if (monster instanceof Tx5) {
                Tx5 tx5 = (Tx5) monster;
                tx5.stopMoving(5);
            }
        }

        pacActor.setSlowDown(3);
    }

    public PacActor getPacActor() {
        return pacActor;
    }

    public void stopMonsters() {
        for (Monster monster: monsters) {
            monster.setStopMoving(true);
        }
    }

    public boolean isNormal(Location location) {
        int a = grid.getCell(location);
        return a > 0;
    }

    public boolean isPill(Location location) {
        int a = grid.getCell(location);
        return a == 1 && propertyPillLocations.size() == 0;
    }

    public boolean isGold(Location location) {
        int a = grid.getCell(location);
        return a == 3 && propertyGoldLocations.size() == 0;
    }

    public boolean isIce(Location location) {
        int a = grid.getCell(location);
        return a == 4;
    }

    public boolean isWall(Location location) {
        int a = grid.getCell(location);
        return a == 0;
    }

    public GameCallback getGameCallback() {
        return gameCallback;
    }

    public ArrayList<Location> getPropertyGoldLocations() {
        return propertyGoldLocations;
    }

    public ArrayList<Location> getPropertyPillLocations() {
        return propertyPillLocations;
    }

    public boolean isHasPacmanBeenHit() {
        return hasPacmanBeenHit;
    }

    public boolean isHasPacmanEatAllPills() {
        return hasPacmanEatAllPills;
    }

    public boolean hasEndGame() {
        return hasPacmanBeenHit || hasPacmanEatAllPills;
    }

    public void checkEndGame() {
        int maxPillsAndItems = countPillsAndItems();
        for (Monster monster: monsters) {
            hasPacmanBeenHit = hasPacmanBeenHit || monster.getLocation().equals(pacActor.getLocation());
        }
        hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
    }


    private int countPillsAndItems() {
        int pillsAndItemsCount = 0;
        for (int y = 0; y < nbVertCells; y++)
        {
            for (int x = 0; x < nbHorzCells; x++)
            {
                Location location = new Location(x, y);
                int a = grid.getCell(location);
                if (a == 1 && propertyPillLocations.size() == 0) { // Pill
                    pillsAndItemsCount++;
                } else if (a == 3 && propertyGoldLocations.size() == 0) { // Gold
                    pillsAndItemsCount++;
                }
            }
        }
        if (propertyPillLocations.size() != 0) {
            pillsAndItemsCount += propertyPillLocations.size();
        }

        if (propertyGoldLocations.size() != 0) {
            pillsAndItemsCount += propertyGoldLocations.size();
        }

        return pillsAndItemsCount;
    }

    public ArrayList<Location> getPillAndItemLocations() {
        return pillAndItemLocations;
    }


    private void loadPillAndItemsLocations() {
        String pillsLocationString = properties.getProperty("Pills.location");
        if (pillsLocationString != null) {
            String[] singlePillLocationStrings = pillsLocationString.split(";");
            for (String singlePillLocationString: singlePillLocationStrings) {
                String[] locationStrings = singlePillLocationString.split(",");
                propertyPillLocations.add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
            }
        }

        String goldLocationString = properties.getProperty("Gold.location");
        if (goldLocationString != null) {
            String[] singleGoldLocationStrings = goldLocationString.split(";");
            for (String singleGoldLocationString: singleGoldLocationStrings) {
                String[] locationStrings = singleGoldLocationString.split(",");
                propertyGoldLocations.add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
            }
        }
    }

    public void setupPillAndItemsLocations() {
        for (int y = 0; y < nbVertCells; y++)
        {
            for (int x = 0; x < nbHorzCells; x++)
            {
                Location location = new Location(x, y);
                int a = grid.getCell(location);
                if (a == 1 && propertyPillLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                if (a == 3 &&  propertyGoldLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                if (a == 4) {
                    pillAndItemLocations.add(location);
                }
            }
        }


        if (propertyPillLocations.size() > 0) {
            for (Location location : propertyPillLocations) {
                pillAndItemLocations.add(location);
            }
        }
        if (propertyGoldLocations.size() > 0) {
            for (Location location : propertyGoldLocations) {
                pillAndItemLocations.add(location);
            }
        }
    }


    public List<ActorLocation> getActorLocations() {
        String[] pacManLocations = this.properties.getProperty("PacMan.location").split(",");
        int pacManX = Integer.parseInt(pacManLocations[0]);
        int pacManY = Integer.parseInt(pacManLocations[1]);

        List<ActorLocation> actorLocations = new ArrayList<>();
        actorLocations.add(new ActorLocation(pacActor, new Location(pacManX, pacManY), Location.EAST));
        actorLocations.addAll(monstersLocation);
        return actorLocations;
    }

    private void angerMonsters() {
        for (Monster monster: monsters) {
            monster.angerMonster();
        }
    }

    private void freezeMonsters() {
        for (Monster monster: monsters) {
            monster.freezeMonster();
        }
    }

    public void removeItem(String type, Location location){
        if(type.equals("gold")){
            for (Actor item : this.goldPieces){
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                    angerMonsters();
                }
            }
        }else if(type.equals("ice")){
            for (Actor item : this.iceCubes){
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                    freezeMonsters();
                }
            }
        }
    }

    public ArrayList<Actor> getGoldPieces() {
        return goldPieces;
    }
    public void putGold(Actor gold) {
        this.goldPieces.add(gold);
    }

    public void putIce(Actor ice) {
        this.iceCubes.add(ice);
    }

    public int getNumHorzCells(){
        return this.nbHorzCells;
    }
    public int getNumVertCells(){
        return this.nbVertCells;
    }
}
