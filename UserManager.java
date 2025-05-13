import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserManager {

    private static final String USERS_FILE = "users.txt";
    private static final String LOG_DIR = "logs";

    public static boolean signUp(String username, String password) throws IOException {
        File file = new File(USERS_FILE);
        if (!file.exists()) file.createNewFile();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return false; // user already exists
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        }

        return true;
    }

    public static boolean login(String username, String password) throws IOException {
        File file = new File(USERS_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void logAction(String username, String action) {
        try {
            File logDir = new File(LOG_DIR);
            if (!logDir.exists()) logDir.mkdir();

            File logFile = new File(logDir, username + "_log.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                writer.write("[" + timestamp + "] " + action);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to log: " + e.getMessage());
        }
    }
}
