package theWorld;

import java.util.ArrayList;

public class Room {

	private boolean hasPlayer = false;
	private boolean hasMonster;
	private int x;
	private int y;
	private ArrayList<Direction> possibleDirections = new ArrayList<Direction>();

	public Room(boolean hasMonster, int x, int y) {
		this.setHasMonster(hasMonster);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
		if (hasPlayer) {
			return " [  X  ] ";
		}

		if (hasMonster) {
			return " [ \\o/ ] ";
		}
		return " [     ] ";
	}

	public void setHasPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}
}
