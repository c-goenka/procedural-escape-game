package src.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', new Color(194, 1, 20), new Color(173, 194, 193), "you");
    public static final TETile WALL = new TETile('⁜', new Color(173, 194, 193), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(236, 235, 243), new Color(173, 194, 193),
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile KEY = new TETile('⚿', new Color(255, 240, 124), new Color(173, 194, 193),
            "key");
}
