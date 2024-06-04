package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviorus.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {

    private final Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        this.health = new Health(100, 100);
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        this.health = new Health(healthValue);
        this.behaviour = behaviour;
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    private void reduceIfNotEnemy() {
        List<Actor> actorList = Objects.requireNonNull(this.getScene()).getActors();

        for (Actor actor : actorList) {
            if (actor.intersects(this) && !(actor instanceof Enemy) && actor instanceof Alive) {
                ((Alive) actor).getHealth().drain(3);
            }
        }
    }

    public Behaviour<? super Alien> getBehaviour() {
        return behaviour;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::reduceIfNotEnemy)).scheduleFor(this);
    }
}
