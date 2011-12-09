package graphics;

import ai.BounceAgent;
import java.util.List;
import java.util.LinkedList;
import java.nio.FloatBuffer;
import game.Util;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class SquareManager {

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
    private List<Square> squares;
    private List<BounceAgent> agents;

    public SquareManager() {
        ShaderManager.useShader(SHADER);
        Square.setModelViewUniformLoc(ShaderManager.getShaderProgram(SHADER).getUniformLocations()[0]);

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a single array with all vertex attributes
        float[] attributes = {
            Square.POS[0], Square.POS[1], Square.POS[2], Square.TEX_COORDS[0], Square.TEX_COORDS[1], // vertex 1
            Square.POS[3], Square.POS[4], Square.POS[5], Square.TEX_COORDS[2], Square.TEX_COORDS[3], // vertex 2
            Square.POS[6], Square.POS[7], Square.POS[8], Square.TEX_COORDS[4], Square.TEX_COORDS[5], // vertex 3
            Square.POS[9], Square.POS[10], Square.POS[11], Square.TEX_COORDS[6], Square.TEX_COORDS[7] // vertex 4
        };
        FloatBuffer attrBuffer = Util.floatArrayToBuffer(attributes);

        // Create a vbo and load all vertex data to it
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glEnableVertexAttribArray(VERT_ATTRIB);
        glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        glBufferData(GL_ARRAY_BUFFER, attrBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(VERT_ATTRIB, POS_SIZE, GL_FLOAT, false, (POS_SIZE + TEX_COORD_SIZE) * FLOAT_SIZE, 0);
        glVertexAttribPointer(TEX_COORD_ATTRIB, TEX_COORD_SIZE, GL_FLOAT, false, (POS_SIZE + TEX_COORD_SIZE) * FLOAT_SIZE, POS_SIZE * FLOAT_SIZE);

        // Create and load squares texture
        textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        samplerLoc = ShaderManager.getShaderProgram(SHADER).getUniformLocations()[1];
        glUniform1i(samplerLoc, 0);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 3, 3, 0, GL_RGB, GL_UNSIGNED_BYTE, Util.byteArrayToBuffer(Square.TEX_PIXELS));

        // Create squares in random locations and give them to agents that move them
        squares = new LinkedList<Square>();
        agents = new LinkedList<BounceAgent>();
        for (int i = 0; i < 10000; i++) {
            Square square = new Square();
            square.scale(0.01f, 0.01f);
            square.moveTo((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1));
            BounceAgent agent = new BounceAgent(square);
            squares.add(square);
            agents.add(agent);
        }
    }

    public void update() {
        ShaderManager.useShader(SHADER);

        for (BounceAgent agent : agents) {
            agent.tick();
        }

        for (Square square : squares) {
            square.draw();
        }
    }
}
