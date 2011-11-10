package game;

import ai.BasicAgent;
import graphics.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Main {

    public static void main(String[] args) {
        Graphics.init();
        
        Square sq = new Square();
        sq.scale(0.5f, 0.5f);
        BasicAgent agent = new BasicAgent(sq);
        
        Time.updateFrameDelta();
        
        while(!Display.isCloseRequested()) {
            Time.updateFrameDelta();
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            Graphics.checkGLErrors("clear");
            agent.tick();
            sq.draw();
            Graphics.checkGLErrors("draw");
            
            Display.update();
            Graphics.checkGLErrors("Main loop end");
            Display.sync(60);
        }
    }
}
