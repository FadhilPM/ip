package bezdelnik.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles storage operations for chatbot
 */
public class WriteStorage {
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

        String taskCommands = taskman.listCommand();

        Path path = Paths.get(outputPath);
        Files.createDirectories(path.getParent());
        Files.writeString(path, taskCommands);

        String status = String.format("Success: contents written to file at: %s", outputPath);
        return status;
    }
}
