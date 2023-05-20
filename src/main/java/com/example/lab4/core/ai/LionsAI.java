package com.example.lab4.core.ai;

import com.example.lab4.core.Habitat;
import com.example.lab4.Baza.Lions;
import com.example.lab4.Baza.Direction;
import com.example.lab4.Baza.Animals;
import com.example.lab4.Collections.Collections;

public class LionsAI extends BaseAI {

    @Override
    public synchronized void calculateMovement() {
      //  System.out.println(getName());
        for (Animals animal : Collections.getInstance().getArrayWhere(Lions.class)) {
            double x = animal.getX();
            double y = animal.getY();
            double speed = animal.getSpeed();
            int fieldWidth = Habitat.getWidth() - animal.getSize();
            int fieldHeight = Habitat.getHeight() - animal.getSize();

            if((x>0) & (y>=0) &(y <= 400) ){
                animal.setDirection(Direction.Left);
            }
            if((y>0) & (x<=0)){
                animal.setDirection(Direction.Up);
            }
            if((y<=0) & (x<=0)){
                animal.setDirection(Direction.Right);
            }
            if((x>=(fieldWidth - animal.getSize())) & (y<=380)){
                animal.setDirection(Direction.Down);
            }
            Direction direction = animal.getDirection();
            animal.setX(x + (speed * direction.getXBias())); /*Устанавливаем новую координату X для животного*/
            animal.setY(y + (speed * direction.getYBias())); /*Устанавливаем новую координату Y для животного*/
        }
    }
}