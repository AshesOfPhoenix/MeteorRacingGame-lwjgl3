package engine.graphics;

import engine.maths.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.Scanner;

public class Shader {
	private String vertexFile, fragmentFile;
	private int vertexID, fragmentID, programID;
	private static FloatBuffer matrixBuffer=BufferUtils.createFloatBuffer(16);

	//!Read Shader
	public Shader(String vertexPath, String fragmentPath) throws IOException {
		Scanner vr = new Scanner(new FileReader(new File(vertexPath)));
		Scanner fr = new Scanner(new FileReader(new File(fragmentPath)));
		vertexFile = "#";
		String line = vr.nextLine();
		String result = line.substring(0, 0) + line.substring(1);
		vertexFile += result + "\n";
		while (vr.hasNextLine()){
			line = vr.nextLine();
			if (line.length() != 0){
				vertexFile += line + "\n";
			}
		}

		fragmentFile = "#";
		String line2 = fr.nextLine();
		String result2 = line2.substring(0, 0) + line2.substring(1);
		fragmentFile += result2 + "\n";
		while (fr.hasNextLine()){
			line2 = fr.nextLine();
			if (line2.length() != 0){
				fragmentFile += line2 + "\n";
			}
		}
		//vertexFile = FileUtils.loadAsString(vertexPath);
		//fragmentFile = FileUtils.loadAsString(fragmentPath);
	}
	//protected void getAllUniformLocations();
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID,uniformName);
	}
	protected void loadBoolean(int location,boolean value){
		float load=0;
		if(value){
			load=1;
		}
		GL20.glUniform1f(location,load);
	}
	protected void loadMatrix(int location, Matrix4f matrix){
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location,false,matrixBuffer);
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
		getUniformLocation("transformationMatrix");
		
		GL20.glDeleteShader(vertexID);
		GL20.glDeleteShader(fragmentID);
	}
	protected void loadFLoat(int location,float value){
		GL20.glUniform1f(location,value);
	}
	protected void loadVector(int location, Vector3f vector){
		GL20.glUniform3f(location,vector.x,vector.y,vector.z);
	}

	//!Bind Shader
	public void bind() {
		GL20.glUseProgram(programID);
	}

	//!UnBind Shader
	public void unbind() {
		GL20.glUseProgram(0);
	}

	//!Destroy program
	public void destroy() {
		GL20.glDeleteProgram(programID);
	}
}