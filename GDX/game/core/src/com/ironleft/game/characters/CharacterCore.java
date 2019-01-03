package com.ironleft.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.ironleft.game.skins.CharacterTexture;


public abstract class CharacterCore extends SpriteBatch {
    protected CharacterTexture skin;
    protected Vector2 position;
    protected Array<Vector2> positions;
    protected Array<Vector2> origins;
    protected Array<Vector3> mutates;

    protected float speed = 1f;
    protected CharacterCore()
    {
        super();
    }

    protected void setDefaultPosition()
    {
        position = new Vector2(400,300);
        positions = new Array<Vector2>();
        origins = new Array<Vector2>();
        mutates = new Array<Vector3>();
        for(int i = 0; i<CharacterTexture.TOTAL; ++i)
        {
            positions.add(new Vector2(0,0));
            origins.add(new Vector2(0,0));
            mutates.add(new Vector3(0,0,0));

        }
    }

    protected void swingArm(int idx,float rotate)
    {
        Vector2 arm1Pos = positions.get(idx);
        Vector2 arm1Origin = origins.get(idx);
        Vector3 arm1Mutate = mutates.get(idx);
        Texture arm1 = skin.getTexture(idx);

        arm1Origin.x = (float)arm1.getWidth() * 0.7f;
        arm1Origin.y = (float)arm1.getHeight() * 0.5f;
        arm1Mutate.z += rotate;

        int idx2 = idx+1;
        Vector2 arm2Pos = positions.get(idx2);
        Vector3 arm2Mutate = mutates.get(idx2);
        Vector2 arm2Origin = origins.get(idx2);
        Texture arm2 = skin.getTexture(idx2);
        arm2Origin.x = (float)arm2.getWidth() * 0.7f;
        arm2Origin.y = (float)arm2.getHeight() * 0.7f;

        float r = (float)arm1.getHeight() * 0.5f;
        double radian = rotate/180*Math.PI;
        arm2Mutate.x += r * (float)Math.sin(radian);
        arm2Mutate.y += -((r * (float)Math.cos(radian)) - r);
        arm2Mutate.z += rotate;
    }

    protected void step()
    {
        Vector3 legL1 = mutates.get(CharacterTexture.LEG_L_1);
        Vector3 legR1 = mutates.get(CharacterTexture.LEG_R_1);
        legL1.y += (legL1.y == 0) ? 2 : 0;
        legR1.y += (legR1.y == 0) ? 2 : 0;
    }

    @Override
    public void begin()
    {
        super.begin();
    }

    private Vector3 getDelta(int idx)
    {
        Vector3 delta = new Vector3();
        Vector3 mutate = this.mutates.get(idx);
        Vector3 mutated = this.mutates.get(idx);

        float dx = mutated.x - mutate.x;
        float dy = mutated.y - mutate.y;
        float dz = mutated.z - mutate.z;
        return delta;
    }

    public void draw()
    {
        int len = this.positions.size;
        for(int i=0; i<len; ++i)
        {
            Vector2 pos = this.positions.get(i);
            Vector3 mutate = this.mutates.get(i);

            Vector2 origin = this.origins.get(i);
            Texture texture = this.skin.getTexture(i);
            if(texture != null) {
                float x = pos.x + mutate.x + position.x;
                float y = pos.y + mutate.y + position.y;
                if(mutate.x + mutate.y + mutate.z == 0){
                    super.draw(texture, x, y);
                }else{
                    int w = texture.getWidth();
                    int h = texture.getHeight();
                    super.draw(texture, x, y,origin.x,origin.y,w,h,1f,1f,mutate.z,0,0,w,h,false,false);
                }
            }
        }
    }
}
