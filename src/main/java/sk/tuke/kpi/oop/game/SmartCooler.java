package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private final Reactor reactor;

    public SmartCooler(@NotNull Reactor reactor) {
        super(reactor);
        this.reactor = reactor;
    }

    public void coolActivation() {
        this.reactor.decreaseTemperature(1);
        this.turnOn();
    }

    public void cool() {
        if (this.reactor == null)
            return;

        if (2500 <= this.reactor.getTemperature()) {
            coolActivation();
        } else if (1500 <= this.reactor.getTemperature()) {
            this.reactor.decreaseTemperature(1);
        } else if (this.reactor.getTemperature() < 1500) {
            this.turnOff();
        }

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addToScene(scene);
        new Loop<>(new Invoke<>(this::cool)).scheduleFor(this);
    }

}



