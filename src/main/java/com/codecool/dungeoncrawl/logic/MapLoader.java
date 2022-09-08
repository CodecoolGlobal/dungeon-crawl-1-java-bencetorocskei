package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {

    public static GameMap loadMap(int mapNr) {
        InputStream is = MapLoader.class.getResourceAsStream("/map" + mapNr + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'o':
                            cell.setType(CellType.OPENDOOR);
                            break;
                        case 'm':
                            cell.setType(CellType.MUD);
                            break;
                        case 'ö':
                            cell.setType(CellType.CLOSEDDOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Skeleton skeleton = new Skeleton(cell);
                            map.setSkeleton(skeleton);
                            map.addSkeleton(skeleton);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell);
                            break;
                        case 't':
                            cell.setType(CellType.FLOOR);
                            new TranquilizerGun(cell);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            map.setAuto(new Auto(cell));
                            break;
                        case 'L':
                            cell.setType(CellType.FLOOR);
                            map.setLion(new Lion(cell));
                            break;
                        case 'P':
                            cell.setType(CellType.FLOOR);
                            map.setPanda(new Panda(cell));
                            break;
                        case 'C':
                            cell.setType(CellType.FLOOR);
                            map.setChameleon(new Chameleon(cell));
                            new Chameleon(cell);
                            break;
                        case 'Ö':
                            cell.setType(CellType.FLOOR);
                            new PandaCage(cell);
                            break;
                        case 'Ü':
                            cell.setType(CellType.FLOOR);
                            new LionCage(cell);
                            break;
                        case 'Ó':
                            cell.setType(CellType.FLOOR);
                            new ChameleonCage(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'N':
                            cell.setType(CellType.FLOOR);
                            new NewGame(cell);
                            break;
                        case 'Q':
                            cell.setType(CellType.FLOOR);
                            new Quit(cell);
                            break;
                        case 'U':
                            cell.setType(CellType.FLOOR);
                            new NextLevel(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
