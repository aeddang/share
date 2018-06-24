package com.credit.korea.KoreaCredit.member;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;


public class ListInterest extends FrameLayout implements View.OnClickListener{

    private TextView text;
    private Button check;
    public String key;
    public ListInterest(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_interest,this);
        text=(TextView) findViewById(R.id._text);
        check=(Button) findViewById(R.id._check);

        check.setSelected(false);

       text.setTypeface(FontFactory.getInstence().FONT_KR);



    }

    public void setData(String str,String _key)
    {

        text.setText(str);
        key=_key;
        if(key.equals("")==false) {

            check.setOnClickListener(this);
        }
    }

    public void onClick(View v){

        if(check.isSelected()==true){
            check.setSelected(false);
        }else{
            check.setSelected(true);
        }

    }


    public void setSelected(boolean selected)
    {
        check.setSelected(selected);

    }
    public boolean getSelected()
    {
        return check.isSelected();

    }





 /////////////////////////////////////////









}
