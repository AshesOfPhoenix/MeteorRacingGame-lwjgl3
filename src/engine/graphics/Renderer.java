package engine.graphics;

import org.lwjgl.opengl.*;

//!Contains a specific shader program
public class Renderer {
	private Shader shader;
	
	public Renderer(Shader shader) {
		this.shader = shader;
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
}