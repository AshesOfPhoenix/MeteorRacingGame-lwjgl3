package engine.Models;

import engine.graphics.Material;
import engine.graphics.Texture;

public class TextureModel {
    private RawModel rawModel;
    private Material material;

    public TextureModel(RawModel model, Material material) {
        this.rawModel = model;
        this.material = material;
    }

    public RawModel getRawModel() {
        return this.rawModel;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMat(Material material) {
        this.material = material;
    }

    public Texture getTexture() {
        return this.material.getTexture();
    }

    public void Bind() {
        this.material.Bind();
    }
}
