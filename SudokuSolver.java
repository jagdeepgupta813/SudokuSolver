package org.jagdeep.sudoku;

import org.jagdeep.sudoku.*;

/**
 * 
 * @author jAgdeep Mohan
 * 
 * this class solves the given input sudoku
 * it tries to place the values from 1 to 9, if either of the value does not work then it back track
 * and also validate if the values placed finally are correct or not
 * and there is utility function to validate if the sudoku has been solved successfully or not
 * its solve Sudoku prints the filled sudoku and returns the success status
 *
 */

public class SudokuSolver 
{
	private int revertCount = 0;
	private int [][] sudokuMatrix;
	
	public SudokuSolver() {
		// TODO Auto-generated constructor stub
	}

	public SudokuSolver(Sudoku s) {
		// TODO Auto-generated constructor stub
		this.sudokuMatrix = s.sudokuMatix;
	}
	
	
	public boolean solveSudoku(int [][] sudokuMatix) {
		Sudoku sudoku = new Sudoku(sudokuMatix);
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				solve(sudoku, i, j);
			}
		}
		
		System.out.println("Solved Sudoku :");
		sudoku.printSudoku(sudokuMatix);
		//TestSudoku.printSudokuArrayInput(sudoku.sudokuMatix);
		boolean response = sudoku.isSolution(sudoku.sudokuMatix);
		System.out.println("\n Given Sudoku input " + ((response)? " CAN " : " CAN'T ") + "be solved");
		System.out.println("Count of backtracks: " + revertCount);
		
		return response;
	}
	
	/**
	 * * solve takes 3 argument and return boolean
	 * @param s 2D sudoku matrix
	 * @param row row of 2D sudoku matrix
	 * @param col col of 2D sudoku matrix
	 * @return true if the board is solved , it may or may not be a correct solution however
	 */
	private boolean solve(Sudoku s, int row, int col)
	{	
		//if no zero left in matrix, sudoku is successfully solved(sudokuSolver only places values if it is allowed)
		if(sudokuFilled(s, row, col))		
			return true;
		
		for(int val = 1; val <= 9; val++)
		{
			if(isValidValuePlacement(s, row, col, val))
			{
				//place value on board
				s.setSquare(row, col, val);

				int[] nextEmptyCell = findNextEmptySquare(s, row, col);
				int nextEmptyX = nextEmptyCell[0];
				int nextEmptyY = nextEmptyCell[1];
				
				//if solved, then stop
				if(solve(s, nextEmptyX, nextEmptyY))
				{
					return true;
				}
				
				//it means the value placed is not correct, revert value to 0 and increase the failed value placement count
				s.setSquare(row, col, 0);
				this.revertCount += 1;
				
			}
		}

		//backtrack
		return false;
	}

	//find next empty square in the board to see where the next spot is to start placing values
	private int[] findNextEmptySquare(Sudoku s, int row, int col) 
	{
		int[] square = new int[2]; //holds an x,y coordinate for next empty square

		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				//if square is empty
				if(s.sudokuMatix[i][j] == 0)
				{
					square[0] = i;
					square[1] = j;
					return square;
				}
			}
		}

		return square;	
	}

	private boolean isValidValuePlacement(Sudoku s, int row, int col, int val)
	{
		//if there is already a number in the square
		if(isOccupied(s, row, col))
		{ 
			return false;
		}
		
		//if row will have duplicates by placing val in the square
		if(rowHasDuplicates(s, row, val))
		{
			return false;
		}
		
		//if col will have duplicates by placing val in the square
		if(colHasDuplicates(s, col, val))
		{
			return false;
		}
		
		//if section will have duplicates by placing val in the square
		if(sectionHasDuplicates(s, row, col, val))
		{
			return false;
		}
		
		//System.out.println(row + ", " + col + val + " true");
		
		return true;
	}
	
	//check for duplicate values in a section (it figures out the section by passing row and col to it)
	private boolean sectionHasDuplicates(Sudoku s, int row, int col, int val)
	{
		int[] values = new int[10]; //an array to test for duplicates
		
		int x, y;
		x = row / 3;
		y = col / 3;
		
		int section = (3 * x) + y;	                   
		
		int topLeftX = section - (section % 3); //x-coordinate of top left square in section
		int topLeftY = (section % 3) * s.N;     //y-coordinate of top left square in section

		//for each row in section
		for(int i = topLeftX; i < topLeftX + s.N; i++)
		{
			for(int j = topLeftY; j < topLeftY + s.N; j++)
			{
				int squareVal = s.sudokuMatix[i][j];
				
				//for each slot in values array
				for(int k = 0; k < values.length; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
			}
		}
		
		return false;
	}

	//check if a square contains a value besides 0
	private boolean isOccupied(Sudoku s, int row, int col) 
	{
		if(s.sudokuMatix[row][col] == 0)
		{
			return false;
		}
		return true;
	}

	//check for duplicate values in row
	private boolean rowHasDuplicates(Sudoku s, int row, int val)
	{
		int[] values = new int[10];
		
		int stop = row + 1;
		
		for(int i = row; i < stop; i++)
		{
			//for each number in row
			for(int j = 0; j < s.N * s.N; j++)
			{
				int squareVal = s.sudokuMatix[i][j];
				
				//for each slot in values array
				for(int k = 0; k < values.length; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
				
			}
		}
		return false;
	}

	//check for duplicates in column
	private boolean colHasDuplicates(Sudoku s, int col, int val)
	{
		int[] values = new int[9];
		
		int stop = col + 1;
		
		for(int i = col; i < stop; i++)
		{
			//for each number in col
			for(int j = 0; j < s.N * s.N; j++)
			{
				int squareVal = s.sudokuMatix[j][i];
				
				//for each slot in values array
				for(int k = 0; k < 9; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
				
			}
		}
		return false;
	}
	
	//check to see if the board is totally filled in
	private boolean sudokuFilled(Sudoku s, int row, int col)
	{
		int size = s.N * s.N;
		
		for(int i = row; i < size; i++)
		{
			for(int j = col; j < size; j++)
			{
				if(s.sudokuMatix[i][j] == 0)
				{
					return false;
				}
			}
		}

		return true;
	}
	

	/*public static void main(String[] args)
	{
		
		
		
		//test Sudoku input with one invalid input
		int sudoku1[][] = {  { 8, 8, 0, 0, 2, 0, 0, 5, 9 },
				 { 9, 7, 0, 4, 0, 0, 1, 2, 0 },
				 { 4, 5, 0, 6, 9, 0, 0, 3, 0 },
				 { 0, 8, 6, 0, 7, 2, 9, 1, 0 },
				 { 1, 4, 7, 9, 0, 0, 0, 8, 5 },
				 { 0, 0, 0, 8, 1, 0, 0, 7, 0 },
				 { 7, 1, 4, 2, 0, 9, 5, 0, 8 },
				 { 5, 0, 9, 1, 8, 6, 7, 4, 0 },
				 { 6, 2, 8, 7, 4, 5, 3, 9, 1 }}
		;
		
		int sudoku2[][] = {  { 8, 0, 0, 0, 2, 0, 0, 5, 9 },
				 { 9, 7, 0, 4, 0, 0, 1, 2, 0 },
				 { 4, 5, 0, 6, 9, 0, 0, 3, 0 },
				 { 0, 8, 6, 0, 7, 2, 9, 1, 0 },
				 { 1, 4, 7, 9, 0, 0, 0, 8, 5 },
				 { 0, 0, 0, 8, 1, 0, 0, 7, 0 },
				 { 7, 1, 4, 2, 0, 9, 5, 0, 8 },
				 { 5, 0, 9, 1, 8, 6, 7, 4, 0 },
				 { 6, 2, 8, 7, 4, 5, 3, 9, 1 }}
		;
		
		
		SudokuSolver ss = new SudokuSolver();
		
		
		//sudoku1 has wrong input
		System.out.println(ss.solveSudoku(sudoku1));
		
		//sudoku2 as has correct input
		System.out.println(ss.solveSudoku(sudoku2));
		
		
		
	}*/

}
