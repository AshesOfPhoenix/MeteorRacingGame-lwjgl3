package engine.maths;

import engine.entitete.Avtomobil;
import engine.entitete.Entity;
import engine.entitete.Meteor;
import engine.entitete.Rocks;
import org.lwjgl.util.vector.Vector3f;

public class Collision {
    Avtomobil car;

    public Collision(Avtomobil car) {
        this.car = car;
    }


    public boolean CheckCollisionSphere(Meteor collidedEntity) {
        //?GET METEOR RADIUS
        float radius = collidedEntity.getRadius();
        //?GET OBJECTS POSITIONS
        Vector3f centerSphere = collidedEntity.getCenter();
        Vector3f centerCar = car.getCenter();
        //?GET HALF-WIDTH AND HALF-HEIGHT OF A AABB (CAR)
        Vector3f aabb_half_extents = new Vector3f(this.car.getxSize() / 2, this.car.getySize() / 2, this.car.getzSize() / 2);
        //?DIFFERENCE VECTOR BETWEEN BOTH CENTERS
        Vector3f difference = new Vector3f(centerSphere.x - centerCar.x, centerSphere.y - centerCar.y, centerSphere.z - centerCar.z);
        //?Add clamped value to AABB_center and we get the value of box closest to circle
        Vector3f clamped = clamp(difference, new Vector3f(-1 * aabb_half_extents.x, -1 * aabb_half_extents.y, -1 * aabb_half_extents.z), aabb_half_extents);
        Vector3f closest = new Vector3f(centerCar.x + clamped.x, centerCar.y + clamped.y, centerCar.z + clamped.z);

        //?Retrieve vector between center circle and closest point AABB and check if length <= radius
        difference = new Vector3f(closest.x - centerSphere.x, closest.y - centerSphere.y, closest.z - centerSphere.z);
        return lenghtVec(difference) < radius;
    }

    public boolean CheckCollisionSphere(Rocks collidedEntity) {
        //?GET METEOR RADIUS
        float radius = collidedEntity.getSphereRadius();
        //?GET OBJECTS POSITIONS
        Vector3f centerSphere = collidedEntity.getCenter();
        Vector3f centerCar = car.getCenter();
        //?GET HALF-WIDTH AND HALF-HEIGHT OF A AABB (CAR)
        Vector3f aabb_half_extents = new Vector3f(this.car.getxSize() / 2, this.car.getySize() / 2, this.car.getzSize() / 2);
        //?DIFFERENCE VECTOR BETWEEN BOTH CENTERS
        Vector3f difference = new Vector3f(centerSphere.x - centerCar.x, centerSphere.y - centerCar.y, centerSphere.z - centerCar.z);
        //?Add clamped value to AABB_center and we get the value of box closest to circle
        Vector3f clamped = clamp(difference, new Vector3f(-1 * aabb_half_extents.x, -1 * aabb_half_extents.y, -1 * aabb_half_extents.z), aabb_half_extents);
        Vector3f closest = new Vector3f(centerCar.x + clamped.x, centerCar.y + clamped.y, centerCar.z + clamped.z);

        //?Retrieve vector between center circle and closest point AABB and check if length <= radius
        difference = new Vector3f(closest.x - centerSphere.x, closest.y - centerSphere.y, closest.z - centerSphere.z);
        return lenghtVec(difference) < radius;
    }

    public boolean CheckCollisionSquare(Entity entity) {
        boolean collisionX = this.car.getCenter().x + this.car.getxSize() >= entity.getCenter().x && entity.getCenter().x + entity.getxSize() >= this.car.getCenter().x;
        boolean collisionY = this.car.getCenter().y + this.car.getySize() >= entity.getCenter().y && entity.getCenter().y + entity.getySize() >= this.car.getCenter().y;
        boolean collisionZ = this.car.getCenter().z + this.car.getzSize() >= entity.getCenter().z && entity.getCenter().z + entity.getzSize() >= this.car.getCenter().z;
        return collisionX && collisionY && collisionZ;
    }

    public Vector3f clamp(Vector3f value, Vector3f vec1, Vector3f vec2) {
        float X = (float) Math.max(vec1.x, Math.min(vec2.x, value.x));
        float Y = (float) Math.max(vec1.y, Math.min(vec2.y, value.y));
        float Z = (float) Math.max(vec1.z, Math.min(vec2.z, value.z));
        return new Vector3f(X, Y, Z);
    }

    public float lenghtVec(Vector3f vector3f) {
        return (float) Math.sqrt(Math.pow(vector3f.x, 2) + Math.pow(vector3f.y, 2) + Math.pow(vector3f.z, 2));
    }
}
