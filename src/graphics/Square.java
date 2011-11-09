package graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Square {

    private final float[] data = {
        0.0f, 1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f
    };
    private FloatBuffer vertices;
    private int vertexShader;
    private int fragmentShader;
    private int shaderProgram;

    public Square() {
        String vertexShaderStr = "#version 150\n"
                + "in  vec3 in_Position;\n"
                + "void main(void) {\n"
                + "gl_Position = vec4(in_Position.x, in_Position.y, in_Position.z, 1.0);\n"
                + "}\n";

        String fragmentShaderStr = "#version 150\n"
                + "precision highp float;\n"
                + "out vec4 fragColor;\n"
                + "void main(void) {\n"
                + "fragColor = vec4(1.0,1.0,1.0,1.0);\n"
                + "}\n";
         
        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        if (vertexShader == 0) {
            System.out.println("Could not create new Vertex shader!");
            System.exit(1);
        }
        fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        if (fragmentShader == 0) {
            System.out.println("Could not create new fragment shader!");
            System.exit(1);
        }
        
        GL20.glShaderSource(vertexShader, vertexShaderStr);
        GL20.glShaderSource(fragmentShader, fragmentShaderStr);
        
        GL20.glCompileShader(vertexShader);
        IntBuffer compiled = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL20.glGetShader(vertexShader, GL20.GL_COMPILE_STATUS, compiled);
        if (compiled.get(0) == 0) {
            System.out.println("Shader compile failed!");
            System.out.println(GL20.glGetShaderInfoLog(vertexShader, 1000));
            System.exit(1);
        }
        GL20.glCompileShader(fragmentShader);
        compiled = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL20.glGetShader(fragmentShader, GL20.GL_COMPILE_STATUS, compiled);
        if (compiled.get(0) == 0) {
            System.out.println("Shader compile failed!");
            System.out.println(GL20.glGetShaderInfoLog(fragmentShader, 1000));
            System.exit(1);
        }
        
        shaderProgram = GL20.glCreateProgram();
        if (shaderProgram == 0) {
            System.out.println("Could not create shader program!");
            System.exit(1);
        }
        
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        
        GL20.glBindAttribLocation(shaderProgram, 0, "in_Position");
        
        GL20.glLinkProgram(shaderProgram);
        IntBuffer linked = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL20.glGetProgram(shaderProgram, GL20.GL_LINK_STATUS, linked);
        if (linked.get(0) == 0) {
            System.out.println("Error linking program:");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgram, 1000));
            System.exit(1);
        }
        
        vertices = Graphics.floatArrayToFloatBuffer(data);
    }
    
    public void draw() {
        GL20.glUseProgram(shaderProgram);
        Graphics.checkGLErrors("useprog");
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        Graphics.checkGLErrors("buffer");
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        Graphics.checkGLErrors("drawarr");
    }
}
