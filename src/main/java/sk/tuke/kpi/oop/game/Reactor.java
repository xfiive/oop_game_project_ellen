package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private final Animation normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Set<EnergyConsumer> devices;
    private int temperature;
    private int damage;
    private boolean status;

    public Reactor() {
        Animation basicAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(basicAnimation);
        this.temperature = 0;
        this.damage = 0;
        this.status = false;
        this.devices = new HashSet<>();
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(final int newTemperature) {
        this.temperature = newTemperature;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(final int newDamage) {
        this.damage = newDamage;
    }

    public void increaseTemperature(int temperatureGrowthRate) {
        if (temperatureGrowthRate < 0 || !this.isOn()) {
            return;
        }

        if (this.damage < 33) {
            this.temperature += temperatureGrowthRate;
        } else if (this.damage < 67) {
            this.temperature += (int) ceil((1.5) * temperatureGrowthRate);
        } else {
            this.temperature += 2 * temperatureGrowthRate;
        }

        if (2000 < this.temperature)
            this.damage = (this.temperature - 2000) / 40;

        if (100 <= this.getDamage()) {
            turnOff();
            this.damage = 100;
        }

        this.updateAnimation();
    }

    public void decreaseTemperature(int temperatureDropRate) {
        if (temperatureDropRate < 0 || !this.isOn()) {
            return;
        }

        if (50 < this.damage && this.damage < 100)
            this.temperature -= round((float) temperatureDropRate / 2);
        else if (this.damage <= 50)
            this.temperature -= temperatureDropRate;

        if (this.temperature < 0)
            this.temperature = 0;

        updateAnimation();
    }

    @Override
    public void turnOn() {
        if (100 <= this.damage)
            return;
        this.status = true;
        if (this.devices != null)
            devices.forEach(d -> d.setPowered(true));
    }

    @Override
    public void turnOff() {
        this.status = false;
        if (this.devices != null)
            devices.forEach(d -> d.setPowered(false));
    }

    @Override
    public boolean isOn() {
        return this.status;
    }

    public void addDevice(@NotNull EnergyConsumer device) {
        if (this.damage <= 100) {
            device.setPowered(isOn());
            this.devices.add(device);
        }
    }

    public void removeDevice(@NotNull EnergyConsumer device) {
        if (this.damage <= 100 && this.devices != null && devices.contains(device)) {
            device.setPowered(false);
            this.devices.remove(device);
        }
    }

    public boolean extinguish() {
        if (this.temperature <= 4000)
            return false;

        this.temperature -= 4000;

        updateAnimation();
        return true;
    }

    @Override
    public boolean repair() {
        if (damage <= 0)
            return false;

        if (damage <= 50)
            damage = 0;
        else
            damage -= 50;

        this.temperature = (40 * this.damage) + 2000;

        updateAnimation();
        return true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }

    private void updateAnimation() {
        if (6000 <= this.temperature || 100 <= damage) {
            setAnimation(this.brokenAnimation);
        } else if (4000 <= this.temperature) {
            setAnimation(this.hotAnimation);
        } else {
            setAnimation(this.normalAnimation);
        }
    }
}
