package engine.PowerUps;

import engine.entitete.Entity;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class PowerUp {
    private float interpolation = 0;
    private boolean upOrdown = true;
    private Armour armour;
    private SpeedBoost speedBoost;
    private boolean speedBoostTimerStart = false;
    private boolean armourTimerStart = false;
    private long startSpeed;
    private long startArmour;

    public PowerUp(List<Entity> powerups) {
        this.speedBoost = (SpeedBoost) powerups.get(0);
        this.armour = (Armour) powerups.get(1);
    }

    public void bouncy_bouncy() {
        interpolate();
        checkActiveTimers();
        this.speedBoost.increaseRotation(0, 0.0f, 2.00f);
        this.speedBoost.increasePosition(0, this.interpolation, 0);
        this.armour.increaseRotation(0, -2.0f, 0);
        this.armour.increasePosition(0, this.interpolation, 0);
    }

    public void checkActiveTimers() {
        if (this.speedBoostTimerStart) {
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - startSpeed;
            if (timeElapsed > 10000) {
                this.speedBoost.setActive(false);
            }
        }
        if (this.armourTimerStart) {
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - startArmour;
            if (timeElapsed > 10000) {
                this.armour.setActive(false);
            }
        }
    }

    private void interpolate() {
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
    }

    public void collectSpeedBoost(Vector3f carPosition) {
        Vector3f speedPowerUp = this.speedBoost.getPosition();
        float relativeDistance = (float) Math.sqrt(Math.pow(carPosition.getX() - speedPowerUp.getX(), 2) + Math.pow(carPosition.getY() - speedPowerUp.getY(), 2) + Math.pow(carPosition.getZ() - speedPowerUp.getZ(), 2));
        if (relativeDistance <= 5) {
            this.speedBoost.setActive(true);
            this.speedBoostTimerStart = true;
            startSpeed = System.currentTimeMillis();
        }
    }

    public void collectArmour(Vector3f carPosition) {
        Vector3f armourPowerUp = this.armour.getPosition();
        float relativeDistance = (float) Math.sqrt(Math.pow(carPosition.getX() - armourPowerUp.getX(), 2) + Math.pow(carPosition.getY() - armourPowerUp.getY(), 2) + Math.pow(carPosition.getZ() - armourPowerUp.getZ(), 2));
        if (relativeDistance <= 5) {
            this.armour.setActive(true);
            this.armourTimerStart = true;
            startArmour = System.currentTimeMillis();
        }
    }

    public boolean checkIfPickedUpSpeed() {
        return this.speedBoost.isActive();
    }

    public boolean checkIfPickedUpArmour() {
        return this.armour.isActive();
    }
}
