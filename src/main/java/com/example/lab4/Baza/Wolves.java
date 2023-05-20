package com.example.lab4.Baza;

import com.example.lab4.ImageWrapper;
import com.example.lab4.core.Habitat;
import com.example.lab4.view.Controller;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Random;

public class Wolves extends Animals implements Serializable {
    private static final Image image2 = new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\Volk.png");
    ImageWrapper image= new ImageWrapper(URL);

    public static int count = 0;
    private double x;
    private double y;
    private final double speed = 200.0 / Controller.FPS;
    private Direction direction = Direction.Left;
    private int birthTime;
    private final int lifeTime;
    private final int id;
    private final int size = 50;
    private final int changeDirectionEach = 5;
    private static final long serialVersionUID = 1L;

    private  String name;
    public Wolves(int birthTime, int lifeTime, int id) {
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
    public void setX(double x) {
        this.x = x;
    }
    static String URL="C://Users/Dlyap/Desktop/lab1/src/main/resources/assets/Volk.png";
    @Override
    public String getURL() {
        return URL;
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
        String type ="Volk";
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
    @Override
    public void setBirthTime(int birthTime) {
        this.birthTime = birthTime;
    }

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
        return birthTime + " - Wolves";
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
