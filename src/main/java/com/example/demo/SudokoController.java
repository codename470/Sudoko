package com.example.demo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SudokoController {

    @FXML
    private Button clearBtn;

    @FXML
    private GridPane grid;

    @FXML
    private Button setBtn;

    private Button[] buttons;
    private final int SIZE = 9;
    private boolean isWhite = false;
    private int counterRow = 9;
    private int counter = 3;

    public void initialize(){
        buttons = new Button[SIZE * SIZE];

        for (int i=0; i<SIZE * SIZE; i++){
            buttons[i] = new Button("");
            buttons[i].setPrefSize(grid.getPrefWidth() / 9, grid.getPrefHeight() / 9);
            if (i == 27)
                isWhite = !isWhite;
            if (i == 54)
                isWhite = !isWhite;
            if (isWhite)
                buttons[i].setStyle("-fx-background-color: white; -fx-border-color: grey");
            else
                buttons[i].setStyle("-fx-background-color: grey; -fx-border-color: white");
            counter --;
            counterRow--;
            if (counter == 0){
                counter = 3;
                isWhite = !isWhite;
                if (counterRow == 0){
                    isWhite = !isWhite;
                    counterRow = 9;
                }
            }
            grid.add(buttons[i], i % 9, i / 9);
        }
    }
}

