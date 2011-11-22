package graphics;

import game.Util;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

public class ShaderManager {

    private static Map<Shader, ShaderProgram> shaderPrograms =
            new EnumMap<Shader, ShaderProgram>(Shader.class);
    
    public static ShaderProgram getShaderProgram(Shader shader) {
        return shaderPrograms.get(shader);
    }

    public static void initShaders() {
        for (Shader shaderType : Shader.values()) {
            String vertShader = Util.readStringFromFile("shaders/"
                    + shaderType.getVertFileName());
            String fragShader = Util.readStringFromFile("shaders/"
                    + shaderType.getFragFileName());

            int programID = createShaderProgram(vertShader, fragShader, shaderType);

            int[] uniformLocations = null;
            switch (shaderType) {
            case SIMPLE: {
                uniformLocations = new int[1];
                uniformLocations[0] = GL20.glGetUniformLocation(programID, "mvpMat");
                break;
            }
            }
            shaderPrograms.put(shaderType, new ShaderProgram(programID, uniformLocations));
        }
    }

    public static void useShader(Shader shaderType) {
        ShaderProgram shaderProgram = shaderPrograms.get(shaderType);
        if (shaderProgram == null) {
            System.out.println("Shader " + shaderType + " not initialized! "
                    + "Have you ran initShaders()?");
            System.exit(1);
        }
        GL20.glUseProgram(shaderProgram.getProgramID());
    }

    private static int compileShader(int shaderType, String shaderSrc) {
        int shaderID = GL20.glCreateShader(shaderType);
        if (shaderID == 0) {
            System.out.println("Could not create new shader!");
            System.exit(1);
        }

        GL20.glShaderSource(shaderID, shaderSrc);

        GL20.glCompileShader(shaderID);

        // Check if shader was compiled succesfully
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
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
            System.out.println("Creating shader program failed for shader \""
                    + shaderType + "\"!");
            System.exit(1);
        }

        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);

        bindAttributes(shaderProgram, shaderType);

        GL20.glLinkProgram(shaderProgram);

        //Check if program was linked succesfully
        if (GL20.glGetProgram(shaderProgram, GL20.GL_LINK_STATUS) == 0) {
            System.out.println("Error linking shader program for " + shaderType
                    + "!");
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
