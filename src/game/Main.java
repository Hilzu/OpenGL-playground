package game;

import ai.BounceAgent;
import graphics.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Main {

    public static void main(String[] args) {
        Graphics.init();
        Graphics.checkGLErrors("Graphics init");
        
        Square sq = new Square();
        sq.scale(0.3f, 0.5f);
        BounceAgent agent = new BounceAgent(sq);
        
        Time.updateFrameDelta();
        
        Graphics.checkGLErrors("Main loop start");
        while(!Display.isCloseRequested()) {
            Time.updateFrameDelta();
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            agent.tick();
            sq.draw();
            
            Display.update();
            Graphics.checkGLErrors("Main loop");
            Display.sync(60);
        }
    }
}
