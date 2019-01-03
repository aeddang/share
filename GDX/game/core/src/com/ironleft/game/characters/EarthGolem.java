package com.ironleft.game.characters;
import com.badlogic.gdx.math.Vector3;
import com.ironleft.game.skins.FrameSkin;


public class EarthGolem extends CharacterSprite {
    public EarthGolem() {
        super();
        this.skin = new FrameSkin("earthgolem/set.png", 4, 300, 224);
    }
    @Override
    protected void setDefaultPosition(Vector3 pos)
    {
        super.setDefaultPosition(pos);
    }
    @Override
    public void doAttack()
    {
        super.doAttack();
    }


}
