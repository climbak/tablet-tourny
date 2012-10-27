package com.jrm.tablettournament.minigames.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

public class TicTacToeMatch extends MiniGameMatch {

	Paint pBlue = new Paint();
	float[] lines;

	public TicTacToeMatch(Context context) {
		super(context);
		pBlue.setColor(Color.BLUE);
		pBlue.setStrokeWidth(5);
	}

	@Override
	protected void onAfterSetScreenDimensions(){
		float boardSide = this.view_width - 50;
		lines = new float[]
				{ this.view_center_x - boardSide/2, this.view_center_y  - boardSide/6, this.view_center_x + boardSide/2, this.view_center_y - boardSide/6,    // first horizontal
				this.view_center_x - boardSide/2, this.view_center_y  + boardSide/6, this.view_center_x + boardSide/2, this.view_center_y + boardSide/6,    // second horizontal
				this.view_center_x - boardSide/6, this.view_center_y  - boardSide/2, this.view_center_x - boardSide/6, this.view_center_y + boardSide/2,    // first vertical
				this.view_center_x + boardSide/6, this.view_center_y  - boardSide/2, this.view_center_x + boardSide/6, this.view_center_y + boardSide/2, }; // second vertical
	};
	
	public void start() {
		// TODO Auto-generated method stub

	}

	public void update(int ds) {

	}

	public void draw(Canvas cv) {
		// single projection, no need to change
//		cv.drawText("hello world", 10, 100, pBlue);
		try{
		cv.drawLines(lines, pBlue);
		} catch (Exception e){
			Log.d("match.draw", "doesn't work", e);
		}
		
	}
}
