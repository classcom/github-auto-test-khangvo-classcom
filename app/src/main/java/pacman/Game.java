// PacMan.java
// Simple PacMan implementation
package pacman;

import ch.aplu.jgamegrid.*;
import pacman.utility.GameCallback;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  private GameController gameController;
  private GameCallback gameCallback;

  public Game(GameCallback gameCallback, Properties properties)
  {
    //Setup game
    super(nbHorzCells, nbVertCells, 20, false);
    this.gameCallback = gameCallback;
    gameController = new GameController(nbHorzCells, nbVertCells, properties, gameCallback);
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    GGBackground bg = getBg();
    drawGrid(bg);

    addKeyRepeatListener(gameController.getPacActor().getPacmanController());
    setKeyRepeatPeriod(150);
    setupActorLocations();

    //Run the game
    doRun();
    show();
    gameController.setupPillAndItemsLocations();

    do {
      gameController.checkEndGame();

      delay(10);
    } while(!gameController.hasEndGame());
    delay(120);

    Location loc = gameController.getPacActor().getLocation();
    gameController.stopMonsters();
    gameController.getPacActor().removeSelf();

    String title = "";
    if (gameController.isHasPacmanBeenHit()) {
      bg.setPaintColor(Color.red);
      title = "GAME OVER";
      addActor(new Actor("sprites/explosion3.gif"), loc);
    } else if (gameController.isHasPacmanEatAllPills()) {
      bg.setPaintColor(Color.yellow);
      title = "YOU WIN";
    }
    setTitle(title);
    gameCallback.endOfGame(title);

    doPause();
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }


  private void setupActorLocations() {
    List<ActorLocation> actorLocations = gameController.getActorLocations();
    for (ActorLocation actorLocation: actorLocations) {
      addActor(actorLocation.getActor(), actorLocation.getLocation(), actorLocation.getDirection());
    }
  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        boolean isNormal = gameController.isNormal(location);
        boolean isPill = gameController.isPill(location);
        boolean isGold = gameController.isGold(location);
        boolean isIce = gameController.isIce(location);
        if (isNormal)
          bg.fillCell(location, Color.lightGray);
        if (isPill)
          putPill(bg, location);
        if (isGold)
          putGold(bg, location);
        if (isIce)
          putIce(bg, location);
      }
    }

    for (Location location : gameController.getPropertyPillLocations()) {
      putPill(bg, location);
    }

    for (Location location : gameController.getPropertyGoldLocations()) {
      putGold(bg, location);
    }
  }

  private void putPill(GGBackground bg, Location location){
    bg.fillCircle(toPoint(location), 5);
  }

  private void putGold(GGBackground bg, Location location){
    bg.setPaintColor(Color.yellow);
    bg.fillCircle(toPoint(location), 5);
    Actor gold = new Actor("sprites/gold.png");
    gameController.putGold(gold);
    addActor(gold, location);
  }

  private void putIce(GGBackground bg, Location location){
    bg.setPaintColor(Color.blue);
    bg.fillCircle(toPoint(location), 5);
    Actor ice = new Actor("sprites/ice.png");
    gameController.putIce(ice);
    addActor(ice, location);
  }

}
