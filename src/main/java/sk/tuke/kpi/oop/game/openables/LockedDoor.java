package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean status;

    public LockedDoor() {
        super("locked door", Orientation.VERTICAL);
        this.status = true;
    }

    @Override
    public void useWith(Actor actor) {
        if (this.isOpened())
            return;
        super.useWith(actor);
    }

    public boolean isOpened() {
        return this.status;
    }

    public void unlock()
    {
        super.open();
        this.status = false;
    }
}


