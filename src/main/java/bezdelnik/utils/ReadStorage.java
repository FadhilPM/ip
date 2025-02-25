package bezdelnik.utils;

import java.util.stream.Stream;
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
    public static Stream<String> readTaskmanFromFile(String inputPath) throws IOException {
        assert inputPath != null;

        Stream<String> st = Files.lines(Paths.get(inputPath));
        return st;
    }
}
