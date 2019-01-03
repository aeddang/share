package com.ironleft.game.characters;
import com.badlogic.gdx.math.Vector2;
import com.ironleft.game.skins.CharacterTexture;

public class EarthGolemCore extends CharacterCore {
    public EarthGolemCore() {
        super();
        this.skin = new CharacterTexture("earthgolem");
        setDefaultPosition();
        this.swingArm(CharacterTexture.ARM_R_1,90f);
    }
    @Override
    public void begin()
    {
        super.begin();
    }

    @Override
    protected void setDefaultPosition()
    {
        super.setDefaultPosition();
        Vector2 headPos = positions.get(CharacterTexture.HEAD);
        headPos.x = 68;
        headPos.y = 63;
        Vector2 jwaPos = positions.get(CharacterTexture.JWA);
        jwaPos.x = 60;
        jwaPos.y = 32;

        Vector2 armL1Pos = positions.get(CharacterTexture.ARM_L_1);
        armL1Pos.x = 95;
        armL1Pos.y = 54;
        Vector2 armL2Pos = positions.get(CharacterTexture.ARM_L_2);
        armL2Pos.x = 101;
        armL2Pos.y = -1;
        Vector2 fingersLPos = positions.get(CharacterTexture.FINGERS_L);
        fingersLPos.x = 115;
        fingersLPos.y = -3;

        Vector2 armR1Pos = positions.get(CharacterTexture.ARM_R_1);
        armR1Pos.x = -40;
        armR1Pos.y = 71;

        Vector2 armR2Pos = positions.get(CharacterTexture.ARM_R_2);
        armR2Pos.x = -58;
        armR2Pos.y = -9;

        Vector2 legL1Pos = positions.get(CharacterTexture.LEG_L_1);
        legL1Pos.x = 58;
        legL1Pos.y = -19;

        Vector2 legR1Pos = positions.get(CharacterTexture.LEG_R_1);
        legR1Pos.x = 22;
        legR1Pos.y = -24;
    }
}
