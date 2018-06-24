package lib.view;

import java.util.ArrayList;
import java.util.Date;


import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class Gesture {

    public static final String START="START";
    public static final String MOVE="MOVE";
    public static final String MOVE_V="MOVE_V";
    public static final String MOVE_H="MOVE_H";
    public static final String END="END";
    public static final String CANCLE="CANCLE";

    public static final String LONG_TOUCH="LONG_TOUCH";
    public static final String TOUCH="TOUCH";

    public static final String PAN="PAN";
    public static final String PAN_RIGHT="PAN_RIGHT";
    public static final String PAN_LEFT="PAN_LEFT";
    public static final String PAN_UP="PAN_UP";
    public static final String PAN_DOWN="PAN_DOWN";



    public static final String PINCH_MOVE="PINCH_MOVE";
    public static final String PINCH_RIGHT="PINCH_RIGHT";
    public static final String PINCH_LEFT="PINCH_LEFT";
    public static final String PINCH_UP="PINCH_UP";
    public static final String PINCH_DOWN="PINCH_DOWN";

    //public static final String PINCH_GESTURE="PINCH_GESTURE";
    public static final String PINCH_IN="PINCH_IN";
    public static final String PINCH_OUT="PINCH_OUT";
    public static final String PINCH_ROTATE="PINCH_ROTATE";

    public GestureDelegate delegate;



    public interface GestureDelegate
    {
        void stateChange(Gesture g,String e);
        void rotateChange(Gesture g,float rotate);
        void pinchChange(Gesture g,float dist);
        void gestureChange(Gesture g,String e);
        void gestureComplete(Gesture g,String e);
    }

    private boolean isHorizontal;
    private boolean isVertical;



    public ArrayList<Point> startPosA;
    public ArrayList<Point> changePosA;
    public ArrayList<Point> movePosA;

    private int moveType=-1;
    private boolean isEventStart;
    private long startTime;
    private long endTime;

    public float startRotate,startDistance;
    private final float changeRotate=30.f;
    private final long  longTime=2;

    public final int moveDelay=5;
    private final int changeMin=10;
    private final int changeMax=50;


    public Gesture(GestureDelegate _delegate,boolean _isVertical,boolean _isHorizontal){


        delegate=_delegate;
        isVertical=_isVertical;
        isHorizontal=_isHorizontal;

        moveType=-1;

        startPosA=new ArrayList<Point>();
        changePosA=new ArrayList<Point>();
        movePosA=new ArrayList<Point>();
        startTime=0;
        startRotate=0;
        startDistance=0;
        endTime=0;




    }
    public boolean adjustEvent(MotionEvent event){

        int action = event.getAction();

        ArrayList<Point> locations=new ArrayList<Point>();
        Point location;
        //Log.i("t","c :"+event.getPointerCount());


        // ... Many touch events later...

        // Use the pointer ID to find the index of the active pointer
        // and fetch its position
        int pointerIndex;
        int mActivePointerId;


        for(int i=0;i<event.getPointerCount();++i){
            mActivePointerId = event.getPointerId(i);
            pointerIndex = event.findPointerIndex(mActivePointerId);
            location=new Point((int)Math.floor(event.getX(pointerIndex)),(int)Math.floor(event.getY(pointerIndex)));
            locations.add(location);

        }
        boolean trigger=true;
        switch (action)
        {
            case MotionEvent.ACTION_DOWN :

                startEvent(locations);
                break;
            case MotionEvent.ACTION_MOVE :
                trigger=moveEvent(locations);
                break;
            case MotionEvent.ACTION_UP :
            case MotionEvent.ACTION_CANCEL :
                endEvent(true);
                break;

        }
        return trigger;

    }
    private synchronized void startEvent(ArrayList<Point> locations){
        isEventStart=true;
        moveType=-1;
        startPosA=locations;
        changePosA=new ArrayList<Point>();


        for(int i=0;i<locations.size();++i){
            changePosA.add(new Point(0,0));
        }
        //Log.i("t","startEvent "+moveType);
        Date now=new Date();
        startTime=now.getTime();
        startDistance=0;
        startRotate=0;
        if(delegate!=null){
            delegate.stateChange(this,START);

        }

    }
    private synchronized boolean moveEvent(ArrayList<Point> locations){


        boolean trigger=true;
        if(isEventStart==false){
            startEvent(locations);
            return trigger;
        }
        movePosA=new ArrayList<Point>();
        int len=locations.size();
        Point location;
        for(int i=0;i<len;++i){
            location = locations.get(i);
            movePosA.add(new Point(location.x,location.y));


        }
        Point start;
        Point change;



        checkEvent(false);
        Log.i("t","len "+len+" - "+startPosA.size());
        if(len == startPosA.size()){


            for(int i=0;i<len;++i){
                location = locations.get(i);
                movePosA.add(new Point(location.x,location.y));
                start = startPosA.get(i);
                change = changePosA.get(i);
                change.x=location.x-start.x;
                change.y=location.y-start.y;

            }
            change = changePosA.get(0);
            if((Math.abs(change.x)+Math.abs(change.y))>moveDelay){
                if(Math.abs(change.x)>Math.abs(change.y)){
                    if(isHorizontal==true){
                        trigger=false;
                    }
                    moveType=1;

                    if(isHorizontal==true  && len==1){
                        if(delegate!=null){
                            delegate.stateChange(this,MOVE_H);

                        }
                    }



                }else if(Math.abs(change.y)>Math.abs(change.x)){
                    if(isVertical==true){
                        trigger=false;
                    }
                    moveType=0;


                    if(isVertical==true && len==1){
                        if(delegate!=null){
                            delegate.stateChange(this,MOVE_V);
                        }
                    }


                }


                if(delegate!=null){
                    delegate.stateChange(this,MOVE);
                }

            }

        }else{

            if(delegate!=null){
                delegate.stateChange(this,CANCLE);
            }
            endEvent(false);


        }


        return trigger;
    }

    private  synchronized void endEvent(boolean isComplete){
        if(isEventStart==false){
            return;
        }

        Date now=new Date();
        endTime=now.getTime();
        checkEvent(isComplete);
        if(delegate!=null){
            delegate.stateChange(this,END);
        }
        isEventStart=false;

    }
    private   synchronized  void  checkEvent(boolean isComplete)
    {


        if(startPosA.size()!=movePosA.size() && isComplete==false){
            return;
        }


        float spdMD=100.f;
        float moveMD=0.f;

        Point start;
        Point move;
        Point change;

        float gestureTime=0;
        if(isComplete==true){
            gestureTime=(endTime-startTime)/spdMD;
            //Log.i("t","gestureTime : "+gestureTime);
        }

        if(startPosA.size()==1){
            change=changePosA.get(0);
            if(isComplete==true){

                if(gestureTime>=longTime){
                    //Log.i("t","gestureTime>=longTime");
                    //Log.i("t","change.x"+change.toString());
                    if(Math.abs(change.x)< changeMin && Math.abs(change.y)< changeMin)
                    {
                        if(delegate!=null){
                            delegate.gestureComplete(this,LONG_TOUCH);
                        }
                    }
                }



                if(moveType==1){


                    moveMD= change.x/gestureTime;

                    if(moveMD>changeMax){
                        if(delegate!=null){
                            delegate.gestureComplete(this,PAN_RIGHT);
                        }

                    }else if(moveMD<-changeMax){
                        if(delegate!=null){
                            delegate.gestureComplete(this,PAN_LEFT);
                        }

                    }
                }

                if(moveType==0){
                    moveMD=change.y/gestureTime;

                    if(moveMD>changeMax){
                        if(delegate!=null){
                            delegate.gestureComplete(this,PAN_DOWN);
                        }

                    }else if(moveMD<-changeMax){
                        if(delegate!=null){
                            delegate.gestureComplete(this,PAN_UP);
                        }

                    }
                }
                if(Math.abs(change.x)< changeMin && Math.abs(change.y)< changeMin)
                {
                    //start=startPosA.get(0);
                    if(delegate!=null){
                        delegate.gestureComplete(this,TOUCH);
                    }
                }


            }else{

                if(Math.abs(change.x)>changeMin || Math.abs(change.y)>changeMin){
                    if(delegate!=null){
                        delegate.stateChange(this,PAN);

                    }
                }
            }
        }else if(startPosA.size()==2){


            Point start2;
            Point move2;
            try{
                change=changePosA.get(0);
                start=startPosA.get(0);
                move=movePosA.get(0);

                start2=startPosA.get(1);
                move2=movePosA.get(1);
            }catch(IndexOutOfBoundsException e){
                return;
            }
            if(startDistance==0){
                startDistance=(float) Math.sqrt((Math.abs(start.x-start2.x)^2) + (Math.abs(start.y-start2.y)^2));
            }
            float startDist=startDistance;
            float moveDist=(float) Math.sqrt((Math.abs(move.x-move2.x)^2) + (Math.abs(move.y-move2.y)^2));
            float dist = moveDist-startDist;

            float rotate = 0;


            float w=0;
            float h=0;
            if(startRotate==0){
                w=start.x-start2.x;
                h=start.y-start2.y;
                startRotate=(float) (Math.atan2(h,w)/Math.PI*360);


            }
            w=move.x-move2.x;
            h=move.y-move2.y;
            rotate=(float) (Math.atan2(h,w)/Math.PI*360);
            // Log.i("t","startRotate :"+startRotate+" rotate: "+rotate);

            if(delegate!=null){
                delegate.rotateChange(this,rotate);
            }

            if(isComplete==true && Math.abs(startRotate-rotate)>changeRotate){
                if(delegate!=null){
                    delegate.gestureComplete(this,PINCH_ROTATE);
                }
            }



            if(isComplete==true){


                if(Math.abs(dist)>changeMin)
                {
                    if(dist>0){
                        if(delegate!=null){
                            delegate.gestureComplete(this,PINCH_OUT);
                        }

                    }else{
                        if(delegate!=null){
                            delegate.gestureComplete(this,PINCH_IN);
                        }

                    }

                }else
                {

                    if(moveType==1){
                        moveMD=change.x/gestureTime;
                        if(moveMD>changeMax){
                            if(delegate!=null){
                                delegate.gestureComplete(this,PINCH_RIGHT);
                            }

                        }else if(moveMD<-changeMax){
                            if(delegate!=null){
                                delegate.gestureComplete(this,PINCH_LEFT);
                            }

                        }
                    }
                    if(this.moveType==0){
                        moveMD=change.y/gestureTime;
                        if(moveMD>changeMax){
                            if(delegate!=null){
                                delegate.gestureComplete(this,PINCH_DOWN);
                            }

                        }else if(moveMD<-changeMax){
                            if(delegate!=null){
                                delegate.gestureComplete(this,PINCH_UP);
                            }

                        }
                    }
                }



            }else{
                if(Math.abs(dist)>changeMin)
                {
                    if(delegate!=null){
                        delegate.pinchChange(this,dist);
                    }

                }else
                {
                    if(delegate!=null){
                        delegate.stateChange(this,PINCH_MOVE);
                    }

                }
            }

        }


    }
}







