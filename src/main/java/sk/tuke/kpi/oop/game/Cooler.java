package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Cooler extends AbstractActor implements Switchable {

    private final Reactor reactor;
    private boolean status;

    public Cooler(Reactor reactor) {
        setAnimation(new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
        this.reactor = reactor;
        this.status = true;
    }

    public void turnOn() {
        this.status = true;
    }

    public void turnOff() {
        this.status = false;
    }

    public boolean isOn() {
        return this.status;
    }

    public void coolReactor() {
        if (this.reactor != null && isOn()) {
            this.reactor.decreaseTemperature(1);
        }
    }

    public void addToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

}
