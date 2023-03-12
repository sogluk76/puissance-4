package connectfour.model;

public class ImplGrid implements Grid {

	private final Tokens[][] grid;
	private int rowOfLastPutToken;
	
	public ImplGrid(int x, int y) {
		if (x < 0 || y < 0 ) {
			throw new IllegalArgumentException("Le nombre de colonne ou/et de ligne est inferieur à 0");
		}
		grid = new Tokens[x][y];
	}
	
	@Override
	public Tokens getToken(int x, int y) {
		if (x < 0 || x > grid.length-1 || y < 0 || y > grid[x].length-1) {
			throw new IllegalArgumentException("x et/ou y ne se situent pas dans le plateau : x = " + x + " y = " + y);
		}
		Tokens token = grid[x][y];
		return token;
	}

	@Override
	public void putToken(Tokens token, int x) throws ConnectException{
		if (token == null) {
			throw new IllegalArgumentException("La valeur du jeton vaux :'null'");
		}
		if (x < 0 || x > grid.length-1) {
			throw new ConnectException("La colonne " + x + " est en dehors du plateau qui est de 0 a " + grid.length); 
		}
		int y = 0;
		while(y < grid[x].length && grid[x][y] != null) {
			y++;
		}
		if (y > grid[x].length-1) {
			throw new ConnectException("La colonne " + x + "est deja pleine");
		}
		grid[x][y] = token;
		rowOfLastPutToken = y;
		if (y < 0 || y > grid[x].length) {
			
		}

	}

	@Override
	public Integer getRowOfLastPutToken() {
		return rowOfLastPutToken;
	}

	@Override
	public void init() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = null;
			}
		}
		rowOfLastPutToken = 0;
				
	}

}
