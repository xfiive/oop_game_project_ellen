package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class SpawnPoint extends AbstractActor {

//    private Disposable loop;
    private int aliensAmount;

    public SpawnPoint(int spawnAliens) {
        this.aliensAmount = spawnAliens;
        setAnimation(new Animation("sprites/spawn.png", 32, 32));
    }

    public int getAliensAmount() {
        return aliensAmount;
    }

//    private void spawnAlien() {
//
//
//    }
//
//    @Override
//    public void addedToScene(@NotNull Scene scene) {
////        super.addedToScene(scene);
////        loop = new Loop<>(new Invoke<>()).scheduleFor(this);
//    }
}
