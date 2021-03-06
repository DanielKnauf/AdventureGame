package model.world;

import model.world.room.DungeonRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static model.world.Direction.*;

public class Dungeon {

    private Random randomizer = new Random();
    private int rowSize;
    private int columnSize;
    private DungeonRoom[][] map;
    private DungeonRoom startRoom;
    private List<DungeonRoom> roomsWithMonster;
    private DungeonRoom playerRoom;

    Dungeon(int rowSize,
            int columnSize,
            DungeonRoom[][] dungeonMap,
            DungeonRoom startRoom,
            List<DungeonRoom> roomsWithMonster) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.map = dungeonMap;
        this.startRoom = startRoom;
        this.roomsWithMonster = roomsWithMonster;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public DungeonRoom getPlayerRoom() {
        return playerRoom;
    }

    public DungeonRoom[][] getDungeonMap() {
        return this.map;
    }

    /**
     * Initial set up for the playerPosition and playerRoom
     */
    public void playerEntersDungeon() {
        startRoom.setHasPlayer(true);
        this.playerRoom = startRoom;
    }

    public void removeRoomFromRoomsWithMonster(DungeonRoom roomWithDeadMonster) {
        roomsWithMonster.remove(roomWithDeadMonster);
    }

    public DungeonRoom moveMonsters(){
        return monsterMoves();
    }

    /**
     * Finds the room the player is entering and changes all parameter to this
     * room.
     *
     * @param direction the direction the player wants to go
     * @return <code>true</code>if change was successful.
     */
    public boolean playerChangeRoom(Direction direction, DungeonRoom monsterRoom) {
        int[] coordinates = direction.getCoordinates(playerRoom.getRow(), playerRoom.getColumn());

        if (hasRoom(coordinates[0], coordinates[1])) {
            movePlayer(monsterRoom, map[coordinates[0]][coordinates[1]]);
            return true;
        }

        return false;
    }

    private void movePlayer(DungeonRoom monsterRoom, DungeonRoom enter) {
        if (playerRoom.equals(monsterRoom)) {
            return;
        }

        playerRoom.setHasPlayer(false);
        enter.setHasPlayer(true);
        playerRoom = enter;
    }

    private DungeonRoom monsterMoves() {
        return checkIfAllMonstersLocked()
                ? moveMonster()
                : null;
    }

    /**
     * Checks if there is a possible movement for one of the monsters.
     * <p>
     * <code>True</code>, when there is at least one possible movement for one
     * of the monsters.
     *
     * @return
     */
    private boolean checkIfAllMonstersLocked() {
        return Stream.of(roomsWithMonster)
                .flatMap(Collection::stream)
                .anyMatch(this::hasFreeRoomToMove);
    }

    private DungeonRoom moveMonster() {
        DungeonRoom monsterRoom = roomsWithMonster.get(randomizer.nextInt(roomsWithMonster.size()));

        if (hasFreeRoomToMove(monsterRoom)) {
            // Find the free rooms
            List<DungeonRoom> freeRooms = findFreeRoomsToMoveIn(monsterRoom);

            // Choose one at random
            DungeonRoom nextRoom = freeRooms.get(randomizer.nextInt(freeRooms.size()));

            // remove room form list
            monsterRoom.setHasMonster(false);
            roomsWithMonster.remove(monsterRoom);

            // add new monsterRoom to list
            nextRoom.setHasMonster(true);
            roomsWithMonster.add(nextRoom);

            return nextRoom;
        } else {
            moveMonster();
        }

        return null;
    }

    /**
     * @param previousRoom
     * @return
     */
    private boolean hasFreeRoomToMove(DungeonRoom previousRoom) {
        int row = previousRoom.getRow();
        int colm = previousRoom.getColumn();

        return roomIsFree(UP.getCoordinates(row, colm))
                || roomIsFree(DOWN.getCoordinates(row, colm))
                || roomIsFree(LEFT.getCoordinates(row, colm))
                || roomIsFree(RIGHT.getCoordinates(row, colm));
    }

    /**
     * @param coordinates
     * @return
     */
    private boolean roomIsFree(int[] coordinates) {
        return hasRoom(coordinates[0], coordinates[1]) && (!map[coordinates[0]][coordinates[1]].isExit() && !map[coordinates[0]][coordinates[1]].hasMonster());
    }

    /**
     * @param row
     * @param column
     * @return <code>true</code> if space contains a room.n
     */
    private boolean hasRoom(int row, int column) {
        return row < rowSize
                && row >= 0
                && column >= 0
                && column < columnSize
                && map[row][column] != null;
    }

    /**
     * @param previousRoom
     * @return
     */
    private List<DungeonRoom> findFreeRoomsToMoveIn(DungeonRoom previousRoom) {
        List<DungeonRoom> freeRooms = new ArrayList<>();
        int row = previousRoom.getRow();
        int colm = previousRoom.getColumn();
        if (roomIsFree(UP.getCoordinates(row, colm))) {
            freeRooms.add(map[row - 1][colm]);
        }
        if (roomIsFree(DOWN.getCoordinates(row, colm))) {
            freeRooms.add(map[row + 1][colm]);
        }
        if (roomIsFree(LEFT.getCoordinates(row, colm))) {
            freeRooms.add(map[row][colm - 1]);
        }
        if (roomIsFree(RIGHT.getCoordinates(row, colm))) {
            freeRooms.add(map[row][colm + 1]);
        }

        return freeRooms;
    }
}
