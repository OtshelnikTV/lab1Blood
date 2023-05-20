// Import necessary classes
package com.example.lab4.core.ai;

import com.example.lab4.core.Habitat;
import com.example.lab4.Baza.Direction;
import com.example.lab4.Baza.Animals;
import com.example.lab4.Baza.Wolves;
import com.example.lab4.Collections.Collections;

// Define a class called WolvesAI which extends from BaseAI
public class WolvesAI extends BaseAI {

    // Override the method calculateMovement() from BaseAI
    @Override
    public synchronized void calculateMovement() {

        // Print the name of the current thread
       // System.out.println(getName());

        // Get an array of Wolves from the Collections singleton object
        for (Animals animal : Collections.getInstance().getArrayWhere(Wolves.class)) {

            // Get the current x position, speed and field width of the wolf
            double x = animal.getX();
            double y = animal.getY();
            double speed = animal.getSpeed();
            int fieldWidth = Habitat.getWidth() - animal.getSize();
            int fieldHeight = Habitat.getHeight() - animal.getSize();

            if((x>0) & (y>=0) &(y <= fieldHeight) ){
                animal.setDirection(Direction.Left);
            }
            if((y>0) & (x<=0)){
                animal.setDirection(Direction.Up);
            }
            if((y<=0) & (x<=0)){
                animal.setDirection(Direction.Right);
            }
            if((x>=(fieldWidth - animal.getSize())) & (y<=(fieldHeight- 20))){
                animal.setDirection(Direction.Down);
            }
            Direction direction = animal.getDirection();
            animal.setX(x + (speed * direction.getXBias())); /*Устанавливаем новую координату X для животного*/
            animal.setY(y + (speed * direction.getYBias())); /*Устанавливаем новую координату Y для животного*/
        }
    }
}