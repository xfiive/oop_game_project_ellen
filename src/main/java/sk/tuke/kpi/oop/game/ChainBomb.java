package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ChainBomb extends TimeBomb {
    private final float time;
    private final Animation activated = new Animation("sprites/bomb_activated.png", 16, 16);
    private boolean isActive;

    public ChainBomb(float time) {
        super(time);
        this.time = time;
    }

    public void activate() {
        if (!this.isActive) {
            this.isActive = true;
            super.setActivated();

            Scene scene = getScene();
            assert scene != null;

            setAnimation(activated);

            new ActionSequence<>(
                new Wait<>(this.time),
                new Invoke<>(this::activateNearbyChainBombs),
                new Invoke<>(super::detonate),
                new Wait<>(0.1f * 8),
                new Invoke<>(scene::removeActor)
            ).scheduleFor(this);
        }
    }

    private void activateNearbyChainBombs() {
        Scene scene = getScene();
        assert scene != null;
        for (Actor actor : scene.getActors()) {
            if (actor instanceof ChainBomb && actor != this) {
                boolean shouldDetonate = isIntersected((ChainBomb) actor);
                if (shouldDetonate) {
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }

    private boolean isIntersected(@NotNull ChainBomb bombSleeping) {
        Ellipse2D blastEllipse = new Ellipse2D.Float(this.getPosX() + 8 - 52, this.getPosY() + 8 - 52, 104, 104);
        Rectangle2D sleepingBombBorders = new Rectangle(bombSleeping.getPosX(), bombSleeping.getPosY(), 16, 16);
        return blastEllipse.intersects(sleepingBombBorders);
    }
}
