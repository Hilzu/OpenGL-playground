package graphics;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Square {

    private final float[] data = {
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f
    };
    private final FloatBuffer vertices;
    private final Shader shaderType;
    private Matrix4f modelViewProjectionMat;
    private FloatBuffer mvpBuffer;
    private boolean transformed;

    public Square() {
        vertices = Graphics.floatArrayToFloatBuffer(data);
        shaderType = Shader.SIMPLE;
        modelViewProjectionMat = new Matrix4f();
        mvpBuffer = Graphics.floatArrayToFloatBuffer(new float[16]);
        transformed = true;
    }
    
    public void draw() {
        if (transformed) {
            modelViewProjectionMat.store(mvpBuffer);
            mvpBuffer.position(0);
            transformed = false;
        }
        ShaderManager.useShader(shaderType, mvpBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
    }
    
    public void scale(Vector3f vec) {
        modelViewProjectionMat.scale(vec);
        transformed = true;
    }
    
    public void scale(float x, float y, float z) {
        this.scale(new Vector3f(x, y, z));
    }
    
    public void scale(float x, float y) {
        this.scale(x, y, 0);
    }
}
