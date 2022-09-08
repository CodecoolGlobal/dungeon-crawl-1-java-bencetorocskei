package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles_corrected.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("closedDoor", new Tile(5, 4));
        tileMap.put("openDoor", new Tile(4, 4));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 1));
        tileMap.put("skeleton", new Tile(30, 9));
        tileMap.put("potion", new Tile(26, 23));
        tileMap.put("tranqgun", new Tile(20, 29));
        tileMap.put("auto", new Tile(13, 23));
        tileMap.put("lion", new Tile(30, 7));
        tileMap.put("panda", new Tile(18, 7));
        tileMap.put("chameleon", new Tile(18, 9));
        tileMap.put("Successfully saved Lion!", new Tile(23, 10));
        tileMap.put("Successfully saved Panda!", new Tile(0, 9));
        tileMap.put("Successfully saved Chameleon!", new Tile(18, 10));
        tileMap.put("newgame", new Tile(18, 31));
        tileMap.put("quit", new Tile(21, 31));
        tileMap.put("nextlevel", new Tile(15, 5));
        tileMap.put("mud", new Tile(6, 0));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
