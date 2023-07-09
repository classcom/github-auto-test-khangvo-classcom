// Monster.java
// Used for PacMan
package pacman.monsters;

import ch.aplu.jgamegrid.*;
import pacman.GameController;
import pacman.models.MonsterState;
import pacman.models.MonsterType;

import java.awt.Color;
import java.util.*;

public abstract class Monster extends Actor
{
  protected GameController gameController;
  protected ArrayList<Location> visitedList = new ArrayList<Location>();
  protected final int listLength = 10;
  protected boolean stopMoving = false;
  protected int seed = 0;
  protected Random randomiser = new Random(0);
  private MonsterState monsterState;
  private Timer currentTimer = null;
  int SECOND_TO_MILLISECONDS = 1000;

  public Monster(GameController gameController, String imageName)
  {
    super(imageName);
    this.gameController = gameController;
    monsterState = MonsterState.Normal;
  }

  public void stopMoving(int seconds) {
    this.stopMoving = true;
    if (currentTimer != null) {
      currentTimer.cancel();
    }

    currentTimer = new Timer(); // Instantiate Timer Object
    final Monster monster = this;
    currentTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
        currentTimer = null;
        monsterState = MonsterState.Normal;
      }
    }, seconds * SECOND_TO_MILLISECONDS);
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

  public void act()
  {
    if (stopMoving) {
      return;
    }
    moveNext();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  public void freezeMonster() {
    monsterState = MonsterState.Frozen;
    stopMoving(3);
  }

  public void angerMonster() {
    if (monsterState == MonsterState.Frozen) {
      return;
    }

    if (currentTimer != null) {
      currentTimer.cancel();
    }
    
    monsterState = MonsterState.Furious;
    currentTimer = new Timer(); // Instantiate Timer Object
    final Monster monster = this;
    currentTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.monsterState = MonsterState.Normal;
        currentTimer = null;
      }
    }, 3 * SECOND_TO_MILLISECONDS);
  }
  protected void moveNext() {
    if (monsterState != MonsterState.Furious) {
      Location next = walkApproach();
      setLocation(next);
      gameController.getGameCallback().monsterLocationChanged(this);
      addVisitedList(next);
    } else {
      // try to move 2 cells at once otherwise wait
      Location next = walkApproach();
      double direction = getLocation().getDirectionTo(next);
      Location nextNext = next.getNeighbourLocation(direction);
      if (canMove(nextNext)) {
        setLocation(nextNext);
        gameController.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
      }
    }
  }


  protected Location getNextRandomLocation() {

    double oldDirection = getDirection();
    Location next;

    // Random walk
    int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
    setDirection(oldDirection);
    turn(sign * 90);  // Try to turn left/right
    next = getNextMoveLocation();
    if (canMove(next))
    {
      return next;
    }
    else {
      setDirection(oldDirection);
      next = getNextMoveLocation();
      if (canMove(next)) // Try to move forward
      {
        return next;
      } else {
        setDirection(oldDirection);
        turn(-sign * 90);  // Try to turn right/left
        next = getNextMoveLocation();
        if (canMove(next)) {
          return next;
        } else {

          setDirection(oldDirection);
          turn(180);  // Turn backward
          next = getNextMoveLocation();
          return next;
        }
      }
    }
  }

  protected abstract Location walkApproach();
  public abstract MonsterType getType();

  protected void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  protected boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }

  protected boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if (c.equals(Color.gray) || location.getX() >= gameController.getNumHorzCells()
          || location.getX() < 0 || location.getY() >= gameController.getNumVertCells() || location.getY() < 0)
      return false;
    else
      return true;
  }
}
