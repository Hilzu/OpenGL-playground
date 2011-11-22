package game;

import ai.BounceAgent;
import graphics.*;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Game {
    
    public void run() {
        Graphics.init();
        Graphics.checkGLErrors("Graphics init");

        List<Square> squares = new LinkedList<Square>();
        List<BounceAgent> agents = new LinkedList<BounceAgent>();
        for (int i = 0; i < 10000; i++) {
            Square square = new Square();
            square.scale(0.1f, 0.05f);
            square.moveTo((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1));
            BounceAgent agent = new BounceAgent(square);
            squares.add(square);
            agents.add(agent);
        }

        Time.updateFrameDelta();

        Graphics.checkGLErrors("Main loop start");
        while (!Display.isCloseRequested()) {
            Time.updateFrameDelta();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            for (BounceAgent agent : agents) {
                agent.tick();
            }
            for (Square square : squares) {
                square.draw();
            }

            Display.update();
            Graphics.checkGLErrors("Main loop");
            Display.sync(60);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
