package com.jrm.tablettournament.inputs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class InputTouch extends Input {

	RectF rect;
	Paint pRed = new Paint();
	Paint pRedFill = new Paint();
	
	public final State state = new State();
	
	public InputTouch(InputTouchDefinition definition){
		rect = new RectF(definition.rect);
		
		pRed.setColor(Color.RED);
		pRed.setStyle(Style.STROKE);
		pRedFill.setColor(Color.RED);
	}
	
	@Override
	public void draw(Canvas cv) {
		cv.drawRect(rect, (state.active)? pRedFill : pRed);
	}

	@Override
	public boolean containsPoint(float region_x, float region_y) {
		return rect.contains(region_x, region_y);
	}

	@Override
	public void handleDownEvent(float region_x, float region_y) {
		state.active = true;
		state.setXY(region_x, region_y);
	}

	@Override
	public void handleMoveEvent(float region_x, float region_y) {
		state.setXY(region_x, region_y);
	}
	
	@Override
	public void handleUpEvent() {
		state.active = false;
	}

	public class State
	{
		boolean active;
		public boolean getActive(){ return active; }
		private void setActive(boolean val){ active = val; } 
		
		PointF location = new PointF();
		public float getX() { return location.x; };
		public float getY(){ return location.y; };
		
		private void setXY(float x, float y){ location.x = x; location.y = y; };
		
		private State(){}
		
	}
}
