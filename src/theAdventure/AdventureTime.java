package theAdventure;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import rpslsFighting.Fight;
import theComponents.Hero;
import theComponents.Monster;
import theWorld.Direction;
import theWorld.Dungeon;
import theWorld.DungeonBuilder;
import theWorld.Room;

public class AdventureTime {
	private static Scanner inputScanner = new Scanner(System.in);
	private static DungeonBuilder dungeonBuilder = new DungeonBuilder();
	private static Hero hero;

	public static void main(String[] args) {

		hero = new Hero("John", 5);

		// first dungeon
		Dungeon firstDungeon = dungeonBuilder.generateDungeon(5, 8);
		goThroughtDungeon(firstDungeon);

		if (nextDungeon() == 0) {
			System.out.println("NextDungeon");
			Dungeon secondDungeon = dungeonBuilder.generateDungeon(2, 1);
			goThroughtDungeon(secondDungeon);
		}

		System.out.println("After the fight our hero goes home.");

	}

	private static int nextDungeon() {
		System.out.println("Enter the next dungeon: Yes[0]; No[1]");
		int answer = inputScanner.nextInt();

		if (answer == 1 || answer == 0) {
			return answer;
		} else {
			nextDungeon();
		}
		return -1;
	}

	private static void goThroughtDungeon(Dungeon dungeon) {
		dungeon.displayDungeon();
		dungeon.playerEntersDungeon();
		while (dungeon.playerChangeRoom(choooseNextMove(dungeon.getPlayerRoom()))) {
			dungeon.displayDungeon();
			if (dungeon.getPlayerRoom().getHasMonster()) {
				System.out.println("\nYou encountered a monster!!!");
				new Fight(hero, new Monster("Monster", determineMonsterHealth(dungeon)));
				dungeon.getPlayerRoom().setHasMonster(false);
				System.out.println("Health of the Hero: " + hero.displayHitpoints());
				dungeon.displayDungeon();
			}

			if (dungeon.getPlayerRoom().checkForExit()) {
				System.out.println("________\nEnd of Dungeon");
				break;
			}

		}
	}

	private static Direction choooseNextMove(Room playerRoom) {
		// Display options
		System.out.println("________");
		ArrayList<Direction> possibleDirections = playerRoom.getDirections();
		System.out.println("Choose the next room you want to explore.");
		int counter = 0;
		for (Direction d : possibleDirections) {
			System.out.println("[" + counter + "]: " + d.getName());
			counter++;
		}

		// Get answer from player
		int answer = -1;
		while (answer < 0 || answer >= possibleDirections.size()) {
			System.out.println("Your move: ");
			answer = inputScanner.nextInt();
			if (answer < 0 || answer >= possibleDirections.size()) {
				System.out.println("FAIL. Choose again.");
			}
		}
		return possibleDirections.get(answer);
	}

	private static int determineMonsterHealth(Dungeon dungeon) {
		int health = 1;
		Random randomizer = new Random();
		int healthBonus = randomizer.nextInt(dungeon.getSize());
		health += healthBonus / 2;
		return health;
	}

}
