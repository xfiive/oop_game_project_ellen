package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {

    public Bullet() {
        setAnimation(new Animation("sprites/bullet.png", 16, 16));
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
        return 4;
    }

    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    private void collisionCheck() {
        List<Actor> actorsList = Objects.requireNonNull(this.getScene()).getActors();

        var aliveActor = actorsList.stream()
            .filter(a -> a.intersects(this) && a instanceof Alive && !(a instanceof Ripley))
            .findFirst()
            .orElse(null);

        if (aliveActor == null)
            return;

        ((Alive) aliveActor).getHealth().drain(15);
        this.collidedWithWall();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::collisionCheck)).scheduleFor(this);
    }
}
