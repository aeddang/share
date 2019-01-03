package com.ironleft.game.lib.page;
public enum ScreenEnum {
    TEST {
        public PageScreen getScreen(Object params) {
            return new TestScreen();
        }
        public boolean isHistory(){return true;}
        public boolean isHome(){return true;}
    };
    public abstract PageScreen getScreen(Object params);
    public abstract boolean isHistory();
    public abstract boolean isHome();
}
