package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import javax.swing.JOptionPane;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

import java.util.Scanner;

public class SudokoController {

    @FXML
    private Button clearBtn;

    @FXML
    private GridPane grid;

    @FXML
    private Button setBtn;
    private TextField textField;

    //private Button[] textFields;
    private TextField[] textFields;
    private final int SIZE = 9;
    private boolean isWhite = false;
    private int counterRow = 9;
    private int counter = 3;
    private int row, col;
    private int[] rowCells, colCells;
    // if we manage to fill 81 spots we are done, we will use an helper array to check if we change answer in a spot
    private int countToFinish;
    private boolean[] helpArr;
    private boolean isGameOn = false;

    public void initialize() {
        //Initialize arrays that will be use to check if we can enter new number
        rowCells = new int[SIZE];
        colCells = new int[SIZE];
        textFields = new TextField[SIZE * SIZE];
        helpArr = new boolean[SIZE * SIZE];

        for (int i = 0; i < SIZE * SIZE; i++) {
            helpArr[i] = false;
            textFields[i] = new TextField("");
            textFields[i].setId(String.valueOf(i));
            textFields[i].setPrefSize(grid.getPrefWidth() / 9, grid.getPrefHeight() / 9);
            textFields[i].setOnAction(e -> {
                textField = (TextField) e.getSource();
                String text = textField.getText();
                if (text == "")
                    countToFinish--;
                if (isGameOn) {
                    String style = textField.getStyle();
                    textField.setStyle(style + "; -fx-text-inner-color: red;");
                }
                try {
                    int tmp = Integer.parseInt(text);

                    if (tmp < 1 || tmp > 9) {
                        JOptionPane.showConfirmDialog(null, "Wrong number!", "Error", JOptionPane.CLOSED_OPTION);
                        textField.clear();
                    } else {
                        if (!validateLegit(textField)) {
                            JOptionPane.showConfirmDialog(null, "You can't put this number here!", "Error", JOptionPane.CLOSED_OPTION);
                            textField.clear();
                        }
                        if (!helpArr[Integer.parseInt(textField.getId())]) {
                            if (countToFinish == 81) {
                                JOptionPane.showConfirmDialog(null, "WOW! You WON!", "Well Done!", JOptionPane.CLOSED_OPTION);
                                clearPressed(e);
                            }
                            countToFinish++;
                            helpArr[Integer.parseInt(textField.getId())] = true;
                        }
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println(textField.getText());
                    JOptionPane.showConfirmDialog(null, "You are wrong!", "Error", JOptionPane.CLOSED_OPTION);
                    textField.clear();
                }
            });
            if (i == 27)
                isWhite = !isWhite;
            if (i == 54)
                isWhite = !isWhite;
            if (isWhite)
                textFields[i].setStyle("-fx-background-color: white; -fx-border-color: grey");
            else
                textFields[i].setStyle("-fx-background-color: grey; -fx-border-color: white");
            counter--;
            counterRow--;
            if (counter == 0) {
                counter = 3;
                isWhite = !isWhite;
                if (counterRow == 0) {
                    isWhite = !isWhite;
                    counterRow = 9;
                }
            }
            grid.add(textFields[i], i % 9, i / 9);
        }
    }

    private boolean validateLegit(TextField button) {
        row = Integer.parseInt(button.getId()) / 9;
        col = Integer.parseInt(button.getId()) % 9;
        // To check if the new number is already found in the specific col / row
        int cntRow = 0;
        int cntCol = 0;

        for (int i = 0; i < SIZE; i++) {
            if (textFields[i + row * SIZE].getText() != "")
                rowCells[i] = Integer.parseInt(textFields[i + row * SIZE].getText());
            else
                rowCells[i] = 0;
            if (textFields[col + i * SIZE].getText() != "")
                colCells[i] = Integer.parseInt(textFields[col + i * SIZE].getText());
            else
                colCells[i] = 0;
        }
        for (int i = 0; i < SIZE; i++) {
            if (Integer.parseInt(button.getText()) == rowCells[i])
                cntRow++;
            if (Integer.parseInt(button.getText()) == colCells[i])
                cntCol++;
        }
        if (cntRow > 1)
            return false;
        else if (cntCol > 1)
            return false;
        else
            return true;
    }

    @FXML
    void setPressed(ActionEvent event) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            if (textFields[i].getText() != "")
                textFields[i].setEditable(false);
            setBtn.setDisable(true);
        }
        isGameOn = true;
    }

    @FXML
    void clearPressed(ActionEvent event) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            textFields[i].setText("");
            textFields[i].setEditable(true);
            setBtn.setDisable(false);
        }
        countToFinish = 0;
        helpArr = new boolean[SIZE * SIZE];
        //initialize();
    }
}

