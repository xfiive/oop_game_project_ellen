package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Health;

public class Energy extends AbstractActor implements Usable<Alive>{

    public Energy() {
        setAnimation(new Animation("sprites/energy.png", 16, 16));
    }

    @Override
    public void useWith(Alive actor) {
        if (actor == null )
            return;
        if(actor instanceof Health && actor.getHealth().getValue() <= 0)
            return;
        actor.getHealth().refill(100);
        Scene scene = getScene();
        assert scene != null;
        scene.removeActor(this);
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
