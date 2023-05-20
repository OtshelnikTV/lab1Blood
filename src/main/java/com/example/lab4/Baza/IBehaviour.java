package com.example.lab4.Baza;

public interface IBehaviour {
    double getX();
    double getY();
    double getSpeed();
    void setX(double x);
    void setY(double y);
    Direction getDirection();
    void setDirection(Direction direction);
    void updateDirection();
    String getType();
}
