package com.jrm.tablettournament.minigames.tictactoe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.jrm.tablettournament.MiniGameMatch;

public class TicTacToeMatch extends MiniGameMatch {

	private static final int[][] winningPaths = { { 0, 1, 2 }, { 3, 4, 5 },
			{ 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
			{ 2, 4, 6 } };
	private static final String[] oneAway = { "XX ", "X X", " XX", "OO ",
			"O O", " OO" };

	HashSet<String> visited = new HashSet<String>();
	
	Paint pBlue = new Paint();
	float[] lines;
	char[] board = new char[9];
	float boardSide;
	int markOffset;
	int winningPath;
	Paint def = new Paint();
	char currentPlayer;
	Character winner = null;

	public TicTacToeMatch(Context context) {
		super(context);

		currentPlayer = 'X';
		winningPath = -1;

		for (int i = 0; i < 9; i++) {
			board[i] = ' ';
		}

		pBlue.setColor(Color.BLUE);
		pBlue.setStrokeWidth(5);
		pBlue.setStyle(Style.STROKE);
	}

	@Override
	protected void onAfterSetScreenDimensions() {
		boardSide = this.view_width - 50;
		markOffset = (int) (boardSide / 20);
		lines = new float[] {
				this.view_center_x - boardSide / 2,
				this.view_center_y - boardSide / 6,
				this.view_center_x + boardSide / 2,
				this.view_center_y - boardSide / 6, // first horizontal
				this.view_center_x - boardSide / 2,
				this.view_center_y + boardSide / 6,
				this.view_center_x + boardSide / 2,
				this.view_center_y + boardSide / 6, // second horizontal
				this.view_center_x - boardSide / 6,
				this.view_center_y - boardSide / 2,
				this.view_center_x - boardSide / 6,
				this.view_center_y + boardSide / 2, // first vertical
				this.view_center_x + boardSide / 6,
				this.view_center_y - boardSide / 2,
				this.view_center_x + boardSide / 6,
				this.view_center_y + boardSide / 2, }; // second vertical
	};

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		float x = me.getX();
		float y = me.getY();
		int clicked = getTouchedBox(new Point((int) x, (int) y));

		if (clicked != -1) {

			if (clicked == -9) {

				for (int i = 0; i < 9; i++)
					board[i] = ' ';
				currentPlayer = 'X';
				winner = null;

			} else {
				// if (board[clicked] == 'X')
				// board[clicked] = 'O';
				// else if (board[clicked] == 'O')
				// board[clicked] = 'X';
				// else
				// board[clicked] = 'X';
				if (!isGameOver()) {
					if (board[clicked] == ' ') {
						makeMove(clicked);
					}
				}
			}
		}
		return super.onTouchEvent(me);
	}

