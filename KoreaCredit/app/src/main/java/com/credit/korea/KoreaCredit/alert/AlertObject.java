package com.credit.korea.KoreaCredit.alert;


import com.credit.korea.KoreaCredit.R;

public class AlertObject
{

    public String titleStr,subTitleStr,selectStr;
    public int titleID,subTitleID,selectID,iconID,okID,cancleID;
    public boolean isDimed;

    public AlertObject() {
        titleID = -1;
        subTitleID =-1;
        selectID=-1;

        selectStr = "";
        titleStr = "";
        subTitleStr="";

        iconID = -1;
        okID=R.string.btn_ok;
        cancleID=R.string.btn_no;
        isDimed=true;
    }







}






