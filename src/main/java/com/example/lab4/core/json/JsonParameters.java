package com.example.lab4.core.json;

import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Lions;
import com.example.lab4.Collections.Collections;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JsonParameters {
    private ArrayList<Animals> animalList;
    private String animalName;
    private Object object;
    double animalX;
    double animalY;
    String animaltype;


    public JsonParameters() {
        super();
    }
    public JsonParameters(String type, String receiver, String message,ArrayList<Animals> animalList) {
        this.type = type;
        this.receiver = receiver;
        this.message = message;
        this.animalList= animalList;
    }
    @JsonProperty("type")
    private String type; // props, clients, message
    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("message")
    private String message; // [PC, as, wer, gu] // 123 456 789 456 123 89 // privet huilopan

    public String getType() {
        return type;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getMessage() {
        return message;
    }
    public Object getObject() {
        return object;
    }

    public ArrayList<Animals> getAnimalList(){
     return animalList;
    }

    public double getAnimalX() {
        return animalX;
    }

    public double getAnimalY() {
        return animalY;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
