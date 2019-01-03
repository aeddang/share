package com.ironleft.game.lib.page;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ironleft.game.lib.rx.RXUIFactory;
import java.util.ArrayList;

public class PagePresenter
{
    private static PagePresenter instance;
    public static PagePresenter getInstance()
    {
        if (instance == null) instance = new PagePresenter();
        return instance;
    }

    private Game game;
    private PageModel model;
    private ArrayList<PageWindow> popups;
    public void initialize(Game game) {
        this.game = game;
        this.popups = new ArrayList<PageWindow>();
        this.model = new PageModel();
    }

    public void onBack() {
        if(this.popups.isEmpty()) {
            PageModel.HistoryModel screenEnum = this.model.getHistory();
            if(screenEnum != null) this.changeScreen(screenEnum.key,screenEnum.params);
        }else{
            this.closeWindow(this.popups.get(this.popups.size()-1));
        }
    }

    public void changeScreen(ScreenEnum screenEnum, Object... params) {
        this.model.addHistory(screenEnum,params);
        Screen currentScreen = this.game.getScreen();
        PageScreen newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();
        newScreen.setPageScreenListener(e->{this.onBack();});
        this.game.setScreen(newScreen);
        if (currentScreen != null) currentScreen.dispose();
    }

    public void closeWindow(PageWindow window) {
        this.popups.remove(window);
        window.clear();
    }

    public void openWindow(WindowEnum windowEnum, Object... params) {

        PageScreen currentScreen = (PageScreen)this.game.getScreen() ;
        PageWindow newWindow = windowEnum.getWindow(params);
        newWindow.buildWindow();
        currentScreen.addActor(newWindow);
        this.popups.add(newWindow);
    }
}
