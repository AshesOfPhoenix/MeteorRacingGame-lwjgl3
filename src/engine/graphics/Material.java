package engine.graphics;


public class Material {
    private String filename;
    private Texture texture;
    private float width, height;
    private int textureID;

    public Material(Texture texture) {
        this.texture = texture;
        this.textureID = texture.getTextureID();
    }

    /*
    public Material(int ID) {
        this.textureID = ID;
    }
    */
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
