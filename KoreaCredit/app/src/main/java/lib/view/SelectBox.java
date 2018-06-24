package lib.view;










import java.util.ArrayList;



import lib.ViewUtil;


import android.content.Context;

import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;



public class SelectBox extends LinearLayout implements OnClickListener,AnimationListener{
	
	private ArrayList<Button> btnA;
	public SelectBoxDelegate delegate;
	private Button cancleBtn;
	private Animation removeAni;
	
	
	
	
	public SelectBox(Context context,String[] dataA, LayoutParams layout,String cancleTitle,
			         int bgID,int btnID,int cancleBgID,int btnColor,int cancleColor,float textSize) {
		 super(context);
		 setBox(context,dataA, layout,cancleTitle,
		         bgID,btnID,cancleBgID,btnColor,cancleColor,textSize,
		         null,null);
		
	}
	public SelectBox(Context context,String[] dataA, LayoutParams layout,String cancleTitle,
			         int bgID,int btnID,int cancleBgID,int btnColor,int cancleColor,float textSize,
			         Animation initAni,Animation _removeAni) {
		
		 super(context);
		 setBox(context,dataA, layout,cancleTitle,
		         bgID,btnID,cancleBgID,btnColor,cancleColor,textSize,
		         initAni,_removeAni);
		   
	}
	
	private void setBox(Context context,String[] dataA, LayoutParams layout,String cancleTitle,
	                    int bgID,int btnID,int cancleBgID,int btnColor,int cancleColor,float textSize,
	                    Animation initAni,Animation _removeAni) {
		 this.setOrientation(LinearLayout.VERTICAL);
		 removeAni=_removeAni;
		 if(removeAni!=null){
			 removeAni.setAnimationListener(this);
		 }
		 
		 btnA=new  ArrayList<Button>();
		 if(bgID!=-1){
		    this.setBackgroundResource(bgID);
		 }
		 for(int i=0;i< dataA.length;++i){
			 Button btn=new  Button(context);
			 btn.setTextColor(btnColor);
			 btn.setTextSize(textSize);
			 btn.setText(dataA[i]);
			 if(btnID!=-1){
			    btn.setBackgroundResource(btnID);
			 }
			 if(i==0){
				 LayoutParams firstlayout=new  LayoutParams(layout.width,layout.height);
				 firstlayout.setMargins(layout.leftMargin, layout.topMargin+40, layout.rightMargin, layout.bottomMargin);
				 this.addView(btn,firstlayout);
			 }else{
				 this.addView(btn,layout);
		     }
			 btn.setOnClickListener(this);
			 btnA.add(btn);
		 }
		 
		 if(!cancleTitle.equals("")){
			 if(cancleBgID==-1){
				 cancleBgID=btnID;
			 }
			 
			 cancleBtn=new Button(context);
			 cancleBtn.setText(cancleTitle);
			 cancleBtn.setTextColor(cancleColor);
			 cancleBtn.setTextSize(textSize);
			 
			 if(cancleBgID!=-1){
				cancleBtn.setBackgroundResource(cancleBgID);
			 }
			 this.addView(cancleBtn,layout);
			 cancleBtn.setOnClickListener(this);
		}
		 
		if(initAni!=null){
			 this.startAnimation(initAni);
		}
	}
	public void closeBox()
	{
		if(removeAni==null){
		   removeBox();
		}else{
		   this.startAnimation(removeAni);
		}
	   
	}
	private void removeBox() {
		ViewUtil.remove(this); 
	    delegate=null;
	}
	public void onAnimationEnd(Animation arg0) {
		   if(arg0==removeAni){
			   new Handler().post(new Runnable() {
			        public void run() {
			        	removeBox();
			        }
			    });
		   }
  
	 }
	 public void onAnimationRepeat(Animation arg0) {
	 }
	 public void onAnimationStart(Animation arg0) {
			
	}
	public void onClick(View v) {
		int idx=btnA.indexOf(v);
		if(idx!=-1){
			if(delegate!=null){
				delegate.selectButton(this, idx);
			}
		}else if(v==cancleBtn){
			closeBox();
		}
		
	}
	
	public interface SelectBoxDelegate
	{
		void selectButton(SelectBox selectBox, int idx);
		
	 
	}
}
