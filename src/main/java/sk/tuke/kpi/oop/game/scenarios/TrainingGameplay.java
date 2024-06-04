package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Game;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class TrainingGameplay extends Scenario {

    @Override
    public void setupPlay(@NotNull Scene scene) {
        sceneUpdating(scene);
        Ripley ripley = new Ripley();
        scene.addActor(ripley, 120, 80);
        sceneInitialized(scene);
        Input input = scene.getInput();
        MovableController movableController = new MovableController(ripley);
        input.registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        input.registerListener(keeperController);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Overlay overlay = scene.getOverlay();
        Game game = scene.getGame();
        final int wHeight = game.getWindowSetup().getHeight();
        final int textYPos = wHeight - GameApplication.STATUS_LINE_OFFSET;
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        game.pushActorContainer(ripley.getBackpack());
        overlay.drawText("Energy: " + ripley.getEnergy(), 120, textYPos);
    }
}
