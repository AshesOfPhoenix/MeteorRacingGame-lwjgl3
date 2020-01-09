package engine.PowerUps;

import engine.Models.TextureModel;
import engine.entitete.Entity;
import org.lwjgl.util.vector.Vector3f;


public class SpeedBoost extends Entity {
    private boolean active = false;
    private String type = "boost";
    private float interpolation = 0;
    private boolean upOrdown = true;

    public float getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(float interpolation) {
        this.interpolation = interpolation;
    }

    public boolean isUpOrdown() {
        return upOrdown;
    }

    public void setUpOrdown(boolean upOrdown) {
        this.upOrdown = upOrdown;
    }

    public String getType() {
        return type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public SpeedBoost(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void interpolate() {
        if (this.upOrdown) {
            this.interpolation += 0.001f;
            if (this.interpolation >= 0.04f) {
                this.upOrdown = false;
            }
        } else {
            this.interpolation -= 0.001f;
            if (this.interpolation <= -0.04f) {
                this.upOrdown = true;
            }
        }
        increaseRotation(0, 0.0f, 2.1f);
        increasePosition(0, this.interpolation, 0);
    }
}
