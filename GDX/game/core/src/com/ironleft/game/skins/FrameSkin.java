package com.ironleft.game.skins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class FrameSkin {

    private Array<TextureRegion> frames;
    private int currentFrame = 0;
    private int totalrame = 0;
    private int tagetFrame = -1;
    private int frameRate = 5;

    public FrameSkin(String path, int totalFrame, int w, int h)
    {
        this.init(path,totalFrame,w,h,totalFrame);
    }
    public FrameSkin(String path, int totalFrame, int w, int h, int line)
    {
        this.init(path,totalFrame,w,h,line);
    }

    private void init(String path, int totalFrame, int w, int h, int line)
    {
        currentFrame = 0;
        totalrame = totalFrame;
        frames = new Array<TextureRegion>();
        Texture set = new Texture(path);

        Gdx.app.log("CharacterTexture", String.format("set : %s %s",set.getWidth(),set.getHeight()));
        for(int i = 0; i< totalFrame; ++i) {
            int tx = w * (i % line);
            int ty = h * (int)Math.floor(i/line);

            Gdx.app.log("CharacterTexture", String.format("position : %s %s",w,h));
            frames.add(new TextureRegion(set,tx,ty,w,h));
        }
        totalrame = frameRate * totalFrame;
    }

    public void setFrameRate(int rate)
    {
        frameRate = rate;
    }

    public TextureRegion getFrame(){
        TextureRegion region = frames.get(currentFrame);
        return region;
    }

    public TextureRegion next(){
        TextureRegion region = frames.get((int)Math.floor(currentFrame/frameRate));
        currentFrame ++ ;
        if (totalrame == -1 && currentFrame>= tagetFrame)  currentFrame = tagetFrame;
        if(currentFrame >= totalrame) currentFrame = 0;
        return region;
    }

    public Boolean isBusy(){
        if (totalrame == -1) return false;
        return (currentFrame == tagetFrame) ? false : true;
    }

    public void reset(){
        currentFrame = 0;
        tagetFrame = -1;
    }

    public void rangePlay(int start, int end){
        currentFrame = start;
        tagetFrame = end * frameRate;
    }

    public void dispose () {

        frames.clear();
    }

}
