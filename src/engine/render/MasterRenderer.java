package engine.render;

import engine.Models.TextureModel;
import engine.entitete.Camera;
import engine.entitete.Entity;
import engine.entitete.Light;
import engine.entitete.Terrain;
import engine.graphics.StaticShader;
import engine.graphics.TerrainShader;
import engine.io.Window;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {
    private static final float FOV = 90;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PlANE = 1000;
    private static final float RED = 0.78f;
    private static final float GREEN = 0.23f;
    private static final float BLUE = 0.29f;
    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader("resources\\shaders\\MainEntityVertex.glsl", "resources\\shaders\\MainEntityFragment.glsl");
    private EntityRenderer entityRenderer;

    private TerrainShader terrainShader = new TerrainShader("resources\\shaders\\TerrainVertexShader.glsl", "resources\\shaders\\TerrainFragmentShader.glsl");
    private TerrainRenderer terrainRenderer;

    private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() throws IOException {
        prepare();
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public void render(Light sun, Camera camera) {
        //!Render entities
        shader.bind();
        shader.loadSkyColor(RED, GREEN, BLUE);
        shader.UniformLight(sun);
        shader.UniformViewMatrix(camera);
        entityRenderer.render(entities);
        shader.UnBind();
        //!Render terrains
        terrainShader.bind();
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.UniformLight(sun);
        terrainShader.UniformViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.UnBind();
        //!Clear arraylists
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
        if (batch != null) { //!Check if batch already exists
            batch.add(entity);
        } else {             //!Else create new one
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.destroy();
        terrainShader.destroy();
    }

    public void prepareFog() {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        GL30.glClearColor(RED, GREEN, BLUE, 1);
    }

    private void prepare() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glEnable(GL30.GL_CULL_FACE);
        GL30.glCullFace(GL30.GL_BACK);
    }


    public void disable() {
        GL30.glEnable(0);
    }
}
