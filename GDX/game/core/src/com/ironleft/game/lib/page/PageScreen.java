package com.ironleft.game.lib.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class PageScreen extends Stage implements Screen {

    private PageScreenListener listener = null;

    protected PageScreen() {
        super( new StretchViewport(640f, 480.0f, new OrthographicCamera()) );
    }

    // Subclasses must load actors in this method
    public abstract void buildStage();
    public void setPageScreenListener(PageScreenListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void dispose () {
        super.dispose();
        this.listener = null;
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calling to Stage methods
        super.act(delta);
        super.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) this.onBack();
        return false;
    }
    protected void onBack()
    {
        if(this.listener == null) return;
        this.listener.onBack(this);
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}


    static public interface PageScreenListener {
        public void onBack (PageScreen screen);
    }
}

