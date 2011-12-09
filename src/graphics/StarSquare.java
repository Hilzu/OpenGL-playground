package graphics;

import java.nio.FloatBuffer;
import game.Util;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class StarSquare extends Square {

    Texture tex;
    public static final int VERT_ATTRIB = 0;
    public static final int TEX_COORD_ATTRIB = 1;
    public static final int POS_SIZE = 3;
    public static final int TEX_COORD_SIZE = 2;
    public static final int FLOAT_SIZE = 4;
    private static final Shader SHADER = Shader.SIMPLE;
    private int vaoID;
    private int vboID;
    private int textureID;
    private int samplerLoc;

    public StarSquare() {
        super();
        scale(0.1f, 0.15f);
        glActiveTexture(GL_TEXTURE1);
        try {
            tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/star.png"));
        } catch (IOException ex) {
            System.out.println("Could not load star.png!");
            System.exit(1);
        }
        
        ShaderManager.useShader(SHADER);
        
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a single array with all vertex attributes
        float[] attributes = {
            Square.POS[0], Square.POS[1], Square.POS[2], Square.TEX_COORDS[0], Square.TEX_COORDS[1], // vertex 1
            Square.POS[3], Square.POS[4], Square.POS[5], Square.TEX_COORDS[2], Square.TEX_COORDS[3], // vertex 2
            Square.POS[6], Square.POS[7], Square.POS[8], Square.TEX_COORDS[4], Square.TEX_COORDS[5], // vertex 3
            Square.POS[9], Square.POS[10], Square.POS[11], Square.TEX_COORDS[6], Square.TEX_COORDS[7] // vertex 4
        };
        FloatBuffer attribBuffer = Util.floatArrayToBuffer(attributes);

        // Create a vbo and load all vertex data to it
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glEnableVertexAttribArray(VERT_ATTRIB);
        glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        glBufferData(GL_ARRAY_BUFFER, attribBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(VERT_ATTRIB, POS_SIZE, GL_FLOAT, false, (POS_SIZE + TEX_COORD_SIZE) * FLOAT_SIZE, 0);
        glVertexAttribPointer(TEX_COORD_ATTRIB, TEX_COORD_SIZE, GL_FLOAT, false, (POS_SIZE + TEX_COORD_SIZE) * FLOAT_SIZE, POS_SIZE * FLOAT_SIZE);

        samplerLoc = ShaderManager.getShaderProgram(SHADER).getUniformLocations()[1];
        glUniform1i(samplerLoc, 1);
    }

    @Override
    public void draw() {
        ShaderManager.useShader(SHADER);
        glBindVertexArray(vaoID);
        glActiveTexture(GL_TEXTURE1);
        glUniform1i(samplerLoc, 1);
        super.draw();
    }
}
