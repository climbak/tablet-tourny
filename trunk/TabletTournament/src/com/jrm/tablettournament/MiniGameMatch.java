package com.jrm.tablettournament;

import com.jrm.tablettournament.enumuerations.ScreenRegion;

import android.graphics.Canvas;

public abstract class MiniGameMatch {
	public abstract void start();
	public abstract void draw(Canvas cv);
	
	protected void setToProjection(ScreenRegion region, Canvas cv){
		
	}
}