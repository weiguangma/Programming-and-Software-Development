import java.util.Scanner;

/**
 * The abstraction and encapsulation of a NimPlayer.
 * 
 * @author Weiguang MA 
 * StudentID: 864752 
 * April 28th, 2018
 */
public abstract class NimPlayer {

	private String userName; // Player's user name
	private String familyName; // Player's family name
	private String givenName; // Player's given name
	private int gamePlay; // Number of player's game times
	private int gameWin; // Number of player's win times
	private String type; // Indicate a human player or an AI player

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public int getGamePlay() {
		return gamePlay;
	}

	public void setGamePlay(int gamePlay) {
		this.gamePlay = gamePlay;
	}

	public int getGameWin() {
		return gameWin;
	}

	public void setGameWin(int gameWin) {
		this.gameWin = gameWin;
	}
	
	/**
	 * The number of stones removed by the player
	 */
	public void removeStone(Scanner scanner,NimGame nimGame) {
		
	}

}