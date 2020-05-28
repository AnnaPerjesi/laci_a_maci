/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lacimaci;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Anna
 */
public class Map {

    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private boolean end = false;
    private int points;
    private Board board;

    ArrayList<Wall> walls;
    ArrayList<Knight> knights;
    ArrayList<Knight> knights2;
    ArrayList<Cake> cakes;

    public ArrayList<Knight> getKnights() {
        return knights;
    }

    public ArrayList<Knight> getKnights2() {
        return knights2;
    }

    public Map(String levelPath) throws IOException {
        loadMap(levelPath);
    }

    public void loadMap(String levelPath) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath));

        walls = new ArrayList<>();
        cakes = new ArrayList<>();
        knights = new ArrayList<>();
        knights2 = new ArrayList<>();

        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char type : line.toCharArray()) {
                if (type == 'w') {
                    Image image = new ImageIcon("data/images/wall.png").getImage();
                    walls.add(new Wall(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT, image));
                } else if (type == 'c') {
                    Image image = new ImageIcon("data/images/cake.png").getImage();
                    cakes.add(new Cake(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT, image));
                } else if (type == 'k') {
                    Image image = new ImageIcon("data/images/knight.png").getImage();
                    knights.add(new Knight(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT, image));
                } else if (type == 'l') {
                    Image image = new ImageIcon("data/images/knight.png").getImage();
                    knights2.add(new Knight(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT, image));
                }
                x++;
            }
            y++;
        }
    }

    public boolean collides(LaciAMaci laci) {
        Cake collideWith = null;
        for (Cake cake : cakes) {
            if (laci.collides(cake)) {
                collideWith = cake;
                break;
            }
        }
        if (collideWith != null) {
            laci.setScore(15);
            System.out.println(laci.getScore());
            cakes.remove(collideWith);
            return true;
        } else {
            return false;
        }

    }

    public Knight meetUp(LaciAMaci laci) {
        Knight meetWith = null;
        //ne booleant hanem meetwith

        for (Knight knight : knights) {
            if (laci.collides(knight)) {
                meetWith = knight;
                break;

            }
        }
        for (Knight knight : knights2) {
            if (laci.collides(knight)) {
                meetWith = knight;
                break;

            }
        }
        return meetWith;
       
    }

    public boolean meetW(LaciAMaci laci) {
        Wall meetWall = null;
        LaciAMaci maci2;
        maci2 = new LaciAMaci(laci.getX(), laci.getY(), 28, 28, null);
        maci2.setVelX(laci.getVelX());
        maci2.setVelY(laci.getVelY());
        maci2.moveX();
        maci2.moveY();
        for (Wall wall : walls) {
            if (maci2.collides(wall)) {
                meetWall = wall;
                break;
            }
        }

        if (meetWall != null) {
            laci.setVelX(0);
            laci.setVelY(0);
            return true;
        } else {
            return false;
        }
    }

    public boolean lovagMeet(Knight knight) {
        Wall lovagWall = null;
        for (Wall wall : walls) {
            if (knight.collides(wall)) {
                lovagWall = wall;
                break;
            }
        }
        if (lovagWall != null) {
            knight.invertVelX();
            knight.invertVelY();
            return true;
        } else {
            return false;
        }
    }

    public boolean isOver() {
        return cakes.isEmpty();
    }

    public void draw(Graphics g) {
        for (Cake cake : cakes) {
            cake.draw(g);
        }
        for (Wall wall : walls) {
            wall.draw(g);
        }
        for (Knight k : knights) {
            k.draw(g);
        }
        for (Knight k : knights2) {
            k.draw(g);
        }
    }

}
