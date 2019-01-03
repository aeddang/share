package com.ironleft.game;

import com.ironleft.game.lib.page.PagePresenter;
import com.ironleft.game.lib.page.ScreenEnum;

public class GameMain extends com.badlogic.gdx.Game {

	@Override
	public void create ()
	{
		PagePresenter.getInstance().initialize(this);
		PagePresenter.getInstance().changeScreen(ScreenEnum.TEST,null);
		//PagePresenter.getInstance().addWindow(WindowEnum.TEST,"title");
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize (int width, int height) {
		super.resize(width,height);
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
