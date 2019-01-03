package com.ironleft.game.skins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class CharacterTexture {

    private Array<Texture> textures;
    private int idx = 0;
    public CharacterTexture(String path){

        this.textures = new Array<Texture>();
        Texture head = getTexture(String.format("%s/heads.png",path));
        Texture armR1 = getTexture(String.format("%s/arm_r_1.png",path));
        Texture armR2 = getTexture(String.format("%s/arm_r_2.png",path));
        Texture armL1 = getTexture(String.format("%s/arm_l_1.png",path));
        Texture armL2 = getTexture(String.format("%s/arm_l_2.png",path));
        Texture fingersL = getTexture(String.format("%s/fingers_l.png",path));
        Texture fingersR = getTexture(String.format("%s/fingers_r.png",path));
        Texture legR1 = getTexture(String.format("%s/leg_r_1.png",path));
        Texture legR2 = getTexture(String.format("%s/leg_r_2.png",path));
        Texture legL1 = getTexture(String.format("%s/leg_l_1.png",path));
        Texture legL2 = getTexture(String.format("%s/leg_l_2.png",path));
        Texture jwa = getTexture(String.format("%s/jwa.png",path));
        Texture torso = getTexture(String.format("%s/torso.png",path));
        this.textures.add(armL1,armL2,fingersL);
        this.textures.add(torso);
        this.textures.add(legL1,legL2,legR1,legR2);
        this.textures.add(head,jwa);
        this.textures.add(armR1,armR2,fingersR);
    }

    private Texture getTexture(String path) {

        try {
            Texture texture = new Texture(path);
            return texture;
        }catch (GdxRuntimeException e)
        {
            Gdx.app.log("CharacterTexture", String.format("no file : %s",path));
            return null;
        }
    }

    public Texture getTexture(int idx) {
        return this.textures.get(idx);
    }

    public static final int HEAD = 8;
    public static final int JWA = 9;

    public static final int ARM_L_1 = 0;
    public static final int ARM_L_2 = 1;
    public static final int FINGERS_L = 2;
    public static final int TORSO = 3;

    public static final int ARM_R_1 = 10;
    public static final int ARM_R_2 = 11;
    public static final int FINGERS_R = 12;

    public static final int LEG_L_1 = 4;
    public static final int LEG_L_2 = 5;
    public static final int LEG_R_1 = 6;
    public static final int LEG_R_2 = 7;

    public static final int TOTAL = 13;
}
