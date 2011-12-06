package graphics;

import game.Util;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Square {

    private static int modelViewUniformLoc;
    private Matrix4f modelViewMatrix;
    private FloatBuffer modelViewBuffer;
    private boolean transformed;

    public Square() {
        modelViewMatrix = new Matrix4f();
        modelViewBuffer = Util.floatArrayToBuffer(new float[16]);
        transformed = true;
    }

    public void draw() {
        if (transformed) {
            modelViewMatrix.store(modelViewBuffer);
            modelViewBuffer.position(0);
            transformed = false;
        }
        GL20.glUniformMatrix4(modelViewUniformLoc, false, modelViewBuffer);

        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
    }

    public void scale(Vector3f vec) {
        modelViewMatrix.scale(vec);
        transformed = true;
    }

    public void scale(float x, float y, float z) {
        this.scale(new Vector3f(x, y, z));
    }

    public static int getModelViewUniformLoc() {
        return modelViewUniformLoc;
    }

    public static void setModelViewUniformLoc(int modelViewUniformLoc) {
        Square.modelViewUniformLoc = modelViewUniformLoc;
    }

    public void scale(float x, float y) {
        this.scale(x, y, 0);
    }

    public void rotate(boolean clockwise, float radians) {
        Vector3f rotateAxis;
        if (clockwise) {
            rotateAxis = new Vector3f(0, 0, -1.0f);
        } else {
            rotateAxis = new Vector3f(0, 0, 1.0f);
        }
        modelViewMatrix.rotate(radians, rotateAxis);
        transformed = true;
    }

    public void translate(Vector2f translateVec) {
        modelViewMatrix.translate(translateVec);
        transformed = true;
    }

    public void translate(float x, float y) {
        this.translate(new Vector2f(x, y));
    }

    /**
     * Move to a certain location on the screen.
     * @param location New location of this.
     */
    public void moveTo(float x, float y) {
        modelViewMatrix.m30 = x;
        modelViewMatrix.m31 = y;
        transformed = true;
    }

    public void moveTo(Vector2f location) {
        this.moveTo(location.x, location.y);
    }

    public Vector2f getDirection() {
        Vector2f dir = new Vector2f(modelViewMatrix.m10, modelViewMatrix.m11);
        dir.normalise(dir);
        return dir;
    }

    public Vector2f getLocation() {
        return new Vector2f(modelViewMatrix.m30, modelViewMatrix.m31);
    }

    public Vector2f getScale() {
        return new Vector2f(modelViewMatrix.m00, modelViewMatrix.m11);
    }
}
