package tic_tac_toe;

public class TicTacToe {
	
	private int[][][] ticTacToe;
	private int totalCells;
	private int numPieces;
	private int[][] pieces;
	private int gamerNow = 1;
	private static int[] score = new int[2];
	
	public TicTacToe(int level) {
		ticTacToe = new int[level][level][2];
		totalCells = (int) Math.pow(level, 2);
		numPieces = (int) Math.ceil(this.totalCells/2)+1;
		pieces = new int[2][this.numPieces];
		init();
	}
	
	public void init() {
		for (int i=0; i < ticTacToe.length; i++) {
			for (int j=0; j < ticTacToe[0].length; j++) {
				for (int k=0; k < ticTacToe[0][0].length; k++) {
					ticTacToe[i][j][k] = 0;
				}
			}
		}
		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[0].length; j++) {
				pieces[i][j] = j+1;
			}
		}
	}
	
	public int[][][] getTicTacToe() {
		return ticTacToe;
	}
	
	public int getTotalCells() {
		return totalCells;
	}
	
	public int getNumPieces() {
		return numPieces;
	}
	
	public int[][] getPieces() {
		return pieces;
	}
	
	public void setPieces(int i, int j, int val) {
		pieces[i][j] = val;
	}
	
	public void zeroPieces() {
		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[0].length; j++) {
				pieces[i][j] = 0;
			}
		}
	}
	
	public int getGamerNow() {
		return gamerNow;
	}
	
	public void turnGamer() {
		gamerNow = gamerNow==1?2:1;
	}
	
	public int[] getScore() {
		return score;
	}
	
	public void plusScore() {
		score[gamerNow-1] += 1;
	}
	
	public boolean win() {
		int len = (int) Math.pow(totalCells, 0.5);
		for (int i  = 0; i < len; i++) {
			int checkRow = 1;
			int checkCol = 1;
			int checkLeftDia = 1;
			int checkRightDia = 1;
			for (int j = 0; j < len-1; j++) {
				if (ticTacToe[i][j][0] == ticTacToe[i][j+1][0] && ticTacToe[i][j+1][0] == gamerNow) {
					checkRow += 1;
				}
				if (ticTacToe[j][i][0] == ticTacToe[j+1][i][0] && ticTacToe[j+1][i][0] == gamerNow) {
					checkCol += 1;
				}
				if (ticTacToe[j][j][0] == ticTacToe[j+1][j+1][0] && ticTacToe[j+1][j+1][0] == gamerNow) {
					checkLeftDia += 1;
				}
				if (ticTacToe[j][len-1-j][0] == ticTacToe[j+1][len-2-j][0] && ticTacToe[j+1][len-2-j][0] == gamerNow) {
					checkRightDia += 1;
				}
			}
			if (checkRow == len || checkCol == len || checkLeftDia == len || checkRightDia == len) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPiecesEmpty() {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[0].length; j++) {
				if (pieces[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean occupy(int x, int y, int piece) {
		if (gamerNow != ticTacToe[x][y][0] && piece > ticTacToe[x][y][1]) {
			ticTacToe[x][y][0] = gamerNow;
			ticTacToe[x][y][1] = piece;
			pieces[gamerNow-1][piece-1] = 0;
			return true;
		}
		else {
			return false;
		}
	}
}
