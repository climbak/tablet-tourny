package com.jrm.tablettournament.minigames.mazerace;

/*+----------------------------------------------------------------------
 ||
 ||  Class Maze 
 ||
 ||         Author:  Ryan Courreges
 ||
 ||        Purpose:  This class represents a Maze object and provides the 
 ||					necessary methods to manipulate it. The maze itself is
 ||					just represented by a char array with '#' as walls and
 ||					' ' as pathways.
 ||
 ||  Inherits From:  None.
 ||
 ||     Interfaces:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  Maze(int, int)
 ||
 ||  Class Methods:  None.
 ||
 ||  Inst. Methods:  void     makeEntrance()
 ||					void     makeExit()
 ||					int[]    getStartPosition()
 ||					void     print()
 ||					boolean  hasNewDireciton(int, int)
 ||					int      getCutDirection(int, int)
 ||					int[]    makeCut(int, int, int)
 ||					boolean  checkDirection(int, int, int)
 ||					char[][] getMaze()
 ||
 ++-----------------------------------------------------------------------*/

public class Maze {

	// instance variables
	private char[][] maze;
	private int rows, cols;

	/*---------------------------------------------------------------------
	|  Method Maze(int, int) (Constructor)
	|
	|  Purpose:  The constructor creates an r x c char array representing the
	|			 maze.
	|
	|  Pre-condition:  None.
	|
	|  Post-condition: r x c Maze is constructed.
	|
	|  Parameters:  int rows - number of rows the maze will contain.
	|				int cols - number of columns the maze will contain.
	|
	|  Returns:  A new Maze object.
	 *-------------------------------------------------------------------*/
	public Maze(int rows, int cols) {

		this.rows = rows;
		this.cols = cols;

		// create the maze array
		maze = new char[2 * rows + 1][2 * cols + 1];

		// fill the initial maze field
		for (int i = 0; i <= 2 * rows; i++) {
			for (int j = 0; j <= 2 * cols; j++) {
				maze[i][j] = '#';
			}
		}

	}// constructor

	/*---------------------------------------------------------------------
	|  Method makeEntrance() and makeExit()
	|
	|  Purpose:  Randomly selects a position on the left/right hand side of
	|            the maze and 'cuts' an entrance/exit.
	|
	|  Pre-condition:  A closed maze.
	|
	|  Post-condition: A Maze with an entrance or exit.
	|
	|  Parameters: None.
	|
	|  Returns:  Nothing.
	 *-------------------------------------------------------------------*/
	public void makeEntrance() {

		maze[(int) (Math.random() * (maze.length - 2) + 1)][0] = ' ';

	}// makeEntrance()

	public void makeExit() {

		maze[(int) (Math.random() * (maze.length - 2) + 1)][maze[0].length - 1] = ' ';

	}// makeExit()

	/*---------------------------------------------------------------------
	|  Method getStartPosition()
	|
	|  Purpose:  Randomly selects a starting point in the maze and 'cuts' it.
	|            Returns the starting position.
	|
	|  Pre-condition:  Blank, uncut maze.
	|
	|  Post-condition: Randomly selected a starting point in the maze is
	|                  cleared and the starting position is returned.
	|
	|  Parameters: None.
	|
	|  Returns:  Returns the starting position.
	 *-------------------------------------------------------------------*/
	public int[] getStartPosition() {

		int[] pos = new int[2];
		pos[0] = (int) (Math.random() * rows);
		pos[1] = (int) (Math.random() * cols);

		maze[2 * pos[0] + 1][2 * pos[1] + 1] = ' ';

		return pos;

	}// getStartPosition()

	public void generateMaze() {

		SimpleStack<int[]> stack = new SimpleStack<int[]>();
		int numRows = rows, NumCols = cols; // rows and columns
		int[] currentPosition = new int[2]; // array to hold current row and
											// column
		int currentRow = 0, currentCol = 0;
		// boolean success = false; // used if we want user input during runtime
		int direction = 0; // holds the value of the direction to make a cut
		while (!stack.isEmpty()) {

			currentPosition = stack.peek();
			currentRow = currentPosition[0] + 1;
			currentCol = currentPosition[1] + 1;

			if (this.hasNewDirection(currentRow, currentCol)) {

				direction = this.getCutDirection(currentRow, currentCol);
				stack.push(this.makeCut(currentRow, currentCol, direction));

			} else
				stack.pop();

			// finish the maze and print the result
			this.makeEntrance();
			this.makeExit();

		}
	}

	/*---------------------------------------------------------------------
	|  Method print()
	|
	|  Purpose:  The current maze is printed to the console.
	|
	|  Pre-condition:  Nothing.
	|
	|  Post-condition: Current maze is printed.
	|
	|  Parameters: None.
	|
	|  Returns:  Nothing.
	 *-------------------------------------------------------------------*/
	public void print() {
		System.out.print("\n  ");
		for (int i = 0; i < maze[0].length; i++) {
			System.out.print(i);
		}
		System.out.println();
		for (int i = 0; i < maze.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}

	}// print()

