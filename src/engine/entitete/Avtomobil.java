package engine.entitete;

import engine.Models.TextureModel;
import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Avtomobil extends Entity {
    private static final float noPOWER = 3;
    private static final float TURN_SPEED = 20;
    static float accnumber = 2;
    private static float RUN_SPEED = 50;
    boolean speedBoost = false;
    boolean armour = false;
    boolean GAME_OVER = false;

    public boolean isGAME_OVER() {
        return GAME_OVER;
    }

    public void setGAME_OVER(boolean GAME_OVER) {
        this.GAME_OVER = GAME_OVER;
    }

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

    public void resetCarSpeed() {
        this.currentSpeed = 0;
        this.currentTS = 0;
        disableSpeedBoost();
        disableArmour();
    }

    public Avtomobil(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.zSize = model.getRawModel().getySize() * scale;
        this.ySize = model.getRawModel().getzSize() * scale;
        this.center = new Vector3f(position.x, position.y + ySize / 2, position.z);
    }

    public void move() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        checkInputs();
        //increase player roatiton in y cordinate in trunspead mnozimo z FTS-jem ker je moramo meriti obrat z casom
        super.increaseRotation(0, 0, currentTS / 15);
        //calculate distance distanceperseccond*current time
        float distance = currentSpeed / 20;

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotZ())));

        /*
        if (center.x >= 10000 || center.x < 0 || center.z >= 5000 || center.z < 0) {
            for (int i = 0; i < 1000000; i++) {
                super.increasePosition(0, (float) 0.001, 0);
                GAME_OVER = true;
            }
        }

         */
        if (center.x >= 10000) {
            super.increasePosition(-5, 0, 0);
        }
        if (center.x < 0) {
            super.increasePosition(5, 0, 0);
        }
        if (center.z >= 5000) {
            super.increasePosition(0, 0, -5);
        }
        if (center.z < 0) {
            super.increasePosition(0, 0, 5);
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_G)) {
            play();
        }
        super.increasePosition(dx, 0, dz);
        Vector3f a = super.getPosition();
        this.center = new Vector3f(a.x, a.y + this.ySize / 2, a.z);
    }

    //check the keyboard input and set car moving speed
    private void checkInputs() {
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            if (this.currentSpeed > -RUN_SPEED) {
                this.currentSpeed += -accnumber;
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

        } else if (this.currentSpeed < 0) {
            this.currentSpeed += noPOWER;
            if (this.currentSpeed > -1) {
                this.currentSpeed = 0;
            }
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

    public void activateArmour() {
        this.armour = true;
    }

    public boolean isArmour() {
        return this.armour;
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

    private void play() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        File f = new File("C:\\Users\\Emera\\Desktop\\MeteorRacingGame\\audio\\Carn Horn 1.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
}