package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Take<T extends Keeper> extends AbstractAction<T> {
    @Override
    public void execute(float deltaTime) {
        var actor = getActor();
        if (actor == null)
            setDone(true);

        try {
            assert actor != null;
            var scene = actor.getScene();
            var actorsList = Objects.requireNonNull(scene).getActors()
                .stream()
                .filter(A -> A instanceof Collectible && A.intersects(getActor()))
                .map(A -> (Collectible) A).findFirst().orElse(null);
            if (actorsList == null)
                return;
            getActor().getBackpack().add(actorsList);
            actor.getScene().removeActor(actorsList);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            setDone(true);
        }
    }
}
