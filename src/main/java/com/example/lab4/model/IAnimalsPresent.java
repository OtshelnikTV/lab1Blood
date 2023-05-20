package com.example.lab4.model;

import com.example.lab4.Baza.Animals;

import java.util.ArrayList;

public interface IAnimalsPresent {
    void setParams(
            String LionsInterval,
            String WolvesInterval,
            double LionsProbability,
            double WolvesProbability,
            String LionsLifeTime,
            String WolvesLifeTime);

    void showStatsDialog();

    void update(double elapsed);

    void resetAll();

    int getSimulationTime();

    boolean checkInput(String input);

    void showCurrentObjects();

    void setLionsAIEnable(Boolean isEnable);

    void setWolvesAIEnable(Boolean isEnable);
//    void setWolvesSpawnAvailable(Boolean isEnable);

    void stopSimulation();
    void stopAllAI();

    void startAllAI();
    ArrayList<Animals> getAnimals();
    void setAnimals(ArrayList<Animals> loadedAnimals);
    void startSimulation();
    void setWolvesAIPriority(int selectedPriority);

    void setLionsAIPriority(int selectedPriority);

    void continueSimulation(boolean wolvesAI, boolean lionsAI);
}
