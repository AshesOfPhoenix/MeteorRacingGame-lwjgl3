package engine.Models;

import org.lwjgl.opengl.GL30;

public class RawModel {

    private int vaoID;
    private int vertexCount;
    private int indexCount;

    public RawModel(int vaoID, int vertexCount, int indexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.indexCount = indexCount;
    }

    public int getVaoID() {
        return this.vaoID;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public int getIndexCount() {
        return this.indexCount;
    }

    public void Bind() {
        GL30.glBindVertexArray(this.vaoID);
    }

    public void UnBind() {
        GL30.glBindVertexArray(0);
    }
}
