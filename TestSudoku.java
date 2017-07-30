package org.jagdeep.sudoku;
import org.jagdeep.sudoku.SudokuGenerator;
import org.jagdeep.sudoku.SudokuSolver;

/**
 * 
 * @author jAgdeep Mohan
 * Test class to generate sudoku and solve it
 */
public class TestSudoku{

	
	public static void main(String[] args) {
		
		//test run one sudoku generator with easy Difficulty
			SudokuGenerator sg = new SudokuGenerator();
			int generatedSudoku[][] = sg.generateSudoku(29);
			//printSudokuArrayInput(generatedSudoku);
			
			Sudoku sudoku = new Sudoku(generatedSudoku);
			System.out.println("Input Sudoku :");
			sudoku.printSudoku(sudoku.getSudokuMatrix());
			SudokuSolver solveSudoku = new SudokuSolver();
			System.out.println(solveSudoku.solveSudoku(sudoku.getSudokuMatrix()));
	}
	
	/**
	 * Utility Function to generate sudoku as input, in case we want to test our own sudoku by chaning 1 or 2 position
	 * @param sudoku
	 */
	public static void printSudokuArrayInput(int [][] sudoku) {
		System.out.print("Print Sudoku as 2D array input { ");
		for(int i=0; i<sudoku.length; i++) {
			System.out.print(" { ");
			for(int j=0 ; j<sudoku[i].length; j++) {
				System.out.print(sudoku[i][j] );
				if (j < sudoku[i].length-1)
					System.out.print(", ");
			}
			System.out.print(" }");
			if(i < sudoku.length -1)
				System.out.println(",");
		}
		System.out.println("}");
	}
	
	/**
	 * prints sudoku in  sudoku format 
	 * @param sudoku
	 */
	
	public static void printSudoku(int sudoku[][])
	{
		
		System.out.println("+++++++++++++++++");
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++) {
				System.out.print(sudoku[i][j]);
				if((j+1) % 3 == 0) // to print separator for each grid vertically
					System.out.print(" | ");
			}
			
			System.out.println();
			if((i+1) % 3 ==0){ // to print separator for each grid horizontally
				System.out.println("+++++++++++++++++");
			}
			
			
		}
		System.out.println();
	}

}
