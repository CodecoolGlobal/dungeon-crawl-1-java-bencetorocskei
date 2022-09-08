package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class PandaCage extends Item{

    public PandaCage (Cell cell){
        super(cell);

    }
    public String getTileName() {
        return "Successfully saved Panda!";
    }

    @Override
    public void act(GameMap map) {
        map.getPlayer().getItems().add(this);
        map.getPlayer().getItems().removeIf(item -> item.getTileName().equals("panda"));
        Cell pandaCell = map.getPlayer().getCell().getNeighbor(0,-1);
        pandaCell.setItem(new Panda(pandaCell));
    }

}
