package com.example.lab4.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Console {
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private TextField consoleTextField;

    private Command commandCallback;

    public Console(Command commandCallback) {
        this.commandCallback = commandCallback;
    }

    @FXML
    private void initialize() {
        setListeners();
    }

    private void setListeners() {
        //consoleTextArea.
        consoleTextField.setOnKeyPressed(keyEvent ->  {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                sendCommand();
            }
        });
    }

    private void sendCommand() {
        String command = consoleTextField.getText();
        consoleTextField.clear();

        Platform.runLater(() -> {
            commandCallback.Command(command);
        });
    }

    public void addMessage(String message) {
        consoleTextArea.appendText(message);
        consoleTextArea.positionCaret(consoleTextArea.getText().length()); // scroll down
    }
}
