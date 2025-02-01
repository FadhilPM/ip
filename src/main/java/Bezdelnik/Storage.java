package Bezdelnik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Storage {
    public static String writeTaskmanToFile(Taskman taskman, String outputPath) throws IOException {
        Path path = Paths.get(outputPath);
        Files.createDirectories(path.getParent());
        String content = taskman.listCommand();
        Files.writeString(path, content);
        return String.format("Success: contents written to file at: %s", outputPath);
    }

    public static Pair<String, Taskman> readTaskmanFromFile(String inputPath) throws IOException {
        Taskman toReturn = Files.lines(Paths.get(inputPath))
            .reduce(new Taskman(), (x, y) -> Parser.parse(y, x).second(), (a, b) -> a.concat(b));
        String status = String.format("        Tasks successfully loaded from %s", inputPath);
        return new Pair<String, Taskman>(status, toReturn);
    }
}
