package com.github.trqhxrd.untitledgame;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import javax.swing.*;

public class Display {
    @Getter
    private final int height = 1080;
    @Getter
    private final int width = 1920;
    @Getter
    private final String title = "DER NAME";

    private static Display display;

    public static Display get() {
        if (Display.display == null) Display.display = new Display();
        return display;
    }
}