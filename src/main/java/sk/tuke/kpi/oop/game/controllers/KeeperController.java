package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class KeeperController implements KeyboardListener {

    private final Keeper actor;

    public KeeperController(Keeper actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key inputKey) {
        switch (inputKey) {
            case S:
                new Shift<>().scheduleFor(this.actor);
                break;
            case BACKSPACE:
                new Drop<>().scheduleFor(this.actor);
                break;
            case ENTER:
                new Take<>().scheduleFor(this.actor);
                break;
            case U:
                Usable<?> item = Objects.requireNonNull(this.actor.getScene()).getActors().stream()
                    .filter(this.actor::intersects)
                    .filter(Usable.class::isInstance)
                    .map(Usable.class::cast).findFirst().orElse(null);
                if (item == null)
                    return;
                new Use<>(item).scheduleForIntersectingWith(this.actor);
                break;
            case B:
                Collectible firstItem = this.actor.getBackpack().peek();
                if (firstItem == null)
                    return;
                if (firstItem instanceof Usable)
                    new Use<>((Usable<?>) firstItem).scheduleForIntersectingWith(this.actor);
                break;
            default:
                return;
        }
    }
}
