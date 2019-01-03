package com.ironleft.game.lib.page;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.ironleft.game.characters.EarthGolem;
import com.ironleft.game.characters.EarthGolemCore;
import com.ironleft.game.lib.rx.RXUIFactory;
import com.ironleft.game.skins.UiSkin;

public class TestScreen extends PageScreen
{
    private EarthGolem batch;
    private Button button;

    @Override
    public void buildStage()
    {
        UiSkin uiSkin = UiSkin.getInstance();
        batch = new EarthGolem();
        batch.init(new Vector3(300,300,0));

        button = new Button(uiSkin);
        button.setSize(50,50);
        button.setPosition(100,100);
        this.addActor(button);

        RXUIFactory.bindButton(this.button).subscribe(e-> {

        });
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw();
        batch.end();
    }
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}