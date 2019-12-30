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
    private int vao, pbo, ibo, tbo;

    //!Send vertex and index data and store it
    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    //!Generate mesh
    //?Create VAO and bind it
    //*Create VBO, bind it and define vertex attributes
    //*Create IBO and bind it
    public void create() {
        this.material.create();
        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < this.vertices.length; i++) {
            positionData[i * 3] = this.vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = this.vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = this.vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();

        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < this.vertices.length; i++) {
            textureData[i * 2] = this.vertices[i].getPosition().getX();
            textureData[i * 2 + 1] = this.vertices[i].getPosition().getY();
        }
        textureBuffer.put(textureData).flip();

        this.tbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.tbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);


        this.pbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.pbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indicesBuffer.put(this.indices).flip();

        this.ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public Vertex[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }

    public int getVAO() {
        return this.vao;
    }

    public int getPBO() {
        return this.pbo;
    }

    public int getIBO() {
        return this.ibo;
    }

    public int getTBO() {
        return this.tbo;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void destroy() {
        GL15.glDeleteBuffers(this.pbo);
        GL15.glDeleteBuffers(this.ibo);
        GL15.glDeleteBuffers(this.tbo);

        GL30.glDeleteVertexArrays(this.vao);
        this.material.unBind();
    }
}