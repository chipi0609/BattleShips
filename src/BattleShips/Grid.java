package BattleShips;

public class Grid {

	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;

	private final Piece[][] grid = new Piece[HEIGHT][WIDTH];

	public Grid() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				grid[i][j] = Piece.WATER;
			}
		}
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public boolean canPlace(Coordinate c, int size, boolean isDown) {
		if (isDown) {
			return c.getRow() + size < HEIGHT;
		} else {
			return c.getColumn() + size < WIDTH;
		}
	}

	public void placeShip(Coordinate c, int size, boolean isDown) {
		assert canPlace(c, size, isDown);
		int row = c.getRow();
		int column = c.getColumn();
		for (int i = 0; i < size; i++) {
			if (isDown) {
				grid[i + row][column] = Piece.SHIP;
			} else {
				grid[row][i + column] = Piece.SHIP;
			}
		}
	}

	public boolean wouldAttackSucceed(Coordinate c) {
		return grid[c.getRow()][c.getColumn()] == Piece.SHIP;
	}

	public void attackCell(Coordinate c) {
		int row = c.getRow();
		int column = c.getColumn();
		if (grid[row][column] == Piece.SHIP) {
			grid[row][column] = Piece.DAMAGED_SHIP;
		}

		if (grid[row][column] == Piece.WATER) {
			grid[row][column] = Piece.MISS;
		}
	}

	public boolean areAllSunk() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (grid[i][j] == Piece.SHIP) {
					return false;
				}
			}
		}
		return true;
	}

	public String toPlayerString() {
		String s = "Your current state: \n";
		Piece[][] clonedGrid = Util.deepClone(grid);
		Util.hideShips(clonedGrid);
		return renderGrid(clonedGrid);
	}

	public String toString() {
		return renderGrid(grid);
	}

	private static String renderGrid(Piece[][] grid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" 0123456789\n");
		for (int i = 0; i < grid.length; i++) {
			sb.append((char) ('A' + i));
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					return "!";
				}
				switch (grid[i][j]) {
				case SHIP:
					sb.append('#');
					break;
				case DAMAGED_SHIP:
					sb.append('*');
					break;
				case MISS:
					sb.append('o');
					break;
				case WATER:
					sb.append('.');
					break;
				}

			}
			sb.append('\n');
		}

		return sb.toString();
	}
}
