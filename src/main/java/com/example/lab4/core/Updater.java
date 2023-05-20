package com.example.lab4.core;

import com.example.lab4.view.Controller;
import com.example.lab4.view.IController;
import javafx.application.Platform;

import java.util.TimerTask;

public class Updater extends TimerTask {
    private Controller controler;
    private Habitat habitat;
    private final IController controller;
    // Первый ли запуск метода run()?
    private boolean firstRun = true;

    // Время начала
    private long startTime;

    // Время последнего обновления
    private long lastTime = 0;

    //Время от которого начнет отсчитываться elapsed
    private final int delta;

    public Updater(IController controller, int delta){
        this.controller = controller;
        this.delta = delta + 1;
    }


    @Override
    public void run() {

        if (firstRun){
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            firstRun = false;
        }
        long currentTime = System.currentTimeMillis();
        // Время, прошедшее от начала, в секундах
        double elapsed = (currentTime - startTime) / 1000.0 + delta;
        // Время, прошедшее с последнего обновления, в секундах
        double frameTime = (lastTime - startTime) / 1000.0;
        // Вызываем обновление
        //controler.getMainPane().getChildren().addAll(habitat.getImageViewBackground());
        Platform.runLater(() -> controller.update(elapsed, frameTime));
        lastTime = currentTime;
    }
}
