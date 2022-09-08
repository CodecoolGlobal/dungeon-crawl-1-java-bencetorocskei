package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.*;
//import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label monsterHealthLabel = new Label();
    Label inventoryLabel = new Label();
    Button pickUpButton = new Button("Pick Up");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(360);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Player Health: "), 0, 0);
        //ui.add(new Label("Capitalist Health: "), 0, 1);

        ui.add(healthLabel, 1, 0);
        //ui.add(monsterHealthLabel, 1, 1);
        ui.add(pickUpButton, 0, 3);
        ui.add(inventoryLabel, 0, 4);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        //scene.setOnAction(pickUpButton::onMouseClicked);
        pickUpButton.setFocusTraversable(false);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cell playerCell = map.getPlayer().getCell();
                if (playerCell.getItem() != null){
                    playerCell.getItem().act(map);
                    if (!(playerCell.getItem().getTileName().equals("Successfully saved Lion!")) &&
                            !(playerCell.getItem().getTileName().equals("Successfully saved Chameleon!")) &&
                            !(playerCell.getItem().getTileName().equals("Successfully saved Panda!"))) {
                        playerCell.setItem(null);
                    }
                }
                //item = map.getItem();
                //inventory.add(item;
                refresh();
            }
        });
        scene.setOnKeyPressed(this::onKeyPressed);
    }



    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                if(map.getAuto() != null) map.getAuto().moveCar();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                if(map.getAuto() != null) map.getAuto().moveCar();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                if(map.getAuto() != null) map.getAuto().moveCar();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                if(map.getAuto() != null) map.getAuto().moveCar();
                refresh();
                break;
        }
    }

    private void refresh() {
        if (map.getSkeleton() != null) {
            for (Skeleton skeleton : map.getSkeletons()) {
                if (skeleton.isAlive()) {
                    skeleton.moveSkeleton();
                }
            }
        }
        if (map.getAuto() != null) map.getAuto().moveCar();
        if (map.getChameleon() != null) map.getChameleon().moveChameleon();
        if (map.getPanda() != null) map.getPanda().movePanda();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        //if (map.getSkeleton() != null) monsterHealthLabel.setText("" + map.getSkeleton().getHealth());
        healthLabel.setText("" + map.getPlayer().getHealth());
        String inventoryText = inventory();
        inventoryLabel.setText("" + inventoryText);
        checkDoorPassing();
        if (map.getPlayer().checkPlayerItem("quit")) {
            System.exit(1);
        }
        if (map.getPlayer().checkPlayerItem("newgame")) {
            map.getPlayer().eraseItems();
            map = MapLoader.loadMap(1);
        }
        if (!(map.getPlayer().isAlive())) {
            map.getPlayer().eraseItems();
            map = MapLoader.loadMap(0);
        }
        if (map.getPlayer().checkPlayerItem("nextlevel")) {
            map.getPlayer().eraseItems();
            map = MapLoader.loadMap(2);
        }


    }

    private void checkDoorPassing() {
        if (map.getPlayer().getCell().getTileName().equals("closedDoor")){
            map.getPlayer().getCell().setType(CellType.OPENDOOR);
        }
    }

    private String inventory(){
        StringBuilder sb = new StringBuilder()
                .append("\n" + "Inventory:");
        if (map.getPlayer().getItems() != null){
            for (Item item : map.getPlayer().getItems()){
                sb.append("\n" + item.getTileName());
            }
        }

        return sb.toString();
    }
}

