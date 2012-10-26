package com.jrm.tablettournament;



public abstract class CPUPlayer implements IPlayer {

private IStrategy currentStrategy;
	
	public CPUPlayer(){
		currentStrategy = null;
	}
	
	public CPUPlayer(IStrategy strategy){
		currentStrategy = strategy;
	}
	
	public String getStrategyType(){
		return currentStrategy.toString();
	}
	
	public IStrategy getStrategy(){
		return currentStrategy;
	}
	
	public void setStrategy(IStrategy strategy){
		currentStrategy = strategy;
	}
	
	//TODO
	public abstract void solve(IGameState gameState);
	
	//TODO
	public abstract IGameState makeMove(IMiniGame game, IMove move);
	
	//TODO
	public abstract IMove getNextMove(IGameState gameState);
	
}
