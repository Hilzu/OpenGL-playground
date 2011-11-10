package graphics;

import game.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.EnumMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

public class ShaderManager {

    private static Map<Shader, Integer> shaderPrograms =
            new EnumMap<Shader, Integer>(Shader.class);

    public static void initShaders() {
        for (Shader shaderType : Shader.values()) {
            String vertShader = Util.readStringFromFile("shaders/"
                    + shaderType.getVertFileName());
            String fragShader = Util.readStringFromFile("shaders/"
                    + shaderType.getFragFileName());

            int programObject = createShaderProgram(vertShader, fragShader,
                    shaderType);
            shaderPrograms.put(shaderType, programObject);
        }
    }

    public static void useShader(Shader shaderType) {
        if (!shaderPrograms.containsKey(shaderType)) {
            System.out.println("Shader " + shaderType + " not initialized! "
                    + "Have you ran initShaders()?");
            System.exit(1);
        }
        GL20.glUseProgram(shaderPrograms.get(shaderType));
    }

    private static int compileShader(int shaderType, String shaderSrc) {
        int shaderID;

        shaderID = GL20.glCreateShader(shaderType);
        if (shaderID == 0) {
            System.out.println("Could not create new shader of type "
                    + shaderType + "!");
            System.exit(1);
        }

        GL20.glShaderSource(shaderID, shaderSrc);

        GL20.glCompileShader(shaderID);

        // Check if shader was compiled succesfully
        IntBuffer compiled = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS, compiled);
        if (compiled.get(0) == 0) {
            System.out.println("Compiling this shader failed:");
            System.out.println(shaderSrc);
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 1000));
            System.exit(1);
        }

        return shaderID;
    }

    private static int createShaderProgram(String vShaderStr, String fShaderStr,
            Shader shaderType) {

        int vertexShader;
        int fragmentShader;

        vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vShaderStr);
        fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fShaderStr);

        int shaderProgram = GL20.glCreateProgram();

        if (shaderProgram == 0) {
            System.out.println("Creating shader program failed for shader \"" +
                    shaderType + "\"!");
            System.exit(1);
        }

        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);

        bindAttributes(shaderProgram, shaderType);

        GL20.glLinkProgram(shaderProgram);

        //Check if program was linked succesfully
        IntBuffer linked = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL20.glGetProgram(shaderProgram, GL20.GL_LINK_STATUS, linked);
        if (linked.get(0) == 0) {
            System.out.println("Error linking program:");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgram, 1000));
            System.exit(1);
        }

        return shaderProgram;
    }

    private static void bindAttributes(int shaderProgram, Shader shaderType) {
        String[] attributes = shaderType.getAttributes();

        if (attributes == null) {
            return;
        }

        for (int i = 0; i < attributes.length; i++) {
            GL20.glBindAttribLocation(shaderProgram, i, attributes[i]);
        }
    }
}
