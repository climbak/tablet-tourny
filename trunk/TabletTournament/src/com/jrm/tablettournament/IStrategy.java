package com.jrm.tablettournament;

public interface IStrategy {

	public void solve(IGameState gameState);
	
	public IMove getNextMove(IMiniGame game, IMove move);
	
	public IGameState makeMove(IMiniGame game, IMove move);
	
}
