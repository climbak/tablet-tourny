package com.jrm.tablettournament.minigames.mazerace;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

public class MazeRaceMatch extends MiniGameMatch
{
	Matrix [] projections;
	
	Paint pBlue = new Paint();
	Paint pRed = new Paint();
	
	public MazeRaceMatch(){
		pBlue.setColor(Color.BLUE);
		pRed.setColor(Color.RED);
		pRed.setTextSize(72);
	}
	
	public void start() {
		// TODO Auto-generated method stub
	}
	
	public void draw(Canvas cv) {
		int middle_x = this.view_center_x;
		int middle_y = this.view_center_y/2;
		
		setToProjection(ScreenRegion.TOP, cv);
		draw_top_left(cv, 0);
		cv.drawText("Player 1", middle_x-150, middle_y, pRed);
		
		setToProjection(ScreenRegion.BOTTOM, cv);
		draw_top_left(cv, 1);
		cv.drawText("Player 2", middle_x-150, middle_y, pRed);
	}

	public void draw_top_left(Canvas cv, int projection){
		cv.drawLine(1, 1, 40, 1, pBlue);
		cv.drawLine(1, 1, 1, 40, pBlue);
		cv.drawText("{x=0,y=0}", 2, 12, pBlue);
		cv.drawText("Projection " + projection, 2, 25, pBlue);
	}
	
}
