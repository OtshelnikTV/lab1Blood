package com.example.lab4.Collections;

import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Lions;
import com.example.lab4.Baza.Wolves;

import java.util.*;

public class Collections {

    private static Collections instance;
    private final ArrayList<Animals> animals = new ArrayList<>();
    private final TreeSet<Integer> animalsId = new TreeSet<>();
    private final HashMap<Integer, Integer> animalsMap = new HashMap<>();// <id, birthTime>

    public static synchronized Collections getInstance() {
        if (instance == null) {
            instance = new Collections();
        }
        return instance;
    }

    private Collections() {
    }

    public void add(Animals animal) {
        animals.add(animal);
        animalsId.add(animal.getId());
        animalsMap.put(animal.getId(), animal.getBirthTime());
    }

    public void remove(Animals animals) {
        this.animals.remove(animals);
        animalsId.remove(animals.getId());
        animalsMap.remove(animals.getId());
    }

    public void clear() {
        animals.clear();
        animalsId.clear();
        animalsMap.clear();
    }

    public int size() {
        return animals.size();
    }

    public Animals get(int index) {
        return animals.get(index);
    }

    public ArrayList<Animals> getArray() {
        return animals;
    }

    public <T extends Animals> ArrayList <T> getArrayWhere(Class<T> clazz) {
        ArrayList<T> filteredAnimals = new ArrayList<>();
        for (Animals animals : animals) {
            if (clazz.isAssignableFrom(animals.getClass())) {
                filteredAnimals.add(clazz.cast(animals));
            }
        }
        return filteredAnimals;
    }
    public void setAnimalss(ArrayList<Animals> loadedAnimals) {
        animals.clear();
        animalsId.clear();
        animalsMap.clear();
        animals.addAll(loadedAnimals);
        for (Animals r : loadedAnimals) {
            r.setBirthTime(0);
            animalsId.add(r.getId());
            animalsMap.put(r.getId(), r.getBirthTime());
        }
        Wolves.count = getArrayWhere(Wolves.class).size();
        Lions.count = getArrayWhere(Lions.class).size();
    }
}
