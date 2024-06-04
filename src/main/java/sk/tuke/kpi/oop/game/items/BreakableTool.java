package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {

    private int uses;

    public BreakableTool() {
        this.uses = 1;
    }

    public BreakableTool(int uses) {
        this.uses = uses;
    }


    public int getRemainingUses() {
        return this.uses;
    }

    public void setUses(final int uses) {
        this.uses = uses;
    }

    public void reduceUses() {
        this.uses -= 1;
    }

    @Override
    public void useWith(A actor) {
        if (actor == null)
            return;

        reduceUses();
        if (getRemainingUses() <= 0) {
            Scene scene = getScene();
            if (scene != null)
                scene.removeActor(this);
        }
    }
}
