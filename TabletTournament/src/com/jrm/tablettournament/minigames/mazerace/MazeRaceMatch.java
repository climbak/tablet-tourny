package com.jrm.tablettournament.minigames.mazerace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

public class MazeRaceMatch extends MiniGameMatch
{
	Matrix [] projections;
	
	Paint pBlue = new Paint();
	Paint pRed = new Paint();
	
	int p1_jstick, p2_jstick;
	
	public MazeRaceMatch(Context context){
		super(context);
		pBlue.setColor(Color.BLUE);
		pBlue.setTextSize(20);
		pRed.setColor(Color.RED);
		pRed.setTextSize(40);
		
		translatorLayout = ScreenLayout.MIRRORED_QUARTERED;
		
		p1_jstick = this.registerJoystick(ScreenRegion.TOP, 50, 50, 20);
		p2_jstick = this.registerJoystick(ScreenRegion.BOTTOM, 50, 50, 20);
	}
	
	public void start() {
		
	}
	
	int i = 0;
	
	public void update(int ds){
		PointF vector = new PointF();
		
		
	}
	
	public void draw(Canvas cv) {
	
		draw_top_left(cv, ScreenRegion.TOP_RIGHT);

		draw_top_left(cv, ScreenRegion.TOP_LEFT);

		draw_top_left(cv, ScreenRegion.BOTTOM_RIGHT);

		draw_top_left(cv, ScreenRegion.BOTTOM_LEFT);
		
		i++;
		
		if (i > 300) i = 0;
	}

	ScreenRegion testRegion = ScreenRegion.TOP;
	
	public void draw_top_left(Canvas cv, ScreenRegion region){
		setToProjection(region, cv);
		
		int middle_x = this.view_center_x;
		int middle_y = this.view_center_y/2;
		
		cv.drawRect(0, 0, i, i, (testRegion == region)? pBlue : pRed);
		cv.drawLine(1, 1, 40, 1, pBlue);
		cv.drawLine(1, 1, 1, 40, pBlue);
		cv.drawText("Projection " + region, 2, 20, pBlue);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		testRegion = this.getRegionFromPoint((int)x, (int)y);
		
	    return super.onTouchEvent(event);
	}
	
}
