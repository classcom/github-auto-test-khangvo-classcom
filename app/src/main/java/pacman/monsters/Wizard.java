package pacman.monsters;


import ch.aplu.jgamegrid.Location;
import pacman.GameController;
import pacman.models.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class Wizard extends Monster {
    public Wizard(GameController gameController) {
        super(gameController, "sprites/" + MonsterType.Wizard.getImageName());
    }

    @Override
    public MonsterType getType() {
        return MonsterType.Wizard;
    }
    @Override
    protected Location walkApproach() {
        Location currentLocation = getLocation();
        List<Location> neighbors = getNeighborhood(currentLocation, 1);

        while (true) {
            int randomIndex = randomiser.nextInt(neighbors.size());
            Location selectedLocation = neighbors.get(randomIndex);

            if (canMove(selectedLocation)) {
                return selectedLocation;
            } else if (isWall(selectedLocation)) {
                Location adjacentLocation = selectedLocation.getNeighbourLocation(
                        currentLocation.getCompassDirectionTo(selectedLocation));

                if (!isWall(adjacentLocation) && canMove(adjacentLocation)) {
                    return adjacentLocation;
                }
            }

            neighbors.remove(randomIndex);
            if (neighbors.isEmpty()) {
                break;
            }
        }

        return getNextRandomLocation();
    }

    private boolean isWall(Location location) {
        return gameController.isWall(location);
    }

    private List<Location> getNeighborhood(Location location, int radius) {
        List<Location> neighbors = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Location neighbor = new Location(location.getX() + dx, location.getY() + dy);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
}