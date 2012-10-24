package com.jrm.tablettournament.minigames.tictactoe;

import android.graphics.Paint;

import com.jrm.tablettournament.GameCanvas;
import com.jrm.tablettournament.IMiniGameMatch;

public class TicTacToeMatch implements IMiniGameMatch
{

	public void Start() {
		// TODO Auto-generated method stub
		
	}

	public void Draw(GameCanvas cv) {
		// single projection, no need to change
		cv.drawText("helow world", 10, 10, new Paint());
	}

}
