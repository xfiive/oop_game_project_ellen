package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Ammo extends AbstractActor implements Usable<Armed> {
    @Override
    public void useWith(Armed actor) {
        if (actor == null)
            return;
        if (500 < actor.getFirearm().getAmmo())
            return;
        actor.getFirearm().reload(actor.getFirearm().getAmmo() + 50);
        Scene scene = getScene();
        assert scene != null;
        scene.removeActor(this);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
