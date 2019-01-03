package com.ironleft.game.lib.page;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ironleft.game.skins.UiSkin;


public class TestWindow extends PageWindow
{
    public TestWindow(String title)
    {
        super(title,UiSkin.getInstance());
        this.setModal(true);
        this.setMovable(true);
    }
    @Override
    public void buildWindow()
    {
        TextButton testBtn = new TextButton("test",this.getSkin());
        this.add(testBtn);
    }
}