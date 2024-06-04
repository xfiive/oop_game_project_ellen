package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor implements Switchable {

    private final Switchable device;

    private boolean state;

    public PowerSwitch(@NotNull Switchable device) {
        setAnimation(new Animation("sprites/switch.png", 16, 16));
        this.device = device;
        state = false;
    }

    public Switchable getDevice() {
        return this.device;
    }

    public void switchOn() {
        if (device != null)
            device.turnOn();
    }

    public void switchOff() {
        if (device != null)
            device.turnOff();
    }

    @Override
    public void turnOn() {
        this.state = true;
        getAnimation().setTint(Color.BLUE);
    }

    @Override
    public void turnOff() {
        this.state = false;
        getAnimation().setTint(Color.GRAY);
    }

    @Override
    public boolean isOn() {
        return this.state;
    }
}
