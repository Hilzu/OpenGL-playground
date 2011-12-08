package graphics;

public enum Shader {

    SIMPLE("simple.vert", "simple.frag", "in_vertices", "in_tex_coords");
    private final String vertFileName;
    private final String fragFileName;
    private final String[] attributes;

    private Shader(String vertShaderFileName, String fragShaderFileName, String... attributes) {
        this.vertFileName = vertShaderFileName;
        this.fragFileName = fragShaderFileName;
        this.attributes = attributes;
    }

    public String getVertFileName() {
        return vertFileName;
    }

    public String getFragFileName() {
        return fragFileName;
    }

    public String[] getAttributes() {
        return attributes;
    }
}