package com.example.lab4.Baza;

import com.example.lab4.ImageWrapper;
import javafx.scene.image.Image;

public abstract class Animals implements IBehaviour{
    public abstract int getId();
    public abstract int getBirthTime();
    public abstract int getLifeTime();
    public abstract String getURL();
    public abstract ImageWrapper getImage();
    public abstract int getSize();
    public abstract void die();
    public abstract void setBirthTime(int birthTime);

}
