package com.jrm.tablettournament.minigames.tictactoe;

import com.jrm.tablettournament.IMiniGame;
import com.jrm.tablettournament.IMiniGameMatch;
import com.jrm.tablettournament.enumuerations.AILevel;
import com.jrm.tablettournament.enumuerations.ScreenLayout;

public class TicTacToe implements IMiniGame {

	public int[] getAllowedNumbersOfPlayers() {
		return new int []{ 2 };
	}

	public AILevel[] getAvailableAILevels() {
		return new AILevel[]
			{
				AILevel.EASY,
				AILevel.MEDIUM, 
				AILevel.HARD
			};
	}

	public ScreenLayout getProjectionLayout(int player_count) {
		return ScreenLayout.FULL; // player count doesn't matter
	}

	public ScreenLayout getTranslatorLayout(int player_count) {		
		return ScreenLayout.FULL; // player count doesn't matter
	}

	public IMiniGameMatch createMatch(int player_count, int[] cpuAILevels) {
		// TODO Auto-generated method stub
		return null;
	}

}
