package ttt;

public class TTTController {
	
	private TTTModel model;
	private TTTView view;
	private boolean naive = false;
	
	public TTTController() {
		model = new TTTModel();
		view = new TTTView(this, model);
		view.setEnabled(false);
	}

	public void squareSelected(int row, int col) {
		
		int dim = model.getDimension();
		
		if (row >= dim || col >= dim)
			return;
		
		if (model.getSquare(row, col) != '.')
			return;
		
		if (model.gameOver()){
			playAgain();
			return;
		}
		
		// human moves
		model.playerSetSquare(row, col);
		if (model.gameOver()){
			playAgain();
			return;
		}
		// now computer moves
		
		if (naive){
			model.playBestMoveNaive();
		} else {
			model.playBestMove();
		}
		}

	private void playAgain() {
		view.setEnabled(false);
		
		String message = "";
		
		switch(model.score()){
		case 0:
			message = "It's a tie!";
			break;
		case 1:
			message = "You lose!";
			break;
		case -1:
			message = "You win";
			break;
		}
		
		message += "\nWould you like to play again?";
		
		if (view.message(message)){
			model.resetSquares();
			play();
		} else {
			view.dispose();
			System.exit(0);
		}
		
	}
	
	private void play(){
		int dim = model.getDimension();
		
		String message = "Would you like to play first?";
		
		if (!view.message(message)){
			model.computerSetSquare(dim/2, dim/2);
		} else {
			model.resetSquares();
		}
		
		view.setEnabled(true);
	}

	public static void main(String[] args){
		new TTTController().play();
	}
}