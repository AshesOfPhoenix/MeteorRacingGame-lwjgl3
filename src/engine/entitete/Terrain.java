package engine.entitete;

import engine.Models.Loader3Dmodel;
import engine.Models.RawModel;
import engine.textures.TerrainTexture;
import engine.textures.TerrainTexturePack;
import org.lwjgl.opengl.GL30;

public class Terrain {
    private static final float SIZE = 10000;
    private static final int VERTEX_COUNT = 2048;

    private static float x;
    private static float z;
    private RawModel model;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;

    public Terrain(int gridX, int gridZ, Loader3Dmodel loader, TerrainTexturePack texturePack, TerrainTexture blendMap) {
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader);
        prepareMipmaping();

    }

    private void prepareMipmaping() {
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
        GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_LOD_BIAS, -1.5f); //!Level of detail - lower means more detail
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_NEAREST);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
        GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_LOD_BIAS, -1.5f); //!Level of detail - lower means more detail
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_NEAREST);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
        GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_LOD_BIAS, -1.5f); //!Level of detail - lower means more detail
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_NEAREST);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
        GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_LOD_BIAS, -1.5f); //!Level of detail - lower means more detail
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_NEAREST);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public void setTexturePack(TerrainTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    public void setBlendMap(TerrainTexture blendMap) {
        this.blendMap = blendMap;
    }

    public RawModel getModel() {
        return model;
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
        float[] empty = {};
        return loader.loadToVAO(vertices, textureCoords, indices, normals, empty);
    }
}
