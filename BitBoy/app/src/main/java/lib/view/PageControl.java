package lib.view;


import java.util.ArrayList;
import lib.ViewUtil;


import android.content.Context;

import android.util.AttributeSet;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;



public class PageControl extends FrameLayout implements OnClickListener {
	
	
	
	public PageControlDelegate delegate;
	
	private ArrayList<ImageView> pointA;
  
    private int totalNum,currentIdx,range,dfID,ovID;
    private Context context;


	public PageControl(Context _context) {

		super(_context);
		context=_context;

	}
	public PageControl(Context _context,AttributeSet attrs) {

		super(_context,attrs,0);
		context=_context;

	}
	public PageControl(Context _context, AttributeSet attrs, int defStyle)
	{
		super(_context, attrs, defStyle);
		context=_context;

	}

	public void initControl(int _dfID,int _ovID,int _range,PageControlDelegate del)
	{
		delegate=del;
		range=_range;
		dfID=_dfID;
		ovID=_ovID;
		
	}
	
	public void setPageControl(int idx,int num)
	{
		
	    totalNum=num;
	    currentIdx=-1;
	    this.removeAllViews();
	    pointA=new ArrayList<ImageView>();
	    for(int i=0;i<totalNum;++i)
	    {
	    	ImageView img=new ImageView(context);
	    	img.setImageResource(dfID);
	    	img.setOnClickListener(this);
	    	pointA.add(img);
	    	this.addView(img);
	    	
	    }
	    changeSize();
	    changePage(idx);
	}
	public void changeSize()
	{
		int size=this.getHeight();
		int tx=(int)Math.floor((this.getWidth()-(size*totalNum)-(range*totalNum))/2);
		for(int i=0;i<totalNum;++i)
	    {
	    	ImageView img=pointA.get(i);
	    	LayoutParams layout =new LayoutParams(size,size);
	    	layout.gravity=Gravity.LEFT;
	    	layout.leftMargin=tx;
	    
	    	tx=tx+size+range;
	    	img.setLayoutParams(layout);
	    }    
		
	}
	public void removeControl()
	{
	    this.removeAllViews();
	    delegate=null;
	    ViewUtil.remove(this);
	}
	public void changePage(int idx)
	{
		 if(idx < 0 || idx >= totalNum)
		 {
			 return;
		 }
		 if(currentIdx!=-1){
			 ImageView img=pointA.get(currentIdx);
			 img.setImageResource(dfID);
		 }
		 currentIdx=idx;
		 ImageView img=pointA.get(currentIdx);
		 img.setImageResource(ovID);
	}
	

	public void onClick(View arg0) {
		
		int idx=pointA.indexOf(arg0);
		if(idx!=-1){
			
			if(delegate!=null) {
				delegate.selectPage(this, idx);
			}
		}
	} 
	
	

	
	
	
	public interface PageControlDelegate
	{
		void selectPage(PageControl pageControl,int idx);
	 
	}


}
