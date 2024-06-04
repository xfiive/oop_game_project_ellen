package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {

    private Teleport destinationTeleport;
    private boolean teleported;

    public Teleport(Teleport destinationTeleport) {
        setAnimation(new Animation("sprites/lift.png", 48, 48));
        if (destinationTeleport != this)
            this.destinationTeleport = destinationTeleport;
        this.teleported = false;
    }

    public Teleport getDestination() {
        return destinationTeleport;
    }

    public void setDestination(Teleport destinationTeleport) {
        if(destinationTeleport != this)
            this.destinationTeleport = destinationTeleport;
    }

    private boolean doesXIntersects(@NotNull Scene scene) {
        Player player = scene.getFirstActorByType(Player.class);

        if (player == null)
            return false;

        int playerCenterX = player.getPosX() + player.getWidth() / 2;
        int teleportEndX = this.getPosX() + this.getWidth();

        return (this.getPosX() <= playerCenterX && playerCenterX <= teleportEndX);
    }

    private boolean doesYIntersects(@NotNull Scene scene) {
        Player player = scene.getFirstActorByType(Player.class);

        if (player == null)
            return false;

        int playerCenterY = player.getPosY() + player.getWidth() / 2;
        int teleportEndY = this.getPosY() + this.getWidth();

        return (this.getPosY() <= playerCenterY && playerCenterY <= teleportEndY);
    }

    public void prepareForTeleportation() {
        Scene scene = getScene();
        if (scene == null)
            return;

        if (this.destinationTeleport == null)
            return;

        if (!doesXIntersects(scene) || !doesYIntersects(scene)) {
            this.teleported = false;
            return;
        }

        Player player = scene.getFirstActorByType(Player.class);

        if (player == null)
            return;

        if (!teleported && getDestination() != null) {
            getDestination().teleportPlayer(player);
        }
    }

    public void teleportPlayer(@NotNull Player player) {
        player.setPosition(this.getPosX() + 8, this.getPosY() + 8);
        this.teleported = true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::prepareForTeleportation)).scheduleFor(this);
    }

}
