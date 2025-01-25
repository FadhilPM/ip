import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Storage {
    public static void writeTaskmanToFile(Taskman taskman, String outputPath) {
        try {
            Path path = Paths.get(outputPath);
            Files.createDirectories(path.getParent());
            String content = taskman.listCommand();
            Files.writeString(path, content);
        } catch (IOException ioexception) {
            System.out.println(ioexception);
        }
    }

    public static Taskman readTaskmanFromFile(String inputPath) {
        try {
            return Files.lines(Paths.get(inputPath))
                .reduce(new Taskman(), (x, y) -> Parser.parse(y, x).second(), (a, b) -> a.concat(b));
        } catch (IOException ioexception) {
            System.out.println("No prior data found, creating new session");
            return new Taskman();
        }
    }
}
