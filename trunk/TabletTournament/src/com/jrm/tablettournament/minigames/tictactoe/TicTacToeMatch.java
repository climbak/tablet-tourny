package com.jrm.tablettournament.minigames.tictactoe;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jrm.tablettournament.MiniGameMatch;

public class TicTacToeMatch extends MiniGameMatch
{

	
	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void draw(Canvas cv) {
		// single projection, no need to change
		cv.drawText("hello world", 10, 10, new Paint());
	}

}
