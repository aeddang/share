package com.ironleft.game.lib.page;
public enum WindowEnum {
    TEST {
        public PageWindow getWindow(Object... params) {
            return new TestWindow((String) params[0]);
        }
    };
    public abstract PageWindow getWindow(Object... params);
}