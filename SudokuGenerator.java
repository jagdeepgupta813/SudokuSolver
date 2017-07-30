package org.jagdeep.sudoku;

import java.util.*;
/**
 * 
 * @author jAgdeep Mohan
 * this class generates sudoku and places 0 in some cells based on the difficulty level defined
 *
 */
public class SudokuGenerator
{
	public static final int SUDOKU_WIDTH = 9;
	public static final int SUDOKU_HEIGHT = 9;
	private int[][] sudokuMatrix;


	public SudokuGenerator() {
		sudokuMatrix = new int[SUDOKU_WIDTH][SUDOKU_HEIGHT];
	}

	/*
	 This method generates a random 2 dimensional sudoku, difficulty defines how many squares to be left blank out of 81 

	 */
	public int[][] generateSudoku(int difficulty)
	{
		sudokuMatrix = new int[SUDOKU_WIDTH][SUDOKU_HEIGHT];
		nextCell(0,0);			// call nextCell to start placing value with position 0
		insertZeros(difficulty);
					
		return sudokuMatrix;

	}

	/**
	 *  Recursive method that attempts to place every number in a cell.
	 returns true if the board completed legally, false if this cell
	 has no valid solutions
	 @param x : cell x position where value needs to be placed
	 @param y : cell Y position
	 	.
	 */
	private boolean nextCell(int x, int y)
	{
		int nextX = x;
		int nextY = y;
		int[] sudokuValuesSuperSet = {1,2,3,4,5,6,7,8,9};
		Random r = new Random();
		int tmp = 0;
		int current = 0;
		int valueLength = sudokuValuesSuperSet.length;

		for(int i=valueLength-1;i>0;i--)
		{
			current = r.nextInt(i);
			tmp = sudokuValuesSuperSet[current];
			sudokuValuesSuperSet[current] = sudokuValuesSuperSet[i];
			sudokuValuesSuperSet[i] = tmp;
		}

		for(int i=0;i<sudokuValuesSuperSet.length;i++)
		{
			if(isValuePlacementValid(x, y, sudokuValuesSuperSet[i]))
			{
				sudokuMatrix[x][y] = sudokuValuesSuperSet[i];
				if(x == 8)
				{
					if(y == 8)
						return true;//it means we have completed all the cells
					else
					{
						nextX = 0;
						nextY = y + 1;
					}
				}
				else
				{
					nextX = x + 1;
				}
				if(nextCell(nextX, nextY))
					return true;
			}
		}
		sudokuMatrix[x][y] = 0;
		return false;
	}

	/*
	 * Check if the value can be inserted at co ordinate
	 */
	private boolean isValuePlacementValid(int row, int column, int value) {
		for(int i=0;i<9;i++) {
			if(value == sudokuMatrix[row][i])
				return false;
		}
		for(int i=0;i<9;i++) {
			if(value == sudokuMatrix[i][column])
				return false;
		}
		int cornerX = 0;
		int cornerY = 0;
		if(row > 2)
			if(row > 5)
				cornerX = 6;
			else
				cornerX = 3;
		if(column > 2)
			if(column > 5)
				cornerY = 6;
			else
				cornerY = 3;
		for(int i=cornerX;i<10 && i<cornerX+3;i++)      //check if the value is not present in the same grid already
			for(int j=cornerY;j<10 && j<cornerY+3;j++)
				if(value == sudokuMatrix[i][j])
					return false;
		return true;
	}

	/**
	 *In a given matrix, replace a asked amount of cells with 0s (0 means a blank cell)
	 *@param zerosToInsert How many 0s to put in the board.
	 */
	private void insertZeros(int zerosToInsert)
	{
		/* Difficulty is defined(read from one wikipedia article):
			Easy: 32+ clues (49 or fewer zeros)
			Medium: 27-31 clues (50-54 zeros)
			Hard: 26 or fewer clues (54+ zeros)
		 */
		double remainingSquares = 81;
		double remainingZeros = (double)zerosToInsert;

		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
			{
				double zeroChance = remainingZeros/remainingSquares;
				if(Math.random() <= zeroChance)
				{
					sudokuMatrix[i][j] = 0;
					remainingZeros--;
				}
				remainingSquares--;
			}
	}
}
