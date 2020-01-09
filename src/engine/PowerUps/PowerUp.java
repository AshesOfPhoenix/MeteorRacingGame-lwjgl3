package engine.PowerUps;

import engine.entitete.Entity;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PowerUp {
    private float interpolation = 0;
    private boolean upOrdown = true;
    private Armour armour;
    private SpeedBoost speedBoost;
    private boolean speedBoostTimerStart = false;
    private boolean armourTimerStart = false;
    private boolean isBoostActive = false;
    private boolean isArmourActive = false;
    private long startSpeed;
    private long startArmour;
    List<SpeedBoost> speedBoosts = new ArrayList<>();
    List<Armour> armours = new ArrayList<>();

    public PowerUp(List<Entity> powerups) {
        for (int i = 0; i < powerups.size(); i += 2) {
            this.speedBoosts.add((SpeedBoost) powerups.get(i));
        }
        for (int i = 1; i < powerups.size(); i += 2) {
            this.armours.add((Armour) powerups.get(i));
        }
    }

    public void update(List<Entity> powerups) {
        for (int i = 0; i < powerups.size(); i += 2) {
            this.speedBoosts.add((SpeedBoost) powerups.get(i));
        }
        for (int i = 1; i < powerups.size(); i += 2) {
            this.armours.add((Armour) powerups.get(i));
        }
    }

    private Vector3f resetSpeedLocation() {
        float randx = (float) (Math.random() * 10000 + (0));
        float randz = (float) (Math.random() * 5000 + (0));
        return new Vector3f(randx, 0.3f, randz);
    }

    private Vector3f resetArmourLocation() {
        float randx = (float) (Math.random() * 10000 + (0));
        float randz = (float) (Math.random() * 5000 + (0));
        return new Vector3f(randx, 4.35f, randz);
    }


    public void bouncy_bouncy() {
        interpolate();
        checkActiveTimers();
        for (SpeedBoost boost : speedBoosts) {
            boost.increaseRotation(0, 0.0f, 2.1f);
            boost.increasePosition(0, this.interpolation, 0);
        }
        for (Armour protection : armours) {
            protection.increaseRotation(0, -2.0f, 0);
            protection.increasePosition(0, this.interpolation, 0);
        }
    }

    public void checkActiveTimers() {
        if (this.speedBoostTimerStart) {
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - startSpeed;
            if (timeElapsed > 10000) {
                isBoostActive = false;
                this.speedBoostTimerStart = false;
            }
        }
        if (this.armourTimerStart) {
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - startArmour;
            if (timeElapsed > 10000) {
                isArmourActive = false;
                this.armourTimerStart = false;
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
        for (SpeedBoost boost : speedBoosts) {
            float relativeDistance = (float) Math.sqrt(Math.pow(carPosition.getX() - boost.getPosition().getX(), 2) + Math.pow(carPosition.getY() - boost.getPosition().getY(), 2) + Math.pow(carPosition.getZ() - boost.getPosition().getZ(), 2));
            if (relativeDistance <= 7) {
                isBoostActive = true;
                boost.setActive(isBoostActive);
                this.speedBoostTimerStart = isBoostActive;
                boost.setPosition(resetSpeedLocation());
                startSpeed = System.currentTimeMillis();
            }
        }
    }

    public void collectArmour(Vector3f carPosition) {
        for (Armour protection : armours) {
            float relativeDistance = (float) Math.sqrt(Math.pow(carPosition.getX() - protection.getPosition().getX(), 2) + Math.pow(carPosition.getY() - protection.getPosition().getY(), 2) + Math.pow(carPosition.getZ() - protection.getPosition().getZ(), 2));
            if (relativeDistance <= 7) {
                isArmourActive = true;
                protection.setActive(isArmourActive);
                this.armourTimerStart = isArmourActive;
                protection.setPosition(resetArmourLocation());
                startArmour = System.currentTimeMillis();
            }
        }
    }

    public void cleanUp() {
        speedBoosts.clear();
        armours.clear();
    }

    public boolean checkIfPickedUpSpeed() {
        return isBoostActive;
    }

    public boolean checkIfPickedUpArmour() {
        return isArmourActive;
    }
}
