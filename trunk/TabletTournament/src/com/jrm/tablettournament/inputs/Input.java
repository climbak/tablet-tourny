package com.jrm.tablettournament.inputs;


import android.graphics.Canvas;

import com.jrm.tablettournament.enumuerations.ScreenRegion;

public abstract class Input {
	public int handle;
	public ScreenRegion region;
	
	public abstract void draw(Canvas cv);
	public abstract boolean containsPoint(float region_x, float region_y);
	public abstract void handleDownEvent(float region_x, float region_y);
	public abstract void handleMoveEvent(float region_x, float region_y);
	public abstract void handleUpEvent();
}
