package com.jrm.tablettournament.minigames.tictactoe;

public abstract class GameState {

	Object state; // the current state this GameState holds

	/**
	 * creates a null GameState
	 */
	public GameState() {
		state = null;
	}

	/**
	 * Creates a GameState holding the input Object as its state.
	 * 
	 * @param state
	 *            The state this GameState holds.
	 */
	public GameState(Object state) {
		this.state = state;
	}

	/**
	 * Gets the game over condition for this GameState.
	 * 
	 * @return True/False if game is over.
	 */
	public abstract boolean isGameOver();

	/**
	 * Returns the winner of this game if game is over, else null;
	 * 
	 * @return The winner of this game.
	 */
	public abstract Object winner();

	/**
	 * Gets the this GameState's held representative Object.
	 * 
	 * @return The Object this GameState holds.
	 */
	public Object getState() {
		return state;
	}

	/**
	 * Sets this GameState's held Object.
	 * 
	 * @param state
	 *            The object to be held by this GameState.
	 */
	public void setState(Object state) {
		this.state = state;
	}

}

