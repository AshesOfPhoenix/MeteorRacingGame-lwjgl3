package engine.entitete;

import engine.Models.TextureModel;
import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Avtomobil extends Entity {
    private static final float noPOWER = 3;
    private static final float TURN_SPEED = 20;
    static float accnumber = 2;
    private static float RUN_SPEED = 50;
    boolean speedBoost = false;
    boolean armour = false;

    private float xSize;
    private float ySize;
    private float zSize;

    private float currentSpeed = 0;
    private float currentTS = 0;
    private Vector3f center;

    public Vector3f getCenter() {
        return center;
    }

    public float getxSize() {
        return xSize;
    }

    public float getySize() {
        return ySize;
    }

    public float getzSize() {
        return zSize;
    }

    public Avtomobil(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.zSize = model.getRawModel().getySize() * scale;
        this.ySize = model.getRawModel().getzSize() * scale;
        this.center = new Vector3f(position.x, position.y + ySize / 2, position.z);
    }

    public void move() {
        checkInputs();
        //increase player roatiton in y cordinate in trunspead mnozimo z FTS-jem ker je moramo meriti obrat z casom
        super.increaseRotation(0, 0, currentTS / 15);
        //calculate distance distanceperseccond*current time
        float distance = currentSpeed / 20;

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotZ())));
        super.increasePosition(dx, 0, dz);

        if (center.x >= 10000 || center.x < 0 || center.z >= 5000 || center.z < 0) {
            for (int i = 0; i < 1000000; i++) {
                super.increasePosition(0, (float) 0.001, 0);
            }
        }

        Vector3f a = super.getPosition();
        this.center = new Vector3f(a.x, a.y + this.ySize / 2, a.z);
    }

    //check the keyboard input and set car moving speed
    private void checkInputs() {
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            if (this.currentSpeed > -RUN_SPEED) {
                this.currentSpeed += -accnumber;
                System.out.print(currentSpeed);
            }
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            if (this.currentSpeed < RUN_SPEED) {
                this.currentSpeed += accnumber;
            } else {
                this.currentSpeed = RUN_SPEED;
            }
        } /*else if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            this.currentSpeed = 0;
        }*/ else if (this.currentSpeed > 0) {
            this.currentSpeed += -noPOWER;
            if (this.currentSpeed < 1) {
                this.currentSpeed = 0;
            }
            System.out.println(currentSpeed);
        } else if (this.currentSpeed < 0) {
            this.currentSpeed += noPOWER;
            if (this.currentSpeed > -1) {
                this.currentSpeed = 0;
            }
            System.out.println(currentSpeed);
        } else if (this.currentSpeed < 0.9 && this.currentSpeed > 0.1) {
            this.currentSpeed = 0;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.currentTS = -TURN_SPEED;
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.currentTS = TURN_SPEED;
        } else {
            this.currentTS = 0;
        }
    }

    public void colisiondetection(Meteor meteor) {
        Vector3f pozicija = meteor.getPosition();
        if (pozicija.x - this.center.x <= 50 && pozicija.y - this.center.y <= 50 && pozicija.z - this.center.z <= 50) {
            System.out.println("Ijeeeeeeeeeeeej smo se zaleteli");
        }
    }

    public void activateArmour() {
        this.armour = true;
    }

    public void disableArmour() {
        this.armour = false;
    }

    public void activateSpeedBoost() {
        this.speedBoost = true;
        this.accnumber = 5;
        this.RUN_SPEED = 70;
    }

    public void disableSpeedBoost() {
        this.speedBoost = false;
        this.accnumber = 2;
        this.RUN_SPEED = 50;
    }
}