package com.pc.java.wordspuzzle;

import java.io.IOException;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public final class Utils {
    //region No instances
    private Utils() {}
    //endregion

    //region Public methods
    public static void clearScreen() {
        String command = isWindows() ? "cls" : "clear";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").equalsIgnoreCase("windows");
    }
    //endregion
}
