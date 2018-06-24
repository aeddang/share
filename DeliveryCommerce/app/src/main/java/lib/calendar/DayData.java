package lib.calendar;

import lib.CommonUtil;

public class DayData
{
	//view
			private int ymd;
			private String ymdStr;
			private int dd;
			private int mm;
			private int yy;
			private int dayNum;
			
			public DayData(int _yy, int _mm, int _dd)
			{
	            yy=_yy;
				mm=_mm;
				dd=_dd;
				ymd=Integer.parseInt(CommonUtil.intToText (yy,4)+CommonUtil.intToText (mm,2)+CommonUtil.intToText (dd,2));
				ymdStr=CommonUtil.getDateByCode (ymd,".");
				dayNum=CommonUtil.getDays(yy,mm,dd);
				
			}
			public void setDayData (String _code)
			{
				ymdStr=_code;
				yy=Integer.parseInt(ymdStr.substring(0,4));
				mm=Integer.parseInt(ymdStr.substring(4,6));
				dd=Integer.parseInt(ymdStr.substring(6,8));
				ymd=Integer.parseInt(ymdStr);
				dayNum=CommonUtil.getDays(yy,mm,dd);
			}
			
			public int getDay(){ return dayNum; }
		    public int getCode(){ return ymd; }
			public String getViewStr  (){return ymdStr;}
			public int getDate  (){return dd;}
			public int getMonth  (){return mm;}
			public int getYear  (){return yy;}
			public String getYearStr (){return String.valueOf(yy)+"��";}
			public String getMonthStr  (){return CommonUtil.intToText (mm,2)+"��";}
			public String getDateStr  (){return CommonUtil.intToText (dd,2)+"��";}
			
			public String getDayStr  ()
			{
				switch(dayNum){
					case 0:
					return "��";
					case 1:
					return "��";
					case 2:
					return "ȭ";
					case 3:
					return "��";
					case 4:
					return "��";
					case 5:
					return "��";
					case 6:
					return "��";
					
				}
				return "err";
			}
			public String getFullStr  ()
			{
				return String.valueOf(yy)+"��"+CommonUtil.intToText (mm,2)+"��"+CommonUtil.intToText (dd,2)+"��";
			}

}//package