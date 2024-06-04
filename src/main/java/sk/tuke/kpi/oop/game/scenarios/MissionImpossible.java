package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class MissionImpossible implements SceneListener {


    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        scene.follow(ripley);
        MovableController movableController = new MovableController(ripley);
        Input input = scene.getInput();
        input.registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        input.registerListener(keeperController);
    }

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (type == null || name == null)
                return null;

            if (name.equals("ellen"))
                return new Ripley();
            if (name.equals("energy"))
                return new Energy();
            if (type.equals("door"))
                return new Door("door", Door.Orientation.HORIZONTAL);

            return null;
        }
    }
}
