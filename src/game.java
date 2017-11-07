import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class game {

	public static final int NUM_OF_PLAYERS = 4;
	public static final int NUM_OF_TURNS = 9;
	public static final int NUM_OF_GAMES = 10000;
	private static Random rand = new Random();
	private static int[] counter = new int[40];
	private static int[] finalTurnCounter = new int[40];
	private static int totalCounter;
	private static int[] spotOn = new int[NUM_OF_PLAYERS];
	private static ArrayList chance = new ArrayList();
	private static ArrayList comm = new ArrayList();
	private static int[] jailTimer = new int[NUM_OF_PLAYERS];
	private static double[] percentages = new double[40];

	public static void main(String[] args) {
		int roll1;
		int roll2;
		for (int i = 0; i < 40; i++) {
			finalTurnCounter[i] = 0;
		}

		for (int j = 0; j < NUM_OF_GAMES; j++) {
			for (int i = 0; i < NUM_OF_PLAYERS; i++) {
				spotOn[i] = 0;
				jailTimer[i] = 0;
			}
			if (!comm.isEmpty()) {
				comm.clear();
			}
			if (!chance.isEmpty()) {
				chance.clear();
			}
			for (int i = 0; i < 16; i++) {
				comm.add(i);
				chance.add(i);
			}
			Collections.shuffle(comm);
			Collections.shuffle(chance);

			for (int t = 0; t < NUM_OF_TURNS; t++) {
				for (int i = 0; i < 40; i++) {
					counter[i] = 0;
				}
				for (int p = 0; p < NUM_OF_PLAYERS; p++) {
					if (jailTimer[p] != 0) {
						jailTimer[p] -= 1;
					} else {
						roll1 = rand.nextInt(6) + 1;
						roll2 = rand.nextInt(6) + 1;
						// System.out.println("player " + p + " rolled " + (roll1+roll2));
						movePlayer(p, (roll1 + roll2));
						doWork(p);

						if (roll1 == roll2) {
							// System.out.println("player " + p + " rolled doubles");
							roll1 = rand.nextInt(6) + 1;
							roll2 = rand.nextInt(6) + 1;
							// System.out.println("player " + p + " rolled " + (roll1+roll2));
							movePlayer(p, (roll1 + roll2));
							doWork(p);

							if (roll1 == roll2) {
								// System.out.println("player " + p + " rolled doubles again and is in JAIL");
								jailTimer[p] = 2;
								spotOn[p] = 10;
							}
						}
						// System.out.println(Integer.parseInt(chance.get(0).toString()));
					}
				}
				if(t==(NUM_OF_TURNS-1)) {
					for(int q = 0; q < 40; q++) {
						finalTurnCounter[q] += counter[q];
					}
					
				}
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < 40; i++) {
			totalCounter += finalTurnCounter[i];
		}
		ArrayList sort = new ArrayList();
		for (int i = 0; i < 40; i++) {
			percentages[i] = (((double)finalTurnCounter[i]/totalCounter*100));
			sort.add(percentages[i]);
		}
		System.out.println("Based on " + NUM_OF_GAMES + " Games with " + NUM_OF_PLAYERS + " players:");
		for (int i = 0; i < 40; i++) {
			System.out.println("On the " + NUM_OF_TURNS + " turn, players landed on space " + i + " " + finalTurnCounter[i] + " times out of " + totalCounter + " which is " + df.format(((double)finalTurnCounter[i]/totalCounter*100)) + "%.");
		}
		Collections.sort(sort, Collections.reverseOrder()); 
		for(int i = 0; i < 40; i++) {
			System.out.println(df.format(sort.get(i)));
		}
	}

	public static void movePlayer(int player, int rolled) {
		for (int i = 0; i < rolled; i++) {
			spotOn[player] += 1;
			if (spotOn[player] == 40) {
				spotOn[player] = 0;
			}
		}
		counter[spotOn[player]] += 1;
	}

	public static void doWork(int p) {
		if (spotOn[p] == 2 || spotOn[p] == 17 || spotOn[p] == 33) {
			drawComm(p);
		} else if (spotOn[p] == 7 || spotOn[p] == 22 || spotOn[p] == 36) {
			drawChance(p);
		}
	}

	public static void drawChance(int player) {
		if(chance.isEmpty()) {
			for (int i = 0; i < 16; i++) {
				chance.add(i);
			}
			Collections.shuffle(chance);
		}
		// System.out.println("player " + player + " drew chance");
		if (Integer.parseInt(chance.get(0).toString()) == 0) {
			spotOn[player] = 0;
			counter[0] += 1;
			// System.out.println("player " + player + " Went to GO");
		} else if (Integer.parseInt(chance.get(0).toString()) == 1) {
			spotOn[player] = 24;
			counter[24] += 1;
			// System.out.println("player " + player + " went to Illinois Avenue");
		} else if (Integer.parseInt(chance.get(0).toString()) == 2) {
			if (spotOn[player] < 12 || spotOn[player] > 28) {
				spotOn[player] = 12;
				counter[12] += 1;
			} else {
				spotOn[player] = 28;
				counter[28] += 1;
			}
			// System.out.println("player " + player + " went to next Utility");
		} else if (Integer.parseInt(chance.get(0).toString()) == 3) {
			if (spotOn[player] < 5 || spotOn[player] > 35) {
				spotOn[player] = 5;
				counter[5] += 1;
			} else if (spotOn[player] < 15 || spotOn[player] > 5) {
				spotOn[player] = 15;
				counter[15] += 1;
			} else if (spotOn[player] < 25 || spotOn[player] > 15) {
				spotOn[player] = 25;
				counter[25] += 1;
			} else if (spotOn[player] < 35 || spotOn[player] > 25) {
				spotOn[player] = 35;
				counter[35] += 1;
			}
			// System.out.println("player " + player + " went to next railroad");
		} else if (Integer.parseInt(chance.get(0).toString()) == 4) {
			spotOn[player] = 11;
			counter[11] += 1;
			// System.out.println("player " + player + " went to ST CHARLES PLACE");
		} 
		
		else if (Integer.parseInt(chance.get(0).toString()) == 7) {
			spotOn[player] -= 3;
			counter[spotOn[player]] += 1;
			// System.out.println("player " + player + " went back 3 spaces from spot " +
			// (spotOn[player]+3) + " to " + spotOn[player]);
		} 
		else if (Integer.parseInt(chance.get(0).toString()) == 8) {
			spotOn[player] = 10;
			counter[10] += 1;
			jailTimer[player] = 2;
			// System.out.println("player " + player + " went to JAIL");
		} else if (Integer.parseInt(chance.get(0).toString()) == 12) {
			spotOn[player] = 5;
			counter[5] += 1;
			// System.out.println("player " + player + " went to Reading Railroad");
		} else if (Integer.parseInt(chance.get(0).toString()) == 13) {
			spotOn[player] = 39;
			counter[39] += 1;
			// System.out.println("player " + player + " went to Boardwalk");
		} else {
			// System.out.println("Nothing important happened");
		}
		chance.remove(0);
	}

	public static void drawComm(int player) {
		if(comm.isEmpty()) {
			for (int i = 0; i < 16; i++) {
				comm.add(i);
			}
			Collections.shuffle(comm);
		}
		// System.out.println("player " + player + " drew Community Chest");
		// If Go
		if (Integer.parseInt(comm.get(0).toString()) == 0) {
			spotOn[player] = 0;
			counter[0] += 1;
			// System.out.println("player " + player + " went to go");
		}
		// If Jail
		else if (Integer.parseInt(chance.get(0).toString()) == 4) {
			spotOn[player] = 10;
			counter[10] += 1;
			jailTimer[player] = 2;
			// System.out.println("player " + player + " went to jail");
		} else {
			// System.out.println("Nothing important happened");
		}
		comm.remove(0);
	}
}
