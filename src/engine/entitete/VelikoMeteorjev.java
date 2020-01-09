package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;


public class VelikoMeteorjev {
    public List<Meteor> meteorji = new ArrayList<>();
    private TextureModel textureModel;
    private boolean LevelUp = false;

    public void setLevelUp(boolean levelUp) {
        LevelUp = levelUp;
    }

    public VelikoMeteorjev(List<Meteor> meteorcki, TextureModel textureModel) {
        this.textureModel = textureModel;
        for (Meteor petarda : meteorcki) {
            meteorji.add(petarda);
        }
    }

    public void update(int stevilometeorjev) {
        cleanUp();
        Meteor meteor;
        for (int i = 0; i < stevilometeorjev; i++) {
            float randx = (float) (Math.random() * 10000 + (-0));
            float randz = (float) (Math.random() * 5000 + (-0));
            float randy = (float) ((Math.random() * 500) + 350);
            float randomsize = (float) (Math.random() * 0.05 + 0.01);
            meteor = new Meteor(textureModel, new Vector3f(randx, randy, randz), 0, 0, 0, randomsize);
            meteorji.add(meteor);
        }
    }

    public void updateCount(int stevilometeorjev, float nov_SPEEED) {
        cleanUp();
        Meteor meteor;
        int nov = stevilometeorjev - meteorji.size();
        for (int i = meteorji.size(); i < stevilometeorjev; i++) {
            float randomsize = (float) (Math.random() * 0.05 + 0.01);
            meteor = new Meteor(textureModel, resetLocation(), 0, 0, 0, randomsize);
            meteor.incrementSpeed(-nov_SPEEED);
            meteorji.add(meteor);
        }
    }

    private Vector3f resetLocation() {
        float randx = (float) (Math.random() * 10000 + (0));
        float randz = (float) (Math.random() * 5000 + (0));
        float randy = (float) ((Math.random() * 500) + 350);
        return new Vector3f(randx, randy, randz);
    }

    public void cleanUp() {
        meteorji.clear();
        LevelUp = false;
    }
}
