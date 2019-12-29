package engine.maths;

import entitete.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
public class Maths {
    public static Matrix4f createTransfMatrix(Vector3f translation,float rx,float ry,float rz,float scale){
        Matrix4f matrix=new Matrix4f();
        matrix.setIdentity();
        matrix.translate(translation, matrix, matrix);
        matrix.rotate((float)Math.toRadians(rx),new org.lwjgl.util.vector.Vector3f(1,0,0),matrix,matrix);
        matrix.rotate((float)Math.toRadians(ry),new org.lwjgl.util.vector.Vector3f(0,1,0),matrix,matrix);

        matrix.rotate((float)Math.toRadians(rz),new org.lwjgl.util.vector.Vector3f(0,0,1),matrix,matrix);

        matrix.scale(new Vector3f(scale,scale,scale),matrix,matrix);

        return matrix;

    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f matrix=new Matrix4f();
        matrix.setIdentity();
        matrix.rotate((float)Math.toRadians(Camera.getPitch()),new org.lwjgl.util.vector.Vector3f(1,0,0),matrix,matrix);
        matrix.rotate((float)Math.toRadians(Camera.getYaw()),new org.lwjgl.util.vector.Vector3f(0,1,0),matrix,matrix);

        Vector3f cameraPos=camera.getPosition();
        Vector3f negCameraPos=new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negCameraPos,matrix,matrix);

        return matrix;

    }
}
