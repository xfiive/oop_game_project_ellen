package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop<T extends Keeper> extends AbstractAction<T> {
    @Override
    public void execute(float deltaTime) {
        var actor = getActor();
        if (actor == null) {
            setDone(true);
            return;
        }
        var scene = actor.getScene();
        if (scene == null) {
            setDone(true);
            return;
        }
        var item = actor.getBackpack().peek();
        if (item == null) {
            setDone(true);
            return;
        }

        int itemPosX = (actor.getPosX() + actor.getWidth() / 2) - item.getWidth() / 2;
        int itemPosY = (actor.getPosY() + actor.getHeight() / 2) - item.getHeight() / 2;
        scene.addActor(item, itemPosX, itemPosY);
        actor.getBackpack().remove(item);

        setDone(true);
    }
}
