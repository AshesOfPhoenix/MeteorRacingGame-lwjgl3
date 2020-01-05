package engine.entitete;

import engine.Models.Loader3Dmodel;
import engine.Models.RawModel;
import engine.graphics.Material;
import org.lwjgl.opengl.GL30;

public class Terrain {
    private static final float SIZE = 10000;
    private static final int VERTEX_COUNT = 2048;

    private static float x;
    private static float z;
    private RawModel model;
    private Material material;

    public Terrain(int gridX, int gridZ, Loader3Dmodel loader, Material material) {
        this.material = material;
        prepare();
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader);
    }

    private void prepare() {
        GL30.glEnable(GL30.GL_FOG);
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glTexParameteri(this.material.getTextureID(), GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST_MIPMAP_NEAREST);
        GL30.glTexParameteri(this.material.getTextureID(), GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(this.material.getTextureID(), GL30.GL_TEXTURE_WRAP_S, GL30.GL_MIRRORED_REPEAT);
    }

    public RawModel getModel() {
        return model;
    }

    public Material getMaterial() {
        return material;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    private RawModel generateTerrain(Loader3Dmodel loader) {
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, indices, normals);
    }
}
