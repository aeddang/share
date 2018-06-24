package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupConsumeBenefitModify extends ViewCore implements OnClickListener
{

    private TextView textTitle,textUsed,inputPointUnit,inputPointSubUnit,textUsedUnit,textUsedSub,textUsedSubUnit;
    private EditText inputPoint,inputPointSub;

    private ImageButton btnComplete;

    private LinearLayout listStack,textUsedSubBox,inputPointSubBox;

    private ArrayList<ListCheck> lists;


    private BenifitObject myObj;



    public PopupConsumeBenefitModify(Context context, PageObject pageInfo) {
        super(context, pageInfo);


        View.inflate(context, R.layout.popup_consume_benefit_modify, this);
        if(pageInfo.info!=null){
            myObj=(BenifitObject)pageInfo.info.get("myObj");

        }else{
            myObj=new BenifitObject();

        }

        textTitle = (TextView) findViewById(R.id._textTitle);
        textUsed = (TextView) findViewById(R.id._textUsed);
        inputPoint = (EditText) findViewById(R.id._inputPoint);
        inputPointSub = (EditText) findViewById(R.id._inputPointSub);

        btnComplete = (ImageButton) findViewById(R.id._btnComplete);


        listStack = (LinearLayout) findViewById(R.id._listStack);

        textUsedSubBox = (LinearLayout) findViewById(R.id._textUsedSubBox);
        inputPointSubBox = (LinearLayout) findViewById(R.id._inputPointSubBox);

        inputPointUnit= (TextView) findViewById(R.id._inputPointUnit);
        inputPointSubUnit= (TextView) findViewById(R.id._inputPointSubUnit);
        textUsedUnit= (TextView) findViewById(R.id._textUsedUnit);
        textUsedSub= (TextView) findViewById(R.id._textUsedSub);
        textUsedSubUnit= (TextView) findViewById(R.id._textUsedSubUnit);

        btnComplete.setOnClickListener(this);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textUsed.setTypeface(FontFactory.getInstence().FONT_ENG);



        setData(myObj);

        inputPoint.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPoint.setText("");
                }

            }
        });
        inputPointSub.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPointSub.setText("");
                }

            }
        });



    }
    private void setData(BenifitObject _myObj) {



        myObj=_myObj;
        textTitle.setText(myObj.title);
        textUsed.setText(String.valueOf((int)Math.round(myObj.usedPoint)));

        if(myObj.point_isyear==true){
            inputPoint.setText(String.valueOf((int)Math.round(myObj.adjustPoint*12)));
        }else{
            inputPoint.setText(String.valueOf((int)Math.round(myObj.adjustPoint)));

        }
        inputPointUnit.setText(myObj.consumePoint_unit);
        textUsedUnit.setText(myObj.usedPoint_unit);
        if(myObj.sub_isuse==true){
            inputPointSubBox.setVisibility(View.VISIBLE);
            textUsedSubBox.setVisibility(View.VISIBLE);

            inputPointSub.setText((int)Math.round(myObj.consumePointsub));
            textUsedSub.setText((int)Math.round(myObj.usedPointsub));

            inputPointSubUnit.setText(myObj.consumePointsub_unit);
            textUsedSubUnit.setText(myObj.usedPointsub_unit);

        }else{

            inputPointSubBox.setVisibility(View.GONE);
            textUsedSubBox.setVisibility(View.GONE);
        }

        lists=new ArrayList<ListCheck>();

        for(int i=0;i<myObj.lists.size();++i){
            ListCheck list=new ListCheck(MainActivity.getInstence());
            list.setData(myObj.lists.get(i).isSelected,myObj.lists.get(i).title);
            lists.add(list);
            listStack.addView(list);
        }



    }

    protected void doResize() {
        super.doResize();

    }

    public void onClick(View v) {

        if (v == btnComplete) {
            onComplete();

        }
    }




    protected void doMovedInit() {
        super.doMovedInit();



    }

    protected void doRemove() {

        super.doRemove();

    }


    /////////////////////////////////////////


    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();

        String key = inputPoint.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_consume_input_year, null);
            inputPoint.requestFocus();
            return;
        }
        params.put("cp", key);
        if(myObj.sub_isuse==true){

            key = inputPointSub.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_consume_input_year_sub, null);
                inputPointSub.requestFocus();
                return;
            }
            params.put("cpsub", key);



        }


        params.put("bene_idx", myObj.seq);

        String str="";
        for(int i=0;i<lists.size();++i){
            if(lists.get(i).getSelected()==true){

                if(str.equals("")==true){
                    str=myObj.lists.get(i).seq;

                }else{
                    str=str+"|"+myObj.lists.get(i).seq;

                }

            }

        }
        params.put("seldata", str);


        ConsumeInfo.getInstence().modifyBenefitDetailData(params);

    }
}
