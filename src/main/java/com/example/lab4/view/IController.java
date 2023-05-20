package com.example.lab4.view;

import com.example.lab4.Baza.Animals;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public interface IController {
    void updateAnimals(ArrayList<Animals> animals);

    void updateStats(int simulationTime);

    void showStatsDialog(int simulationTime, int LionsCount, int WolvesCount);

    void showErrorDialog(String errorMessage);

    void update(double elapsed, double frameTime);

    void setWolvesTextField(String s);

    void setLionsTextField(String s);

    void setWolvesLifeTimeTextField(String s);
    void setLionsLifeTimeTextField(String s);

    void showCurrentObjectsDialog(ArrayList<Animals> animals);
    Pane getMainPane();
}
