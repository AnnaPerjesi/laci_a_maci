/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lacimaci;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anna
 */
public class LaciMaciGUI extends JFrame {

    private final JFrame frame;
    private final JFrame frame2;
    private final Board gameBoard;


    public LaciMaciGUI() throws FileNotFoundException {
        frame = new JFrame("LaciMaci");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame2 = new JFrame("HIGHSCORES");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(450, 450);
        frame2.setResizable(false);

        gameBoard = new Board();
        frame.getContentPane().add(gameBoard);

        frame.setJMenuBar(new Menu());

        frame.setPreferredSize(new Dimension(646, 700));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    public class Menu extends JMenuBar {

        private JMenu gameMenu;
        private JMenuItem newGame;
        private JMenuItem hs;

        private final Action nGameAction;

        public Menu() {
            this.nGameAction = new AbstractAction("New Game") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard.restart();
                    frame.pack();
                }
            };

            gameMenu = new JMenu("Menu");
            newGame = new JMenuItem(nGameAction);
            gameMenu.add(newGame);

            hs = new JMenuItem("HighScore");
            hs.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame2.setVisible(true);
                    frame.dispose();
                    try {
                        HighScores highscores = new HighScores(10);
                        ArrayList<HighScore> scores = highscores.getHighScores();
                       
                        String col[] = {"Place","Name", "Score"};
                        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                        // The 0 argument is number rows
                        JTable table = new JTable(tableModel);
                        Object rowData[] = new Object[3];
                        for (int i = 0; i < scores.size(); i++) {
                            System.out.println(scores.get(i).getName() + " - " + scores.get(i).getScore());

                            rowData[0] = i+1;
                            rowData[1] = scores.get(i).getName();
                            rowData[2] = scores.get(i).getScore();
                            tableModel.addRow(rowData);

                        }
                        
                        table.setRowHeight(50);
                        table.setSize(90, 150);
                       
                        frame2.add(new JScrollPane(table));
                        

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                }

            });
            gameMenu.add(hs);
            add(gameMenu);

        }

    }

}
