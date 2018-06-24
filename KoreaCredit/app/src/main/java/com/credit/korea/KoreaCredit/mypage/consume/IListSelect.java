package com.credit.korea.KoreaCredit.mypage.consume;


public interface IListSelect {



    void setOnSelectListener(ISelectListener _delegate);
    void setData(boolean isSelected, String text);
    void setSelect(boolean isSelected);
    boolean getSelected();






}
