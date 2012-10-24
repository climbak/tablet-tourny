package com.jrm.tablettournament.minigames.mazerace;

import com.jrm.tablettournament.IMiniGame;
import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.AILevel;
import com.jrm.tablettournament.enumuerations.ScreenLayout;

public class MazeRace implements IMiniGame
{

	public int[] getAllowedNumbersOfPlayers() {
		return new int[] {2};
	}

	public AILevel[] getAvailableAILevels() {
		return new AILevel[]{
			AILevel.EASY,
			AILevel.MEDIUM,
			AILevel.HARD
		};
	}

	public ScreenLayout getProjectionLayout(int player_count) {
		return ScreenLayout.MIRRORED_SPLIT;
	}

	public ScreenLayout getTranslatorLayout(int player_count) {
		return ScreenLayout.MIRRORED_SPLIT;
	}

	public MiniGameMatch createMatch(int player_count, int[] cpuAILevels) {
		// TODO Auto-generated method stub
		return null;
	}

}
