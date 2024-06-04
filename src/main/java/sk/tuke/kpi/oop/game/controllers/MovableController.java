package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashMap;
import java.util.Map;

public class MovableController implements KeyboardListener {
    private final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private final Movable actor;
    private final Map<Input.Key, Boolean> keyPressed = new HashMap<>(Map.ofEntries(
        Map.entry(Input.Key.UP, false),
        Map.entry(Input.Key.DOWN, false),
        Map.entry(Input.Key.LEFT, false),
        Map.entry(Input.Key.RIGHT, false)
    ));
    private Direction lastDirection = Direction.NONE;
    private Move<Movable> move;

    public MovableController(Movable actor) {
        this.actor = actor;
        this.move = null;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (keyPressed.containsKey(key)) {
            keyPressed.put(key, true);
            parseKey();
        }
    }

    private void parseKey() {
        Direction combined = keyPressed.keySet().stream()
            .filter(keyPressed::get)
            .map(keyDirectionMap::get)
            .reduce(Direction.NONE, Direction::combine);

        if (lastDirection != combined) {
            if (move != null)
                move.stop();

            move = new Move<>(combined, 1000f);
            lastDirection = combined;
            move.scheduleFor(actor);
        }
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if (keyPressed.containsKey(key)) {
            keyPressed.put(key, false);
            parseKey();
        }
    }
}

