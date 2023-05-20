package com.example.lab4.core.ai;

import com.example.lab4.view.Controller;

// Базовый класс для всех ИИ
abstract class BaseAI extends Thread {
    private volatile boolean isActive = false;

    // Метод для запуска потока ИИ
    public synchronized void startAI() {
        isActive = true;
        notify();
    }

    // Метод для остановки потока ИИ
    public synchronized void stopAI() {
        isActive = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this){
                    if (!isActive) {
                        wait(); // Ожидание активации потока ИИ
                    }
                }
                calculateMovement(); // Вычисление движения
                Thread.sleep(1000/ Controller.FPS); // Ожидание до следующего расчета движения
            } catch (InterruptedException e) {
               // e.printStackTrace();
            }
        }
    }

    // Метод для проверки, активен ли поток ИИ
    public boolean isActive() {
        return isActive;
    }

    // Метод для установки приоритета потока ИИ
    public void setAIPriority(int priority) {
        setPriority(priority);
    }


    // Абстрактный метод для вычисления движения, реализация которого должна быть определена в дочерних классах
    abstract void calculateMovement();

}