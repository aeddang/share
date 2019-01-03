package com.ironleft.game.lib.page;
import java.util.Stack;

public class PageModel
{
    private HistoryModel currentHistoryStack = null;
    private Stack<HistoryModel> historys;

    public PageModel()
    {
        this.historys = new Stack<HistoryModel>();
    }

    public void addHistory(ScreenEnum screen,Object... params)
    {
        HistoryModel history = new HistoryModel();
        history.key = screen;
        history.params = params;
        if(screen.isHome()) this.clearAllHistory();
        if(screen.isHistory() == false) return;
        if(this.currentHistoryStack != null) this.historys.push(this.currentHistoryStack);
        this.currentHistoryStack = history;
    }

    public HistoryModel getHistory()
    {
        this.currentHistoryStack = null;
        if(this.historys.empty()) return null;
        return this.historys.pop();
    }

    public void clearAllHistory() {
        this.historys.clear();
    }

    static public class HistoryModel
    {
        public ScreenEnum key;
        public Object params;
    }
}


