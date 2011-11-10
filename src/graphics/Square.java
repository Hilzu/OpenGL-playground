package graphics;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Square {

    private final float[] data = {
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f
    };
    private final FloatBuffer vertices;
    private final Shader shaderType;

    public Square() {
        vertices = Graphics.floatArrayToFloatBuffer(data);
        shaderType = Shader.SIMPLE;
    }
    
    public void draw() {
        ShaderManager.useShader(shaderType);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
    }
}
