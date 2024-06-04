package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {

    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("player die", Ripley.class);
    private int energy;
    private int ammo;
    private Backpack backpack;
    private Health health;
    private Firearm gun;

    public Ripley() {
        setAnimation(
            new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        this.energy = 100;
        this.ammo = 0;
        this.backpack = new Backpack("Ripley's Backpack", 10);
        this.health = new Health(100, 100);
        this.gun = new Gun(0, 500);
        health.onFatigued(() -> {
                var scene = getScene();
                if (scene == null) return;
                scene.getMessageBus().publish(RIPLEY_DIED, this);
                setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
            }
        );
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getEnergy() {
        return this.health.getValue();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        if (this.energy <= 0) {
            setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
            Objects.requireNonNull(this.getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    @Override
    public void startedMoving(Direction direction) {
        this.getAnimation().play();
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        this.getAnimation().pause();
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Backpack getBackpack() {
        if (this.backpack == null)
            return null;
        return this.backpack;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm firearm) {
        this.gun = firearm;
    }
}
