package com.github.trqhxrd.untitledgame;

import java.awt.*;

public class Display {
    private int höhe, länge;
   private String Name;

   private static Window Display= null;

    private Display() {
        this.länge = 1920;
        this.höhe = 1080;
        this.Name = "DER NAME";
    }

    public static Window get() {
        if (Window.Display == null) {
            Window.Dispaly = new Window();
        }


    }