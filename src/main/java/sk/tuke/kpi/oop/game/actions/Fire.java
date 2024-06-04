package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire<A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        setDone(true);

        if (getActor() == null || getActor().getScene() == null) {
            return;
        }

        var fireable = getActor().getFirearm().fire();
        if (fireable == null)
            return;

        int posX = Objects.requireNonNull(Direction.fromAngle(getActor().getAnimation().getRotation())).getDx();
        int posY = Objects.requireNonNull(Direction.fromAngle(getActor().getAnimation().getRotation())).getDy();

        if (posX == 0 && posY == 0)
            return;

        getActor().getScene().addActor(fireable, getActor().getPosX() + 10 + posX * 24, getActor().getPosY() + 10 + posY * 24);

        var direction = Direction.fromAngle(getActor().getAnimation().getRotation());
        if (direction == null)
            return;

        fireable.startedMoving(direction);
        new Move<Fireable>(direction, Float.MAX_VALUE).scheduleFor(fireable);
    }
}
