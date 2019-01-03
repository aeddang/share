package com.ironleft.game.lib.page;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class PageWindow extends Window
{
    protected PageWindow(String title, Skin skin) {
        super(title,skin);
    }
    public abstract void buildWindow();
}
