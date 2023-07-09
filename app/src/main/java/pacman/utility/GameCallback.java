package pacman.utility;

import ch.aplu.jgamegrid.Location;
import pacman.monsters.Monster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Please do not change this class. This class is used for testing and your code needs to print the correct output to pass the test
 */
public class GameCallback {
    StringBuilder logResult;

    public GameCallback() {
        logResult = new StringBuilder();
    }

    public void writeString(String str) {
        logResult.append(str);
        logResult.append("\n");
    }

    public void endOfGame(String gameResult) {
        writeString(gameResult);
    }

    public void pacManLocationChanged(Location pacmanLocation, int score, int nbPills) {
        String pacmanLocationString = String.format("[PacMan] Location: %d-%d. Score: %d. Pills: %d", pacmanLocation.getX(),
                pacmanLocation.getY(), score, nbPills);
        writeString(pacmanLocationString);
    }

    public void monsterLocationChanged(Monster monster) {
        String monsterLocationString = String.format("[%s] Location: %d-%d", monster.getType(),
                monster.getLocation().getX(), monster.getLocation().getY());
        writeString(monsterLocationString);
    }

    public void pacManEatPillsAndItems(Location pacmanLocation, String type) {
        String pillOrItemLocationString = String.format("[PacMan] Location: %d-%d. Eat Pill/Item: %s", pacmanLocation.getY(),
                pacmanLocation.getY(), type);
        writeString(pillOrItemLocationString);
    }

    public String getResultLog() {
        return logResult.toString();
    }
}
