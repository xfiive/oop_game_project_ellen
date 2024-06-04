package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;


public class ShooterController implements KeyboardListener {

    private Armed actor;

    public ShooterController(Armed shooter) {
        this.actor = shooter;
    }

    @Override
    public void keyPressed(@NotNull Input.Key inputKey) {
        if (inputKey == Input.Key.SPACE) {
            new Fire<>().scheduleFor(this.actor);
        } else {
            return;
        }
    }
}
