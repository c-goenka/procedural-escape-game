package src.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private final int[] bottomLeft;
    private final int[] topRight;

    public Room(Random random, int worldWidth, int worldHeight) {
        int roomWidth = RandomUtils.uniform(random, 4, 7);
        int roomHeight = RandomUtils.uniform(random, 4, 7);
        int x = RandomUtils.uniform(random, 1, worldWidth - roomWidth);
        int y = RandomUtils.uniform(random, 1, worldHeight - roomHeight);

        this.bottomLeft = new int[2];
        bottomLeft[0] = x;
        bottomLeft[1] = y;

        this.topRight = new int[2];
        topRight[0] = x + roomWidth;
        topRight[1] = y + roomHeight;
    }

    public int[] getBottomLeft() {
        return bottomLeft;
    }

    public int[] getTopRight() {
        return topRight;
    }

    public boolean overlaps(ArrayList<Room> rooms) {
        for (Room r : rooms) {
            if (equals(r)) {
                continue;
            }
            if (getBottomLeft()[0] < r.getTopRight()[0] && getBottomLeft()[1] < r.getTopRight()[1]
                    && getTopRight()[0] > r.getBottomLeft()[0] && getTopRight()[1] > r.getBottomLeft()[1]) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Room r) {
        return Arrays.equals(getBottomLeft(), r.getBottomLeft());
    }

    public int[] center() {
        int[] center = new int[2];
        center[0] = Math.floorDiv((getBottomLeft()[0] + getTopRight()[0]), 2);
        center[1] = Math.floorDiv((getBottomLeft()[1] + getTopRight()[1]), 2);
        return center;
    }
}

// Extra Methods:
//
//    public int getWidth() {
//        return getTopRight()[0] - getBottomLeft()[0];
//    }
//
//    public int getHeight() {
//        return getTopRight()[1] - getBottomLeft()[1];
//    }
//
//    public boolean onBorder(int x, int y) {
//        return (x == getBottomLeft()[0] || y == getBottomLeft()[1] ||
//        x == getTopRight()[0] - 1 || y == getTopRight()[1] - 1);
//    }
//
//    public double distance(Room r) {
//        return Math.sqrt(Math.pow((r.getBottomLeft()[0] - getBottomLeft()[0]), 2)
//                + Math.pow((r.getBottomLeft()[1] - getBottomLeft()[1]), 2));
//    }
//
//    public Room nearestRoom(ArrayList<Room> rooms){
//        Room closestRoom = null;
//        double minDist = Integer.MAX_VALUE;
//        for (Room r : rooms) {
//            if (equals(r)) {
//                continue;
//            }
//            if (distance(r) < minDist) {
//                minDist = distance(r);
//                closestRoom = r;
//            }
//        }
//        return closestRoom;
//    }
