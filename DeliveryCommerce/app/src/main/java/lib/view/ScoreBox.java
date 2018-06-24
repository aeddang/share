package lib.view;










import java.util.ArrayList;

import lib.CommonUtil;


import android.content.Context;

import android.util.Log;
import android.widget.FrameLayout;



public class ScoreBox extends FrameLayout{
	
	private ArrayList<ScoreList> scoreA;
	public long delay;
	private int uint;
	//private Button exitBtn,startBtn;
	public ScoreBox(Context context) {
		
		 super(context);
		 delay=0;
		 uint=0;
	}
	public void setScoreBox(ArrayList<ScoreList> _scoreA,int _uint){
		scoreA=_scoreA;
		uint=_uint;
	}
	public void setScore(int score){
		
		String str=CommonUtil.intToText(score, scoreA.size());
		
		int num=str.length();
		for(int i=0;i<num;++i){
			String value=(String)Character.toString(str.charAt(i));
			int pos=Integer.parseInt(value);
			ScoreList list=scoreA.get(i);
			if(delay<0){
				list.movePos(pos, (-delay*i),uint);
			}else if(delay>0){
				list.movePos(pos, (delay*(num-1-i)),uint);
			}else{
				list.movePos(pos, 0,uint);
				
			}
		}
		
	}
}
