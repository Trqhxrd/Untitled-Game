package com.github.trqhxrd.untitledgame.engine;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@UtilityClass
public class Core {

    public static final Logger logger = LogManager.getLogger(Core.class);
    @Getter
    private static Window window;

    public static void main(String[] args) {
        logger.info("Hello World!");

        if (!GLFW.glfwInit()) {
            logger.fatal("Could not initialize LWJGL.");
            System.exit(1);
        }

        window = new Window("HelloWorld", 800, 600);
        window.show();
        window.makeContextCurrent();

        while (!window.checkForClose()) {
            window.pollEvents();
            window.fillColor(Color.CYAN);
            window.swapBuffers();
        }

        GLFW.glfwTerminate();
    }
}
