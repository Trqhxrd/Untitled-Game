package com.github.trqhxrd.untitledgame;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public GUI(){

        JFrame frame = new JFrame();

        JButton button1 = new JButton("Start");

        //button1.addActionListener();  (mach ich später)

        JButton button2 = new JButton("ENDE");



        JLabel label = new JLabel("Dieses Spiel ist von Trqhxrd und Oasis");


        JPanel panel = new JPanel();
panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20 ,50 ));
panel.setLayout(new GridLayout(1, 2));
panel.add(button1);
panel.add(button2);
panel.add(label);


frame.add(panel, BorderLayout.CENTER);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setTitle("GAME");
frame.pack();
frame.setVisible(true);
    }
    public static void main(String[] args) {
        new GUI();
            }

}
