package com.jrm.tablettournament.inputs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

public class InputButton extends Input
{
	RectF rect = new RectF();
	public boolean active = false;
	
	Paint pRed = new Paint();
	Paint pRedFill = new Paint();
	
	public InputButton(int x, int y, int width, int height){
		rect.left = x;
		rect.top = y;
		rect.right = x + width;
		rect.bottom = y + height;
		
		pRed.setColor(Color.RED);
		pRed.setStyle(Style.STROKE);
		pRedFill.setColor(Color.RED);
	}
	
	@Override
	public void handleDownEvent(float region_x, float region_y) {
		active = true;
	}

	@Override
	public void handleMoveEvent(float region_x, float region_y) {
		// don't care what happens here
	}

	@Override
	public void handleUpEvent() {
		active = false;
	}

	@Override
	public boolean containsPoint(float region_x, float region_y) {
		return rect.contains(region_x, region_y);
	}

	@Override
	public void draw(Canvas cv) {
		cv.drawRect(rect, (active)? pRedFill : pRed);
	}

}
