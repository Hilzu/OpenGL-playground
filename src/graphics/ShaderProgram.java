package graphics;

public class ShaderProgram {

    private int programID;
    private int[] uniformLocations;

    public ShaderProgram(int programID, int[] uniformLocations) {
        this.programID = programID;
        if (uniformLocations == null) {
            this.uniformLocations = new int[0];
        } else {
            this.uniformLocations = uniformLocations;
        }
    }

    public ShaderProgram(int programID) {
        this.programID = programID;
        this.uniformLocations = new int[0];
    }

    public int getProgramID() {
        return programID;
    }

    public int[] getUniformLocations() {
        return uniformLocations;
    }
}
