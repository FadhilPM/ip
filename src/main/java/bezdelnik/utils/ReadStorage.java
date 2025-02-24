package bezdelnik.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles storage operations for chatbot
 */
public class ReadStorage {
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

        String status = String.format("Success: %d tasks successfully loaded from %s\n%s",
                                      toReturn.size(), inputPath, toReturn.listString());
        return new Pair<String, Taskman>(status, toReturn);
    }
}
