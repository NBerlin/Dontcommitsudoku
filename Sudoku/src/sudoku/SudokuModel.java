package sudoku;

import java.util.ArrayList;

public class SudokuModel {
	int aosj = 0;
	private int[][] board;

	public SudokuModel(int[][] board) {
		this.board = board.clone();
	}

	public void set(int x, int y, int i) {
		if (0 <= x && x < 9 && 0 <= y && y < 9) {
			board[x][y] = i;
		} else {
			throw new IllegalArgumentException("Brädet får endast innehålla tal mellan 0-9");
		}
	}

	public int[][] getSudoku() {
		return board.clone();
	}

	public int get(int x, int y) {
		return board[x][y];
	}

	public void clear() {
		board = new int[9][9];
	}

	public boolean solve() {

		return solve(0, 0);
	}

	public boolean solve(int x, int y) {
		if(x == 0 && y == 9) 
			return true;
		
		if(get(x, y) == 0) {
			for(int i = 1; i < 10; i++) {
				set(x, y, i);
				if(isLegal(x, y) && solve((x + 1) % 9, y + x / 8)) {
					return true;
				}
			}
			
			set(x, y, 0);
			return false;
		} else {
			return solve((x + 1) % 9, y + x / 8);
		}
	}

	public boolean isLegal(int x, int y) {
		return legalLine(y, false) && legalLine(x, true) && legalSquare(x, y);
	}

	public boolean legalLine(int n, boolean yolo) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			int tempbox;

			if (yolo) {
				tempbox = get(n, i);
			} else {
				tempbox = get(i, n);
			}

			if (tempbox == 0)
				continue;

			if (temp.contains(tempbox)) {
				return false;
			}
			temp.add(tempbox);
		}
		return true;
	}

	public boolean legalSquare(int x, int y) {
		int rad = (y / 3) * 3;
		int col = (x / 3) * 3;

		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int j = col; j < col + 3; j++) {
			for (int i = rad; i < rad + 3; i++) {
				
				int tempbox = get(i, j);

				if (tempbox == 0)
					continue;

				if (temp.contains(tempbox)) {
					return false;
				}

				temp.add(tempbox);
			}
		}
		return true;
	}

	public void printOut() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(get(j, i) + "\t");
			}
			System.out.print("\n");
		}
		System.out.println(aosj);
	}

	public static void main(String[] args) {
		// Hard sudoku
		int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
		SudokuModel s = new SudokuModel(board);
		s.solve();
		s.printOut();

	}
}
