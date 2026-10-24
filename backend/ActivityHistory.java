package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class ActivityHistory {
    private static final String FILE_NAME = "ACTIVITY.txt";
    public Stack<String> activities = new Stack<>();

    public ActivityHistory() {
        loadActivities();
    }

    public boolean isEmpty() {
        loadActivities();
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
        loadActivities();

        if (activities.isEmpty()) {
            return null;
        }

        return activities.peek();
    }

    public ArrayList<String> getRecentActivities() {
        loadActivities();

        ArrayList<String> recentActivities = new ArrayList<>();
        int startIndex = Math.max(0, activities.size() - 10);

        for (int i = activities.size() - 1; i >= startIndex; i--) {
            recentActivities.add(activities.get(i));
        }

        return recentActivities;
    }

    public ArrayList<String> getClaimedItemIDs() {
        loadActivities();

        ArrayList<String> claimedIDs = new ArrayList<>();

        for (String activity : activities) {
            if (activity.matches("^(\\[.*?\\] )?Item ID: ITM-\\d{4} \\| Unclaimed -> Claimed$")) {
                int start = activity.indexOf("ITM-");
                String itemID = activity.substring(start, start + 8);

                if (!claimedIDs.contains(itemID)) {
                    claimedIDs.add(itemID);
                }
            }
        }

        return claimedIDs;
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
}