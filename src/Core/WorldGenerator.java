package src.Core;

import src.TileEngine.TETile;
import src.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    private final int WIDTH;
    private final int HEIGHT;
    private final long SEED;
    private final Random RANDOM;
    private final TETile[][] world;
    private final ArrayList<Room> rooms =  new ArrayList<>();
    private final int ROOM_LOWER_LIMIT = 10;
    private final int ROOM_UPPER_LIMIT = 15;
    private int[] player;

    public WorldGenerator(int width, int height, long seed) {
        SEED = seed;
        RANDOM = new Random(SEED);
        WIDTH = width;
        HEIGHT = height;
        world = new TETile[width][height];

        makeEmptyBoard();
        drawRandomRooms();
        drawHallways();
        drawWalls();
        spawnPlayer();
        spawnExit();
        spawnKey();
    }

    public void makeEmptyBoard() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void drawRandomRooms() {
        int numberOfRooms = RandomUtils.uniform(RANDOM, ROOM_LOWER_LIMIT, ROOM_UPPER_LIMIT);
        for (int i = 0; i < numberOfRooms; i += 1) {
            Room r = new Room(RANDOM, WIDTH, HEIGHT);
            if (r.overlaps(rooms)) {
                i -= 1;
                continue;
            }
            rooms.add(r);
            drawRoom(r);
        }
    }

    public void drawRoom(Room r) {
        for (int i = r.getBottomLeft()[0]; i < r.getTopRight()[0]; i += 1) {
            for (int j = r.getBottomLeft()[1]; j < r.getTopRight()[1]; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public void drawWalls() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    continue;
                }
                if (isNeighborFloor(x, y)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * Finds a random wall tile that is accessible (adjacent to a floor tile).
     * Used for placing doors and other interactive elements.
     */
    public int[] getRandomWall() {
        int x, y;
        do {
            x = RandomUtils.uniform(RANDOM, WIDTH);
            y = RandomUtils.uniform(RANDOM, HEIGHT);
        } while (world[x][y] != Tileset.WALL || !isAccessible(x, y));
        return new int[] {x, y};
    }

    public boolean isAccessible(int x, int y) {
        if (isInBounds(x + 1, y) && world[x + 1][y] == Tileset.FLOOR) {
            return true;
        }
        if (isInBounds(x - 1, y) && world[x - 1][y] == Tileset.FLOOR) {
            return true;
        }
        if (isInBounds(x, y + 1) && world[x][y + 1] == Tileset.FLOOR) {
            return true;
        }
        if (isInBounds(x, y - 1) && world[x][y - 1] == Tileset.FLOOR) {
            return true;
        }
        return false;
    }

    public void spawnExit() {
        int[] cord = getRandomWall();
        world[cord[0]][cord[1]] = Tileset.LOCKED_DOOR;
    }

    public void spawnKey() {
        int roomNum;
        Room roomWithKey;
        int[] cord;
        do {
            roomNum = RandomUtils.uniform(RANDOM, 1, rooms.size() - 2);
            roomWithKey = rooms.get(roomNum);
            cord = roomWithKey.center();
        } while (world[cord[0]][cord[1]] == Tileset.AVATAR);
        world[cord[0]][cord[1]] = Tileset.KEY;
    }

    public void spawnPlayer() {
        int roomNum = RandomUtils.uniform(RANDOM, 0, rooms.size() - 1);
        Room room = rooms.get(roomNum);
        int[] cord = room.center();
        player = cord;
        world[cord[0]][cord[1]] = Tileset.AVATAR;
    }

    public boolean isInBounds(int x, int y) {
        return x <= WIDTH - 1 && x >= 0 && y <= HEIGHT - 1 && y >= 0;
    }

    public boolean isNeighborFloor(int x, int y) {
        if (isInBounds(x - 1, y - 1) && world[x - 1][y - 1] == Tileset.FLOOR) {
            return true; //Left Lower-Diagonal
        }
        if (isInBounds(x, y - 1) && world[x][y - 1] == Tileset.FLOOR) {
            return true; //Bottom
        }
        if (isInBounds(x + 1, y - 1) && world[x + 1][y - 1] == Tileset.FLOOR) {
            return true; // Right Lower Diagonal
        }
        if (isInBounds(x - 1, y) && world[x - 1][y] == Tileset.FLOOR) {
            return true; //Left
        }
        if (isInBounds(x + 1, y) && world[x + 1][y] == Tileset.FLOOR) {
            return true; // Right
        }
        if (isInBounds(x - 1, y + 1) && world[x - 1][y + 1] == Tileset.FLOOR) {
            return true; // Left Upper Diagonal
        }
        if (isInBounds(x, y + 1) && world[x][y + 1] == Tileset.FLOOR) {
            return true; // Top
        }
        return isInBounds(x + 1, y + 1) && world[x + 1][y + 1] == Tileset.FLOOR; // Right Upper Diagonal
    }

    public void drawHallways() {
        for (int i = 0; i < rooms.size() - 1; i += 1) {
            drawHallway(rooms.get(i), rooms.get(i + 1));
        }
    }

    public void drawHallway(Room r1, Room r2) {
        int[] cord1 = r1.center();
        int[] cord2 = r2.center();

        if (cord1[1] < cord2[1]) {
            for (int i = cord1[1]; i <= cord2[1]; i++) {
                world[cord1[0]][i] = Tileset.FLOOR;
            }
        } else {
            for (int i = cord1[1]; i >= cord2[1]; i--) {
                world[cord1[0]][i] = Tileset.FLOOR;
            }
        }

        if (cord1[0] < cord2[0]) {
            for (int i = cord1[0]; i <= cord2[0]; i++) {
                world[i][cord2[1]] = Tileset.FLOOR;
            }
        } else {
            for (int i = cord1[0]; i >= cord2[0]; i--) {
                world[i][cord2[1]] = Tileset.FLOOR;
            }
        }
    }

    public TETile[][] getTiles() {
        return world;
    }

    public int[] getPlayer() {
        return player;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public long getSeed() {
        return SEED;
    }

}
