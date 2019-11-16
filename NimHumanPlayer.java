import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer {
	/**
	 * The number of stones removed by the player
	 */
	public void removeStone(Scanner scanner,NimGame nimGame) {
		int remove;
		remove = scanner.nextInt();
		while (remove > nimGame.getBound() || remove < 1 || remove > nimGame.getStones()) {
			System.out.println();
			
			nimGame.displayStones();
			if (nimGame.getStones() > nimGame.getBound())
				System.out.println(
						"Invaild move. You must remove between 1 and " + nimGame.getBound() + " stones.");
			else
				System.out.println(
						"Invaild move. You must remove between 1 and " + nimGame.getStones() + " stones.");
			System.out.println();
			System.out.println(getGivenName() + "'s turn - remove how many?");
			remove = scanner.nextInt();
		}
		nimGame.setStones(nimGame.getStones()-remove);
	}
}
