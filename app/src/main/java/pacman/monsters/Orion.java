package pacman.monsters;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import pacman.GameController;
import pacman.models.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class Orion extends Monster {

    private Location dest;
    private List<Location> visited = new ArrayList<>();

    public Orion(GameController gameController) {
        super(gameController, "sprites/" + MonsterType.Orion.getImageName());
    }

    @Override
    public MonsterType getType() {
        return MonsterType.Orion;
    }

    @Override
    protected Location walkApproach() {

        List<Actor> unvisited = new ArrayList<>();
        List<Actor> unvisitedGold = new ArrayList<>();

        if (dest != null && !getLocation().equals(dest)) {
            Location.CompassDirection compassDir =
                    getLocation().get4CompassDirectionTo(dest);
            setDirection(compassDir);
            Location next = getNextMoveLocation();
            if (!isVisited(next) && canMove(next)) {
                return next;
            }
            return getNextRandomLocation();
        }


        Actor destActor;
        if (dest != null && getLocation().equals(dest)) {
            visited.add(dest);
            dest = null;
            destActor = null;
            unvisited.remove(destActor);
            unvisitedGold.remove(destActor);
        }

        // information expert: game class knows all locations of gold pieces
        List<Actor> goldPieces = gameController.getGoldPieces();

        for (Actor actor : goldPieces) {
            if (!visited.contains(actor.getLocation())) {
                unvisited.add(actor);
                if (actor.getIdVisible() != -1) {
                    unvisitedGold.add(actor);
                }
            }
        }

        // if have visited all, revisit
        if (unvisited.size() == 0) {
            visited.clear();
        }

        // prefer locations still have gold
        if (unvisitedGold.size() > 0) {
            int i = randomiser.nextInt(unvisitedGold.size());
            dest = unvisitedGold.get(i).getLocation();
            destActor = unvisitedGold.get(i);
        } else {
            int i = randomiser.nextInt(unvisited.size());
            dest = unvisited.get(i).getLocation();
            destActor = unvisited.get(i);
        }

        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(dest);
        setDirection(compassDir);

        Location next;
        next = getNextMoveLocation();
        if (!isVisited(next) && canMove(next)) {
            return next;
        }
        return getNextRandomLocation();
    }
}
