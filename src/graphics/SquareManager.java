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
    public static final int TEXCOORD_SIZE = 2;
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

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        float[] attributes = {
            Square.POSITIONS[0], Square.POSITIONS[1], Square.POSITIONS[2], Square.TEX_COORDS[0], Square.TEX_COORDS[1],
            Square.POSITIONS[3], Square.POSITIONS[4], Square.POSITIONS[5], Square.TEX_COORDS[2], Square.TEX_COORDS[3],
            Square.POSITIONS[6], Square.POSITIONS[7], Square.POSITIONS[8], Square.TEX_COORDS[4], Square.TEX_COORDS[5],
            Square.POSITIONS[9], Square.POSITIONS[10], Square.POSITIONS[11], Square.TEX_COORDS[6], Square.TEX_COORDS[7]
        };
        FloatBuffer attrBuffer = Util.floatArrayToBuffer(attributes);
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glEnableVertexAttribArray(VERT_ATTRIB);
        glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        glBufferData(GL_ARRAY_BUFFER, attrBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(VERT_ATTRIB, POS_SIZE, GL_FLOAT, false, (POS_SIZE + TEXCOORD_SIZE) * FLOAT_SIZE, 0);
        glVertexAttribPointer(TEX_COORD_ATTRIB, TEXCOORD_SIZE, GL_FLOAT, false, (POS_SIZE + TEXCOORD_SIZE) * FLOAT_SIZE, POS_SIZE * FLOAT_SIZE);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);

        samplerLoc = ShaderManager.getShaderProgram(SHADER).getUniformLocations()[1];
        glUniform1i(samplerLoc, 0);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 3, 3, 0, GL_RGB, GL_UNSIGNED_BYTE, Util.byteArrayToBuffer(Square.TEX_PIXELS));

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        Square.setModelViewUniformLoc(ShaderManager.getShaderProgram(SHADER).getUniformLocations()[0]);

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
