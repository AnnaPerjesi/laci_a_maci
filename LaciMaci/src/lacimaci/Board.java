/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lacimaci;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class Board extends JPanel {

    private final int FPS = 240;
    private final int LACI_Y = 32;
    private final int LACI_X = 32;
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private final int LACI_MOVEMENT = 2;

    private boolean paused = false;
    private int levelNum = 0;
    private Map map;
    private LaciAMaci maci;
    private Knight knight;
    private Cake cake;
    private Wall wall;
    private Timer newFrameTimer;
    private int points = 0;

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public Board() {
        super();

        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(-LACI_MOVEMENT);
                maci.setVelX(0);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "released up");
        this.getActionMap().put("released up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(0);
                maci.setVelX(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelX(-LACI_MOVEMENT);
                maci.setVelY(0);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "released left");
        this.getActionMap().put("released left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(0);
                maci.setVelX(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelX(LACI_MOVEMENT);
                maci.setVelY(0);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "released right");
        this.getActionMap().put("released right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(0);
                maci.setVelX(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(LACI_MOVEMENT);
                maci.setVelX(0);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "released down");
        this.getActionMap().put("released down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                maci.setVelY(0);
                maci.setVelX(0);
            }
        });

        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }

    public void restart() {
        try {
            map = new Map("data/levels/level" + getLevelNum() + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image maciImage = new ImageIcon("data/images/yogi.png").getImage();
        maci = new LaciAMaci(LACI_X, LACI_Y, 28, 28, maciImage);
        maci.setScore(points);

    }

    @Override
    protected void paintComponent(Graphics paint) {
        super.paintComponent(paint);
        map.draw(paint);
        maci.draw(paint);

    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!paused) {
                map.meetW(maci);
                maci.moveX();
                maci.moveY();
                map.collides(maci);
                map.meetUp(maci);

                for (Knight k : map.getKnights()) {
                    k.moveX();
                    map.lovagMeet(k);
                }
                for (Knight k : map.getKnights2()) {
                    k.moveY();
                    map.lovagMeet(k);
                }

            }
            if (map.isOver()) {
                points = maci.getScore();
                if (levelNum < 6) {
                    levelNum = (levelNum + 1);
                } else {
                    levelNum = 0;
                }
                restart();
            }
            repaint();

            if (map.meetUp(maci) != null) {
                if (maci.getLives() > 1) {
                    maci.setLives(-1);
                    System.out.println(maci.getLives());
                    maci.returnToStart();
                } else {

                    points = maci.getScore();
                    maci.returnToStart();
                    maci.setScore(-maci.getScore());

                    String name = JOptionPane.showInputDialog(null, "What is your name?", null);
                    System.out.println(name);
                    try {

                        HighScores highScores = new HighScores(10);

                        highScores.putHighScore(name, points);

                    } catch (SQLException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if ((JOptionPane.showConfirmDialog(null, "Do you want to start a New Game?", "GAME OVER", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
                        points=0;
                        setLevelNum(0);

                        restart();
                        repaint();

                    } else {
                        System.exit(-2);
                    }
                }

            }
        }

    }
}
