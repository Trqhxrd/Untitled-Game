package com.github.trqhxrd.untitledgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Render extends JFrame implements Runnable {

    BufferStrategy buff;
    Graphics2D g;


    public static boolean START = true;


public Render(){

    setResizable(false);
    setSize(800, 600);
    setLayout(null);
    setDefaultCloseOperation(3);
    setLocationRelativeTo(null);
    setVisible(true);

    createbuff();

    new Thread(this).start();

}

public void createbuff() {
    createBufferStrategy(3);
    buff = getBufferStrategy();

}

    public void Draw(Graphics2D g ) {


}

    public void Update() {

    }

    public void run() {


    g = (Graphics2D) buff.getDrawGraphics();

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    // while(START) {
            //do {
          //  do {


       //         g = (Graphics2D)buff.getDrawGraphics();

       //         Update();
      //          Draw(g);
      //          buff.show();


    //        }while(buff.contentsLost());

   //     }while(buff.contentsRestored());

   //     try {
     //       Thread.sleep(100);
     //   } catch (InterruptedException e) {
      //      e.printStackTrace();
      //  }

     // }

}
