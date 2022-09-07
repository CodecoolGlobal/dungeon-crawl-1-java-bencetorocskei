package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Potion extends Item{

    public Potion(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "potion";
    }

    @Override
    public void act(GameMap map) {

       int currentHealth = getCell().getActor().getHealth();
        getCell().getActor().setHealth(currentHealth+2);

    }
}
