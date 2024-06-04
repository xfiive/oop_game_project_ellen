package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<T extends Movable> implements Action<T> {

    private Direction direction;
    private float duration;
    private float elapsedTime;
    private boolean done;
    private T actor;
    private boolean executeRunning;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.actor = null;
        this.done = false;
        this.executeRunning = false;
        this.elapsedTime = 0;
    }

    public Move(Direction direction) {
        this.direction = direction;
        this.duration = 0;
        this.elapsedTime = 0;
    }

    @Nullable
    @Override
    public T getActor() {
        return this.actor;
    }

    @Override
    public void setActor(@Nullable T actor) {
        this.actor = actor;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void stop() {
        if (this.actor == null)
            return;
        if (this.getActor() == null)
            return;
        this.actor.stoppedMoving();
        this.reset();
        setDone(true);
    }

    @Override
    public void execute(float deltaTime) {
        if (this.actor == null) return;

        this.elapsedTime += deltaTime;
        if (this.isDone()) {
            return;
        }

        if ((this.elapsedTime - this.duration) >= 1e-5) {
            this.stop();
            return;
        }

        if (this.done)
            return;

        if (!this.executeRunning) {
            this.actor.startedMoving(this.direction);
            this.executeRunning = true;
        }

        int previousDx = this.actor.getPosX();
        int previousDy = this.actor.getPosY();
        int dx = this.actor.getPosX() + (this.direction.getDx() * this.actor.getSpeed());
        int dy = this.actor.getPosY() + (this.direction.getDy() * this.actor.getSpeed());
        this.actor.setPosition(dx, dy);
        if (Objects.requireNonNull(actor.getScene()).getMap().intersectsWithWall(actor)) {
            this.actor.setPosition(previousDx, previousDy);
            this.actor.collidedWithWall();
        }
    }

    @Override
    public void reset() {
        this.done = false;
    }
}
