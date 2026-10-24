package backend;

import java.io.File;
import java.io.IOException;

public class FileInitializer {

    private static final String[] FILES = {
        "ITEMS.txt",
        "ACTIVITY.txt"
    };

    public static void initializeFiles() {
        for (String fileName : FILES) {
            File file = new File(fileName);

            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println(fileName + " created successfully.");
                    }
                } catch (IOException e) {
                    System.out.println(
                        "Error creating " + fileName + ": " + e.getMessage()
                    );
                }
            }
        }
    }
}