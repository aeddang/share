package com.credit.korea.KoreaCredit.card;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;

import lib.CommonUtil;
import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupAddCard extends ViewCore implements OnClickListener{


	private FrameLayout body;
    private EditText inputYY,inputMM,inputPhone0,inputPhone1,inputNum;
    private TextView textTitle,textCardTitle,textCardName;
    private ImageButton btnComplete,btnCancel;

    private CardObject cardObj;





	public PopupAddCard(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
		String title="";
		if(pageInfo.info!=null){
            cardObj=(CardObject)pageInfo.info.get("cardObj");
            title=(String)pageInfo.info.get("title");

        }else{
            cardObj=new CardObject();
			
		}

        Log.i("","PopupAddCard ");
		View.inflate(context, R.layout.popup_card_add, this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textCardTitle=(TextView) findViewById(R.id._textCardTitle);
        textCardName=(TextView) findViewById(R.id._textCardName);

        inputYY=(EditText) findViewById(R.id._inputYY);
        inputMM=(EditText) findViewById(R.id._inputMM);
        inputPhone0=(EditText) findViewById(R.id._inputPhone0);
        inputPhone1=(EditText) findViewById(R.id._inputPhone1);
        inputNum=(EditText) findViewById(R.id._inputNum);

        btnComplete=(ImageButton) findViewById(R.id._btnComplete);
        btnCancel=(ImageButton) findViewById(R.id._btnCancel);


        textTitle.setTypeface(FontFactory.getInstence().FONT_KR_B);
        textCardTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textCardName.setTypeface(FontFactory.getInstence().FONT_KR);

        textTitle.setText(title);
        textCardTitle.setText(cardObj.title);
        textCardName.setText(cardObj.name);


        String[] pA=cardObj.smsNumber.split("-");

        if(pA.length>0) {
            inputPhone0.setText(pA[0]);
        }
        if(pA.length>1) {
            inputPhone1.setText(pA[1]);
        }

        btnComplete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }
	
    
	protected void doMovedInit() { 
	    super.doMovedInit();



	}

    protected void doRemove() { 
    	
    	super.doRemove();


    }

    public void onClick(View v) {
		
			 
		if(v==btnComplete){
            String key = inputMM.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_input_mm, null);
                inputMM.requestFocus();
                return;
            }else if(key.length()!=2){

                mainActivity.viewAlert("", R.string.msg_card_input_mm_size, null);
                inputMM.requestFocus();
                return;
            }
            int mm=Integer.valueOf(key);
            if(mm < 1 && mm > 12){

                mainActivity.viewAlert("", R.string.msg_card_input_mm_size, null);
                inputMM.requestFocus();
                return;
            }

            cardObj.mm=key;
            key = inputYY.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_input_yy, null);
                inputYY.requestFocus();
                return;
            }else if(key.length()!=2){

                mainActivity.viewAlert("", R.string.msg_card_input_yy_size, null);
                inputYY.requestFocus();
                return;
            }
            cardObj.yy=key;

            String p0 = inputPhone0.getText().toString();
            if (p0.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_input_sms, null);
                inputPhone0.requestFocus();
                return;
            }else if(p0.length()!=4){

                mainActivity.viewAlert("", R.string.msg_card_input_sms_size, null);
                inputPhone0.requestFocus();
                return;
            }
            String p1 = inputPhone1.getText().toString();
            if (p1.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_input_sms, null);
                inputPhone1.requestFocus();
                return;
            }else if(p1.length()!=4){

                mainActivity.viewAlert("", R.string.msg_card_input_sms_size, null);
                inputPhone1.requestFocus();
                return;
            }
            cardObj.sms=p0+"-"+p1;

            key = inputNum.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_input_cnum, null);
                inputNum.requestFocus();
                return;
            }else if(key.length()!=2){

                mainActivity.viewAlert("", R.string.msg_card_input_cnum_size, null);
                inputNum.requestFocus();
                return;
            }
            cardObj.extnum=key;


            MyCardInfo.getInstence().requestCard(cardObj,MyCardInfo.REQUEST_TYPE_MYCARD);


		}else{


        }
        MainActivity.getInstence().hideKeyBoard();
        MainActivity.getInstence().removePopup(this);
		
	} 



	  
	 

}
