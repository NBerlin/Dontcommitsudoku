package sudoku;

import java.util.ArrayList;

public class SudokuModel {
	private int[][] board;

	public SudokuModel(int[][] board) {
		this.board = board.clone();
	}

	public void set(int x, int y, int i) {
		if (0 > x || x > 10 || 0 > y || y > 10) {
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
		if (get(x, y) == 0) {
			for (int i = 0; i < 9; i++) {
				set(x, y, i);
				if (solve(x, y)) {
					return true;
				}
			}
			set(x, y, 0);
			return false;

		} else {
			if (isLegal(x, y)) {
				if (y == 8 && x == 8) {
					return true;
				} else if (x == 8) {
					return solve(0, y + 1);
				} else {
					return solve(x + 1, y);
				}

			} else {
				return false;
			}
		}
	}

	public boolean isLegal(int x, int y) {
		if (legalLine(y, false) && legalLine(x, true) && legalSquare(x, y)) {
			return true;
		}
		return false;

	}

	public boolean legalLine(int n, boolean yolo) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			int tempbox;
			if (yolo == true) {
				tempbox = get(i, n);
			} else {
				tempbox = get(n, i);
			}
			if (temp.contains(tempbox)) {
				return false;
			}
			temp.add(tempbox);
		}
		return true;
	}

	public boolean legalSquare(int x, int y) {
		int rad = (y / 3) * 3;
		int inteRad = (x / 3) * 3;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = rad; i < rad + 3; i++) {
			for (int j = inteRad; j < inteRad + 3; j++) {
				int tempbox = get(i, j);
				if (tempbox != 0 && temp.contains(tempbox)) {
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
				System.out.print(get(i, j) + "\t");
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		// Hard sudoku
		int[][] board = { { 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 9, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
		SudokuModel s = new SudokuModel(board);
		 s.solve(); 
			s.printOut();
		
	}
}
