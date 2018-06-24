package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;


public class ListCheck extends FrameLayout implements View.OnClickListener,IListSelect{

    private TextView textTitle;
    private Button check;
    public ISelectListener delegate;
    public ListCheck(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_check,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        check=(Button) findViewById(R.id._check);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        check.setOnClickListener(this);


    }
    public void setOnSelectListener(ISelectListener _delegate){

        delegate=_delegate;
    }
    public void onClick(View v) {
        if(check.isSelected()==true){

            check.setSelected(false);
        }else{
            check.setSelected(true);
        }
        if(delegate!=null){
            delegate.onSelectedChange(this,check.isSelected());
        }
    }
    public void setData(boolean isSelected, String text)
    {
        textTitle.setText(text);
        check.setSelected(isSelected);
    }
    public void setSelect(boolean isSelected)
    {

        check.setSelected(isSelected);
    }

    public boolean getSelected()
    {
        return check.isSelected();
    }





 /////////////////////////////////////////









}
