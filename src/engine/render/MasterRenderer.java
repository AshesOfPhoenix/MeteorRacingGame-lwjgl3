package engine.render;

import engine.Models.TextureModel;
import engine.entitete.Camera;
import engine.entitete.Entity;
import engine.entitete.Terrain;
import engine.graphics.StaticShader;
import engine.io.Window;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {
    private static final float FOV = 90;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PlANE = 1000;
    private Matrix4f projectionMatrix;

    private StaticShader shader;
    private EntityRenderer renderer;

    //private TerrainRenderer terrainRenderer;
    //private TerrainShader terrainShader = new TerrainShader();

    private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();

    public MasterRenderer() {
        GL30.glEnable(GL30.GL_CULL_FACE);
        GL30.glCullFace(GL30.GL_BACK);
        createProjectionMatrix();
        renderer = new EntityRenderer(shader);
        //terrainRenderer = new TerrainRenderer(shader,projectionMatrix);
    }

    public void render(Camera camera) {
        prepare();
        shader.bind();
        //shader.loadLight(sun);
        shader.UniformViewMatrix(camera);
        //renderer.renderEntity(entities);
        shader.UnBind();
        //terrainShader.start();
        //terrainShader.loadLight(sun);
        //terrainShader.loadViewMatrix(camera);
        //terrainRenderer.render(terrains);
        //terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float yscale = (float) ((1f / Math.tan(Math.toRadians((FOV / 2f)))) * aspectRatio);
        float xscale = yscale / aspectRatio;
        float lenght = FAR_PlANE - NEAR_PLANE;

        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.m00 = xscale;
        this.projectionMatrix.m11 = yscale;

        this.projectionMatrix.m22 = -((FAR_PlANE + NEAR_PLANE) / lenght);
        this.projectionMatrix.m23 = -1;
        this.projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PlANE) / lenght);
        this.projectionMatrix.m33 = 0;
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(Entity entity) {
        TextureModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void prepare() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glEnable(GL30.GL_BLEND);
    }

    public void cleanUp() {
        shader.destroy();
        //terrainShader.destroy();
    }

    public void disable() {
        GL30.glEnable(0);
    }
}
