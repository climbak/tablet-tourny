package com.jrm.tablettournament;

import com.jrm.tablettournament.enumuerations.AILevel;
import com.jrm.tablettournament.enumuerations.ScreenLayout;

public interface IMiniGame {
	
	// return the allowed numbers of players (i.e 2,4)
	int [] getAllowedNumbersOfPlayers();
	
	// return the available AI difficulties
	AILevel [] getAvailableAILevels();
	ScreenLayout getProjectionLayout(int player_count);
	ScreenLayout getTranslatorLayout(int player_count);
	MiniGameMatch createMatch(int player_count, int [] cpuAILevels);
}