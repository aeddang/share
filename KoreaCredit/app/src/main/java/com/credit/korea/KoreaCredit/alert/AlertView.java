package com.credit.korea.KoreaCredit.alert;


import android.content.Context;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;



public class AlertView extends FrameLayout implements View.OnClickListener,Animation.AnimationListener{

    public static final int SELECTED_OK=0;
    public static final int SELECTED_CANCLE=1;
    public static final int SELECTED_NONE=2;

    private TextView title,subTitle,textSelect;
    private Button btnOk,btnCancle;
    private ImageView icon,dimed;

    public AlertViewDelegate delegate;

    public interface AlertViewDelegate
    {
        void onSelected(AlertView v,int selectIdx);


    }


    public AlertView(Context context,AlertViewDelegate _delegate)
	{
        super(context);
        AlertObject info =new AlertObject();
        init(context,info,_delegate);
    }
    public AlertView(Context context,AlertObject info,AlertViewDelegate _delegate)
    {
        super(context);
        init(context,info,_delegate);



    }

    public void init(Context context, AlertObject info,AlertViewDelegate _delegate)
    {
        View.inflate(context, R.layout.alert_default,this);

        delegate=_delegate;

        title=(TextView) findViewById(R.id._title);
        subTitle=(TextView) findViewById(R.id._subTitle);
        textSelect=(TextView) findViewById(R.id._textSelect);
        icon=(ImageView) findViewById(R.id._icon);
        dimed=(ImageView) findViewById(R.id._dimed);


        btnOk=(Button) findViewById(R.id._btnOk);
        btnCancle=(Button) findViewById(R.id._btnCancle);

        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        dimed.setOnClickListener(this);
        btnOk.setTypeface(FontFactory.getInstence().FONT_KR);
        btnCancle.setTypeface(FontFactory.getInstence().FONT_KR);
        MainActivity.alerts.add(this);

        title.setTypeface(FontFactory.getInstence().FONT_KR);
        subTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textSelect.setTypeface(FontFactory.getInstence().FONT_KR);
        if(info.iconID==-1){
            icon.setVisibility(View.GONE);
        }else{
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(info.iconID);
        }

        if(info.okID==-1){
            btnOk.setVisibility(View.GONE);
        }else{
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setText(info.okID);
        }

        if(info.cancleID==-1){
            btnCancle.setVisibility(View.GONE);
        }else{
            btnCancle.setVisibility(View.VISIBLE);
            btnCancle.setText(info.cancleID);
        }

        if(info.titleID==-1 && info.titleStr.equals("")==true){
            title.setVisibility(View.GONE);
        }else{
            title.setVisibility(View.VISIBLE);
            if(info.titleStr.equals("")==true) {
                title.setText(info.titleID);
            }else{

                title.setText(info.titleStr);
            }
        }
        if(info.subTitleID==-1 && info.subTitleStr.equals("")==true){
            subTitle.setVisibility(View.GONE);
        }else{
            subTitle.setVisibility(View.VISIBLE);
            if(info.subTitleStr.equals("")==true) {
                subTitle.setText(info.subTitleID);
            }else{

                subTitle.setText(info.subTitleStr);
            }

        }
        if(info.selectID==-1 && info.selectStr.equals("")==true){
            textSelect.setVisibility(View.GONE);
        }else{
            textSelect.setVisibility(View.VISIBLE);
            if(info.selectStr.equals("")==true) {
                textSelect.setText(info.selectID);
            }else{

                textSelect.setText(info.selectStr);
            }
        }

        if(info.isDimed==true){
            dimed.setVisibility(View.VISIBLE);
        }else{
            dimed.setVisibility(View.GONE);
        }
        viewBox();
    }

    public void onClick(View v){

        int idx=0;
        if(v==btnOk){
            idx=AlertView.SELECTED_OK;
        }else if(v==btnCancle){
            idx=AlertView.SELECTED_CANCLE;

        }else if(v==dimed){
            idx=AlertView.SELECTED_NONE;

        }

        if(delegate!=null){

            delegate.onSelected(this,idx);
        }
        removeBox(true);

    }


    public void viewBox() {


        Animation ani = AnimationUtils.loadAnimation(MainActivity.getInstence(), R.anim.motion_in);
        this.startAnimation(ani);


    }
    public void removeBox() {

        removeBox(false);

    }
    private void removeBox(boolean isSelf) {

        Animation ani = AnimationUtils.loadAnimation(MainActivity.getInstence(), R.anim.motion_out);
        ani.setAnimationListener(this);
        this.startAnimation( ani );
        if(isSelf==false){

            if(delegate!=null){

                delegate.onSelected(this,AlertView.SELECTED_NONE);
            }
        }

    }
    public void  remove()
    {

        ViewUtil.remove(this);

    }


    public void onAnimationEnd(Animation arg) {
        Handler aniHandler=new  Handler();
        aniHandler.post(new Runnable() {
                public void run() {
                    remove();
                }
            });



    }


    @Override
    public void onAnimationRepeat(Animation arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onAnimationStart(Animation arg0) {
        // TODO Auto-generated method stub

    }


 /////////////////////////////////////////









}
