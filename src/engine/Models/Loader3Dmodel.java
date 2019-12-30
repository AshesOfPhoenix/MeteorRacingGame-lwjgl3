package engine.Models;

import engine.graphics.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader3Dmodel {
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();
    private int vaoID;

    public RawModel loadToVAO(float[] vertices, float[] textCords, int[] indices) {
        //*Create and bind vertex object
        this.vaoID = createVAO();
        //!Bind indices and create buffers
        bindIndicesBuffer(indices);
        //!Create buffer for vertices
        storeDataInAttributeList(0, 3, vertices);
        //!Create buffer for texture coordinates
        storeDataInAttributeList(1, 2, textCords);
        //*UnBind vertex object
        unbindVAO();
        return new RawModel(this.vaoID, vertices.length, indices.length);
    }

    public int loadTexture(String fileName) {
        Texture texture = new Texture(fileName);
        this.textures.add(texture.getTextureID());
        return texture.getTextureID();
    }

    public void pocisti() {
        for (int vao : this.vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : this.vbos) {
            GL30.glDeleteBuffers(vbo);
            //GL30.glDeleteB
        }
    }

    private int createVAO() {
        vaoID = GL30.glGenVertexArrays();
        this.vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL30.glGenBuffers();  //VertexBuffer
        this.vbos.add(vboID);
        GL15.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(attributeNumber, coordinateSize, GL30.GL_FLOAT, false, 0, 0);
        GL30.glEnableVertexAttribArray(attributeNumber);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
