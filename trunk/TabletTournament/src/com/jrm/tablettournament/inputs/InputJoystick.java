package com.jrm.tablettournament.inputs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;


public class InputJoystick extends Input
{
	Paint pGreen = new Paint();
	Paint pRed = new Paint();
	
	public InputJoystick(){
		pGreen.setColor(Color.GREEN);
		pGreen.setStyle(Style.STROKE);
		
		pRed.setColor(Color.RED);
		pRed.setStyle(Style.STROKE);
	}
	
	public int cx, cy;
	public int r;
	
	public boolean active;
	public float current_x, current_y;
	
	public boolean containsPoint(float x, float y){
		return Math.pow(x-cx,2) + Math.pow(y-cy,2) < Math.pow(r, 2);
	}
	
	@Override
	public void handleDownEvent(float region_x, float region_y){
		this.active = true;
		current_x = region_x - cx;
		current_y = region_y - cy;
	}
	
	@Override
	public void handleMoveEvent(float region_x, float region_y){
		current_x = region_x - cx;
		current_y = region_y - cy;
	}
	
	@Override
	public void handleUpEvent(){
		this.active = false;
	}

	@Override
	public void draw(Canvas cv) {
		cv.drawCircle(cx, cy, r, active? pRed : pGreen);
		
		if (active){
			cv.drawLine(cx, cy, cx + current_x, cy + current_y, pRed);
			cv.drawCircle(cx + current_x, cy + current_y, 20, pRed);
		}
	}
}

