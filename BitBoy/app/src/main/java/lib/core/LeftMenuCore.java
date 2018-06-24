package lib.core;

import android.content.Context;
import android.graphics.Point;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ironraft.bitboy.R;

import lib.ViewUtil;
import lib.observers.ObserverController;
import lib.view.Gesture;


public class LeftMenuCore extends FrameLayout implements Gesture.GestureDelegate,View.OnClickListener,Animation.AnimationListener
{
    static final String NotificationLeftMenuOn = "NotificationLeftMenuOn";
    static final String  NotificationLeftMenuOff = "NotificationLeftMenuOff";

    private Gesture gesture;
    public LeftMenuCoreDelegate coreDelegate;

    private float startPosition = 0;

    public FrameLayout body;
    private ImageView dimed;

    public boolean isActive=false;
    public boolean isInit=false;

    private String finalGesture = "";

    private Context context;
    private boolean trigger = false;
    private int menuSize = 0;
    public LeftMenuCore(Context _context,int wid) {
        super(_context);
        this.menuSize = wid;
        this.context=_context;
        this.gesture=new Gesture(this,false,true);

        this.dimed = new ImageView(this.context);
        this.dimed.setScaleType(ImageView.ScaleType.FIT_XY);
        this.dimed.setImageResource(R.drawable.transparent70);
        LayoutParams layoutDimed=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT , Gravity.LEFT|Gravity.TOP);
        layoutDimed.leftMargin = 0;
        layoutDimed.topMargin = 0;
        this.addView(this.dimed, layoutDimed);

        this.body = new FrameLayout(this.context);
        LayoutParams layoutBody=new LayoutParams(wid,LayoutParams.MATCH_PARENT , Gravity.LEFT|Gravity.TOP);
        layoutBody.leftMargin = -wid;
        this.addView(this.body, layoutBody);
        ViewUtil.setAlpha(this.dimed,0);
        this.setVisibility(View.GONE);

    }
    public void initAction()
    {
        this.isInit=true;
        coreDelegate.initMenu(this);

    }

    public void onClick(View arg) {
        this.passive();
    }


    public void active()
    {


        if(this.isActive==true){
            return;
        }
        this.isActive = true;
        this.setVisibility(View.VISIBLE);
        if(this.coreDelegate != null) {

            this.coreDelegate.activeMenu(this, this.menuSize);
        }
        ViewUtil.setAlpha(this.dimed,1);
        int tx=0;
        LayoutParams layout=( LayoutParams)this.body.getLayoutParams();
        int dfX=layout.leftMargin;
        layout.leftMargin=tx;
        this.body.setLayoutParams(layout);
        TranslateAnimation moveAni=new TranslateAnimation(dfX,0,0,0);
        moveAni.setDuration(200);
        moveAni.setAnimationListener(this);
        this.body.startAnimation(moveAni);
        ObserverController.shareInstence().notifyPost(LeftMenuCore.NotificationLeftMenuOn,null,null);


    }


    public void passive()
    {
        if(isActive == false){
            return;
        }
        isActive = false;

        if(this.coreDelegate != null) {
            this.coreDelegate.passiveMenu(this);
        }
        ViewUtil.setAlpha(this.dimed,0);
        int tx= -this.body.getWidth();
        LayoutParams layout=( LayoutParams)this.body.getLayoutParams();
        int dfX=layout.leftMargin;
        layout.leftMargin=tx;
        this.body.setLayoutParams(layout);
        TranslateAnimation moveAni=new TranslateAnimation(dfX-tx,tx,0,0);
        moveAni.setDuration(200);
        moveAni.setAnimationListener(this);
        this.body.startAnimation(moveAni);
        ObserverController.shareInstence().notifyPost(LeftMenuCore.NotificationLeftMenuOff,null, null);


    }

    public void onAnimationEnd(Animation arg0) {

        if(this.isActive ==true){


        }else{
            this.setVisibility(View.GONE);

        }

    }
    public void stateChange(Gesture g,String e){


        Point d=g.changePosA.get(0);
        if(e == Gesture.START){
            this.touchStart();
        }else if(e == Gesture.MOVE_H){
            this.touchMove(d.x);

        }else if(e == Gesture.END || e == Gesture.CANCLE){
            this.touchEnd();
        }
    }
    private void touchStart()
    {
        this.finalGesture = Gesture.NONE;
        this.startPosition = this.body.getX();
    }

    private void touchMove(int point)
    {
        float p = point + this.startPosition;

        if(p > 0)
        {
            p = 0;
        }
        else if(p < - this.body.getWidth())
        {
            p = -this.body.getWidth();
        }
        float pct = (this.body.getWidth()+p)/this.body.getWidth();
        ViewUtil.setAlpha(this.dimed,pct);
        ViewUtil.setFrame(this.body,(int)Math.floor(p),-1,-1,-1);
        if(this.coreDelegate != null) {
            this.coreDelegate.moveMenu(this,p+this.body.getWidth());
        }

    }

    private void touchEnd()
    {
        if(this.finalGesture == Gesture.PAN_RIGHT)
        {
            this.active();
        }else if( this.finalGesture == Gesture.PAN_LEFT){
            this.passive();
        }else if( this.finalGesture == Gesture.TOUCH){

            int bodyPos = (int)Math.floor(this.body.getX()) + this.body.getWidth();

            if(this.gesture.startPosA.get(0).x > bodyPos){
                this.passive();
            }


        }else{

            if(this.isActive){

                this.isActive = false;
                this.active();
            }else{
                this.isActive = true;
                this.passive();
            }

        }

    }

    public void gestureComplete(Gesture g,String e)
    {
        if(e == Gesture.PAN_RIGHT || e == Gesture.PAN_LEFT || e == Gesture.TOUCH){
            this.finalGesture=e;

        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {

        if(trigger == false)
        {
            return true;
        }else
        {
            trigger=this.gesture.adjustEvent(event);
            if(trigger == false)
            {
                return true;
            }else
            {
                return false;
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        trigger=this.gesture.adjustEvent(event);
        return trigger;
    }


    public void rotateChange(Gesture g,float rotate){}
    public void pinchChange(Gesture g,float dist){}
    public void gestureChange(Gesture g,String e){}




    public void onAnimationRepeat(Animation arg0) {
        // TODO Auto-generated method stub

    }

    public void onAnimationStart(Animation arg0) {
        // TODO Auto-generated method stub

    }
    public interface LeftMenuCoreDelegate
    {

        void moveMenu(LeftMenuCore view,float p);
        void activeMenu(LeftMenuCore view,float p);
        void passiveMenu(LeftMenuCore view);
        void initMenu(LeftMenuCore view);
    }
} 
