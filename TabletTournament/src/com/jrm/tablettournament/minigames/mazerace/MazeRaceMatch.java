package com.jrm.tablettournament.minigames.mazerace;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

public class MazeRaceMatch extends MiniGameMatch
{
	Matrix [] projections;
	
	public void start() {
		// TODO Auto-generated method stub
	}
	
	public void draw(Canvas cv) {
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		
		setToProjection(ScreenRegion.TOP, cv);
		cv.drawText("test (0)", 50, 50, p);
		
		setToProjection(ScreenRegion.BOTTOM, cv);
		cv.drawText("test (1)", 50, 50, p);
	}

	
}
