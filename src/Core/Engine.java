package src.Core;

import src.TileEngine.TERenderer;
import src.TileEngine.TETile;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 35;
    private long SEED;
    private final int FONT_SIZE_TITLE = 40;
    private final int FONT_SIZE_BIG = 30;
    private final int CANVAS_CONST = 16;
    private final double FORMAT_CONST_ONE = 1.5;
    private final double FORMAT_CONST_TWO = 2.5;

    /**
     * Method used for exploring a fresh world.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        drawMenu();
        WorldGenerator worldGenerator;
        Game escape = null;

        char input = 'i';
        while (input != 'N' && input != 'L' && input != 'R' && input != 'Q') {
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input = Character.toUpperCase(StdDraw.nextKeyTyped());
        }

        switch (input) {
            case 'N' -> {
                inputSeed();
                worldGenerator = new WorldGenerator(WIDTH, HEIGHT, SEED);
                escape = new Game(worldGenerator, ter);
            }
            case 'L' -> {
                String moves = loadGame();
                worldGenerator = new WorldGenerator(WIDTH, HEIGHT, SEED);
                escape = new Game(worldGenerator, ter);
                escape.loadSave(moves, false, false);
            }
            case 'R' -> {
                String moves = loadGame();
                worldGenerator = new WorldGenerator(WIDTH, HEIGHT, SEED);
                escape = new Game(worldGenerator, ter);
                escape.loadSave(moves, false, true);
            }
            case 'Q' -> System.exit(0);
            default -> { }
        }

        escape.startGame();
    }

    public TETile[][] interactWithInputString(String input) {
        WorldGenerator worldGenerator = null;
        Game escape = null;
        input = input.toUpperCase();
        int idx = 0;
        if (input.charAt(0) == 'L') {
            String moves = loadGame();
            worldGenerator = new WorldGenerator(WIDTH, HEIGHT, SEED);
            escape = new Game(worldGenerator, ter);
            escape.loadSave(moves, false, false);
        } else {
            idx = extractSeed(input);
            worldGenerator = new WorldGenerator(WIDTH, HEIGHT, SEED);
            escape = new Game(worldGenerator, ter);
        }
        String moves = input.substring(idx + 1);
        escape.loadSave(moves, true, false);
        return worldGenerator.getTiles();
    }

    public String loadGame() {
        In in = new In("src/Core/save.txt");
        String inputs = "";
        if (!in.isEmpty()) {
            SEED = Long.parseLong(in.readLine().trim());
            inputs = in.readLine();
        }
        return inputs;
    }

    public int extractSeed(String s) {
        StringBuilder seed = new StringBuilder();
        int i = 1;
        char c = s.charAt(i);
        while (c != 'S') {
            seed.append(c);
            i += 1;
            c = s.charAt(i);
        }
        this.SEED = Long.parseLong(seed.toString());
        return i;
    }

    public void inputSeed() {
        String inputString = "";
        drawInputSeed(inputString);
        char s = 't';
        while (s != 'S') {
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            s = Character.toUpperCase(StdDraw.nextKeyTyped());
            inputString += s;
            if (s != 'S') {
                drawInputSeed(inputString);
            }
        }
        SEED = Long.parseLong(inputString.substring(0, inputString.length() - 1));
    }

    public void setCanvas() {
        StdDraw.setCanvasSize(WIDTH * CANVAS_CONST, HEIGHT * CANVAS_CONST);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();
    }

    public void drawMenu() {
        setCanvas();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.cyan);
        Font fontBig = new Font("Monospaced", Font.BOLD, FONT_SIZE_TITLE);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_ONE, "─❅─ An Icy Adventure ─❅─");
        Font fontSmall = new Font("Monospaced", Font.BOLD, FONT_SIZE_BIG);
        StdDraw.setFont(fontSmall);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_TWO + 6, "New Game (N)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_TWO + 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_TWO + 2, "Replay Save (R)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_TWO, "Quit (Q)");
        StdDraw.show();
    }

    public void drawInputSeed(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monospaced", Font.BOLD, FONT_SIZE_BIG);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2.0, HEIGHT / FORMAT_CONST_ONE, "Enter World Seed and Press S to Continue:");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, s);
        StdDraw.show();
    }
}
