package com.example.lab4.model;

import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Lions;
import com.example.lab4.Baza.Wolves;
import com.example.lab4.view.IController;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Vector;

public class AnimalsPresent implements IAnimalsPresent {
    private final IController controller;
    private final IAnimalsModel model = new AnimalsModel();
    private final ImageView backgroundImage = new ImageView(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\Show.png");
    public AnimalsPresent(IController controller) {
        this.controller = controller;
    }

    @Override
    public void setParams(
            String LionsInterval,
            String WolvesInterval,
            double LionsProbability,
            double WolvesProbability,
            String LionsLifeTime,
            String WolvesLifeTime) {

        int LionsIntervalInt = 0;
        int WolvesIntervalInt = 0;
        int LionsProbabilityInt;
        int WolvesProbabilityInt;
        int LionsLifeTimeInt = 0;
        int WolvesLifeTimeInt = 0;

        if (LionsInterval.isEmpty()) {
            controller.showErrorDialog("Lions interval is empty");
            controller.setLionsTextField("3");
        } else {
            LionsIntervalInt = Integer.parseInt(LionsInterval);
        }

        if (WolvesInterval.isEmpty()) {
            controller.showErrorDialog("Wolves interval is empty");
            controller.setWolvesTextField("5");
        } else {
            WolvesIntervalInt = Integer.parseInt(WolvesInterval);
        }

        if (LionsLifeTime.isEmpty()){
            controller.showErrorDialog("Lions life time is empty");
            controller.setLionsLifeTimeTextField("30");
        } else {
            LionsLifeTimeInt = Integer.parseInt(LionsLifeTime);
        }

        if (WolvesLifeTime.isEmpty()) {
            controller.showErrorDialog("Wolves life time is empty");
            controller.setWolvesLifeTimeTextField("20");
        } else {
            WolvesLifeTimeInt = Integer.parseInt(WolvesLifeTime);
        }

        LionsProbabilityInt = (int) LionsProbability;
        WolvesProbabilityInt = (int) WolvesProbability;

        model.setParams(
                LionsIntervalInt,
                WolvesIntervalInt,
                LionsProbabilityInt,
                WolvesProbabilityInt,
                LionsLifeTimeInt,
                WolvesLifeTimeInt);
    }

    @Override
    public void showStatsDialog() {
        int simulationTime = model.getSimulationTime();
        int WolvesCount = Wolves.count;
        int LionsCount = Lions.count;

        controller.showStatsDialog(simulationTime, WolvesCount, LionsCount);
    }

    @Override
    public void update(double elapsed) {
        model.update(elapsed);

        ArrayList<Animals> animals = model.getAnimals();
        int simulationTime = model.getSimulationTime();

        controller.updateAnimals(animals);
        controller.updateStats(simulationTime);
    }

    @Override
    public void showCurrentObjects() {
        ArrayList<Animals> animals = model.getAnimals();
        controller.showCurrentObjectsDialog(animals);
    }

    @Override
    public void setLionsAIEnable(Boolean isEnable) {
        model.setLionsAIEnable(isEnable);
    }

    @Override
    public void setWolvesAIEnable(Boolean isEnable) {
        model.setWolvesAIEnable(isEnable);
    }
//    @Override
//    public void setWolvesSpawnAvailable(Boolean isEnable) {
//        model.setWolvesSpawnAvailable(isEnable);
//    }
    @Override
    public void stopAllAI() {
        model.stopAllAI();
    }

    @Override
    public void startAllAI() {
        model.startAllAI();
    }
    @Override
    public ArrayList<Animals> getAnimals() {
        return model.getAnimals();
    }
    @Override
    public void setAnimals(ArrayList<Animals> loadedAnimals) {
        model.setAnimals(loadedAnimals);
    }

    @Override
    public void stopSimulation() {
        model.stopSimulation();
    }

    @Override
    public void startSimulation() {
        backgroundImage.setFitWidth(200);
        backgroundImage.setFitHeight(200);
        controller.getMainPane().getChildren().add(backgroundImage);
        model.startSimulation();
    }
    @Override
    public void setWolvesAIPriority(int selectedPriority) {
        model.setWolvesAIPriority(selectedPriority);
    }

    @Override
    public void setLionsAIPriority(int selectedPriority) {
        model.setLionsAIPriority(selectedPriority);
    }

    @Override
    public void continueSimulation(boolean defaultAI, boolean albinoAI) {
        model.continueSimulation(defaultAI, albinoAI);
    }
    @Override
    public void resetAll() {
        model.resetAll();
    }

    @Override
    public int getSimulationTime() {
        return model.getSimulationTime();
    }

    @Override
    public boolean checkInput(String input) {
        return isNumeric(input);
    }

    private static boolean isNumeric(String str) {
        return str.matches("^[1-9][0-9]*") || str.isEmpty();
    }


}
