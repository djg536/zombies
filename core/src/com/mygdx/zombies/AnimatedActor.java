package com.mygdx.zombies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AnimatedActor extends BaseActor {
    private float elapsedTime;
    private Animation<TextureRegion> activeAnim;
    private String activeName;
    private HashMap<String, Animation<TextureRegion>> animationStorage;

    public AnimatedActor() {
        super();
        elapsedTime = 0;
        activeAnim = null;
        activeName = null;
        animationStorage = new HashMap<String, Animation<TextureRegion>>();
    }

    public void storeAnimation(String name, Animation anim) {
        animationStorage.put(name, anim);
        if (activeName == null)
            setActiveAnimation(name);
    }

    public void storeAnimation(String name, Texture tex) {
        TextureRegion reg = new TextureRegion(tex);
        TextureRegion[] frames = {reg};
        Animation anim = new Animation(0.1f, frames);
        storeAnimation(name, anim);
    }

    public void setActiveAnimation(String name) {
        if (!animationStorage.containsKey(name)){
            System.out.println("No animation: " + name);
            return;
        }

        if (activeName.equals(name))
            return;

        activeName = name;
        activeAnim = animationStorage.get(name);
        elapsedTime = 0;
        TextureRegion tex = activeAnim.getKeyFrame(0, true);
        setWidth(tex.getRegionWidth());
        setHeight(tex.getRegionHeight());
    }

    public String getAnimationName() {
        return activeName;
    }

    public void act(float dt) {
        super.act(dt);
        elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha) {
//        region.setRegion(activeAnim.getKeyFrame(elapsedTime, true));
        super.draw(batch, parentAlpha);
    }
}
