import java.util.Scanner;

/**
 * The abstraction and encapsulation of a NimGame.
 * 
 * @author Weiguang MA 
 * StudentID: 864752 
 * April 28th, 2018
 */
public class NimGame {

	private int stones; // Current stone count
	private int bound; // Stone removal upper bound
	private NimPlayer player1; // Create the first instance of NimPlayer
	private NimPlayer player2; // Create the second instance of NimPlayer

	/**
	 * Determine whether the player is a human player or an AI player
	 */
	public void judgePlayer(String userName1, String userName2,Nimsys nimsys) {
		for(int i=0;i<nimsys.getHumanUserName().length;i++) {
			if(userName1.equals(nimsys.getHumanUserName()[i])) {
				player1 = new NimHumanPlayer(); 
			}
		}
		
		for(int i=0;i<nimsys.getHumanUserName().length;i++) {
			if(userName2.equals(nimsys.getHumanUserName()[i])) {
				player2 = new NimHumanPlayer(); 
			}
		}
		
		for(int i=0;i<nimsys.getAIUserName().length;i++) {
			if(userName1.equals(nimsys.getAIUserName()[i])) {
				player1 = new NimAIPlayer(); 
			}
		}
		
		for(int i=0;i<nimsys.getAIUserName().length;i++) {
			if(userName2.equals(nimsys.getAIUserName()[i])) {
				player2 = new NimAIPlayer(); 
			}
		}
		
	}
	
	/**
	 * Game initialisation
	 */
	public void gameInit(String stones, String bounds) {
		System.out.println();
		System.out.print("Initial stone count: ");
		this.stones = Integer.parseInt(stones);
		System.out.println(this.stones);
		System.out.print("Maximum stone removal: ");
		this.bound = Integer.parseInt(bounds);
		System.out.println(this.bound);
		System.out.print("Player 1: ");
		System.out.println(player1.getGivenName() + " " + player1.getFamilyName());
		System.out.print("Player 2: ");
		System.out.println(player2.getGivenName() + " " + player2.getFamilyName());
		System.out.println();
	}

	/**
	 * Player initialisation
	 */
	public void playerInit(String player1UserName, String player2UserName, Nimsys nimsys) {
		judgePlayer(player1UserName,player2UserName,nimsys);
		for (int i = 0; i < nimsys.getPlayerInfo().length; i++) {
			if (nimsys.getPlayerInfo()[i].getUserName().equals(player1UserName)) {
				player1 = nimsys.getPlayerInfo()[i];
				break;
			}
		}
		for (int i = 0; i < nimsys.getPlayerInfo().length; i++) {
			if (nimsys.getPlayerInfo()[i].getUserName().equals(player2UserName)) {
				player2 = nimsys.getPlayerInfo()[i];
				break;
			}
		}
	}

	/**
	 * Game process
	 */
	public void gameProcess(Scanner scanner,NimGame nimGame) {
		String winner = ""; // Initialise the winner
		player1.setGamePlay(player1.getGamePlay() + 1);
		player2.setGamePlay(player2.getGamePlay() + 1);

		// Handle the illegal input
		while (stones != 0) {
			displayStones();
			System.out.println(player1.getGivenName() + "'s turn - remove how many?");
			
			player1.removeStone(scanner,nimGame); // Player1 remove stones
			
			System.out.println();

			// Condition of player 2 win
			if (stones == 0) {
				winner = player2.getGivenName() + " " + player2.getFamilyName();
				player2.setGameWin(player2.getGameWin() + 1);
				break;
			}

			displayStones();
			System.out.println(player2.getGivenName() + "'s turn - remove how many?");

			player2.removeStone(scanner,nimGame);

			// Condition of player 1 win
			if (stones == 0) {
				winner = player1.getGivenName() + " " + player1.getFamilyName();
				player1.setGameWin(player1.getGameWin() + 1);
				break;
			}
			System.out.println();
		}

		System.out.println("Game Over");
		System.out.println(winner + " wins!"); // Display the winner
		System.out.println();
		if (!(player1 instanceof NimAIPlayer && player2 instanceof NimAIPlayer)) {
			scanner.nextLine();
		}
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public int getBound() {
		return bound;
	}

	public void setBound(int bound) {
		this.bound = bound;
	}

	/**
	 * display the stones
	 */
	public void displayStones() {
		System.out.print(stones + " stones left:");
		for (int i = 0; i < stones; i++) {
			System.out.print(" *");
		}
		System.out.println();
	}

}
