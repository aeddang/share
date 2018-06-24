package lib.core;


import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import lib.ViewUtil;
import lib.view.Gesture;


public class PopupCore extends ViewCore implements  Gesture.GestureDelegate
{


    static int POPUP_TYPE_BOTTOM = 0;
    static int POPUP_TYPE_TOP = 1;
    static int POPUP_TYPE_CENTER = 2;

    public FrameLayout body;
    private Gesture gesture;

    public PopupCoreDelegate popupDelegate;

    private float startPosition = 0;
    private String finalGesture = Gesture.NONE;
    private int popupType = POPUP_TYPE_BOTTOM;
    private boolean trigger;

	public PopupCore(Context context, PageObject _pageInfo)
    {
        super(context,_pageInfo);
        this.gesture=new Gesture(this,true,false);

        if(this.pageInfo != null)
        {
            switch(this.pageInfo.dr){

                case -1:
                    popupType = POPUP_TYPE_TOP;
                    break;
                case 1:
                    popupType = POPUP_TYPE_BOTTOM;
                    break;
                default:
                    popupType = POPUP_TYPE_CENTER;
                    break;
            }
        }


    }
    protected void doMovedInit() {
        this.setBodySize();
        super.doMovedInit();
    }
    protected void doRemove()
    {
        if(popupDelegate != null){
            popupDelegate.removePopup(this);
            popupDelegate = null;
        }
        this.gesture.delegate = null;
        this.gesture = null;
        super.doRemove();


    }

    public void setBodySizeChanged(int proposedWidth,int proposedheight)
    {
        ViewUtil.setFrame(this.body,-1,-1,proposedWidth,proposedheight);
        super.setBodySizeChanged(proposedWidth,proposedheight);

    }
    public void setBodySize()
    {
        ViewUtil.setFrame(this.body,-1,-1,this.getWidth(),this.getHeight());
    }
    private void onGestureClose()
    {
        this.pageInfo.dr = 0;
        this.removePosition();
        if(ActivityCore.getInstence() != null)
        {
            ActivityCore.getInstence().removePopup(this);
        }
    }


    public void stateChange(Gesture g,String e){

        Point d=g.changePosA.get(0);

        if(e == Gesture.START){
            this.touchStart();
        }else if(e == Gesture.MOVE_V){
            this.touchMove(d.y);

        }else if(e == Gesture.END || e == Gesture.CANCLE){
            this.touchEnd();
        }


    }

    private void touchStart()
    {
        this.finalGesture = Gesture.NONE;
        this.startPosition = this.body.getY();
    }

    private void touchMove(int point)
    {



        float p = point + this.startPosition;
        float pct = 1;

        if(this.popupType == POPUP_TYPE_TOP)
        {
            if(p > 0){
                p = 0;
            }else if(p < -this.getHeight())
            {
                p = -this.getHeight();
            }
            pct = (this.getHeight()+p)/this.getHeight();

        }else{
            float topPos = 0;

            if(p > this.getHeight())
            {
                p = this.getHeight();
            }else if(p < topPos)
            {
                p = topPos;
            }

            pct = (this.getHeight() + (topPos - p))/this.getHeight();
        }


        ViewUtil.setFrame(this.body, -1,(int)Math.floor(p),-1,-1);
        ViewUtil.setAlpha(this,pct);

    }

    private void touchEnd()
    {
        if(this.popupType == POPUP_TYPE_TOP)
        {
            if( finalGesture == Gesture.PAN_UP)
            {
                this.onGestureClose();
            }else{
                this.returnPosition();
            }
        }
        else
        {
            if( finalGesture == Gesture.PAN_DOWN)
            {
                this.onGestureClose();
            }else{
                this.returnPosition();
            }


        }

    }

    public void gestureComplete(Gesture g,String e)
    {
        if(e == Gesture.PAN_UP || e == Gesture.PAN_DOWN)
        {
            this.finalGesture=e;
        }
    }

    /*
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
        if(this.gesture==null)
        {

            return super.onTouchEvent(event);
        }
        if(this.pageInfo.isGestureUsed==true)
        {
            trigger=this.gesture.adjustEvent(event);
            return trigger;
        }
        else
        {
            return super.onTouchEvent(event);
        }

    }
    */
    public boolean onTouchEvent(MotionEvent event)
    {
        if(this.gesture==null)
        {
            return super.onTouchEvent(event);
        }
        if(this.pageInfo.isGestureUsed==true)
        {
            trigger=this.gesture.adjustEvent(event);
            return trigger;
        }
        else
        {
            return super.onTouchEvent(event);
        }

    }
    public void rotateChange(Gesture g,float rotate){}
    public void pinchChange(Gesture g,float dist){}
    public void gestureChange(Gesture g,String e){}
    private void removePosition()
    {
        Log.i("move","removePosition");
        LayoutParams layout=( LayoutParams)this.body.getLayoutParams();
        int ty = this.getHeight();
        int dfY=(int)Math.floor(this.body.getY());
        if(this.popupType == POPUP_TYPE_TOP){
            ty = -ty;
        }
        layout.topMargin=ty;
        this.body.setLayoutParams(layout);
        TranslateAnimation moveAni=new TranslateAnimation(0,0,dfY-ty,ty);
        moveAni.setDuration(200);
        //moveAni.setAnimationListener(this);
        this.body.startAnimation(moveAni);

    }
    private void returnPosition()
    {
        ViewUtil.setAlpha(this,1);
        Log.i("move","returnPosition");

        LayoutParams layout=( LayoutParams)this.body.getLayoutParams();
        int dfY=(int)Math.floor(this.body.getY());
        layout.topMargin=0;
        this.body.setLayoutParams(layout);
        TranslateAnimation moveAni=new TranslateAnimation(0,0,dfY,0);
        moveAni.setDuration(200);
        //moveAni.setAnimationListener(this);
        this.body.startAnimation(moveAni);

    }

    public void onAnimationRepeat(Animation arg0) {
        // TODO Auto-generated method stub

    }

    public void onAnimationStart(Animation arg0) {
        // TODO Auto-generated method stub

    }


    public interface PopupCoreDelegate
    {
        void removePopup(PopupCore view);

    }
} 
