package pacman.monsters;

import ch.aplu.jgamegrid.Location;
import pacman.ActorLocation;
import pacman.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MonsterCreator {

    public List<ActorLocation> createMonsters(GameController gameController, Properties properties) {
        List<ActorLocation> actorLocations = new ArrayList<>();

        actorLocations.add(createTroll(gameController, properties));
        actorLocations.add(createTx5(gameController, properties));
        actorLocations.add(createAlien(gameController, properties));
        actorLocations.add(createOrion(gameController, properties));
        actorLocations.add(createWizard(gameController, properties));
        List<ActorLocation> nonNulActorLocations = new ArrayList<>();
        for (ActorLocation actorLocation: actorLocations) {
            if (actorLocation != null) {
                nonNulActorLocations.add(actorLocation);
            }
        }

        return nonNulActorLocations;
    }

    private ActorLocation createTroll(GameController gameController, Properties properties) {
        String[] locations = properties.getProperty("Troll.location").split(",");
        int locationX = Integer.parseInt(locations[0]);
        int locationY = Integer.parseInt(locations[1]);
        if (locationX <= 0 || locationY <= 0) {
            return null;
        }
        Troll monster = new Troll(gameController);
        return new ActorLocation(monster, new Location(locationX, locationY), Location.NORTH);
    }

    private ActorLocation createTx5(GameController gameController, Properties properties) {
        String[] locations = properties.getProperty("TX5.location").split(",");
        int locationX = Integer.parseInt(locations[0]);
        int locationY = Integer.parseInt(locations[1]);
        if (locationX <= 0 || locationY <= 0) {
            return null;
        }
        Tx5 monster = new Tx5(gameController);
        return new ActorLocation(monster, new Location(locationX, locationY), Location.NORTH);
    }

    private ActorLocation createAlien(GameController gameController, Properties properties) {
        String[] locations = properties.getProperty("Alien.location").split(",");

        int locationX = Integer.parseInt(locations[0]);
        int locationY = Integer.parseInt(locations[1]);
        if (locationX <= 0 || locationY <= 0) {
            return null;
        }

        Alien monster = new Alien(gameController);
        return new ActorLocation(monster, new Location(locationX, locationY), Location.NORTH);
    }

    private ActorLocation createOrion(GameController gameController, Properties properties) {
        String[] locations = properties.getProperty("Orion.location").split(",");

        int locationX = Integer.parseInt(locations[0]);
        int locationY = Integer.parseInt(locations[1]);
        if (locationX <= 0 || locationY <= 0) {
            return null;
        }

        Orion monster = new Orion(gameController);
        return new ActorLocation(monster, new Location(locationX, locationY), Location.NORTH);
    }

    private ActorLocation createWizard(GameController gameController, Properties properties) {
        String[] locations = properties.getProperty("Wizard.location").split(",");

        int locationX = Integer.parseInt(locations[0]);
        int locationY = Integer.parseInt(locations[1]);
        if (locationX <= 0 || locationY <= 0) {
            return null;
        }

        Wizard monster = new Wizard(gameController);
        return new ActorLocation(monster, new Location(locationX, locationY), Location.NORTH);
    }

}
