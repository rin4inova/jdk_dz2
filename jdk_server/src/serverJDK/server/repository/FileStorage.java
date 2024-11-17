package serverJDK.server.repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorage implements Repository<String> {
    private static final String LOG_FILE_PATH = "chat.txt";

    @Override
    public void save(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String load() {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_FILE_PATH)) {
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            return content.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}