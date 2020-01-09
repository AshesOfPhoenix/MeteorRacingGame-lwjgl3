package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;


//!Instance of a textured model
//*Useful when we want to render multiple objects with the same VAO
//*all with a different Transformation/Model matrix
//?Imagine everyone of them as an entity
public class Entity {
    private TextureModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private float xSize;
    private float ySize;
    private float zSize;
    private Vector3f center;

    public float getxSize() {
        return xSize;
    }

    public float getySize() {
        return ySize;
    }

    public float getzSize() {
        return zSize;
    }

    public Vector3f getCenter() {
        return center;
    }

    public void setxSize(float xSize) {
        this.xSize = xSize;
    }

    public void setySize(float ySize) {
        this.ySize = ySize;
    }

    public void setzSize(float zSize) {
        this.zSize = zSize;
    }

    public void setCenter(Vector3f center) {
        this.center = center;
    }

    public Entity(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.xSize = model.getRawModel().getxSize() * scale;
        this.ySize = model.getRawModel().getySize() * scale;
        this.zSize = model.getRawModel().getzSize() * scale;
        this.center = new Vector3f(position.x, position.y, position.z);
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
        this.center = new Vector3f(position.x, position.y, position.z);
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public float euclideanDistance(Vector3f carPosition) {
        float distance = (float) Math.sqrt(Math.pow(carPosition.x - this.position.x, 2) + Math.pow(carPosition.y - this.position.y, 2) + Math.pow(carPosition.z - this.position.z, 2));
        return distance;
    }

    public TextureModel getModel() {
        return this.model;
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
