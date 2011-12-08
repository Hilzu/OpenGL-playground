package graphics;

import game.Util;
import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
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
    static final byte x = (byte) 255;
    private static final byte[] TEX_PIXELS = {
        x,0,0, 0,x,0, x,0,0,
        0,x,0, 0,0,x, 0,x,0,
        x,0,x, 0,x,0, x,0,0
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
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        int vbo1 = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo1);
        GL20.glEnableVertexAttribArray(VERT_ATTRIB);
        GL20.glVertexAttribPointer(VERT_ATTRIB, 3, GL11.GL_FLOAT, false, 0, 0);
        FloatBuffer vertsBuffer = Util.floatArrayToBuffer(SQUARE_VERTS);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertsBuffer, GL15.GL_STATIC_DRAW);

        int vbo2 = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo2);
        GL20.glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        GL20.glVertexAttribPointer(TEX_COORD_ATTRIB, 2, GL11.GL_FLOAT, false, 0, 0);
        FloatBuffer texCoordBuffer = Util.floatArrayToBuffer(TEX_COORDS);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordBuffer, GL15.GL_STATIC_DRAW);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    private static void initTexture() {
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        
        int textureID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        
        int samplerLoc = ShaderManager.getShaderProgram(Shader.SIMPLE).getUniformLocations()[1];
        GL20.glUniform1i(samplerLoc, 0);
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 3, 3, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, Util.byteArrayToBuffer(TEX_PIXELS));
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
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
