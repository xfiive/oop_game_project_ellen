package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private final Animation vDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private final Animation hDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private final Orientation orientation;
    private final String name;
    private boolean isOpen;

    public Door(String name, Orientation orientation) {
        this.orientation = orientation;
        this.name = name;
        this.isOpen = false;
        if (orientation == Orientation.VERTICAL) {
            setAnimation(this.vDoorAnimation);
            this.vDoorAnimation.stop();
        } else {
            setAnimation(this.hDoorAnimation);
            this.hDoorAnimation.stop();
        }
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void open() {
        if (this.isOpen)
            return;
        this.isOpen = true;
        var scene = getScene();
        assert scene != null;
        if (orientation == Orientation.HORIZONTAL) {
            this.hDoorAnimation.setFrameDuration(0.1f);
            this.hDoorAnimation.setPlayMode(Animation.PlayMode.ONCE);
            setAnimation(this.hDoorAnimation);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            scene.getMap().getTile(getPosX() / 16 + 1, getPosY() / 16).setType(MapTile.Type.CLEAR);
        } else {
            this.vDoorAnimation.setFrameDuration(0.1f);
            this.vDoorAnimation.setPlayMode(Animation.PlayMode.ONCE);
            setAnimation(this.vDoorAnimation);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        }
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        if (!this.isOpen)
            return;
        this.isOpen = false;
        var scene = getScene();
        assert scene != null;
        if (orientation == Orientation.VERTICAL) {
            this.vDoorAnimation.setFrameDuration(0.1f);
            this.vDoorAnimation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
            setAnimation(this.vDoorAnimation);
            this.vDoorAnimation.play();

        } else {
            this.hDoorAnimation.setFrameDuration(0.1f);
            this.hDoorAnimation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16 + 1, getPosY() / 16).setType(MapTile.Type.WALL);
            setAnimation(this.hDoorAnimation);
            this.hDoorAnimation.play();
        }
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (orientation == Orientation.HORIZONTAL) {
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16 + 1, getPosY() / 16).setType(MapTile.Type.WALL);
        } else {
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
    }

    @Override
    public void useWith(Actor actor) {
        if (this.isOpen) {
            close();
        } else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
