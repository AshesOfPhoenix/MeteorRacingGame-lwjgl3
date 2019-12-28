package engine.maths;

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
}
