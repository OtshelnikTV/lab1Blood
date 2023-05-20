package com.example.lab4.core;

import com.example.lab4.Baza.Lions;
import com.example.lab4.Baza.Wolves;
import com.example.lab4.Baza.Animals;
import com.example.lab4.core.ai.WolvesAI;
import com.example.lab4.core.ai.LionsAI;
import com.example.lab4.Collections.Collections;
import com.example.lab4.model.AnimalsPresent;
import com.example.lab4.model.IAnimalsPresent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

//Singleton
public class Habitat {
    private static Habitat instance;
    public ImageView getImageViewBackground() {

        ImageView imageViewBackground = new ImageView(imageBackground);
        imageViewBackground.setFitWidth(600);
        imageViewBackground.setFitHeight(500);
        return imageViewBackground;
    }
    public static synchronized Habitat getInstance() {
        if (instance == null) {
            instance = new Habitat();
        }
        return instance;
    }
    private static final Image imageBackground = new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\Show.png");
    private final WolvesAI wolvesAI = new WolvesAI();
    private final LionsAI lionsAI = new LionsAI();
    private static final int width = 600;
    private static final int height = 500;
    private final Random random = new Random();
    private int simulationTime = 0;
    private int LionsInterval;
    private double LionsProbability;
    private int WolvesInterval;
    private double WolvesProbability;
    private int LionsLifeTime;
    private int WolvesLifeTime;
    private static Collections collections;

    private Habitat() {
        lionsAI.start();
        wolvesAI.start();
    }

    public void setParams(int LionsInterval, int WolvesInterval, int LionsProbability, int WolvesProbability, int LionsLifeTime, int WolvesLifeTime) {
        this.LionsInterval = LionsInterval == 0 ? 3 : LionsInterval;
        this.WolvesInterval = WolvesInterval == 0 ? 3 : WolvesInterval;
        this.LionsProbability = LionsProbability / 100.0;
        this.WolvesProbability = WolvesProbability / 100.0;
        this.LionsLifeTime = LionsLifeTime == 0 ? 30 : LionsLifeTime;
        this.WolvesLifeTime = WolvesLifeTime == 0 ? 30 : WolvesLifeTime;
    }
    public void clearWithoutSimulationTime() {
       collections.clear();
    }
    public void setAnimalsFromClient(ArrayList<Animals> animals) {
        try{
            clearWithoutSimulationTime();
            for (Animals animal : animals) {
//                animal.setTimeOfBirth(textAboutTypeAndNumbers.get("T").getNumbers());
//                addVehicle(vehicle);
            }
        }catch(Exception e){System.out.println(e);}
    }
    private void spawnAnimals() {
        if (simulationTime % LionsInterval == 0 && random.nextDouble(1) < LionsProbability) {
            spawnLions();
        }

        if (simulationTime % WolvesInterval == 0 && random.nextDouble(1) < WolvesProbability) {
            spawnWolves();
        }
    }

    private void checkAnimals() {
        for (int i = 0; i < Collections.getInstance().size(); i++) {
            Animals animals = Collections.getInstance().get(i);
            if (animals.getBirthTime() + animals.getLifeTime() < simulationTime) {

                Collections.getInstance().remove(animals);
                animals.die();
            }

            animals.updateDirection();
        }
    }

    public void update(double elapsed) {
        simulationTime = (int) elapsed;
        checkAnimals();
        spawnAnimals();
    }

    private void spawnWolves() {//Wolves=Default
        int id = generateId();
        Collections.getInstance().add(new Wolves(simulationTime, LionsLifeTime, id));
    }

    private void spawnLions() {
        int id = generateId();
        Collections.getInstance().add(new Lions(simulationTime, WolvesLifeTime, id));
    }

    public void setWolvesAIEnable(Boolean isEnable) {
        if (isEnable && !wolvesAI.isActive()) {
            wolvesAI.startAI();
        } else {
            if (!isEnable && wolvesAI.isActive()) {
                wolvesAI.stopAI();
            }
        }
    }

    public void setLionsAIEnable(Boolean isEnable) {
        if (isEnable && !lionsAI.isActive()) {
            lionsAI.startAI();
        } else {
            if (!isEnable && lionsAI.isActive()) {
                lionsAI.stopAI();
            }
        }
    }

    public void resetAll() {
        simulationTime = 0;
        Wolves.count = 0;
        Lions.count = 0;
        Collections.getInstance().clear();
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public ArrayList<Animals> getAnimals() {
        return Collections.getInstance().getArray();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private int generateId() {
        return random.nextInt(1000000);
    }
    public void setWolvesAIPriority(int selectedPriority) {
        wolvesAI.setAIPriority(selectedPriority);
    }

    public void setLionsAIPriority(int selectedPriority) {
        lionsAI.setAIPriority(selectedPriority);
    }
}
