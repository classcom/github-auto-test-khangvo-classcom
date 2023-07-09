package pacman.monsters;

import ch.aplu.jgamegrid.Location;
import pacman.GameController;
import pacman.models.MonsterType;

public class Tx5 extends Monster {
    public Tx5(GameController gameController) {
        super(gameController, "sprites/m_tx5.gif");
    }

    public MonsterType getType() {
        return MonsterType.TX5;
    }
    protected Location walkApproach() {
        Location pacLocation = gameController.getPacActor().getLocation();
        // Walking approach:
        // TX5: Determine direction to pacActor and try to move in that direction. Otherwise, random walk.
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);
        if (!isVisited(next) && canMove(next))
        {
            return next;
        }
        // otherwise random walk
        return getNextRandomLocation();
    }
}
