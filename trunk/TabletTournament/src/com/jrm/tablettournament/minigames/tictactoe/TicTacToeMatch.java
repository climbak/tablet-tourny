package com.jrm.tablettournament.minigames.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.jrm.tablettournament.MiniGameMatch;

public class TicTacToeMatch extends MiniGameMatch {

	Paint pBlue = new Paint();
	float[] lines;
	char[] board = new char[9];
	float boardSide;
	int markOffset;

	public TicTacToeMatch(Context context) {
		super(context);

		for (int i = 0; i < 9; i++)
			board[i] = ' ';

		pBlue.setColor(Color.BLUE);
		pBlue.setStrokeWidth(5);
	}

	@Override
	protected void onAfterSetScreenDimensions() {
		boardSide = this.view_width - 50;
		markOffset = (int) (boardSide/20);
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
			if(board[clicked] == 'X')
				board[clicked] = 'O';
			else if(board[clicked] == 'O')
				board[clicked] = 'X';
			else
				board[clicked] = 'X';
		}
		return super.onTouchEvent(me);
	}

	/**
	 * Private method that returns the 0-based index of where on the game board
	 * the screen touch occurred.
	 * 
	 * @param p
	 *            Point where the touch event occurred
	 * @return The 1 indexed square in which the click occurred. Returns -1 if
	 *         click occurred on a dividing line or outside the bounds.
	 */
	private int getTouchedBox(Point p) {
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

		try {
			cv.drawLines(lines, pBlue);
			
			/* draw the necessary 'X's and 'O's */
			for (int i = 0; i < 9; i++) {
				switch (i) {
				case 0:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y - boardSide / 2 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y - boardSide / 6 - markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 2 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y - boardSide / 2 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 1:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 2 + markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 - markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 2 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 6 + markOffset,
						this.view_center_y - boardSide / 2 + markOffset,
						this.view_center_x + boardSide / 6 - markOffset,
						this.view_center_y - boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 2:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 2 + markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y - boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 - markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y - boardSide / 2 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x + boardSide / 6 + markOffset,
						this.view_center_y - boardSide / 2 + markOffset,
						this.view_center_x + boardSide / 2 - markOffset,
						this.view_center_y - boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 3:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y + boardSide / 6 - markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 4:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 - markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y - boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 5:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset, pBlue);
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 - markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y - boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y - boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y + boardSide / 6 - markOffset), pBlue);
					}
					break;
				case 6:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y + boardSide / 2 - markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 2 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x - boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset), pBlue);
					}
					break;
				case 7:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset, pBlue);
						cv.drawLine(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 2 - markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x - boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 6 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset), pBlue);
					}
					break;
				case 8:
					if (board[i] == 'X') {
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset, pBlue);
						cv.drawLine(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 2 - markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y + boardSide / 6 + markOffset, pBlue);
					} else if (board[i] == 'O') {
						cv.drawOval(new RectF(this.view_center_x + boardSide / 6 + markOffset,
								this.view_center_y + boardSide / 6 + markOffset,
								this.view_center_x + boardSide / 2 - markOffset,
								this.view_center_y + boardSide / 2 - markOffset), pBlue);
					}
					break;
				}
			}
		} catch (Exception e) {
			Log.d("match.draw", "doesn't work", e);
		}

	}
}
