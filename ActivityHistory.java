import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class ActivityHistory {
    private static final String FILE_NAME = "ACTIVITY.txt";
    private Stack<String> activities = new Stack<>();
    public ActivityHistory() {
        loadActivities();
    }

    public boolean isEmpty() {
        return activities.isEmpty();
    }

    public void addActivity(String activity) {
        if (activity == null || activity.trim().isEmpty()) {
            return;
        }

        activities.push(activity);
        appendActivity(activity);
    }

    public String viewLatestActivity() {
        if (activities.isEmpty()) {
            return null;
        }

        return activities.peek();
    }

    public String removeLatestActivity() {
        if (activities.isEmpty()) {
            return null;
        }

        String removed = activities.pop();
        saveAllActivities();

        return removed;
    }

    public void displayActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities yet.");
            return;
        }

        System.out.println("\n===== RECENT ACTIVITIES =====");

        for (int i = activities.size() - 1; i >= 0; i--) {
            System.out.println(activities.get(i));
        }
    }

    private void loadActivities() {
        activities.clear();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    activities.push(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading activities!");
        }
    }

    private void appendActivity(String activity) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_NAME, true))) {

            writer.write(activity);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving activity!");
        }
    }

    private void saveAllActivities() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            for (String activity : activities) {
                writer.write(activity);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving activities!");
        }
    }
}