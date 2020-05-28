/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lacimaci;

import java.awt.Image;

/**
 *
 * @author User
 */
public class LaciAMaci extends Sprite {

    private double velx;
    private double vely;
    private int score;
    private int lives;


    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives += lives;
    }
    

    public void setScore(int score) {
        this.score += score;
    }
    
    public void SetNull(int points){
        this.score -= points;
    }

    public int getScore() {
        return score;
    }

    public LaciAMaci(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        this.score = 0;
        this.lives= 3;
    }

    public void moveX() {
        if ((velx < 0 && x > 0) || (velx > 0 && x + width <= 646)) {
            x += velx;
        }
    }

    public void moveY() {
        if ((vely < 0 && y > 0 || vely > 0 && y + height <= 646)) {
            y += vely;
        }
    }
    
    public void returnToStart(){
        setX(32);
        setY(32);
    }

    public double getVelX() {
        return velx;
    }

    public void setVelX(double velx) {
        this.velx = velx;
    }

    public double getVelY() {
        return vely;
    }

    public void setVelY(double vely) {
        this.vely = vely;
    }

}
