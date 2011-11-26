package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Util {

    public static String readStringFromFile(String path) {
        File fileHandle = new File(path);
        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader(new FileReader(fileHandle));
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        String line = null;
        StringBuilder string = new StringBuilder();

        try {
            try {
                while (true) {

                    line = inputReader.readLine();

                    if (line == null) {
                        break;
                    }

                    string.append(line);
                    string.append(System.getProperty("line.separator"));
                }
            } finally {
                inputReader.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        return string.toString();
    }

    public static FloatBuffer floatArrayToBuffer(float[] floatArray) {
        FloatBuffer fBuffer = createBuffer(floatArray.length * 4).asFloatBuffer();
        fBuffer.put(floatArray).position(0);
        return fBuffer;
    }

    public static ShortBuffer shortArrayToBuffer(short[] shortArray) {
        ShortBuffer sBuffer = createBuffer(shortArray.length * 2).asShortBuffer();
        sBuffer.put(shortArray).position(0);
        return sBuffer;
    }

    public static ByteBuffer byteArrayToBuffer(byte[] byteArray) {
        ByteBuffer bBuffer = createBuffer(byteArray.length);
        bBuffer.put(byteArray).position(0);
        return bBuffer;
    }

    public static IntBuffer intArrayToBuffer(int[] intArray) {
        IntBuffer iBuffer = createBuffer(intArray.length * 4).asIntBuffer();
        iBuffer.put(intArray).position(0);
        return iBuffer;
    }

    public static ByteBuffer createBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }
}
