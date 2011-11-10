package game;

import org.lwjgl.Sys;

public class Time {

    private static long lastFrame = 0;
    private static int frameDelta = 1;

    /**
     * Get the time in milliseconds
     * 
     * @return The system time in milliseconds
     */
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public static void updateFrameDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        frameDelta = delta;
    }
    
    public static int getFrameDelta() {
        return frameDelta;
    }
}
