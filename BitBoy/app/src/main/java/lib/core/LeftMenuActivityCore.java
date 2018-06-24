package lib.core;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import lib.ViewUtil;


public class LeftMenuActivityCore extends ActivityCore implements LeftMenuCore.LeftMenuCoreDelegate

{
	protected int leftMenuSize = 205;
	public LeftMenuCore leftMenuCore;
	private Boolean initSet = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if(isInit==true){
			return;
		}
		this.leftMenuCore=new LeftMenuCore(this.getApplicationContext(),(int)this.dpi * leftMenuSize);
		this.leftMenuCore.coreDelegate = this;
		this.body.addView(this.leftMenuCore, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		this.leftMenuCore.initAction();


	}
	public void setInit()
	{
		if(initSet == false) {
			initSet = true;
			ViewUtil.setFrame(this.viewer, -1,-1, this.body.getWidth(), this.body.getHeight());
		}

	}


	public void resizeScreen(int proposedWidth,int proposedheight)
	{
		ViewUtil.setFrame(this.viewer, -1,-1, proposedWidth, proposedheight);
		super.resizeScreen(proposedWidth,proposedheight);

	}

	public void toggleLeftMenu()
	{
		setInit();
		if(leftMenuCore.isActive)
		{
			leftMenuCore.passive();

		}else{
			leftMenuCore.active();
		}
	}
	public void openLeftMenu()
	{
		setInit();
		leftMenuCore.active();
	}

	public void closeLeftMenu()
	{
		setInit();
		leftMenuCore.passive();
	}

	@Override
	public void initMenu(LeftMenuCore view){
		//ViewUtil.setFrame(this.viewer, 0,0,this.body.getWidth(),this.body.getHeight());
	}

	@Override
	public void moveMenu(LeftMenuCore view, float p)
	{
		ViewUtil.setFrame(this.viewer, (int)Math.floor(p),-1,-1,-1);
	}

	public void activeMenu(LeftMenuCore view, float p)
	{
		int tx = (int)Math.floor(p);
		FrameLayout.LayoutParams layout=(FrameLayout.LayoutParams)this.viewer.getLayoutParams();
		int dfX=layout.leftMargin;
		layout.leftMargin=tx;

		Log.i("activeMenu","dfX : " + dfX + "  tx : "+ tx);

		this.viewer.setLayoutParams(layout);
		TranslateAnimation moveAni=new TranslateAnimation(dfX-tx,0,0,0);
		moveAni.setDuration(200);
		this.viewer.startAnimation(moveAni);


	}
	public void passiveMenu(LeftMenuCore view)
	{

		FrameLayout.LayoutParams layout=(FrameLayout.LayoutParams)this.viewer.getLayoutParams();
		int dfX=layout.leftMargin;
		layout.leftMargin=0;
		this.viewer.setLayoutParams(layout);
		TranslateAnimation moveAni=new TranslateAnimation(dfX,0,0,0);
		moveAni.setDuration(200);
		this.viewer.startAnimation(moveAni);

	}

	
	

}
