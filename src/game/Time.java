package game;

import org.lwjgl.Sys;

public class Time {

    private static long lastFrame = 0;
    private static int frameDelta = 1;
    private static int fps = 0;
    private static int framesSinceFPSupdate = 0;
    private static int timeSinceFPSupdate = 0;

    /**
     * Get the time in milliseconds
     * 
     * @return The system time in milliseconds
     */
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static int getFrameDelta() {
        return frameDelta;
    }

    public static int getFps() {
        return fps;
    }

    public static void tick() {
        updateFrameDelta();
        updateFPS();
    }

    private static void updateFrameDelta() {
        long time = getTime();
        frameDelta = (int) (time - lastFrame);
        lastFrame = time;
    }

    private static void updateFPS() {
        framesSinceFPSupdate++;
        timeSinceFPSupdate += frameDelta;
        if (timeSinceFPSupdate >= 1000) {
            fps = framesSinceFPSupdate;
            framesSinceFPSupdate = 0;
            timeSinceFPSupdate = 0;
        }
    }
}
