package src.Core;

import src.TileEngine.TERenderer;
import src.TileEngine.Tileset;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    private final int WIDTH;
    private final int HEIGHT;
    WorldGenerator wg;
    TERenderer ter;
    private final int FONT_SIZE_BIG = 30;
    private final int FONT_SIZE_SMALL = 17;
    private final int LONG_PAUSE = 2000;
    private final int SHORT_PAUSE = 200;
    private boolean quitCheck = false;
    private String inputString = "";
    private boolean gameOver = false;
    private boolean hasKey = false;
    private boolean doorOpened = false;
    private boolean interactWithInputString = false;

    public Game(WorldGenerator wg, TERenderer ter) {
        this.wg = wg;
        WIDTH = wg.getWidth();
        HEIGHT = wg.getHeight();
        this.ter = ter;
    }

    public void movePlayer(char dir) {
        int x = wg.getPlayer()[0];
        int y = wg.getPlayer()[1];

        switch (dir) {
            case 'W' -> {
                y += 1;
            }
            case 'A' -> {
                x -= 1;
            }
            case 'S' -> {
                y -= 1;
            }
            case 'D' -> {
                x += 1;
            }
            default -> { }
        }

        if (!wg.isInBounds(x, y) || wg.getTiles()[x][y].equals(Tileset.WALL)) {
            return;
        }
        if (wg.getTiles()[x][y].equals(Tileset.LOCKED_DOOR) && hasKey) {
            gameOver = true;
            gameStatus();
        } else if (wg.getTiles()[x][y].equals(Tileset.KEY)) {
            hasKey = true;
        } else if (wg.getTiles()[x][y].equals(Tileset.LOCKED_DOOR)) {
            gameOver= true;
            gameStatus();
        }

        wg.getTiles()[wg.getPlayer()[0]][wg.getPlayer()[1]] = Tileset.FLOOR;
        wg.getTiles()[x][y] = Tileset.AVATAR;
        wg.getPlayer()[0] = x;
        wg.getPlayer()[1] = y;
    }

    public void gameStatus() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monospaced", Font.BOLD, FONT_SIZE_BIG);
        StdDraw.setFont(fontBig);
        if (hasKey) {
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "─❅─ Congratulations!!! You Won! ─❅─");
        } else {
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "You are stuck in here for ETERNITY!!!");
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.5, "·☠· You Loose!!! ·☠·");
        }

        StdDraw.show();
        StdDraw.pause(LONG_PAUSE);

        if (!interactWithInputString) {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }
    }

    public void drawFrame() {
        ter.renderFrame(wg.getTiles());
        drawHUD();
        StdDraw.show();
        StdDraw.pause(16);
    }

    public void drawHUD() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monospaced", Font.BOLD, FONT_SIZE_SMALL);
        StdDraw.setFont(fontSmall);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String des = "";
        if (wg.isInBounds(x, y)) {
            des = wg.getTiles()[x][y].description();
        }
        StdDraw.textLeft(1, this.HEIGHT - 1, "Tile: " + des);
        if (hasKey) {
            StdDraw.text(WIDTH / 2.0, this.HEIGHT - 1, "Get to the Door!");
        } else if (doorOpened) {
            StdDraw.text(WIDTH / 2.0, this.HEIGHT - 1, "You are stuck for ETERNITY!!!");
        } else {
            StdDraw.text(WIDTH / 2.0, this.HEIGHT - 1, "Find the Key! ⚿");
        }

        StdDraw.textRight(WIDTH - 1, this.HEIGHT - 1, formatter.format(date));
    }

    public void inputMove(char input) {
        input = Character.toUpperCase(input);
        switch (input) {
            case ':' -> {
                quitCheck = true;
            }
            case 'Q' -> {
                if (quitCheck) {
                    saveGame();
                    if (!interactWithInputString) {
                        System.exit(0);
                    }
                }
            }
            default -> {
                inputString += input;
                quitCheck = false;
                movePlayer(input);
            }
        }
    }

    public void saveGame() {
        Out out = new Out("src/Core/save.txt");
        out.println(wg.getSeed());
        out.print(inputString);
    }

    public void startGame() {
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                inputMove(StdDraw.nextKeyTyped());
            }
            drawFrame();
        }
    }

    public void loadSave(String moves, boolean interactWithString, boolean replay) {
        this.interactWithInputString = interactWithString;
        for (char move : moves.toCharArray()) {
            inputMove(move);
            if (replay) {
                drawFrame();
                StdDraw.pause(SHORT_PAUSE);
            }
        }
    }

}
