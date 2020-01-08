package engine.Models;

import org.lwjgl.opengl.GL30;

public class RawModel {
    private int vaoID;
    private int vertexCount;
    private int indexCount;
    private float[] modelMeasurements;
    private float xSize;
    private float ySize;
    private float zSize;

    public float getxSize() {
        return xSize;
    }

    public float getySize() {
        return ySize;
    }

    public float getzSize() {
        return zSize;
    }

    public RawModel(int vaoID, int vertexCount, int indexCount, float[] modelMeasurements) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.indexCount = indexCount;
        this.modelMeasurements = modelMeasurements;
        calculateSizes();
    }

    private void calculateSizes() {
        if (this.modelMeasurements.length != 0) {
            this.xSize = Math.abs(this.modelMeasurements[0]) + Math.abs(this.modelMeasurements[1]);
            this.ySize = Math.abs(this.modelMeasurements[2]) + Math.abs(this.modelMeasurements[3]);
            this.zSize = Math.abs(this.modelMeasurements[4]) + Math.abs(this.modelMeasurements[5]);
        }
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
