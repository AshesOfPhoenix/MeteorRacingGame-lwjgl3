package engine.Models;

import engine.RawModel;
import engine.graphics.Material;

public class TextureModel {
    private RawModel rawModel;
    private Material mat;

    public TextureModel(RawModel model,Material mat){
        this.rawModel=model;
        this.mat=mat;
    }

    public RawModel getRawModel(){
        return rawModel;
    }
}
