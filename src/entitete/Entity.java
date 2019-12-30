package entitete;

import engine.Models.TextureModel;
import engine.maths.Vector3f;

//!Instance of a textured model
//*Useful when we want to render (same) multiple objects with the same VAO
//*all with a different Transformation/Model matrix
//?Imagine everyone of them as an entity
public class Entity {
    private TextureModel model;
    private Vector3f position;
    private float rotX,rotY,rotZ;
    private float scale;

    public Entity(TextureModel model, Vector3f position, float rotX, float rotY, float rozZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x+=dx;
        this.position.y+=dy;
        this.position.z+=dz;
    }

    public void increaseRotation(float dx, float dy, float dz){
        this.rotX+=dx;
        this.rotY+=dy;
        this.rotZ+=dz;
    }

    public TextureModel getModel() {
        return model;
    }

    public void setModel(TextureModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rozZ) {
        this.rotZ = rozZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
