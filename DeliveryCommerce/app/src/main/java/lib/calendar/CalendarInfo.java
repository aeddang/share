package lib.calendar;

import java.util.ArrayList;

import lib.CommonUtil;

public class CalendarInfo
{
		public CalendarInfo ()
		{
            
		}
		
		public ArrayList<MonthData> getMonthInfo(int sday,int eday)
		{
			String mm;
			ArrayList<MonthData> dataA=new ArrayList<MonthData>();
			while(sday<=eday){
				dataA.add(new MonthData(sday));
				sday++;
			    mm = CommonUtil.intToText (sday,4);
				mm = mm.substring(4);
				if(mm=="13"){
					sday=sday+100-12;
				} 
			}
			return dataA;
		}
		
		public ArrayList<YearData> getYearInfo(ArrayList<MonthData> listA)
		{
			int yy=-1;
			ArrayList<YearData> dataA =new ArrayList<YearData>();
			YearData dataObj = null;
			int num=listA.size()-1;
			int _yy;
			for(int i=num;i>=0;--i)
			{
				_yy=listA.get(i).getYear();
				if(yy!=_yy){
					dataObj=new YearData(_yy);
					dataA.add(dataObj);
					yy=_yy;
				} 
				dataObj.addList(listA.get(i));
			}
			
			return dataA;
		}
		
		public ArrayList<DayData> getCalendarInfo (int yy,int mm)
		{
			int num=CommonUtil.getDaysInMonth(yy,mm);
			int dd=1;
			ArrayList<DayData> dataA=new ArrayList<DayData>();
	        for(int i=0;i<num;++i){
				dataA.add(new DayData(yy,mm,dd));
				dd++;
			}
			return dataA;
		}
		public ArrayList<DayData> getWeekData (int yy,int mm,int dd)
		{
			DayData data=new DayData(yy,mm,dd);
			ArrayList<DayData> dataA=new ArrayList<DayData>();
			int num=data.getDay();
			int i;
			for(i=0;i<7;++i){
				dataA.add(null);
			}
			dataA.set(num,data);
			int _yy=yy;
			int _mm=mm;
			int _dd=dd;
			DayData cdata;
			if(num!=0){	
				
			   for(i=num-1;i>=0;--i){
				   cdata=getPrevDay (_yy,_mm,_dd);
				   dataA.set(i, cdata);
				   _yy=cdata.getYear();
				   _mm=cdata.getMonth();
				   _dd=cdata.getDate();
				  // trace(dataA[i].fullStr)	   
			   }	
			}
			_yy=yy;
			_mm=mm;
			_dd=dd;
			if(num!=6){	
				for(i=num+1;i<7;++i){
					cdata=getNextDay (_yy,_mm,_dd);
					dataA.set(i, cdata);
					_yy=cdata.getYear();
					_mm=cdata.getMonth();
					_dd=cdata.getDate();
					//trace(dataA[i].fullStr)	 
				}	
			}	
			return 	dataA;
		}
		public DayData getPrevDay (int yy,int mm,int dd)
		{
			dd--;
			if(dd<1){
			  mm--;
			  if(mm<1){
			      yy--;
			      mm=12;
			  } 
			  dd=CommonUtil.getDaysInMonth(yy,mm);
			}	
			return new DayData(yy,mm,dd);
		}
		
		public DayData getNextDay (int yy,int mm,int dd)
		{
			int num=CommonUtil.getDaysInMonth(yy,mm);
			dd++;
			if(dd>num){
				mm++;
				if(mm>12){
					yy++;
					mm=1;
				} 
				dd=1;
			}	
			return new DayData(yy,mm,dd);
		}
}//class
