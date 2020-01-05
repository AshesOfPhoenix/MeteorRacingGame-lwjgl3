package engine.graphics;


public class Material {
    private String filename;
    private Texture texture;
    private float width, height;
    private int textureID;

    //lightning config
    private float shineDamper = 1;
    private float reflectivity = 0;

    public Material(Texture texture, float shineDamper, float reflectivity) {
        this.texture = texture;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.textureID = texture.getTextureID();
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public void Bind() {
        this.texture.bind();
    }

    public void unBind() {
        this.texture.unbind();
    }

    public Texture getTexture() {
        return this.texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return this.textureID;
    }

    public void create() {
        this.texture = new Texture("objects/textures/" + filename);
        width = this.texture.getWidth();

        height = this.texture.getHeight();
        this.textureID = this.texture.getTextureID();
        this.texture.bind();
    }
}
