package com.ironleft.game.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ironleft.game.skins.FrameSkin;

public abstract class CharacterSprite extends SpriteBatch {
    protected FrameSkin skin;

    protected Boolean isPlay = true;
    protected Vector3 position;
    protected Vector3 mutate;
    protected CharacterSprite()
    {
        super();
    }

    public void init(Vector3 pos)
    {
        setDefaultPosition(pos);
    }

    protected void setDefaultPosition(Vector3 pos)
    {
        position = pos;
        mutate = new Vector3(0,0,0);
    }

    public Boolean attack()
    {
        if(skin.isBusy()) return false;
        doAttack();
        return true;
    }

    protected void doAttack()
    {

    }

    public void reset()
    {
        skin.reset();
    }

    public void draw()
    {
        TextureRegion region = (isPlay) ? skin.next() : skin.getFrame();
        super.draw(region,position.x,position.y);
        /*
        TextureRegion region, float x, float y, float originX, float originY, float width, float height,
        float scaleX, float scaleY, float rotation)
        */
    }
}