package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {

    public FireExtinguisher() {
        super();
        setAnimation(new Animation("sprites/extinguisher.png", 16, 16));
        setUses(1);
    }


    public void useWith(Reactor reactor) {
        if (reactor != null && reactor.extinguish())
            super.useWith(reactor);
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }


}
