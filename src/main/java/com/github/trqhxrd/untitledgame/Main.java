package com.github.trqhxrd.untitledgame;

import com.github.trqhxrd.untitledgame.engine.Core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static final Logger logger = LogManager.getLogger("main");

    public static void main(String[] args) {
        Core.INSTANCE.init();

        logger.info("Hello World!");
    }
}
