package pacman;


import ch.aplu.jgamegrid.GGKeyRepeatListener;
import ch.aplu.jgamegrid.Location;

import java.awt.event.KeyEvent;

class PacmanController implements GGKeyRepeatListener {
    private PacActor pacActor;
    public PacmanController(PacActor pacActor) {
        this.pacActor = pacActor;
    }

    public void keyRepeated(int keyCode)
    {
        if (pacActor.isAuto()) {
            return;
        }
        if (pacActor.isRemoved())  // Already removed
            return;
        Location next = null;
        switch (keyCode)
        {
            case KeyEvent.VK_LEFT:
                next = pacActor.getLocation().getNeighbourLocation(Location.WEST);
                pacActor.setDirection(Location.WEST);
                break;
            case KeyEvent.VK_UP:
                next = pacActor.getLocation().getNeighbourLocation(Location.NORTH);
                pacActor.setDirection(Location.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                next = pacActor.getLocation().getNeighbourLocation(Location.EAST);
                pacActor.setDirection(Location.EAST);
                break;
            case KeyEvent.VK_DOWN:
                next = pacActor.getLocation().getNeighbourLocation(Location.SOUTH);
                pacActor.setDirection(Location.SOUTH);
                break;
        }
        if (next != null && pacActor.canMove(next))
        {
            pacActor.setLocation(next);
            pacActor.eatPill(next);
        }
    }

}
