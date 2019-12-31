package engine.maths;

import engine.entitete.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Maths {
    public static Matrix4f createTransfMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix.translate(translation, matrix, matrix);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        matrix.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f ViewMatrix = new Matrix4f();
        ViewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(Camera.getPitch()), new Vector3f(1, 0, 0), ViewMatrix, ViewMatrix);
        Matrix4f.rotate((float) Math.toRadians(Camera.getYaw()), new Vector3f(0, 1, 0), ViewMatrix, ViewMatrix);
        //matrix.rotate((float) Math.toRadians(Camera.getRoll()), new Vector3f(0, 0, 1), matrix, matrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negCameraPos, ViewMatrix, ViewMatrix);
        return ViewMatrix;
    }
}
