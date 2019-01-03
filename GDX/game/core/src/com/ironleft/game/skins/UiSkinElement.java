package com.ironleft.game.skins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UiSkinElement {
    Pixmap pixmap;
    Texture texture;
    BitmapFont font;

    public UiSkinElement(){
        this.pixmap = new Pixmap(1, 1, Pixmap.Format.Alpha.RGBA8888);
        this.pixmap.setColor(Color.WHITE);
        this.pixmap.fill();
        this.texture = new Texture(pixmap);
        this.font = new BitmapFont();
    }
}
