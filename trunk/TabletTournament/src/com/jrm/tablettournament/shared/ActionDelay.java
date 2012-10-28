package com.jrm.tablettournament.shared;

import java.util.ArrayList;
import java.util.Date;

public class ActionDelay 
{

	ArrayList<DelayData> datas = new ArrayList<DelayData>();
	
	public int addDelay(int millisecondsDelay){
		datas.add(new DelayData(millisecondsDelay));
		return datas.size() - 1;
	}
	
	public boolean get(int id){
		DelayData data = datas.get(id);
		long msCurrent = System.currentTimeMillis();
		
		if (msCurrent - data.msLast > data.msDelay){
			data.msLast = msCurrent;
			return true;
		} else {
			return false;
		}
	}
	
	private class DelayData {
		
		public DelayData(int millisecondsDelay){
			msDelay = millisecondsDelay;
		}
		
		public long msLast;
		public int msDelay;
	}
}
