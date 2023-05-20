package com.example.lab4;

import com.example.lab4.core.Client;
import com.example.lab4.core.Habitat;
import com.example.lab4.view.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalsApplication extends Application {
    //private Controller controller;
    private Habitat habitat;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AnimalsApplication.class.getResource("animals-view.fxml"));
        Controller controller =new Controller(stage);
        fxmlLoader.setController(controller);
        System.out.println(AnimalsApplication.class.getResource("animals-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 968, 600);
        stage.setOnCloseRequest(event -> {
            try {
                controller.saveProperties();
                Platform.exit();
                System.exit(0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        scene.getRoot().requestFocus();
        stage.setResizable(false);
        stage.setTitle("Animals!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}