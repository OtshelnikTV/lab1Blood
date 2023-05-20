package com.example.lab4;

import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Lions;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class ImageWrapper {
    private Image image;
    String imagePath;
    Animals animals;

    public ImageWrapper(String image) {
        //this.imagePath = animals.getURL();
        this.image = new Image(image);
    }

    public PixelReader getPixelReader() {
        return image.getPixelReader();
    }

    public int getWidth() {
        return (int) image.getWidth();
    }

    public int getHeight() {
        return (int) image.getHeight();
    }

    // добавьте геттеры/сеттеры для других свойств Image при необходимости

    public Image getImage() {
        return image;
    }
}