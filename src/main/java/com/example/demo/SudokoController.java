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

    //private Button[] buttons;
    private TextField[] buttons;
    private final int SIZE = 9;
    private boolean isWhite = false;
    private int counterRow = 9;
    private int counter = 3;
    private int temp;

    public void initialize() {
        buttons = new TextField[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            temp = i;
            buttons[i] = new TextField("");
            buttons[i].setPrefSize(grid.getPrefWidth() / 9, grid.getPrefHeight() / 9);
            buttons[i].setOnAction(e -> {
                textField = (TextField) e.getSource();
                String text = textField.getText();
                try {
                    int tmp = Integer.parseInt(text);

                    if (tmp < 1 || tmp > 9) {
                        JOptionPane.showConfirmDialog(null, "You are wrong!", "Error", JOptionPane.CLOSED_OPTION);
                        textField.clear();
                    } else {
                        if (!validateLegit(textField, temp))
                            textField.clear();
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showConfirmDialog(null, "You are wrong!", "Error", JOptionPane.CLOSED_OPTION);
                    textField.clear();
                }
            });
            if (i == 27)
                isWhite = !isWhite;
            if (i == 54)
                isWhite = !isWhite;
            if (isWhite)
                buttons[i].setStyle("-fx-background-color: white; -fx-border-color: grey");
            else
                buttons[i].setStyle("-fx-background-color: grey; -fx-border-color: white");
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
            grid.add(buttons[i], i % 9, i / 9);
        }
    }

    private boolean validateLegit(TextField button, int num) {
        return true;
    }

    @FXML
    void setPressed(ActionEvent event) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            if (buttons[i].getText() != "")
                buttons[i].setEditable(false);
            setBtn.setDisable(true);
        }
    }

    @FXML
    void clearPressed(ActionEvent event) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            buttons[i].setText("");
            setBtn.setDisable(false);
        }
    }
}

