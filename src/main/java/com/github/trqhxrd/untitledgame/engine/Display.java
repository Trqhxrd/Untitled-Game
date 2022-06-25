package com.github.trqhxrd.untitledgame.engine;

import lombok.Getter;

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
