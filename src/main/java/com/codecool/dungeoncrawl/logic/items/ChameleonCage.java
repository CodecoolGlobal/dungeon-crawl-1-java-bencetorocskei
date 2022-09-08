package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class ChameleonCage extends Item{

    public ChameleonCage (Cell cell){
        super(cell);

    }
    public String getTileName() {
        return "Successfully saved Chameleon!";
    }

    @Override
    public void act(GameMap map) {
        map.getPlayer().getItems().add(this);
        map.getPlayer().getItems().removeIf(item -> item.getTileName().equals("chameleon"));
        Cell chameleonCell = map.getPlayer().getCell().getNeighbor(0,-1);
        chameleonCell.setItem(new Chameleon(chameleonCell));
    }
}
