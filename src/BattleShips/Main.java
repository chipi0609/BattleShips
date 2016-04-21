package BattleShips;

import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome!");
		System.out
				.println("Type R to play with random grid, or type anything else to play with fixed grid");
		String random = input.next();
		Grid grid = null;
		if (random.equals("R")) {
			System.out
					.println("Please choose the difficulty: L(low), M(medium), H(high):");
			String difficulty = input.next();
			int level = 0;
			switch (difficulty) {
			case "L":
				level = 50;
				break;
			case "M":
				level = 30;
				break;
			case "H":
				level = 10;
				break;
			}

			grid = makeRandomGrid(level);

		} else {
			grid = makeInitialGrid();
		}

		do {

			System.out.println(grid.toPlayerString());
			System.out
					.println("Please enter which cell you would like to attack:");

			String s = input.next();
			Coordinate c = Util.parseCoordinate(s);

			if (grid.wouldAttackSucceed(c)) {
				System.out.println("Oh yeah! A ship was hit!");
			} else {
				System.out.println("Miss! Try again");
			}
			grid.attackCell(c);

		} while (!grid.areAllSunk());

		System.out.println("Woohoo! All the ships are sunk!");

	}

	private static Grid makeInitialGrid() {
		Grid grid = new Grid();

		String[] coords = { "A7", "B1", "B4", "D3", "F7", "H1", "H4" };
		int[] sizes = { 2, 4, 1, 3, 1, 2, 5 };
		boolean[] isDowns = { false, true, true, false, false, true, false };
		int sum = 0;

		for (int i = 0; i < coords.length; i++) {
			Coordinate c = Util.parseCoordinate(coords[i]);
			grid.placeShip(c, sizes[i], isDowns[i]);
			sum += sizes[i];
		}

		System.out
				.println("To sink all the ships, you are required to attack at least "
						+ sum + " times");

		return grid;
	}

	private static Grid makeRandomGrid(int level) {
		Grid grid = new Grid();
		int numOfShips = (int) (Math.random() * level);
		int sum = 0;

		for (int i = 0; i < numOfShips; i++) {
			int row = (int) (Math.random() * grid.getWidth());
			int column = (int) (Math.random() * grid.getHeight());
			Coordinate c = new Coordinate(row, column);

			int size = (int) (Math.random() * row) + 1;
			boolean isDown = new Random().nextBoolean();


			if (grid.canPlace(c, size, isDown)) {
				grid.placeShip(c, size, isDown);
				sum += size;
			}
		}

		System.out
				.println("To sink all the ships, you are required to attack at least "
						+ sum + " times");
		return grid;
	}
}
