package tic_tac_toe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	final int level = 3;
	int[] piece = new int[2];
    TicTacToe t;
    JLabel[] gamerScore;
    JButton[][] ticTacToeBtn;
    JButton[][] piecesBtn;
    int width = 120 * level + 120;
    int height = 120 * level + 60;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() {
		t = new TicTacToe(level);
		gamerScore = new JLabel[2];
		ticTacToeBtn = new JButton[level][level];
		piecesBtn = new JButton[2][t.getNumPieces()];

		setTitle("Tic Tac Toe Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((screenWidth-width)/2, (screenHeight-height)/2, width, height);
		
		JPanel score = new JPanel();
		getContentPane().add(score, BorderLayout.NORTH);
		score.setPreferredSize(new Dimension(width, 60));
		score.setLayout(new GridLayout(1, 2));
		creategamerScore(score);
		
		JPanel battle = new JPanel();
		getContentPane().add(battle, BorderLayout.CENTER);
		battle.setPreferredSize(new Dimension(height - 60, height - 60));
		battle.setLayout(new GridLayout(level, level));
		createTicTacToeBtn(battle);
		
		JPanel gamer1Pieces = new JPanel();
		getContentPane().add(gamer1Pieces, BorderLayout.WEST);
		gamer1Pieces.setPreferredSize(new Dimension((width - (height - 60)) / 2, height - 60));
		gamer1Pieces.setLayout(new GridLayout(t.getNumPieces(), 1));
		createPiecesBtn(gamer1Pieces, 0, Color.red);
		
		JPanel gamer2Pieces = new JPanel();
		getContentPane().add(gamer2Pieces, BorderLayout.EAST);
		gamer2Pieces.setPreferredSize(new Dimension((width - (height - 60)) / 2, height - 60));
		gamer2Pieces.setLayout(new GridLayout(t.getNumPieces(), 1));
		createPiecesBtn(gamer2Pieces, 1, Color.blue);
		
		updatePiecesBtn();
	}
	
	// create object
	public void creategamerScore(JPanel score) {
		for (int i = 0; i < gamerScore.length; i++) {
			gamerScore[i] = new JLabel("Gamer " + String.valueOf(i+1) + ": 0");
			gamerScore[i].setFont(new Font("Arial", Font.BOLD, 20));
			gamerScore[i].setHorizontalAlignment(SwingConstants.CENTER);
			score.add(gamerScore[i]);
		}
    }
	
	public void createTicTacToeBtn(JPanel jpanel) {
        for (int i = 0; i < ticTacToeBtn.length; i++) {
        	for (int j = 0; j < ticTacToeBtn[0].length; j++) {
            	ticTacToeBtn[i][j] = new JButton("0");
				ticTacToeBtn[i][j].setFont(new Font("Arial", Font.BOLD, 60));
				ticTacToeBtn[i][j].setForeground(Color.white);
            	ticTacToeBtn[i][j].addActionListener(new ticTacToeListener());
                jpanel.add(ticTacToeBtn[i][j]);
        	}
        }
    }
	
	public void createPiecesBtn(JPanel jpanel, int gamer, Color color) {
        for (int i = 0; i < piecesBtn[0].length; i++) {
        	piecesBtn[gamer][i] = new JButton(String.valueOf(t.getPieces()[gamer][i]));
        	piecesBtn[gamer][i].setFont(new Font("Arial", Font.BOLD, 22));
        	piecesBtn[gamer][i].setForeground(color);
        	piecesBtn[gamer][i].addActionListener(new pieceListener());
            jpanel.add(piecesBtn[gamer][i]);
        }
    }
	
	// update buttons state
	public void updatePiecesBtn() {
		for (int i = 0; i < piecesBtn.length; i++) {
			for (int j = 0; j < piecesBtn[0].length; j++) {
				piecesBtn[i][j].setEnabled(t.getPieces()[i][j]>0 && t.getGamerNow()==i+1?true:false);
			}
		}
	}
	
	public void updateTicTacToeBtn() {
		for (int i = 0; i < ticTacToeBtn.length; i++) {
			for (int j = 0; j < ticTacToeBtn[0].length; j++) {
				Color color = t.getTicTacToe()[i][j][0]==1?Color.red:t.getTicTacToe()[i][j][0]==2?Color.blue:Color.white;
				ticTacToeBtn[i][j].setText(String.valueOf(t.getTicTacToe()[i][j][1]));
				ticTacToeBtn[i][j].setFont(new Font("Arial", Font.BOLD, 60));
				ticTacToeBtn[i][j].setForeground(color);
			}
		}
	}
	
	// show game over
	public void showWin() {
		int dialog = JOptionPane.showConfirmDialog(null, "The winner is  Gamer " + String.valueOf(t.getGamerNow()) + ".\n Do you want to start the next game? ", 
				"Result", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (dialog == JOptionPane.YES_OPTION) {
			t.init();
		}
		else if (dialog == JOptionPane.NO_OPTION) {
			for (int i = 0; i < piecesBtn.length; i++) {
				for (int j = 0; j < piecesBtn[0].length; j++) {
					piecesBtn[i][j].setEnabled(false);
				}
			}
		}
		updateTicTacToeBtn();
	}
	
	public void showTie() {
		int dialog = JOptionPane.showConfirmDialog(null, "The game ended in a tie.\n Do you want to start the next game? ", 
				"Result", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (dialog == JOptionPane.YES_OPTION) {
			t.init();
		}
		else if (dialog == JOptionPane.NO_OPTION) {
			for (int i = 0; i < piecesBtn.length; i++) {
				for (int j = 0; j < piecesBtn[0].length; j++) {
					piecesBtn[i][j].setEnabled(false);
				}
			}
		}
		updateTicTacToeBtn();
	}
	
	// event listener
	private class pieceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			for (int i = 0; i < piecesBtn.length; i++) {
				for (int j = 0; j < piecesBtn[0].length; j++) {
					if (evt.getSource() == piecesBtn[i][j] && piecesBtn[i][j].isEnabled()) {
						if (piece[0] != 0) {
							t.setPieces(piece[0]-1, piece[1]-1, piece[1]);
						}
						piece[0] = i+1;
						piece[1] = t.getPieces()[i][j];
						t.setPieces(i, j, 0);
					}
					updatePiecesBtn();
				}
			}
		}
	}
	
	private class ticTacToeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			for (int i = 0; i < ticTacToeBtn.length; i++) {
				for (int j = 0; j < ticTacToeBtn[0].length; j++) {
					if (evt.getSource() == ticTacToeBtn[i][j] && piece[0] != 0) {
						if (t.occupy(i, j, piece[1])) {
							updateTicTacToeBtn();
							if (t.win()) {
								t.plusScore();
								t.zeroPieces();
								for (int k = 0; k < gamerScore.length; k++) {
									gamerScore[k].setText("Gamer " + String.valueOf(k+1) + ": " + String.valueOf(t.getScore()[k]));
								}
								showWin();
							}
							else if (t.isPiecesEmpty()) {
								t.zeroPieces();
								for (int k = 0; k < gamerScore.length; k++) {
									gamerScore[k].setText("Gamer " + String.valueOf(k+1) + ": " + String.valueOf(t.getScore()[k]));
								}
								showTie();
							}
							t.turnGamer();
						}
						else {
							t.setPieces(piece[0]-1, piece[1]-1, piece[1]);
						}
						piece[0] = 0;
						piece[1] = 0;
						updatePiecesBtn();
					}
				}
			}
		}
	}
}
