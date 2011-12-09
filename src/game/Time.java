package game;

import org.lwjgl.Sys;

public class Time {

    private static long prevFrameTime = 0;
    private static int frameDelta = 1;
    private static int fps = 0;
    private static int framesSinceFPSupdate = 0;
    private static long prevFPSupdateTime = 0;

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
        frameDelta = (int) (time - prevFrameTime);
        prevFrameTime = time;
    }

    private static void updateFPS() {
        framesSinceFPSupdate++;
        if ((getTime() - prevFPSupdateTime) >= 1000) {
            fps = framesSinceFPSupdate;
            framesSinceFPSupdate = 0;
            prevFPSupdateTime = getTime();
        }
    }
}
