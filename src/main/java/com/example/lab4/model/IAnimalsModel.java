package com.example.lab4.model;

import com.example.lab4.Baza.Animals;

import java.util.ArrayList;
import java.util.Vector;

public interface IAnimalsModel {
    ArrayList<Animals> getAnimals();

    int getSimulationTime();

    void setParams(
            int WolvesInterval,
            int LionsInterval,
            int WolvesProbability,
            int LionsProbability,
            int WolvesLifeTime,
            int LionsLifeTime);


    void update(double elapsed);

    void resetAll();

    void setLionsAIEnable(Boolean isEnable);

    void setWolvesAIEnable(Boolean isEnable);
//    void setWolvesSpawnAvailable(Boolean isEnable);
    void stopAllAI();

    void startAllAI();
    void stopSimulation();

    void startSimulation();
    void setWolvesAIPriority(int selectedPriority);

    void setLionsAIPriority(int selectedPriority);

    void continueSimulation(boolean wolvesAI, boolean lionsAI);
    void setAnimals( ArrayList<Animals> loadedAnimals);
}
