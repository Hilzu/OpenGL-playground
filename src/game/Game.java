package game;

import graphics.*;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    public void run() {
        Graphics.init();
        SquareManager squareManager = new SquareManager();
        Time.tick();

        Thread fpsPrinter = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    System.out.println("FPS: " + Time.getFps());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            }
        });
        fpsPrinter.start();

        Graphics.checkGLErrors("Main loop start");
        while (!Display.isCloseRequested()) {
            Time.tick();
            glClear(GL_COLOR_BUFFER_BIT);
            
            squareManager.update();

            Display.update();
            Graphics.checkGLErrors("Main loop");
            //Display.sync(60);
        }
        fpsPrinter.interrupt();
        Display.destroy();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
