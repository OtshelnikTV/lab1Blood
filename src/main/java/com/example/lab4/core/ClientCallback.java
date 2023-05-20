package com.example.lab4.core;

public interface ClientCallback {
    void setClients(String[] clientsNames);

    void setParametersFromClient(String lionsLifeTime, String lionsInterval, double lionsProbability, String wolvesLifeTime, String wolvesInterval, double wolvesProbability);

    void showFetchedMessage(String message);
}
