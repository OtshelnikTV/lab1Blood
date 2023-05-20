package com.example.lab4.model;

import com.example.lab4.Collections.Collections;
import com.example.lab4.core.Habitat;
import com.example.lab4.Baza.Animals;

import java.util.ArrayList;
import java.util.Vector;

public class AnimalsModel implements IAnimalsModel {

    private final Habitat habitat = Habitat.getInstance();
    @Override
    public int getSimulationTime() {
        return habitat.getSimulationTime();
    }

    @Override
    public ArrayList<Animals> getAnimals() {
        return habitat.getAnimals();
    }

    @Override
    public void setParams(
            int LionsInterval,
            int WolvesInterval,
            int LionsProbability,
            int WolvesProbability,
            int LionsLifeTime,
            int WolvesLifeTime) {

        habitat.setParams(
                LionsInterval,
                WolvesInterval,
                LionsProbability,
                WolvesProbability,
                LionsLifeTime,
               WolvesLifeTime);
    }

    @Override
    public void update(double elapsed) {
        if ((int) elapsed != Habitat.getInstance().getSimulationTime()) {
            habitat.update(elapsed);
        }
    }

    @Override
    public void resetAll() {
        habitat.resetAll();
    }

    @Override
    public void setLionsAIEnable(Boolean isEnable) {
        habitat.setLionsAIEnable(isEnable);
    }

    @Override
    public void setWolvesAIEnable(Boolean isEnable) {
        habitat.setWolvesAIEnable(isEnable);
    }

    @Override
    public void stopAllAI() {
        habitat.setWolvesAIEnable(false);
        habitat.setLionsAIEnable(false);
    }

    @Override
    public void startAllAI() {
        habitat.setWolvesAIEnable(true);
        habitat.setLionsAIEnable(true);
    }
    @Override
    public void stopSimulation() {
        habitat.setWolvesAIEnable(false);
        habitat.setLionsAIEnable(false);
    }

    @Override
    public void startSimulation() {
        habitat.setWolvesAIEnable(true);
        habitat.setLionsAIEnable(true);
    }
    @Override
    public void setWolvesAIPriority(int selectedPriority) {
        habitat.setWolvesAIPriority(selectedPriority);
    }
    @Override
    public void setAnimals(ArrayList<Animals> loadedAnimals) {
        Collections.getInstance().setAnimalss(loadedAnimals);
    }
    @Override
    public void setLionsAIPriority(int selectedPriority) {
        habitat.setLionsAIPriority(selectedPriority);
    }

    @Override
    public void continueSimulation(boolean wolvesAI, boolean lionsAI) {
        habitat.setWolvesAIEnable(wolvesAI);
        habitat.setLionsAIEnable(lionsAI);
    }

}