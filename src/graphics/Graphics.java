package graphics;

import game.Util;
import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;

public class Graphics {

    public static final int DISPLAY_WIDTH = 800;
    public static final int DISPLAY_HEIGHT = 600;
    public static final int VERT_ATTRIB = 0;
    public static final int TEX_COORD_ATTRIB = 1;
    private static final float[] SQUARE_VERTS = {
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f
    };
    private static final float[] TEX_COORDS = {
        0f, 0f,
        0f, 1.0f,
        1.0f, 0f,
        1.0f, 1.0f
    };
    private static final byte FF = (byte) 255;
    private static final byte[] TEX_PIXELS = {
        FF, 0, 0,    0, FF, 0,    FF, 0, 0,
        0, FF, 0,    0, 0, FF,    0, FF, 0,
        FF, 0, FF,   0, FF, 0,    FF, 0, 0
    };

    public static void init() {
        initDisplay();
        initGL();
        ShaderManager.initShaders();
        Square.setModelViewUniformLoc(
                ShaderManager.getShaderProgram(Shader.SIMPLE).getUniformLocations()[0]);
        // TODO: Must move after using several shaders
        ShaderManager.useShader(Shader.SIMPLE);

        initTexture();

        checkGLErrors("init");
    }

    private static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Best Game");
            Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true));
        } catch (LWJGLException ex) {
            System.out.println("Could not init display!");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void initGL() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo1 = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo1);
        glEnableVertexAttribArray(VERT_ATTRIB);
        glVertexAttribPointer(VERT_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        FloatBuffer vertsBuffer = Util.floatArrayToBuffer(SQUARE_VERTS);
        glBufferData(GL_ARRAY_BUFFER, vertsBuffer, GL_STATIC_DRAW);

        int vbo2 = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo2);
        glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        glVertexAttribPointer(TEX_COORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
        FloatBuffer texCoordBuffer = Util.floatArrayToBuffer(TEX_COORDS);
        glBufferData(GL_ARRAY_BUFFER, texCoordBuffer, GL_STATIC_DRAW);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glEnable(GL_CULL_FACE);
    }

    private static void initTexture() {
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        int textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);

        int samplerLoc = ShaderManager.getShaderProgram(Shader.SIMPLE).getUniformLocations()[1];
        glUniform1i(samplerLoc, 0);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 3, 3, 0, GL_RGB, GL_UNSIGNED_BYTE, Util.byteArrayToBuffer(TEX_PIXELS));

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    public static void checkGLErrors(String msg) {
        try {
            org.lwjgl.opengl.Util.checkGLError();
        } catch (OpenGLException ex) {
            System.out.print(msg + ": ");
            System.out.println(ex.getMessage());
        }
    }
}
