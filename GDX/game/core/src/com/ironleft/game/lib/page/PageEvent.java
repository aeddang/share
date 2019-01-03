package com.ironleft.game.lib.page;
import com.badlogic.gdx.scenes.scene2d.Event;

public class PageEvent extends Event {
    private Type type;
    public PageEvent(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }

    public String toString (){
        return type.toString();
    }

    static public enum Type {
        onBack
    }
}


