package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {

    private boolean isBlinking;

    public DefectiveLight() {
        super();
        this.isBlinking = true;
    }

    private void changeAnimation() {
        if (!this.isBlinking)
            return;
        if ((int) (Math.random() * 20) % 2 == 1) {
            if (super.isOn()) {
                super.turnOff();
            } else {
                super.turnOn();
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::changeAnimation)).scheduleFor(this);
    }

    public boolean repair() {
        if (!this.isBlinking) {
            return false;
        }
        this.isBlinking = false;

        new ActionSequence<>(new Wait<>(10), new Invoke<>(this::setBlinking)).scheduleFor(this);

        return true;
    }

    public void setBlinking() {
        this.isBlinking = true;
    }
}
