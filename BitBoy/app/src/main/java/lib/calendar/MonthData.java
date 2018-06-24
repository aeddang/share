package lib.calendar;

import lib.CommonUtil;

public class MonthData
{
	
			private int ym;
		    private int mm;
			private int yy;
			
			public MonthData (int yymm)
			{
	            ym=yymm;
				yy=(int) Math.floor(ym/100);
				mm=ym%100;
			}
			
			public int getCode  ()
			{
				return ym;
			}
			
			public int getMonth  ()
			{
				return mm;
			}
			public int getYear  ()
			{
				return yy;
			}
			public String getYearStr ()
			{
				return String.valueOf(yy)+"";
			}
			public String getMonthStr  ()
			{
				return CommonUtil.intToText (mm,2)+"월";
			}
			public String getFullStr  ()
			{
				return String.valueOf(yy)+"년"+CommonUtil.intToText (mm,2)+"월";
			}

}//package