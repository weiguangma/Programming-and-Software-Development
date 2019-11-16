import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The abstraction and encapsulation of a Nimsys.
 * 
 * @author Weiguang MA StudentID: 864752 April 28th, 2018
 */
public class Nimsys {

	private int NUM = 100; // The maximum number of players

	// Create an object array to store each player's information
	private NimPlayer[] playerInfo = new NimPlayer[NUM];

	private int addPosition = 0; // The index of a new player
	private int position; // The index of an exist player

	private String sortOperator; // Decide to use which kind of display method

	// Mark to divide a String to a 3 length array
	private String divideOperator = "default";

	private File file = new File("players.dat");

	private String[] humanUserName = new String[NUM];
	private String[] AIUserName = new String[NUM];
	private int humanIndex = 0;
	private int AIIndex = 0;

	public NimPlayer[] getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(int index, NimPlayer player) {
		this.playerInfo[index] = player;
	}

	public String[] getHumanUserName() {
		return humanUserName;
	}

	public void setHumanUserName(int index, String humanUserName) {
		this.humanUserName[index] = humanUserName;
	}

	public String[] getAIUserName() {
		return AIUserName;
	}

	public void setAIUserName(int index, String aIUserName) {
		this.AIUserName[index] = aIUserName;
	}

	public void initPlayerInfo() {
		for (int i = 0; i < getPlayerInfo().length; i++) {
			setPlayerInfo(i, new NimHumanPlayer()); // Initial the object array
		}
	}

	/*
	 * Find a user whether he or she is existed
	 */
	public int findUser(String userName) {
		boolean flag = false; // Mark whether a player exists

		for (int i = 0; i < getPlayerInfo().length; i++) {
			if (userName.equals(getPlayerInfo()[i].getUserName())) {
				flag = true;
				position = i;
				break;
			}
		}
		if (flag)
			return position;
		else
			return -1;
	}

	/*
	 * Divide the content of command and save it into an array
	 */
	public String[] divideInfo(String origional, String operator) {
		int INFONUM; // Length of the array after dividing
		if (operator.equals("gamestart")) {
			INFONUM = 4;// Array needs to store stones, remove number, player1's user name and
						// player2's user name
		} else {
			// Array needs to store user name, given name and family name
			INFONUM = 3;
		}
		String[] info = new String[INFONUM];
		info = origional.split(","); // Split a string according to a comma
		if (info[INFONUM - 1].equals("null")) {
			throw new RuntimeException(); // Generate an exception
		}
		return info;
	}

	/*
	 * exchange two players' position in the array
	 */
	public void exchangeUser(int index1, int index2) {
		NimPlayer tempObject1 = new NimHumanPlayer();
		NimPlayer tempObject2 = new NimHumanPlayer();
		tempObject1 = getPlayerInfo()[index1];
		tempObject2 = getPlayerInfo()[index2];
		setPlayerInfo(index1, tempObject2);
		setPlayerInfo(index2, tempObject1);
	}

	/*
	 * Calculate a player's win rate
	 */
	public double getWinRate(int winNum, int gameNum) {
		if (gameNum == 0) {
			return 0.0;
		} else {
			return (double) winNum / (double) gameNum;
		}
	}

	/*
	 * Sort the players according to their names
	 */
	public void sortUser(String operator) {
		int sortLength = 0; // Record the number of exist players
		for (int i = 0; i < getPlayerInfo().length; i++) {
			if (getPlayerInfo()[i].getUserName() != null) {
				sortLength++;
			}
		}

		// Descending sort when displaying players
		if (operator.equals("display")) {
			for (int i = 0; i < sortLength - 1; i++) {
				for (int j = 0; j < sortLength - 1 - i; j++) {
					if (getPlayerInfo()[j].getUserName()
							.compareTo(getPlayerInfo()[j + 1].getUserName()) > 0) {
						exchangeUser(j, j + 1);
					}
				}
			}
		}

		// Descending sort when ranking players
		else if (operator.equals("rankdesc")) {
			for (int i = 0; i < sortLength - 1; i++) {
				for (int j = 0; j < sortLength - 1 - i; j++) {
					if (getWinRate(getPlayerInfo()[j].getGameWin(),
							getPlayerInfo()[j].getGamePlay()) < getWinRate(
									getPlayerInfo()[j + 1].getGameWin(),
									getPlayerInfo()[j + 1].getGamePlay())) {
						exchangeUser(j, j + 1);
					}
				}
			}
		}

		// Ascending sort when ranking players
		else if (operator.equals("rankasc")) {
			for (int i = 0; i < sortLength - 1; i++) {
				for (int j = 0; j < sortLength - 1 - i; j++) {
					if (getWinRate(getPlayerInfo()[j].getGameWin(),
							getPlayerInfo()[j].getGamePlay()) > getWinRate(
									getPlayerInfo()[j + 1].getGameWin(),
									getPlayerInfo()[j + 1].getGamePlay())) {
						exchangeUser(j, j + 1);
					}
				}
			}
		}
	}

