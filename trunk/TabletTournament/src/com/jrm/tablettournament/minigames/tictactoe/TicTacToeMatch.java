package com.jrm.tablettournament.minigames.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.jrm.tablettournament.MiniGameMatch;

public class TicTacToeMatch extends MiniGameMatch
{

	
	public TicTacToeMatch(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void draw(Canvas cv) {
		// single projection, no need to change
		cv.drawText("hello world", 10, 10, new Paint());
	}

}
