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
    private static final Shader SHADER = Shader.SIMPLE;
    private int vaoID;
    private int verticeVBO;
    private int textureVBO;
    private int textureID;
    private int samplerLoc;
    private List<Square> squares;
    private List<BounceAgent> agents;

    public SquareManager() {
        ShaderManager.useShader(SHADER);

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        verticeVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticeVBO);
        glEnableVertexAttribArray(VERT_ATTRIB);
        glVertexAttribPointer(VERT_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        FloatBuffer vertsBuffer = Util.floatArrayToBuffer(Square.VERTS);
        glBufferData(GL_ARRAY_BUFFER, vertsBuffer, GL_STATIC_DRAW);

        textureVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
        glEnableVertexAttribArray(TEX_COORD_ATTRIB);
        glVertexAttribPointer(TEX_COORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
        FloatBuffer texCoordBuffer = Util.floatArrayToBuffer(Square.TEX_COORDS);
        glBufferData(GL_ARRAY_BUFFER, texCoordBuffer, GL_STATIC_DRAW);

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