	/*
	 * Display the ranking result
	 */
	public void displayRankings() {
		int MAXNUM = 10; // Maximum of displaying players
		for (int i = 0; i < MAXNUM; i++) {
			if (getPlayerInfo()[i].getUserName() != null) {
				double winRate; // Calculate the player's winning rate
				int roundResult; // Rounding the winning Rate
				String formatResult; // Format the final result

				winRate = getWinRate(getPlayerInfo()[i].getGameWin(),
						getPlayerInfo()[i].getGamePlay()) * 100;
				roundResult = Integer.parseInt(new java.text.DecimalFormat("0").format(winRate));
				formatResult = roundResult + "%";
				System.out.print(formatResult);

				// Format the output
				for (int j = formatResult.length(); j < 5; j++) {
					System.out.print(" ");
				}
				System.out.print("| ");
				if (getPlayerInfo()[i].getGamePlay() < 10) {
					System.out.print("0" + getPlayerInfo()[i].getGamePlay() + " games | ");
				} else {
					System.out.print(getPlayerInfo()[i].getGamePlay() + " games | ");
				}
				System.out.print(getPlayerInfo()[i].getGivenName() + " "
						+ getPlayerInfo()[i].getFamilyName());
				System.out.println();
			}
		}
		System.out.println();
	}

