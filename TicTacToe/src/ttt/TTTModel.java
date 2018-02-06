package ttt;

public class TTTModel {
	
	private char[][] squares, states;
	
	public TTTModel(){
		
		squares = new char[][]{
			{'.','.','.'},
			{'.','.','.'},
			{'.','.','.'}
		};
	
		states = new char[][]{
			{'.','.','.'},
			{'.','.','.'},
			{'.','.','.'}
		};
		
	}
	
	@Override
	public String toString() {
		
		String result = "";
		
		for (int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares.length; j++){
				result += "[" + states[i][j] + "]";
			}
			result += "\n";
		}
		
		return result;
	}
	
	public void playerSetSquare(int i, int j){
		
		if (squares[i][j] == '.'){
			squares[i][j] = 'O';
			states[i][j] = 'O';
		}
	}
	
	public void computerSetSquare(int i, int j){
		
		if (squares[i][j] == '.'){
			squares[i][j] = 'X';
			states[i][j] = 'X';
		}
	}

	public char getSquare(int i, int j){
		return states[i][j];
	}
	
	public void playBestMoveNaive(){
		int score;
		int bestScore = -2;
		int bestRow = -1;
		int bestCol = -1;
		
		for (int row = 0; row < states.length; row++){
			for (int col = 0; col < states[0].length; col++){
				if (squares[row][col] == '.'){
					
					score = score();
					if (score > bestScore){
						bestScore = score;
						bestRow = row;
						bestCol = col;
					}
					squares[row][col] = '.';
				}
			}
		}
		squares[bestRow][bestCol] = 'X';
		states[bestRow][bestCol] = 'X';
	}

	public int score() {
		
		int lineScore = 0;
		
		for (int i = 0; i < states.length; i++){
			
			// Check every row for a win/lose
			lineScore = scoreLine(squares[i][0], squares[i][1], squares[i][2]);
			
			if (lineScore != 0)
				return lineScore;
			
			//Check every column for a win/lose
			lineScore = scoreLine(squares[0][i], squares[1][i], squares[2][i]);
			
			if (lineScore != 0)
				return lineScore;
		}
		//lead diagonal
		lineScore = scoreLine(squares[0][0], squares[1][1], squares[2][2]);
		
		if (lineScore != 0)
			return lineScore;
		//trail diagonal
		lineScore = scoreLine(squares[0][2], squares[1][1], squares[2][0]);
		
		if (lineScore != 0)
			return lineScore;
		
		return lineScore;
	}
	
	private int scoreLine(char a, char b, char c){
		if (a == 'X' && b == 'X' && c == 'X')
			return 1;
		if (a == 'O' && b == 'O' && c == 'O')
			return -1;
		return 0;
	}
	
	public void resetSquares(){
		
		for (int i = 0; i < states.length; i++){
			for (int j = 0; j < states.length; j++){
				squares[i][j] = states[i][j] = '.';
			}
		}
	}
	
	public int getDimension(){
		return squares.length;
	}
	
	public boolean gameOver(){
		// score == 0 mean tie
		if (score() != 0)
			return true;
		
		for (int i = 0; i < squares.length; i++){
			for (int j = 0; j < squares.length; j++){
				if (squares[i][j] == '.')
					return false;
			}
		}
		
		return true;
	}

	public void playBestMove() {
		int score;
		int bestScore = -2;
		int bestRow = -1;
		int bestCol = -1;
		
		for (int row = 0; row < states.length; row++){
			for (int col = 0; col < states[0].length; col++){
				if (squares[row][col] == '.'){
					squares[row][col] = 'X';
					score = minimaxFor0();
					if (score > bestScore){
						bestScore = score;
						bestRow = row;
						bestCol = col;
					}
					squares[row][col] = '.';
				}
			}
		}
		squares[bestRow][bestCol] = 'X';
		states[bestRow][bestCol] = 'X';
	}

	private int minimaxFor0() {
		int score = score();
		if(gameOver())
			return score;
		int bestScore = 2; //absurdly high value 
		
		for (int row = 0; row < states.length; row++) {
			for (int col  = 0; col  < states[0].length; col++) {
				if (squares[row][col] == '.'){
						squares[row][col] = 'O';
						score = minimaxForX();
						if(score < bestScore){
							bestScore = score;
						}
						squares[row][col] = '.';
				}
			}
		}
		return bestScore;
	}

	public int minimaxForX() {
		int score = score();
		if(gameOver())
			return score;
		int bestScore = 2; //absurdly high value 
		
		for (int row = 0; row < states.length; row++) {
			for (int col  = 0; col  < states[0].length; col++) {
				if (squares[row][col] == '.'){
						squares[row][col] = 'X';
						score = minimaxFor0();
						if(score > bestScore){
							bestScore = score;
						}
						squares[row][col] = '.';
				}
			}
		}
		
		
		return bestScore;
	}

	}

