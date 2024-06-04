package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {

    private final Animation lightOnAnimation = new Animation("sprites/light_on.png", 16, 16);
    private final Animation lightOffAnimation = new Animation("sprites/light_off.png", 16, 16);
    private boolean currentState;
    private boolean flow;

    public Light() {
        setAnimation(lightOffAnimation);
        currentState = false;
    }

    public void toggle() {
        this.currentState = !this.isOn();
    }

    public boolean isFlow() {
        return this.flow;
    }

    public void setElectricityFlow(boolean flow) {
        this.flow = flow;
    }

    @Override
    public void turnOn() {
        this.currentState = true;
        if (this.flow)
            setAnimation(lightOnAnimation);
        else
            setAnimation(lightOffAnimation);
    }

    @Override
    public void turnOff() {
        this.currentState = false;
        setAnimation(lightOffAnimation);
    }

    @Override
    public boolean isOn() {
        return this.currentState;
    }

    @Override
    public void setPowered(boolean status) {
        setElectricityFlow(status);
        if (this.currentState && status)
            setAnimation(lightOnAnimation);
        else
            setAnimation(lightOffAnimation);
    }
}
