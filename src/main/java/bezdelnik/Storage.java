package bezdelnik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles storage operations for chatbot
 */
public class Storage {
    /**
     * Writes the task manager state to a file at the specified output path.
     *
     * @param taskman    The current task manager state.
     * @param outputPath The file path where the state is written.
     * @return A success message including the output file path.
     * @throws IOException If an I/O error occurs.
     */
    public static String writeTaskmanToFile(Taskman taskman, String outputPath) throws IOException {
        assert taskman != null;
        assert outputPath != null;

        Path path = Paths.get(outputPath);
        Files.createDirectories(path.getParent());
        String content = taskman.listCommand();
        Files.writeString(path, content);
        return String.format("Success: contents written to file at: %s", outputPath);
    }

    /**
     * Reads the task manager state from a file at the specified input path.
     *
     * @param inputPath The file path from which the state is read.
     * @return A Pair containing a status message and the task manager state.
     * @throws IOException If an I/O error occurs.
     */
    public static Pair<String, Taskman> readTaskmanFromFile(String inputPath) throws IOException {
        assert inputPath != null;
        Taskman toReturn = Files.lines(Paths.get(inputPath))
            .reduce(new Taskman(), (x, y) -> Parser.parse(y, x).second(), (a, b) -> a.concat(b));
        String status = String.format("Tasks successfully loaded from %s", inputPath);
        return new Pair<String, Taskman>(status, toReturn);
    }
}
