package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;


public class ListBtn extends FrameLayout implements View.OnClickListener,IListSelect{


    private Button btn;
    public ISelectListener delegate;
    public ListBtn(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_button,this);
        btn=(Button) findViewById(R.id._btn);


        btn.setTypeface(FontFactory.getInstence().FONT_KR);
        btn.setOnClickListener(this);


    }
    public void setOnSelectListener(ISelectListener _delegate){

        delegate=_delegate;
    }
    public void onClick(View v) {
        if(btn.isSelected()==true){

            setPs();
        }else{
            setAc();
        }
        if(delegate!=null){
            delegate.onSelectedChange(this,btn.isSelected());
        }
    }
    private void setAc()
    {
        btn.setSelected(true);
        btn.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setPs()
    {
        btn.setSelected(false);
        btn.setTextColor(Color.parseColor("#666666"));
    }
    public void setData(boolean isSelected, String text)
    {
        btn.setText(text);
        setSelect(isSelected);

    }
    public void setSelect(boolean isSelected)
    {

        if(isSelected==true){
            setAc();
        }else{
            setPs();

        }
    }

    public boolean getSelected()
    {
        return btn.isSelected();
    }





 /////////////////////////////////////////









}
