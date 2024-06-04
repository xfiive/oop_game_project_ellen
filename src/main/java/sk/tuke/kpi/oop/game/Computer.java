package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {

    private boolean status;

    public Computer() {
        Animation computerAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f);
        setAnimation(computerAnimation);
        this.status = false;
    }

    public int add(final int summandFirst, final int summandSecond) {
        if (!this.status)
            return 0;
        return summandFirst + summandSecond;
    }

    public int sub(final int diminishable, final int subtracted) {
        if (!this.status)
            return 0;
        return diminishable - subtracted;
    }

    public float add(final float summandFirst, final float summandSecond) {
        if (!this.status)
            return 0;
        return summandFirst + summandSecond;
    }

    public float sub(final float diminishable, final float subtracted) {
        if (!this.status)
            return 0;
        return diminishable - subtracted;
    }

    @Override
    public void setPowered(boolean status) {
        this.status = status;
    }
}