	/**
	 * Private method that returns the 0-based index of where on the game board
	 * the screen touch occurred.
	 * 
	 * @param p
	 *            Point where the touch event occurred
	 * @return The 1 indexed square in which the click occurred. Returns -9 for
	 *         reset area, -1 if click occurred on a dividing line or outside
	 *         the bounds.
	 */
	private int getTouchedBox(Point p) {

		if (p.y < 75 || p.y > this.view_height - 75)
			return -9;
		if (p.x >= this.view_center_x - boardSide / 2
				&& p.x < this.view_center_x - boardSide / 6) {
			if (p.y >= this.view_center_y - boardSide / 2
					&& p.y < this.view_center_y - boardSide / 6)
				return 0;
			else if (p.y > this.view_center_y - boardSide / 6
					&& p.y < this.view_center_y + boardSide / 6)
				return 3;
			else if (p.y > this.view_center_y + boardSide / 6
					&& p.y <= this.view_center_y + boardSide / 2)
				return 6;
		} else if (p.x > this.view_center_x - boardSide / 6
				&& p.x < this.view_center_x + boardSide / 6) {
			if (p.y >= this.view_center_y - boardSide / 2
					&& p.y < this.view_center_y - boardSide / 6)
				return 1;
			else if (p.y > this.view_center_y - boardSide / 6
					&& p.y < this.view_center_y + boardSide / 6)
				return 4;
			else if (p.y > this.view_center_y + boardSide / 6
					&& p.y <= this.view_center_y + boardSide / 2)
				return 7;
		} else if (p.x > this.view_center_x + boardSide / 6
				&& p.x <= this.view_center_x + boardSide / 2) {
			if (p.y >= this.view_center_y - boardSide / 2
					&& p.y < this.view_center_y - boardSide / 6)
				return 2;
			else if (p.y > this.view_center_y - boardSide / 6
					&& p.y < this.view_center_y + boardSide / 6)
				return 5;
			else if (p.y > this.view_center_y + boardSide / 6
					&& p.y <= this.view_center_y + boardSide / 2)
				return 8;
		}

		return -1;
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void update(int ds) {

	}

	public void draw(Canvas cv) {
		// single projection, no need to change

		cv.drawPaint(def);
		try {
			cv.drawLines(lines, pBlue);
			/* draw the necessary 'X's and 'O's */
			drawPlays(cv);
			if (winner == 'X' || winner == 'O')
				drawWinLine(cv, winningPath);
		} catch (Exception e) {
			Log.d("match.draw", "doesn't work", e);
		}

	}

	private void drawPlays(Canvas cv) {
		for (int i = 0; i < 9; i++) {
			switch (i) {
			case 0:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 2
							+ markOffset, this.view_center_y - boardSide / 2
							+ markOffset, this.view_center_x - boardSide / 6
							- markOffset, this.view_center_y - boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 1:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 6
							+ markOffset, this.view_center_y - boardSide / 2
							+ markOffset, this.view_center_x + boardSide / 6
							- markOffset, this.view_center_y - boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 2:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 6 - markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y - boardSide / 2 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x + boardSide / 6
							+ markOffset, this.view_center_y - boardSide / 2
							+ markOffset, this.view_center_x + boardSide / 2
							- markOffset, this.view_center_y - boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 3:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 2
							+ markOffset, this.view_center_y - boardSide / 6
							+ markOffset, this.view_center_x - boardSide / 6
							- markOffset, this.view_center_y + boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 4:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 6
							+ markOffset, this.view_center_y - boardSide / 6
							+ markOffset, this.view_center_x + boardSide / 6
							- markOffset, this.view_center_y + boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 5:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 6 - markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y - boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x + boardSide / 6
							+ markOffset, this.view_center_y - boardSide / 6
							+ markOffset, this.view_center_x + boardSide / 2
							- markOffset, this.view_center_y + boardSide / 6
							- markOffset), pBlue);
				}
				break;
			case 6:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 2 + markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							this.view_center_x - boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 2
							+ markOffset, this.view_center_y + boardSide / 6
							+ markOffset, this.view_center_x - boardSide / 6
							- markOffset, this.view_center_y + boardSide / 2
							- markOffset), pBlue);
				}
				break;
			case 7:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x - boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							this.view_center_x + boardSide / 6 - markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x - boardSide / 6
							+ markOffset, this.view_center_y + boardSide / 6
							+ markOffset, this.view_center_x + boardSide / 6
							- markOffset, this.view_center_y + boardSide / 2
							- markOffset), pBlue);
				}
				break;
			case 8:
				if (board[i] == 'X') {
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							pBlue);
					cv.drawLine(
							this.view_center_x + boardSide / 6 + markOffset,
							this.view_center_y + boardSide / 2 - markOffset,
							this.view_center_x + boardSide / 2 - markOffset,
							this.view_center_y + boardSide / 6 + markOffset,
							pBlue);
				} else if (board[i] == 'O') {
					cv.drawOval(new RectF(this.view_center_x + boardSide / 6
							+ markOffset, this.view_center_y + boardSide / 6
							+ markOffset, this.view_center_x + boardSide / 2
							- markOffset, this.view_center_y + boardSide / 2
							- markOffset), pBlue);
				}
				break;
			}
		}
	}

	private void drawWinLine(Canvas cv, int path) {
		switch (path) {
		case 0:
			cv.drawLine(this.view_center_x - boardSide / 2 + markOffset / 2,
					this.view_center_y - boardSide / 3, this.view_center_x
							+ boardSide / 2 - markOffset / 2,
					this.view_center_y - boardSide / 3, pBlue);
			break;
		case 1:
			cv.drawLine(this.view_center_x - boardSide / 2 + markOffset / 2,
					this.view_center_y, this.view_center_x + boardSide / 2
							- markOffset / 2, this.view_center_y, pBlue);
			break;
		case 2:
			cv.drawLine(this.view_center_x - boardSide / 2 + markOffset / 2,
					this.view_center_y + boardSide / 3, this.view_center_x
							+ boardSide / 2 - markOffset / 2,
					this.view_center_y + boardSide / 3, pBlue);
			break;
		case 3:
			cv.drawLine(this.view_center_x - boardSide / 3, this.view_center_y
					- boardSide / 2 + markOffset / 2, this.view_center_x
					- boardSide / 3, this.view_center_y + boardSide / 2
					- markOffset / 2, pBlue);
			break;
		case 4:
			cv.drawLine(this.view_center_x, this.view_center_y - boardSide / 2
					+ markOffset / 2, this.view_center_x, this.view_center_y
					+ boardSide / 2 - markOffset / 2, pBlue);
			break;
		case 5:
			cv.drawLine(this.view_center_x + boardSide / 3, this.view_center_y
					- boardSide / 2 + markOffset / 2, this.view_center_x
					+ boardSide / 3, this.view_center_y + boardSide / 2
					- markOffset / 2, pBlue);
			break;
		case 6:
			cv.drawLine(this.view_center_x - boardSide / 2
					+ (float) (1.5 * markOffset), this.view_center_y
					- boardSide / 2 + markOffset / 2, this.view_center_x
					+ boardSide / 2 - (float) (1.5 * markOffset),
					this.view_center_y + boardSide / 2 - markOffset / 2, pBlue);
			break;
		case 7:
			cv.drawLine(this.view_center_x + boardSide / 2
					- (float) (1.5 * markOffset), this.view_center_y
					- boardSide / 2 + markOffset / 2, this.view_center_x
					- boardSide / 2 + (float) (1.5 * markOffset),
					this.view_center_y + boardSide / 2 - markOffset / 2, pBlue);
			break;
		}

	}

	private void makeMove(int clicked) {
		// Input is valid; update board and currentPlayer
		board[clicked] = currentPlayer;
		currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

		// Check for winning conditions
		for (int i = 0; i < winningPaths.length; i++) {
			String s = "" + board[winningPaths[i][0]]
					+ board[winningPaths[i][1]] + board[winningPaths[i][2]];
			if (s.equals("XXX")) {
				winner = 'X';
				winningPath = i;
				return;
			} else if (s.equals("OOO")) {
				winner = 'O';
				winningPath = i;
				return;
			}
		}

		// Check for draw conditions
		for (int i = 0; i < board.length; i++) {
			if (board[i] == ' ')
				break;
			if (i == 8)
				return;
		}
		// If the next player is an AI, make its move
		if (currentPlayer == 'O') {
			makeMove(nextAIMove());
		}
	}

	private int nextAIMove() {
//		return easyAINextMove():
//		return intermediateAINextMove();
		return expertAINextMove();
	}

	private int easyAINextMove() {
		// easy AI
		Random rand = new Random();
		ArrayList<Integer> choicePool = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == ' ') {
				choicePool.add(i);
			}
		}

		return choicePool.get(rand.nextInt(choicePool.size()));
	}

	private int intermediateAINextMove() {
		// intermediate AI
		for (int i = 0; i < winningPaths.length; i++) {
			// If there are two of the same shape in a winning path...
			String s = "" + board[winningPaths[i][0]]
					+ board[winningPaths[i][1]] + board[winningPaths[i][2]];
			for (int j = 0; j < oneAway.length; j++) {
				if (s.equals(oneAway[j])) {
					// ...fill in the blank space
					int choice = winningPaths[i][s.indexOf(' ')];
					return choice;
				}
			}
		}
		// If we're not one away from victory / loss, pick at random
		return easyAINextMove();
	}

	private int expertAINextMove(){
		String state = "";
		for (char c : board) {
			if (c == ' ')
				state += '_';
			else
				state += c;
		}
		int xCount = 0, oCount = 0;
		for(int i=0; i<9; i++){
			if(state.charAt(i) == 'X')
				xCount++;
			else if(state.charAt(i) == 'O')
				oCount++;
		}
		boolean maxPlayer = !(oCount < xCount);
		int[] move = minimax(new TicTacToeGameState(state, null, maxPlayer));
		return 3*move[0] + move[1];
	}
	
	/**
	 * This is the minimax graph search algorithm. It performs a recursive DFS
	 * of the game space and returns the best move for the given state.
	 * 
	 * @param gameState
	 *            The state from which the search will begin.
	 * @return The best next move for this state.
	 */
	private int[] minimax(TicTacToeGameState gameState) {

		String[] temp = getInvariants(gameState);

		for (String tmp : temp) {
			visited.add(tmp);
		}

		if (gameState.isGameOver()) {
			int[] move = getMove(gameState);
			return new int[] { move[0], move[1], (Integer) gameState.winner() };
		}

		int bestScore;
		int currentScore;
		int row = -1, col = -1;

		if (gameState.isMaxPlayer()) {

			bestScore = Integer.MIN_VALUE;

			ArrayList<TicTacToeGameState> children = gameState
					.generateChildren('X', !gameState.isMaxPlayer());

			int[] result;
			for (TicTacToeGameState child : children) {
				if (!visited.contains(child.getState())) {
					result = minimax(child);
					currentScore = result[2];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						int[] move = getMove(child);
						row = move[0];
						col = move[1];
					}
				}
			}
			return new int[] { row, col, bestScore };
		}

		else {

			bestScore = Integer.MAX_VALUE;

			ArrayList<TicTacToeGameState> children = gameState
					.generateChildren('O', !gameState.isMaxPlayer());

			int[] result;
			for (TicTacToeGameState child : children) {
				if (!visited.contains(child.getState())) {
					result = minimax(child);
					currentScore = result[2];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						int[] move = getMove(child);
						row = move[0];
						col = move[1];
					}
				}
			}
			return new int[] { row, col, bestScore };
		}
	}
	
	private String[] getInvariants(TicTacToeGameState gameState) {
		
		String[] states = new String[4];
		states[0] = gameState.toString();
		states[1] = rotate(states[0]);
		states[2] = rotate(states[1]);
		states[3] = rotate(states[2]);
		
		return states;
	}
	
	private String rotate(String toRotate){
		StringBuilder tmp = new StringBuilder();
		tmp.append(toRotate.charAt(6));
		tmp.append(toRotate.charAt(3));
		tmp.append(toRotate.charAt(0));
		tmp.append(toRotate.charAt(7));
		tmp.append(toRotate.charAt(4));
		tmp.append(toRotate.charAt(1));
		tmp.append(toRotate.charAt(8));
		tmp.append(toRotate.charAt(5));
		tmp.append(toRotate.charAt(8));
		
		return tmp.toString();
	}
	
	private int[] getMove(TicTacToeGameState gameState) {
		if (gameState.getParent() == null)
			return new int[] { -1, -1 };

		String orig = (String) gameState.getParent().getState();
		String move = (String) gameState.getState();
		int[] x = new int[2];
		for (int i = 0; i < 9; i++) {
			if (orig.charAt(i) != move.charAt(i)) {
				x[0] = i / 3;
				x[1] = i % 3;
				break;
			}
		}
		return x;
	}
	
	private boolean isGameOver() {
		return winner != null;
	}
}
