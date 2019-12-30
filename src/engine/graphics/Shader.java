package engine.graphics;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
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
        //vertexFile = FileUtils.loadAsString(vertexPath);
        //fragmentFile = FileUtils.loadAsString(fragmentPath);
    }

    protected abstract void getAllUniformLocations();

    protected abstract void bindAttributes();

    //!Get the uniform location of the variable in the shader program
    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    //!Send boolean uniform to the shader program
    protected void Uniform1b(int location, boolean value) {
        float load = 0;
        if (value) {
            load = 1;
        }
        GL20.glUniform1f(location, load);
    }

    //!Send matrix uniform to the shader program
    public void Uniform1m(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    //!Send float uniform to the shader program
    protected void Uniform1f(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    //!Send vector uniform to the shader program
    protected void Uniform1v(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    //!Attach shaders to the program
    public void create() {
        programID = GL20.glCreateProgram();

        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexID, vertexFile);
        GL20.glCompileShader(vertexID);
        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID));
            return;
        }

        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentID, fragmentFile);
        GL20.glCompileShader(fragmentID);
        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID));
            return;
        }

        //*Attach shaders to the program
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        bindAttributes();

        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programID));
            return;
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
            return;
        }

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
    }

    //!Bind Shader
    public void bind() {
        GL20.glUseProgram(this.programID);
    }

    //!UnBind Shader
    public void unbind() {
        GL20.glUseProgram(0);
    }

    //!Destroy program
    public void destroy() {
        GL20.glDeleteProgram(programID);
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