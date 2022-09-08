package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public abstract class Actor implements Drawable {
    private Cell cell;

    protected int health;

    private int damage;

    protected boolean isAlive;


    public Actor(Cell cell, int damage) {
        this.cell = cell;
        this.cell.setActor(this);
        isAlive = true;
        this.damage = damage;

    }

    public int getDamage() {
        return damage;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("skeleton") ||
                nextCell.getActor() != null && nextCell.getActor().getTileName().equals("auto") ||
                nextCell.getActor() != null && nextCell.getActor().getTileName().equals("lion"))
            confrontation(nextCell);
        else if (nextCell.getTileName().equals("wall") || nextCell.getTileName().equals("mud") || (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("lion")) ||
                (nextCell.getTileName().equals("closedDoor") && (!checkPlayerItem("Successfully saved Chameleon!") ||
                        !checkPlayerItem("Successfully saved Panda!") || !checkPlayerItem("Successfully saved Lion!"))) ||
                (nextCell.getItem() != null && nextCell.getItem().getTileName().equals("lion") && !checkPlayerItem("tranqgun")) ||
                (nextCell.getItem() != null && nextCell.getItem().getTileName().equals("Successfully saved Chameleon!") && !checkPlayerItem("chameleon")) ||
                (nextCell.getItem() != null && nextCell.getItem().getTileName().equals("Successfully saved Panda!") && !checkPlayerItem("panda")) ||
                (nextCell.getItem() != null && nextCell.getItem().getTileName().equals("Successfully saved Lion!") && !checkPlayerItem("lion"))) {
        } else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    protected void confrontation(Cell nextCell) {

        nextCell.getActor().beingAttacked(this);
        if (!nextCell.getActor().isAlive) {
            nextCell.setActor(null);
        } else {
            this.beingAttacked(nextCell.getActor());
        }
/*        int enemyHealth = enemy.getHealth();
        health -= 2;
        enemy.setHealth(enemyHealth - 5);
        enemyHealth = enemy.getHealth();
        if (enemyHealth < 1) {
            enemy.getCell().setActor(null);
        } else if (health < 1) this.getCell().setActor(null);*/
    }

    public void beingAttacked(Actor attacker) {
        health -= attacker.getDamage();
        if (health < 1) {
            isAlive = false;
        }
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }


    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean checkPlayerItem(String requiredKey) {
        boolean hasItem = false;
        Player player = (Player) this;
        for (Item item : player.getItems()) {
            if (item.getTileName().equals(requiredKey)) {
                hasItem = true;
            }
        }
        return hasItem;
    }
}
