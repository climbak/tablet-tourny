package com.jrm.tablettournament.minigames.tictactoe;

import java.util.ArrayList;

import com.jrm.tablettournament.IGameState;


/**
 * TicTacToeGameState extends {@link GameState}. This class holds a state of the
 * game and links to the state's parent. It also provides methods necessary for
 * getting state specific information.
 * 
 * @author Ryan Courreges
 * 
 */
public class TicTacToeGameState extends GameState {

	TicTacToeGameState parent;
	boolean isMaxPlayer;

	/**
	 * Creates a new TicTacToeGameState object using the input parameters.
	 * 
	 * @param initialState
	 *            The state this GameState will initially hold.
	 * @param parent
	 *            The parent GameState of this state.
	 * @param isMaxPlayer
	 *            The boolean value to control if this state is a min or max
	 *            player for the minimax search algorithm.
	 */
	public TicTacToeGameState(String initialState, TicTacToeGameState parent,
			boolean isMaxPlayer) {
		super(initialState);
		this.parent = parent;
		this.isMaxPlayer = isMaxPlayer;
	}

	/**
	 * Generates all possible child states of this state for the given player
	 * turn.
	 * 
	 * @param whichTurn
	 *            'X' or 'O' player turn.
	 * @param isMaxPlayer
	 *            Max player value of this state.
	 * @return A list of all the possible direct children of this state for the
	 *         given player's move.
	 */
	public ArrayList<TicTacToeGameState> generateChildren(char whichTurn,
			boolean isMaxPlayer) {

		if (isGameOver())
			return null;

		ArrayList<TicTacToeGameState> children = new ArrayList<TicTacToeGameState>();
		String state = (String) getState();
		for (int i = 0; i < state.length(); i++) {
			if (state.charAt(i) == '_') {
				String newState = state.substring(0, i) + whichTurn
						+ state.substring(i + 1);
				children.add(new TicTacToeGameState(newState, this, isMaxPlayer));
			}
		}
		return children;
	}

	/**
	 * Returns whether this state matches a game over condition.
	 */
	public boolean isGameOver() {

		String state = (String) getState();

		// no moves left
		if (!state.contains("_"))
			return true;

		// first row, column and left top to bottom right diag
		if (state.charAt(0) != '_') {
			// first row
			if (state.charAt(0) == state.charAt(1)
					&& state.charAt(0) == state.charAt(2))
				return true;

			// first column
			if (state.charAt(0) == state.charAt(3)
					&& state.charAt(0) == state.charAt(6))
				return true;

			// diagonal left top to bottom right
			if (state.charAt(0) == state.charAt(4)
					&& state.charAt(0) == state.charAt(8))
				return true;
		}

		// second column
		if (state.charAt(1) != '_') {
			if (state.charAt(1) == state.charAt(4)
					&& state.charAt(1) == state.charAt(7))
				return true;
		}

		// third column and top right to bottom left diag
		if (state.charAt(2) != '_') {
			// third column
			if (state.charAt(2) == state.charAt(5)
					&& state.charAt(2) == state.charAt(8))
				return true;
			// diag
			if (state.charAt(2) == state.charAt(4)
					&& state.charAt(2) == state.charAt(6))
				return true;
		}

		// second row
		if (state.charAt(3) != '_') {
			if (state.charAt(3) == state.charAt(4)
					&& state.charAt(3) == state.charAt(5))
				return true;
		}

		// third row
		if (state.charAt(6) != '_') {
			if (state.charAt(6) == state.charAt(7)
					&& state.charAt(6) == state.charAt(8))
				return true;
		}

		return false;
	}

	/**
	 * Returns the winner of this state if the game is over. -1 for 'O', 0 for a
	 * draw and +1 for 'X'. If game is not over when this method is called,
	 * returns -9.
	 */
	public Object winner() {

		String state = (String) getState();

		if (!isGameOver())
			return -9;

		else {

			if (state.charAt(0) != '_') {
				// first row
				if (state.charAt(0) == state.charAt(1)
						&& state.charAt(0) == state.charAt(2))
					return state.charAt(0) == 'X' ? 1 : -1;

				// first column
				if (state.charAt(0) == state.charAt(3)
						&& state.charAt(0) == state.charAt(6))
					return state.charAt(0) == 'X' ? 1 : -1;

				// diagonal left top to bottom right
				if (state.charAt(0) == state.charAt(4)
						&& state.charAt(0) == state.charAt(8))
					return state.charAt(0) == 'X' ? 1 : -1;
			}

			if (state.charAt(1) != '_') {
				// second column
				if (state.charAt(1) == state.charAt(4)
						&& state.charAt(1) == state.charAt(7))
					return state.charAt(1) == 'X' ? 1 : -1;
			}

			if (state.charAt(2) != '_') {
				// third column
				if (state.charAt(2) == state.charAt(5)
						&& state.charAt(2) == state.charAt(8))
					return state.charAt(2) == 'X' ? 1 : -1;
				// diag
				if (state.charAt(2) == state.charAt(4)
						&& state.charAt(2) == state.charAt(6))
					return state.charAt(2) == 'X' ? 1 : -1;
			}

			if (state.charAt(3) != '_') {
				// second row
				if (state.charAt(3) == state.charAt(4)
						&& state.charAt(3) == state.charAt(5))
					return state.charAt(3) == 'X' ? 1 : -1;
			}

			if (state.charAt(6) != '_') {
				// third row
				if (state.charAt(6) == state.charAt(7)
						&& state.charAt(6) == state.charAt(8))
					return state.charAt(6) == 'X' ? 1 : -1;
			}

			// no winner
			return 0;
		}
	}

	/**
	 * @return Boolean value of this state being max player.
	 */
	public boolean isMaxPlayer() {
		return isMaxPlayer;
	}

	/**
	 * Sets this state to be a max or min player.
	 * @param isMaxPlayer True for max player, false for min player.
	 */
	public void setMaxPlayer(boolean isMaxPlayer) {
		this.isMaxPlayer = isMaxPlayer;
	}

	/**
	 * @return Parent GameState of this state.
	 */
	public TicTacToeGameState getParent() {
		return parent;
	}

}
