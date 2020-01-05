package engine.PowerUps;

import engine.Models.TextureModel;
import engine.entitete.Entity;
import org.lwjgl.util.vector.Vector3f;

public class Armour extends Entity {
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    private boolean active = false;
    private String type = "armour";

    public String getType() {
        return type;
    }

    public Armour(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

}
