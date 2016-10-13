package theWorld;

import java.util.ArrayList;

public class Room {

	private boolean hasPlayer = false;
	private boolean hasMonster;
	private int row;
	private int colm;
	private ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
	private boolean isExit = false;

	public Room(boolean hasMonster, int row, int colm) {
		this.setHasMonster(hasMonster);
		this.row = row;
		this.colm = colm;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return colm;
	}

	/**
	 * Mark this room as an exit.
	 */
	public void isExit() {
		this.isExit = true;
	}

	public boolean checkForExit() {
		return isExit;
	}

	public boolean getHasMonster() {
		return hasMonster;
	}

	public void setHasMonster(boolean hasMonster) {
		this.hasMonster = hasMonster;
	}

	public void addPossibleDirection(Direction direction) {
		possibleDirections.add(direction);
	}

	public ArrayList<Direction> getDirections() {
		return possibleDirections;
	}

	public String displayRoom() {
		String roomDisplay = " [ _._ ] ";
		// ⇡⇠⇢⇣
		if (hasPlayer && hasMonster) {
			roomDisplay = roomDisplay.replace("_._", "PvM");
		}
		if (hasPlayer) {
			roomDisplay = roomDisplay.replace('.', 'P');
		}

		if (hasMonster) {
			roomDisplay = roomDisplay.replace('.', 'M');
		}

		if (isExit) {
			roomDisplay = roomDisplay.replace('.', 'E');
		}

		return roomDisplay;
	}

	public void setHasPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}

}