	public void readData() {
		BufferedReader reader;
		String data;

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader = new BufferedReader(new FileReader(file));
			int line = 0;		
			while ((data = reader.readLine()) != null) {
				String divideFileData[] = data.split(",");
				if (divideFileData[5].equals("human")) {
					NimPlayer human = new NimHumanPlayer();
					human.setUsername(divideFileData[0]);
					human.setGivenName(divideFileData[1]);
					human.setFamilyName(divideFileData[2]);
					human.setGamePlay(Integer.parseInt(divideFileData[3]));
					human.setGameWin(Integer.parseInt(divideFileData[4]));
					human.setType("human");
					setPlayerInfo(line, human);
					line++;
				} else {
					NimPlayer ai = new NimAIPlayer();
					ai.setUsername(divideFileData[0]);
					ai.setGivenName(divideFileData[1]);
					ai.setFamilyName(divideFileData[2]);
					ai.setGamePlay(Integer.parseInt(divideFileData[3]));
					ai.setGameWin(Integer.parseInt(divideFileData[4]));
					ai.setType("ai");
					setPlayerInfo(line, ai);
					line++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeData(NimPlayer[] writeData) {
		int playerNum = 0;
		for (int i = 0; i < writeData.length; i++) {
			if (writeData[i].getUserName() != null) {
				playerNum++;
			}
		}

		try {
			FileWriter fw = new FileWriter(file, false);
			for (int i = 0; i < playerNum; i++) {
				fw.write(writeData[i].getUserName() + "," + writeData[i].getGivenName() + ","
						+ writeData[i].getFamilyName() + "," + writeData[i].getGamePlay() + ","
						+ writeData[i].getGameWin() + "," + writeData[i].getType() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Execute the command "addplayer"
	 */
	public void addPlayer(String content) {
		divideOperator = "default";
		if (findUser(divideInfo(content, divideOperator)[0]) == -1) {
			NimPlayer nimPlayer = new NimHumanPlayer();
			nimPlayer.setUsername(divideInfo(content, divideOperator)[0]);
			setHumanUserName(humanIndex, divideInfo(content, divideOperator)[0]);
			humanIndex++;
			nimPlayer.setFamilyName(divideInfo(content, divideOperator)[1]);
			nimPlayer.setGivenName(divideInfo(content, divideOperator)[2]);
			nimPlayer.setType("human");
			setPlayerInfo(addPosition, nimPlayer);
			addPosition++; // The index plus 1

			System.out.println();
		} else {
			System.out.println("The player already exists.");
			System.out.println();
		}

	}

	public void addAIPlayer(String content) {
		divideOperator = "default";
		if (findUser(divideInfo(content, divideOperator)[0]) == -1) {
			NimPlayer nimPlayer = new NimAIPlayer();
			nimPlayer.setUsername(divideInfo(content, divideOperator)[0]);
			setAIUserName(AIIndex, divideInfo(content, divideOperator)[0]);
			AIIndex++;
			nimPlayer.setFamilyName(divideInfo(content, divideOperator)[1]);
			nimPlayer.setGivenName(divideInfo(content, divideOperator)[2]);
			nimPlayer.setType("AI");
			setPlayerInfo(addPosition, nimPlayer);
			addPosition++; // The index plus 1
			System.out.println();
		} else {
			System.out.println("The player already exists.");
			System.out.println();
		}

	}

	/*
	 * Execute the command "removeplayer"
	 */
	public void removePlayer(String content, Scanner scanner) {
		if (content.equals("null")) {
			System.out.println("Are you sure you want to remove all players? (y/n)");
			String response; // Remove confirmation sign
			response = scanner.nextLine();

			if (response.equals("y")) {
				for (int i = 0; i < getPlayerInfo().length; i++) {
					setPlayerInfo(i, new NimHumanPlayer());
				}
			}
			System.out.println();
		} else {
			if (findUser(content) != -1) {
				// The player i+1 in the array replaces the player i
				for (int i = position; i < getPlayerInfo().length - 1; i++) {
					setPlayerInfo(i, getPlayerInfo()[i + 1]);
				}
				setPlayerInfo(getPlayerInfo().length - 1, new NimHumanPlayer());
				addPosition--; // The index minus 1
				System.out.println();
			} else {
				System.out.println("The player does not exist.");
				System.out.println();
			}
		}

	}

	/*
	 * Handle the command "editplayer"
	 */
	public void editPlayer(String content) {
		divideOperator = "default";
		if (findUser(divideInfo(content, divideOperator)[0]) != -1) {
			NimPlayer temp = new NimHumanPlayer(); // Create a temporary object of NimPlayer

			temp = getPlayerInfo()[position];
			temp.setFamilyName(divideInfo(content, divideOperator)[1]);
			temp.setGivenName(divideInfo(content, divideOperator)[2]);

			setPlayerInfo(position, temp);
			System.out.println();

		} else {
			System.out.println("The player does not exist.");
			System.out.println();
		}

	}

	/*
	 * Handle the command "resetstats"
	 */
	public void resetStats(String content, Scanner scanner) {
		if (content.equals("null")) {
			System.out.println("Are you sure you want to reset all player statistics? (y/n)");
			String response; // Reset confirmation sign
			response = scanner.nextLine();

			if (response.equals("y")) {
				NimPlayer temp = new NimHumanPlayer();
				for (int i = 0; i < getPlayerInfo().length; i++) {
					temp = getPlayerInfo()[i];
					temp.setGamePlay(0);
					temp.setGameWin(0);
					setPlayerInfo(i, temp);
				}
			}
			System.out.println();
		} else {
			if (findUser(content) != -1) {
				NimPlayer temp = new NimHumanPlayer();
				temp = getPlayerInfo()[position];
				temp.setGamePlay(0);
				temp.setGameWin(0);

				setPlayerInfo(position, temp);
				System.out.println();

			} else {
				System.out.println("The player does not exist.");
				System.out.println();
			}
		}

	}

	/*
	 * Handle the command "displayplayer"
	 */
	public void displayPlayer(String content) {
		if (content.equals("null")) {
			sortOperator = "display";
			sortUser(sortOperator);
			for (int i = 0; i < getPlayerInfo().length; i++) {
				if (getPlayerInfo()[i].getUserName() != null)
					System.out.println(getPlayerInfo()[i].getUserName() + ","
							+ getPlayerInfo()[i].getGivenName() + ","
							+ getPlayerInfo()[i].getFamilyName() + ","
							+ getPlayerInfo()[i].getGamePlay() + " games,"
							+ getPlayerInfo()[i].getGameWin() + " wins");
			}
			System.out.println();
		} else {
			if (findUser(content) != -1) {
				System.out.println(getPlayerInfo()[position].getUserName() + ","
						+ getPlayerInfo()[position].getGivenName() + ","
						+ getPlayerInfo()[position].getFamilyName() + ","
						+ getPlayerInfo()[position].getGamePlay() + " games,"
						+ getPlayerInfo()[position].getGameWin() + " wins");
				System.out.println();

			} else {
				System.out.println("The player does not exist.");
				System.out.println();
			}
		}

	}

	/*
	 * Handle the command "rankings"
	 */
	public void rankings(String content) {
		sortUser("display"); // Sort users according to their user names
		if ((content.equals("null") || content.equals("desc"))) {
			sortOperator = "rankdesc";
			sortUser(sortOperator);
			displayRankings();
		}

		else if (content.equals("asc")) {
			sortOperator = "rankasc";
			sortUser(sortOperator);
			displayRankings();
		}

	}

	/*
	 * Handle the command "startgame"
	 */
	public void startGame(String content, Scanner scanner, NimGame nimGame, Nimsys nimsys) {
		divideOperator = "gamestart";
		if (findUser(divideInfo(content, divideOperator)[2]) == -1
				|| findUser(divideInfo(content, divideOperator)[3]) == -1) {
			System.out.println("One of the players does not exist.");
			System.out.println();

		} else {
			nimGame.playerInit(divideInfo(content, divideOperator)[2],
					divideInfo(content, divideOperator)[3], nimsys);
			nimGame.gameInit(divideInfo(content, divideOperator)[0],
					divideInfo(content, divideOperator)[1]);
			nimGame.gameProcess(scanner,nimGame);
		}

	}

	/*
	 * Handle the input information
	 */
	public void handleCommand(String command, String content, Scanner scanner, Nimsys nimsys) {
		try {
			if (command.equals("addplayer")) {
				addPlayer(content);
			}

			else if (command.equals("addaiplayer")) {
				addAIPlayer(content);
			}

			else if (command.equals("removeplayer")) {
				removePlayer(content, scanner);
			}

			else if (command.equals("editplayer")) {
				editPlayer(content);
			}

			else if (command.equals("resetstats")) {
				resetStats(content, scanner);
			}

			else if (command.equals("displayplayer")) {
				displayPlayer(content);
			}

			else if (command.equals("rankings")) {
				rankings(content);
			}

			else if (command.equals("startgame")) {
				NimGame nimGame = new NimGame(); // Create an object of NimGame
				startGame(content, scanner, nimGame, nimsys);
			}

			// Exit the game
			else if (command.equals("exit")) {
				writeData(playerInfo);
				System.out.println();
				System.exit(0);
			}

			else {
				System.out.println("'" + command + "' is not a valid command.");
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Incorrect number of arguments supplied to command.");
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Nimsys nimsys = new Nimsys(); // Create an object of Nimsys
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Nim");
		System.out.println();
		nimsys.initPlayerInfo();
		nimsys.readData(); // Read data from the player statistics file
		
		for (int i = 0; i < nimsys.playerInfo.length; i++) {
			if (nimsys.playerInfo[i].getUserName() != null) {
				nimsys.addPosition++;
			}
		}

		do {
			System.out.print("$");

			// Divide the input into command and content
			String[] input = scanner.nextLine().split(" ");

			String command;
			String content = "null";

			command = input[0]; // The first element of the array store the command
			if (input.length == 2) {
				content = input[1]; // The second element of the array store the content
			}
			if (command.equals("exit")) {
				scanner.close();
			}

			// Handle the input information
			nimsys.handleCommand(command, content, scanner, nimsys);

		} while (true);

	}

}
