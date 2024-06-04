package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;


public class Main {

    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        Scene scene = new World("mission-impossible", "maps/escape-room.tmx", new MissionImpossible.Factory());

        MissionImpossible missionImpossible = new MissionImpossible();

        scene.addListener(missionImpossible);

        game.addScene(scene);

        game.start();

    }

}
