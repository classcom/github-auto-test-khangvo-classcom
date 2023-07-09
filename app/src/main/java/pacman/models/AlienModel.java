package pacman.models;

import ch.aplu.jgamegrid.Location;
import pacman.GameController;

import java.util.ArrayList;

public class AlienModel extends MonsterModel {

    public AlienModel(GameController gameController) {
        super(gameController);
    }

    @Override
    public MonsterType getType() {
        return MonsterType.Alien;
    }

//    @Override
//    public Location walkApproach() {
//
//        Location pacLocation = gameController.getPacActor().getLocation();
//        ArrayList<Location> locations = pacLocation.getNeighbourLocations(1.5);
//
//        double shortestDistance = Integer.MAX_VALUE;
//        Location dest = null;
//        for (Location location : locations) {
//            double distance = location.getDistanceTo(getLocation());
//            if (distance < shortestDistance) {
//                shortestDistance = distance;
//                dest = location;
//            }
//        }
//        Location.CompassDirection compassDir =
//                getLocation().get4CompassDirectionTo(dest);
//        Location next = getLocation().getNeighbourLocation(compassDir);
//        setDirection(compassDir);
//        if (
//                !isVisited(next) && canMove(next))
//        {
//            return next;
//        }
//        return getNextRandomLocation();
//    }
}
