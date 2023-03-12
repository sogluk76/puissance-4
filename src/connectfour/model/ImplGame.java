package connectfour.model;

public class ImplGame implements Game {

	private final Grid grid;
	private Tokens currentPlayer;
	private final Tokens[] TOKEN_VALUES = Tokens.values();
	private boolean over;
	private Tokens winner;
	
	
	public ImplGame() {
		grid = new ImplGrid(COLUMNS, ROWS);
		init();
	}
	
	
	@Override
	public void init() {
		grid.init();
		over = false;
		int n = (int)(Math.random() * TOKEN_VALUES.length);
		currentPlayer = TOKEN_VALUES[n];
		
	}
	
	
	@Override
	public Tokens getToken(int x, int y) {
		return grid.getToken(x, y);
	}
	
	
	@Override
	public Tokens getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	@Override
	public boolean isOver() {
		return over;
	}
	
	
	@Override
	public Tokens getWinner() {
		return winner;
	}
	
	
	@Override
	public void putToken(int column) throws ConnectException {
		grid.putToken(currentPlayer,column);
		over = calculateOver(column);
		if (!isOver()) {
			currentPlayer = getNextPlayer();
		}
	}
	
	
	private Tokens getNextPlayer() {
		int r =(currentPlayer.ordinal() + 1)%TOKEN_VALUES.length;
		return TOKEN_VALUES[r];
	}
	

	private boolean calculateOver(int x) {
		//win
		if (inspectSouth(x,grid.getRowOfLastPutToken()) >= REQUIRED_TOKENS) {
			winner = currentPlayer;
			return true;
		}
		if (inspectWestEast(x,grid.getRowOfLastPutToken()) >= REQUIRED_TOKENS) {
			winner = currentPlayer;
			return true;
		}
		if (inspectNWSE(x,grid.getRowOfLastPutToken()) >= REQUIRED_TOKENS) {
			winner = currentPlayer;
			return true;
		}
		if (inspectNESW(x,grid.getRowOfLastPutToken()) >= REQUIRED_TOKENS) {
			winner = currentPlayer;
			return true;
		}
		//match null
		for (x = 0 ; x < COLUMNS ; x++) {
			if (getToken(x,ROWS-1)==null) {
				return false;
			}
		}
		return true;
	}
	
	
	
	//score colonne
	private int inspectSouth(int x , int y) {
		int foundInLine = 0;
		for (int i = 1; y - i >= 0 && getToken(x , y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;	
	}
	
	//score ligne
	private int inspectWestEast(int x , int y) {
		int foundInLine = 0;
		for (int i = 1; x - i >= 0 && getToken(x - i, y ) == currentPlayer; i++) {
			foundInLine++;
		}
		for (int i = 1; x + i < COLUMNS && getToken(x + i, y ) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
		
	
	//score diagonale hautG - basD
	private int inspectNWSE(int x , int y) {
		int foundInLine = 0;
		for (int i = 1; x - i >= 0 && y + i < ROWS && getToken(x - i, y + i) == currentPlayer; i++) {
			foundInLine++;
		}
		for (int i = 1; x + i < COLUMNS && y - i >= 0 && getToken(x + i, y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
	
	//score diagonale hautD - basG
	private int inspectNESW(int x , int y) {
		int foundInLine = 0;
		for (int i = 1; x + i < COLUMNS && y + i < ROWS && getToken(x + i, y + i) == currentPlayer; i++) {
			foundInLine++;
		}
		for (int i = 1; x - i >= 0 && y - i >= 0 && getToken(x - i, y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
}
