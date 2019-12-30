package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
	private Vertex[] vertices;
	private int[] indices;
	private Material material;
	private int vao, pbo, ibo,tbo;

	//!Send vertex and index data and store it
	public Mesh(Vertex[] vertices, int[] indices,Material material) {
		this.vertices = vertices;
		this.indices = indices;
		this.material=material;
	}

	//!Generate mesh
	//!Create VAO and bind it
	//!Create VBO, bind it and define vertex attributes
	//!Create IBO and bind it
	public void create() {

	    material.create();
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] positionData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			positionData[i * 3] = vertices[i].getPosition().getX();
			positionData[i * 3 + 1] = vertices[i].getPosition().getY();
			positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
		}
		positionBuffer.put(positionData).flip();

		FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
		float[] textureData = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++) {
			textureData[i * 2] = vertices[i].getPosition().getX();
			textureData[i * 2 + 1] = vertices[i].getPosition().getY(); }
		textureBuffer.put(textureData).flip();

		tbo=GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);



		pbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, pbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();
		
		ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public int getVAO() {
		return vao;
	}

	public int getPBO() {
		return pbo;
	}

	public int getIBO() {
		return ibo;
	}

	public int getTBO(){return tbo;}

	public Material getMaterial(){return material; }
	public void destroy(){
		GL15.glDeleteBuffers(pbo);
		GL15.glDeleteBuffers(ibo);
		GL15.glDeleteBuffers(tbo);

		GL30.glDeleteVertexArrays(vao);
		material.destroy();
	}
}