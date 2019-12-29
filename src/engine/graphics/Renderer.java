package engine.graphics;

import engine.io.Window;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

//!Contains a specific shader program
public class Renderer {
	private Shader shader;
	private static final float FOV= 70;
	private static final float NEAR_PLANE =0.1f;
	private static final float FAR_PlANE=1000;
	private Matrix4f projectionMatrix;
    private int location_projfMatrix;
	public Renderer(Shader shader) {
		createProjectionMatrix();
		this.shader = shader;
		shader.bind();
		shader.loadMatrix(location_projfMatrix,projectionMatrix);
		shader.unbind();
	}

	//!Render mesh by binding vao, enabling vertex attributes and drawing the mesh
	public void renderMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D,mesh.getMaterial().getTextureID());
		shader.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void createProjectionMatrix(){
		float aspectRatio=(float) Window.getWidth()/(float) Window.getHeight();
		float yscale= (float) ((1f / Math.tan(Math.toRadians((FOV/2f)))) *aspectRatio);
		float xscale=yscale/aspectRatio;
		float lenght=FAR_PlANE-NEAR_PLANE;

		projectionMatrix=new Matrix4f();
		projectionMatrix.m00=xscale;
		projectionMatrix.m11=yscale;

		projectionMatrix.m22=-((FAR_PlANE+NEAR_PLANE)/lenght);
		projectionMatrix.m23=-1;
		projectionMatrix.m32= -((2*NEAR_PLANE*FAR_PlANE)/lenght);
		projectionMatrix.m33=0;

	}
}