package bezdelnik;

import javafx.application.Application;

/*
 * Main entry point for GUI task manager
 */
public class Launcher {
    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("nogui")) {
            Bezdelnik.main(new String[0]);
        }
        Application.launch(Main.class, args);
    }
}

