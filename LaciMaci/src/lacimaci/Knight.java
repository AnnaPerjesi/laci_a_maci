/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lacimaci;

import java.awt.Image;

/**
 *
 * @author Anna
 */
public class Knight extends Sprite{
    
    private double velx;
    private double vely;
    

    public Knight(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        velx = 1;
        vely = 1;
    }
    
    public void moveX() {
        x+= velx;
        if(x+width >= 677 || x <=0){
            invertVelX();
        }
    }
    

    public void moveY(){
        y+= vely;
        if(y+width >= 646 || y <=0){
            invertVelY();
        }
    }
    
    
    public void invertVelX(){
        velx = -velx;
    }
    
    public void invertVelY(){
        vely = -vely;
    }
}
