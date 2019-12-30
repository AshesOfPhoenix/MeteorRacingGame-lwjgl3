package engine.entitete;

import engine.io.Input;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private static float pitch;
    private static float yaw;
    private float roll;

    public Camera(){}

    public void move(){
        if(Input.isKeyDown(Keyboard.KEY_W)){
            this.position.z -= 0.02f;
        }
        if(Input.isKeyDown(Keyboard.KEY_D)){
            this.position.x += 0.02f;
        }
        if(Input.isKeyDown(Keyboard.KEY_A)){
            this.position.x -= 0.02f;
        }

    }
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public static float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public static float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}

