package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.framework.actions.Rotate;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Helicopter extends AbstractActor {

    private static final int STEP = 1;

    public Helicopter() {
        setAnimation(new Animation("sprites/heli.png", 64, 64, 0.1f, Animation.PlayMode.LOOP));
    }

    // aka Metallica's "Seek And Destroy" ^_^
    public void searchAndDestroy() {
        new Loop<>(new Invoke<>(this::seekAndDestroy)).scheduleFor(this);
    }

    public void seekAndDestroy() {
        Scene scene = getScene();
        if (scene == null) return;

        Player player = scene.getFirstActorByType(Player.class);
        assert player != null;

        int playerX = player.getPosX();
        int playerY = player.getPosY();
        int oldX = getPosX();
        int oldY = getPosY();

        int rotation = calculateRotation(playerX, playerY);
        if (rotation != getAnimation().getRotation()) {
            rotateTo(rotation);
        }

        moveTowardsPlayer(playerX, playerY);

        handleCollision(oldX, oldY, player);
    }

    private int calculateRotation(int playerX, int playerY) {
        if (playerX < this.getPosX()) {
            setPosition(getPosX() - STEP, getPosY());
        } else if (playerY < getPosY()) {
            setPosition(getPosX(), getPosY() - STEP);
        } else if (playerY > getPosY()) {
            setPosition(getPosX(), getPosY() + STEP);
        } else if (playerX > getPosX()) {
            setPosition(getPosX() + STEP, getPosY());
        }
        return (int) getAnimation().getRotation();
    }

    private void rotateTo(int rotation) {
        new Rotate<>(rotation, 0.1f).scheduleFor(this);
    }

    private void moveTowardsPlayer(int playerX, int playerY) {
        if (playerX < getPosX()) {
            setPosition(getPosX() - STEP, getPosY());
        } else if (playerY < getPosY()) {
            setPosition(getPosX(), getPosY() - STEP);
        } else if (playerY > getPosY()) {
            setPosition(getPosX(), getPosY() + STEP);
        } else if (playerX > getPosX()) {
            setPosition(getPosX() + STEP, getPosY());
        }
    }

    private void handleCollision(int oldX, int oldY, Player player) {
        if (Objects.requireNonNull(getScene()).getMap().intersectsWithWall(this)) {
            setPosition(oldX, oldY);
        }

        if (intersects(player)) {
            player.setEnergy(player.getEnergy() - 1);
        }
    }
}
