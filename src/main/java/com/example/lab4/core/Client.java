package com.example.lab4.core;

import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Wolves;
import com.example.lab4.core.json.JsonParameters;
import com.example.lab4.view.Controller;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String clientName = "Бес";
    private final String serverAddress = "localhost";
    //String host = "localhost";
    private final int serverPort = 1337;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientCallback clientCallback;
    private List<Animals> animalList;
    private ObjectOutputStream outputStream;

    public Client(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }


    public String getClientName() {
        return clientName;
    }

    public void sendObjects(String name,
                               String LionsIntervalTextField,
                               String WolvesIntervalTextField,
                               double LionsProbabilitySlider,
                               double WolvesProbabilitySlider,
                               String WolvesLifeTimeTextField,
                               String LionsLifeTimeTextField,
                                ArrayList<Animals> animalList)
    {
        new Thread(() -> {
            try {
                JsonParameters jsonParameters = new JsonParameters("props " , name ,
                        " " + LionsIntervalTextField +
                        " " + WolvesIntervalTextField +
                        " " + LionsProbabilitySlider +
                        " " + WolvesProbabilitySlider +
                        " " + WolvesLifeTimeTextField +
                        " " + LionsLifeTimeTextField ,
                        animalList);
                String json = objectMapper.writeValueAsString(jsonParameters);
                outputStream.writeObject(json);
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendMessage(String name, String message,ArrayList<Animals> animalList) {
        new Thread(() -> {
            try {
                JsonParameters jsonParameters = new JsonParameters("message", name, message,animalList);
                String json = objectMapper.writeValueAsString(jsonParameters);
                outputStream.writeObject("message " + name + " " + message);
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void start() {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Подключено к серверу");

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Отправляем сигнал серверу с именем клиента
            outputStream.writeObject(clientName);
            outputStream.flush();

            // Запускаем поток для чтения сообщений от сервера
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String json = (String) inputStream.readObject();
                        System.out.println("JSON: " + json);
                        JsonParameters jsonParameters = objectMapper.readValue(json, JsonParameters.class);

                        if (jsonParameters.getType().equals("clients")) {
                            // "[PC1, PC2]"
                            String[] clients = jsonParameters.getMessage().
                                    replace('[', ' ').
                                    replace(']', ' ').
                                    trim().
                                    split(", ");

                            clientCallback.setClients(clients);
                        } else if (jsonParameters.getType().equals("props")) {
                            //   0                 1                         2                   3                     4                   5
                            // "albinoLifeTime albinoRabbitInterval albinoRabbitPercentage defaultLifeTime defaultRabbitInterval defaultRabbitProbability"
                            String[] splited = jsonParameters.getMessage().split(" ");

                            String albinoLifeTime = splited[0];
                            String albinoRabbitInterval = splited[1];
                            double albinoRabbitPercentage = Double.parseDouble(splited[2]);
                            String defaultLifeTime = splited[3];
                            String defaultRabbitInterval = splited[4];
                            double defaultRabbitProbability = Double.parseDouble(splited[5]);

                            clientCallback.setParametersFromClient(albinoLifeTime,
                                    albinoRabbitInterval,
                                    albinoRabbitPercentage,
                                    defaultLifeTime,
                                    defaultRabbitInterval,
                                    defaultRabbitProbability);
                        } else if (jsonParameters.getType().equals("message")) {
                            String name = jsonParameters.getReceiver();
                            String message = jsonParameters.getMessage();
                            clientCallback.showFetchedMessage(name + ": " + message);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Сервер недоступен");
        }
    }


}
