package lib.calendar;

import java.util.ArrayList;



public class YearData extends MonthData
{
	
	private ArrayList<MonthData> listA;

	public YearData (int _yy)
	{
	    super((_yy*100)+12);
		listA=new ArrayList<MonthData>();
    }
	
    public void addList(MonthData list)
	{
		listA.add(list);
	}
	public int getMonthListNum ()
	{
		return listA.size();
	}
	public ArrayList<MonthData> getMonthList ()
	{
		return listA;
	}
	
}//package