package lib.view;










import java.util.ArrayList;



import lib.ViewUtil;


import android.content.Context;

import android.util.Log;
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
   
	
	//private Button exitBtn,startBtn;
	public PageControl(Context _context,int _dfID,int _ovID,int _range,PageControlDelegate del) {
		
		super(_context); 
		context=_context;
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
			
			delegate.selectPage(this,idx);
		}
	} 
	
	

	
	
	
	public interface PageControlDelegate
	{
		void selectPage(PageControl pageControl,int idx);
	 
	}


}
