package connectfour.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import connectfour.model.ConnectException;
import connectfour.model.Game;
import connectfour.model.Tokens;

public class ConsoleGameView implements GameView {
	

	// ATTRIBUTES
	
	private final Game game;
	
	private final BufferedReader reader;
	private String userInput;
	private String errorMessage;
	private boolean exitRequest;
	private final static String KEYWORD_EXIT = "e";
	private final static String KEYWORD_RESTART = "r";
	
	// CONSTRUCTOR
	
	public ConsoleGameView(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game ne peut être null");
		}
		this.game = game;
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// METHODS

	@Override
	public void play() {
		clearConsole();
		System.out.println("Quitter : " + KEYWORD_EXIT + "   |   Recommencer : " + KEYWORD_RESTART);
		displayGrid();
		try {
			do {
				errorMessage = null;
				displayGameState();
				userInput = reader.readLine();
				switch(userInput) {
					case KEYWORD_EXIT:
						System.out.println("Le jeu est terminee");
						exitRequest = true;
						break;
					case KEYWORD_RESTART:
						game.init();
						break;
					default:
						if (!game.isOver()) {
							try {
								int column = Integer.parseInt(userInput);
								game.putToken(column);
								clearConsole();
							} catch (NumberFormatException e) {
								errorMessage = "Je n'ai pas compris cette reponse...";
							}
							catch (ConnectException e) {
								errorMessage = e.getMessage();
							}
							
						} else {
							errorMessage = "Je n'ai pas compris cette reponse...";
						}			
				}
				if (errorMessage != null) {
					System.err.println(errorMessage);
				}
			} while (errorMessage != null);
		} catch (Exception e) {
			e.printStackTrace();
			exitRequest = true;
		}
		if (!exitRequest) {
			play();
		}
	}
	
	// TOOLS
	
	private void displayGameState() {
		if (game.isOver()) {
			Tokens winner = game.getWinner();
			if (winner == null) {
				System.out.println("La partie est fini sur un match nul ...");
			} else {
				System.out.println(winner + " a gagner!");
			}
		} else {
			System.out.print("C'est au tour de [" + game.getCurrentPlayer() + "] ! [0-" + (Game.COLUMNS - 1) + "] : ");
		}
	}
	
	private void displayGrid() {
		StringBuffer output = new StringBuffer();
		Tokens token;
		for (int x = 0; x < Game.COLUMNS; x++) {
			output.append("   " + x + "  ");
		}
		output.append('\n');
		for (int y = Game.ROWS - 1; y >= 0; y--) {
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|     ");
			}
			output.append("|\n");
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|  ");
				token = game.getToken(x, y);
				if (token == null) {
					output.append(' ');
				} else {
					output.append(token);
				}
				output.append("  ");
			}
			output.append("|\n");
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|_____");
			}
			output.append("|\n");
		}
		System.out.println(output.toString());
	}
	
	private void clearConsole() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

}
