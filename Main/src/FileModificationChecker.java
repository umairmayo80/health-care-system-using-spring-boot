import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileModificationChecker {
    private static final List<String> filenames = List.of("users.csv", "lastAssignedId.txt","appointments.csv","schedule.csv");
    public static Map<String, Long> loadedLastModifiedInfo = loadLastModifiedInfo();

    static {
        loadedLastModifiedInfo = loadLastModifiedInfo();
    }

    public static boolean isFileModified(String filePath, long previousTimestamp) {
        File file = new File(filePath);

        if (file.exists()) {
            long currentTimestamp = file.lastModified();
            return currentTimestamp > previousTimestamp;
        }

        return false; // File does not exist
    }

    public static void saveLastModifiedInfo(List<String> filenames) {
        Map<String, Long> lastModifiedInfo = new HashMap<>();

        for (String filename : filenames) {
            File file = new File(filename);
            if (file.exists()) {
                long lastModified = file.lastModified();
                lastModifiedInfo.put(filename, lastModified);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("lastModified.csv"))) {
            writer.println("filename,lastmodified");
            for (Map.Entry<String, Long> entry : lastModifiedInfo.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving last modified info: " + e.getMessage());
        }
    }

    public static Map<String, Long> loadLastModifiedInfo() {
        Map<String, Long> lastModifiedInfo = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("lastModified.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String filename = parts[0];
                    long lastModified = Long.parseLong(parts[1]);
                    lastModifiedInfo.put(filename, lastModified);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Last modified info file does not exist.");
        } catch (IOException e) {
            System.out.println("Error loading last modified info: " + e.getMessage());
        }

        return lastModifiedInfo;
    }

    public static void main(String[] args) {
//         List<String> filenames = List.of("users.csv", "lastAssignedId.txt");
         saveLastModifiedInfo(filenames);

//        Map<String, Long>  FileModificationChecker.loadedLastModifiedInfo = loadLastModifiedInfo();
//        FileModificationChecker.saveLastModifiedInfo(FileModificationChecker.filenames);

        System.out.println("Loaded last modified info:");
        for (Map.Entry<String, Long> entry : FileModificationChecker.loadedLastModifiedInfo.entrySet()) {
            System.out.println("File: " + entry.getKey() + ", Last Modified: " + entry.getValue());
        }

        System.out.println(isFileModified("lastAssignedId.txt",FileModificationChecker.loadedLastModifiedInfo.get("lastAssignedId.txt")));

    }
}
