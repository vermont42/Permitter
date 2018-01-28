package biz.joshadams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Logger {
    public static void log(String message) {
        String filePath = "logfile.txt";
        String messageWithNewline = message + System.getProperty("line.separator");
        System.out.println(message);
        try {
            Files.write(Paths.get(filePath), messageWithNewline.getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            System.out.println("Failed to write to " + filePath + ".");
            System.exit(-1);
        }
    }
}
