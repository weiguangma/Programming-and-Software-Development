import java.util.Scanner;

public class NimAIPlayer extends NimPlayer {
	/**
	 * The number of stones removed by the player
	 */
	public void removeStone(Scanner scanner, NimGame nimGame) {
		int maxRemove;
		int minRemove = 1;
		int attemptRemove;
		if (nimGame.getStones() > nimGame.getBound()) {
			maxRemove = nimGame.getBound();
		} else {
			maxRemove = nimGame.getStones();
		}
		for (attemptRemove = minRemove; attemptRemove < maxRemove; attemptRemove++) {
			if ((nimGame.getStones() - attemptRemove - 1) % (maxRemove + 1) == 0) {
				break;
			}
		}
		if ((nimGame.getStones() - attemptRemove) < 0) {
			attemptRemove = 1;
		}
		nimGame.setStones(nimGame.getStones() - attemptRemove);
		// System.out.println(attemptRemove+" stones");
	}
}
