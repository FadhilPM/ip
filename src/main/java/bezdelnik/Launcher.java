package bezdelnik;

import bezdelnik.ui.ConsoleUi;
import bezdelnik.ui.Main;
import javafx.application.Application;

/**
 * Main entry point for the Bezdelnik application.
 * <p>
 * This class determines whether to launch the application with a GUI
 * or command-line interface based on the provided command-line arguments.
 * </p>
 */
public class Launcher {
    /**
     * Main method that launches either the GUI or CLI version of Bezdelnik.
     * <p>
     * If the first argument is "nogui", launches the command-line interface.
     * Otherwise, launches the graphical user interface.
     * </p>
     *
     * @param args Command-line arguments, where args[0] can be "nogui" to launch CLI
     */
    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("nogui")) {
            ConsoleUi.main(new String[0]);
        } else {
            Application.launch(Main.class, args);
        }
    }
}

