package pacman;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class ActorLocation {
    private Actor actor;
    private Location location;
    private Location.CompassDirection direction;

    public ActorLocation(Actor actor, Location location, Location.CompassDirection direction) {
        this.actor = actor;
        this.location = location;
        this.direction = direction;
    }

    public Actor getActor() {
        return actor;
    }

    public Location getLocation() {
        return location;
    }

    public Location.CompassDirection getDirection() {
        return direction;
    }
}
