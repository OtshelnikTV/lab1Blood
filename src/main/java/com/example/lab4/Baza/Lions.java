package com.example.lab4.Baza;

import com.example.lab4.ImageWrapper;
import com.example.lab4.core.Habitat;
import com.example.lab4.view.Controller;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Random;

public class Lions extends Animals implements Serializable {
    static String URL="C://Users/Dlyap/Desktop/lab1/src/main/resources/assets/Lev.png";
    ImageWrapper image = new ImageWrapper("C://Users/Dlyap/Desktop/lab1/src/main/resources/assets/Lev.png");
    @Override
    public String getURL() {
        return URL;
    }

    public static int count = 0;
    private double x;
    private double y;
    private static final long serialVersionUID = 1L;

    private String name;
    private Direction direction = Direction.Left;
    private final double speed = 300.0 / Controller.FPS;
    private int birthTime;
    private final int lifeTime;
    private final int id;
    private final int size = 50;
    public Lions(int birthTime, int lifeTime, int id) {
        count++;
        Random random = new Random();
        x = random.nextInt(Habitat.getWidth() - size);
        y = random.nextInt(Habitat.getHeight() - size);
        this.birthTime = birthTime;
        this.lifeTime = lifeTime;
        this.id = id;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
    @Override
    public void setBirthTime(int birthTime) {
        this.birthTime = birthTime;
    }
    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void updateDirection() {

    }

    @Override
    public String getType() {
        String type ="Leo";
        return type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getBirthTime() {
        return birthTime;
    }

    @Override
    public int getLifeTime() {
        return lifeTime;
    }
    javafx.scene.image.Image image1 = image.getImage();
    public ImageWrapper getImage() {
        return image;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void die() {
        count--;
    }

    @Override
    public String toString() {
        return birthTime + " - Lions";
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

}
