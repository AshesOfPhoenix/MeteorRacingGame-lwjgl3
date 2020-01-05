package engine.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;

public abstract class Shader {
    private String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    //!Read Shader
    public Shader(String vertexPath, String fragmentPath) throws IOException {
        ParseShader(vertexPath, 0); //*0 = Vertex shader
        ParseShader(fragmentPath, 1); //*1 = Fragment shader
        create();
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    protected abstract void bindAttributes();

    //!Get the uniform location of the variable in the shader program
    protected int getUniformLocation(String uniformName) {
        int location = GL30.glGetUniformLocation(programID, uniformName);
        if (location == -1) {
            System.err.println("Error in getUniformLocation: uniform [" + uniformName + "] does not exist");
            System.exit(1);
        }
        return location;
    }

    //!Send boolean uniform to the shader program
    protected void Uniform1b(int location, boolean value) {
        GL30.glUniform1f(location, (value) ? 1 : 0);
    }

    //!Send matrix uniform to the shader program
    protected void Uniform1m(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL30.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    //!Send float uniform to the shader program
    protected void Uniform1f(int location, float value) {
        GL30.glUniform1f(location, value);
    }

    //!Send vector uniform to the shader program
    protected void Uniform1v(int location, Vector3f vector) {
        GL30.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    //!Attach shaders to the program
    private void create() {
        programID = GL30.glCreateProgram();

        vertexID = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
        GL30.glShaderSource(vertexID, vertexFile);
        GL30.glCompileShader(vertexID);
        if (GL30.glGetShaderi(vertexID, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) {
            System.err.println("Vertex Shader: " + GL30.glGetShaderInfoLog(vertexID));
            return;
        }

        fragmentID = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
        GL30.glShaderSource(fragmentID, fragmentFile);
        GL30.glCompileShader(fragmentID);
        if (GL30.glGetShaderi(fragmentID, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) {
            System.err.println("Fragment Shader: " + GL30.glGetShaderInfoLog(fragmentID));
            return;
        }

        //*Attach shaders to the program
        GL30.glAttachShader(programID, vertexID);
        GL30.glAttachShader(programID, fragmentID);

        bindAttributes();

        GL30.glLinkProgram(programID);
        if (GL30.glGetProgrami(programID, GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
            System.err.println("Program Linking: " + GL30.glGetProgramInfoLog(programID));
            return;
        }

        GL30.glValidateProgram(programID);
        if (GL30.glGetProgrami(programID, GL30.GL_VALIDATE_STATUS) == GL30.GL_FALSE) {
            System.err.println("Program Validation: " + GL30.glGetProgramInfoLog(programID));
            return;
        }

        GL30.glDeleteShader(vertexID);
        GL30.glDeleteShader(fragmentID);
    }

    //!Bind Shader
    public void bind() {
        GL30.glUseProgram(this.programID);
    }

    //!UnBind Shader
    public void UnBind() {
        GL30.glUseProgram(0);
    }

    //!Destroy program
    public void destroy() {
        GL30.glDeleteProgram(programID);
    }

    //!Bind vertex attributes
    public void bindAttribute(int attribute, String variableName) {
        GL30.glBindAttribLocation(this.programID, attribute, variableName);
    }

    private void ParseShader(String shaderPath, int type) {
        if (type == 0) {
            Scanner vr = null;
            try {
                vr = new Scanner(new FileReader(new File(shaderPath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.vertexFile = "#";
            String line = vr.nextLine();
            String result = line.substring(0, 0) + line.substring(1);
            this.vertexFile += result + "\n";
            while (vr.hasNextLine()) {
                line = vr.nextLine();
                if (line.length() != 0) {
                    this.vertexFile += line + "\n";
                }
            }
        } else if (type == 1) {
            Scanner fr = null;
            try {
                fr = new Scanner(new FileReader(new File(shaderPath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.fragmentFile = "#";
            String line2 = fr.nextLine();
            String result2 = line2.substring(0, 0) + line2.substring(1);
            this.fragmentFile += result2 + "\n";
            while (fr.hasNextLine()) {
                line2 = fr.nextLine();
                if (line2.length() != 0) {
                    this.fragmentFile += line2 + "\n";
                }
            }
        }
    }
}