	/*---------------------------------------------------------------------
	|  Method hasNewDirection(int, int)
	|
	|  Purpose:  Takes input position and checks in all direction to see if
	|            any cuts are possible.
	|
	|  Pre-condition:  Nothing.
	|
	|  Post-condition: Existence of valid cuts is returned.
	|
	|  Parameters: int row - starting row
	|			   int col - starting column
	|
	|  Returns:  Returns true/false if valid cut exists.
	 *-------------------------------------------------------------------*/
	public boolean hasNewDirection(int row, int col) {

		boolean up = row != 1 && maze[2 * row - 3][2 * col - 1] == '#';

		boolean right = col != (maze[2 * row - 1].length - 1) / 2
				&& maze[2 * row - 1][2 * col + 1] == '#';
		// maze[2*row+1][2*col+2] == '#' && col != maze[row].length-1;

		boolean down = row != (maze.length - 1) / 2
				&& maze[2 * row + 1][2 * col - 1] == '#';
		// maze[2*row+2][2*col+1] == '#' && row != maze.length-1;

		boolean left = col != 1 && maze[2 * row - 1][2 * col - 3] == '#';
		// maze[2*row+1][2*col] == '#' && col != 1;

		return (up || down || left || right);

	}// hasNewDirection()

	/*---------------------------------------------------------------------
	|  Method getCutDirection(int, int)
	|
	|  Purpose:  Randomly selects a valid cut direction and returns it.
	|
	|  Pre-condition:  Nothing.
	|
	|  Post-condition: Returns a random valid cut direction.
	|
	|  Parameters: int row - row position to start from.
	|			   int col - column position to start from.
	|
	|  Returns:  Returns random valid cut direction.
	 *-------------------------------------------------------------------*/
	public int getCutDirection(int row, int col) {

		int direction = 0;
		// int cutUp = 1, cutRight = 2, cutDown = 3, cutLeft = 4;
		int[] possibleDirection = new int[4];
		int count = 0;

		if (hasNewDirection(row, col)) {
			for (int i = 1; i <= 4; i++) {
				if (checkDirection(row, col, i)) {
					possibleDirection[count++] = i;
					// count++;
				}
			}
		} else
			direction = -1;

		direction = possibleDirection[(int) (Math.random() * count)];

		return direction;

	}// getCutDirection

	/*---------------------------------------------------------------------
	|  Method makeCut(int, int, int)
	|
	|  Purpose:  Clear the character array by one position in the input
	|			 input direction from the input starting position.
	|
	|  Pre-condition:  Uncut maze.
	|
	|  Post-condition: Maze is cut according to the input parameters.
	|
	|  Parameters: int startRow - row to start from
	|			   int startCol - column to start from
	|			   int direction - direction to make the cut
	|
	|  Returns:  Returns the new position.
	 *-------------------------------------------------------------------*/
	public int[] makeCut(int startRow, int startCol, int direction) {

		int[] newPosition = new int[2];
		int row = 2 * startRow - 1;
		int col = 2 * startCol - 1;

		switch (direction) {
		case 1:
			maze[--row][col] = ' ';
			maze[--row][col] = ' ';
			newPosition[0] = (row - 1) / 2;
			newPosition[1] = (col - 1) / 2;
			break;
		case 2:
			maze[row][++col] = ' ';
			maze[row][++col] = ' ';
			newPosition[0] = (row - 1) / 2;
			newPosition[1] = (col - 1) / 2;
			break;
		case 3:
			maze[++row][col] = ' ';
			maze[++row][col] = ' ';
			newPosition[0] = (row - 1) / 2;
			newPosition[1] = (col - 1) / 2;
			break;
		case 4:
			maze[row][--col] = ' ';
			maze[row][--col] = ' ';
			newPosition[0] = (row - 1) / 2;
			newPosition[1] = (col - 1) / 2;
		}
		return newPosition;
	}// makeCut()

	/*---------------------------------------------------------------------
	|  Method checkDirection(int, int, int)
	|
	|  Purpose:  Checks if the input direction is valid to make a cut.
	|
	|  Pre-condition:  Nothing.
	|
	|  Post-condition: Validity of cut direction is returned.
	|
	|  Parameters: int row - row to check from
	|			   int col - column to check from
	|			   int direction - direction to check
	|
	|  Returns:  Returns the true/false validity of the input cut.
	 *-------------------------------------------------------------------*/
	private boolean checkDirection(int row, int col, int direction) {
		// 1 = up, 2 = right, 3 = down, 4 = left
		boolean valid = false;
		switch (direction) {
		case 1:
			valid = row != 1 && maze[2 * row - 3][2 * col - 1] == '#';
			break;
		case 2:
			valid = col != (maze[2 * row - 1].length - 1) / 2
					&& maze[2 * row - 1][2 * col + 1] == '#';
			break;
		case 3:
			valid = row != (maze.length - 1) / 2
					&& maze[2 * row + 1][2 * col - 1] == '#';
			break;
		case 4:
			valid = col != 1 && maze[2 * row - 1][2 * col - 3] == '#';
		}

		return valid;
	}// checkDirection()

	/*---------------------------------------------------------------------
	|  Method getMaze()
	|
	|  Purpose:  Returns a copy of the maze char array.
	|
	|  Pre-condition:  Nothing.
	|
	|  Post-condition: Copy of the maze char array is returned
	|
	|  Parameters: None.
	|
	|  Returns:  Returns a copy of the maze array.
	 *-------------------------------------------------------------------*/
	public char[][] getMaze() {
		char[][] clone = new char[rows][cols];
		// clone the terms
		clone = maze.clone();

		return clone;
	}// getMaze()
}// Maze
