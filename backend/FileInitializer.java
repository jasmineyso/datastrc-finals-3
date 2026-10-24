package backend;

import java.io.File;
import java.io.IOException;

public class FileInitializer {

    public static final File ITEMS_FILE = new File("data", "ITEMS.txt");
    public static final File ACTIVITY_FILE = new File("data", "ACTIVITY.txt");

    private static final File[] FILES = {
        ITEMS_FILE,
        ACTIVITY_FILE
    };

    public static void initializeFiles() {
        File dataDirectory = new File("data");
        if (!dataDirectory.exists() && !dataDirectory.mkdirs()) {
            throw new IllegalStateException(
                "Could not create data directory: " + dataDirectory.getPath()
            );
        }

        for (File file : FILES) {
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println(file.getPath() + " created successfully.");
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(
                        "Error creating " + file.getPath(),
                        e
                    );
                }
            }
        }
    }
}