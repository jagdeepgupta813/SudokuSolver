package org.jagdeep.sudoku;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author jAgdeep Mohan
 * This class holds the sudoku matrix and defines utility methods to print the sudoku and verify if that has any duplicate value
 * Also checks if the sudoku has been solved completely 
 *
 */
public class Sudoku 
{
	
	protected int[][] sudokuMatix; // it should be 9*9 board 
	protected final int N = 3; //size of sections on board(3x3) = 9x9 board
	
	
	Sudoku(int[][] matrix){
		this.sudokuMatix = matrix;
	}
	
	public Sudoku()
	{
		int size = N*N; //width and height of board
		
		this.sudokuMatix = new int[size][size];
		
		//initialize array slots to 0
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				this.setSquare(i, j, 0);
			}
		}
	}
	
	int [][] getSudokuMatrix(){
		
		return sudokuMatix;
	}
	
	//getter and setter to set and get individual cell value
	public void setSquare(int i, int j, int val)
	{
		this.sudokuMatix[i][j] = val;
	}
	
	//gets the value of a particular square on the board
	public int getSquare(int i, int j)
	{
		return this.sudokuMatix[i][j];
	}
	
	
	
	/**
	 * encode each Row, Column, and Grid into 9 character string 
	 * @param sudokuMatrix input Sudoku matrix
	 * @return
	 */
	private ArrayList<String> makeStringCollectionOfAllRowsColumnsAndGrid(int[][] sudokuMatrix)
	{
		ArrayList<String> collection = new ArrayList<String>();
		
		
		for(int i = 0; i < sudokuMatrix.length; i++)
		{
			//encode columns into string format
			String col ="";
			int z = i;
			for(int y = 0; y < sudokuMatrix[i].length; y++)
			{
				col += sudokuMatrix[y][z];	
			}
			
			collection.add(col);
			
			//encode rows into string format
			String row = "";
			
			//for each column in board
			for(int j = 0; j < sudokuMatrix[i].length; j++)
			{
				row += sudokuMatrix[i][j];
			}
			
			collection.add(row);
		}
		
		collection = makeSections(sudokuMatrix, collection);
		
		return collection;
	}
	
	/**
	 * encode each 3x3 Grid into 9 character string
	 * @param sudokuMatrix 
	 * @param collection collection of already generated encoded strings
	 * @return after encoding individual grids return the collections
	 */
	private ArrayList<String> makeSections(int[][] sudokuMatrix, ArrayList<String> collection)
	{
		int xOffset = 3;
		int yOffset = 3;
		int startX = 0;
		int startY = 0;
		int count = 0;
		
			//for each row of sections
			for(int i = 0; i < 3; i++)
			{
				//for each column of sections
				for(int f = 0; f < 3; f++)
				{
					String section = "";
					//for each row in section
					for(int x = startX; x < xOffset; x++)
					{
						//for each column in section
						for(int j = startY; j < yOffset; j++)
						{
							section += sudokuMatrix[x][j];
						}		
					}
					
					collection.add(section);
					startY += 3;
					yOffset += 3;
				}
				startX += 3;
				xOffset += 3;
				yOffset = 3;
				startY = 0;
			}
		return collection;
	}
	
	//check to see if a Sudoku board is solved
	public boolean isSolution(int[][] board)
	{
		
		ArrayList<String> collection = makeStringCollectionOfAllRowsColumnsAndGrid(board);
		//for each string, check individual character and if there is any 0 in any of the string, it means the sudoku is not completely solved
		for(int i = 0; i < collection.size(); i++)
		{
			
			if(collection.get(i).indexOf('0')>-1)
				return false;

			//if a row, col, or section has duplicates, return false
			if(hasDuplicates(collection.get(i)))
			{
				System.out.println(collection.get(i) + " has duplicate value");
				return false;
			}
		}
		return true;
	}
	
	//check row, column, or section encoded string for duplicates
	private boolean hasDuplicates(String value)
	{
		char[] values = {'0', '0', '0', '0', '0', '0', '0', '0', '0'};
		
		//for each character in string
		for(int i = 0; i < value.length(); i++)
		{
			//get each character in string
			char c = value.charAt(i);

			for(int j = 0; j < values.length; j++)
			{
				//System.out.println("VALUES: " + values[j]);
				if(values[j] == c)
				{
					return true;
				}
			}
			
			values[i] = c;
		}
		return false;
		
	}
	
	
	// prints sudoku matrix
	public static void printSudoku(int sudoku[][])
	{

		System.out.println("+++++++++++++++++");
		for(int row = 0 ; row < sudoku.length ; row++)
		{
			for(int column = 0; column< sudoku[row].length;column++) {
				System.out.print( sudoku[row][column] );
				if((column+1) % 3 == 0) // to print separator for each grid vertically
					System.out.print(" | ");
			}

			System.out.println();
			if((row+1) % 3 ==0){ // to print separator for each grid horizontally
				System.out.println("+++++++++++++++++");
			}


		}
		System.out.println();
	}
	
	
	
	/*public static void main(String[] args) 
	{
		//create an unsolved sudoku puzzle
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
		
		
		Sudoku s1 = new Sudoku(sudoku1);
		
		printSudoku(sudoku1);
		System.out.println(s1.isSolution(s1.sudokuMatix) + " : is not a solution");
		
		
	}*/
}
