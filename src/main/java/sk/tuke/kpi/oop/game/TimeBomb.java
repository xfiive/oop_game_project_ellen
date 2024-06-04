package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private final Animation activated = new Animation("sprites/bomb_activated.png", 16, 16);
    private final Animation detonated = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);

    private final float time;
    private boolean isActive;


    public TimeBomb(final float time) {
        setAnimation(new Animation("sprites/bomb.png", 16, 16));
        this.time = time;
        this.isActive = false;
    }

    public boolean isActivated() {
        return this.isActive;
    }

    public void setActivated()
    {
        this.isActive = true;
    }

    public void activate() {
        if (this.isActive) {
            return;
        }

        this.isActive = true;

        Scene scene = getScene();
        assert scene != null;

        setAnimation(activated);

        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::detonate),
            new Wait<>(0.1f * 8),
            new Invoke<>(scene::removeActor)
        ).scheduleFor(this);

    }

    public void detonate() {
        setAnimation(detonated);
    }

}
