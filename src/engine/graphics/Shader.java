package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import java.io.*;
import java.util.Scanner;

public class Shader {
	private String vertexFile, fragmentFile;
	private int vertexID, fragmentID, programID;

	//!Read Shader
	public Shader(String vertexPath, String fragmentPath) throws IOException {
		Scanner vr = new Scanner(new FileReader(new File(vertexPath)));
		Scanner fr = new Scanner(new FileReader(new File(fragmentPath)));
		vertexFile = "#";
		String line = vr.nextLine();
		String result = line.substring(0, 0) + line.substring(0+1);
		vertexFile += result + "\n";
		while (vr.hasNextLine()){
			line = vr.nextLine();
			if (line.length() != 0){
				vertexFile += line + "\n";
			}
		}

		fragmentFile = "#";
		String line2 = fr.nextLine();
		String result2 = line2.substring(0, 0) + line2.substring(0+1);
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
		
		GL20.glDeleteShader(vertexID);
		GL20.glDeleteShader(fragmentID);
	}

	//!Bind Shader
	public void bind() {
		GL20.glUseProgram(programID);
	}

	//!UnBind Shader
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void destroy() {
		GL20.glDeleteProgram(programID);
	}
}