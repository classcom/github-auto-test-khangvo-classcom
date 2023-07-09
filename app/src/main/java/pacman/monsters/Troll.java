package pacman.monsters;

import ch.aplu.jgamegrid.Location;
import pacman.GameController;
import pacman.models.MonsterType;

public class Troll extends Monster {
    public Troll(GameController gameController) {
        super(gameController, "sprites/m_troll.gif");
    }

    public MonsterType getType() {
        return MonsterType.Troll;
    }

    protected Location walkApproach() {
        return getNextRandomLocation();
    }
}
