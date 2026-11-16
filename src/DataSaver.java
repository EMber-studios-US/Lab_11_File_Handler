import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean done = false;

        while (!done) {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");
            int idNum = SafeInput.getRangedInt(in, "Enter ID Number", 0, 999999);
            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int birthYear = SafeInput.getRangedInt(in, "Enter Year of Birth", 1900, 2025);

            String line = String.format("%s,%s,%06d,%s,%d", firstName, lastName, idNum, email, birthYear);
            records.add(line);

            boolean addMore = SafeInput.getYNConfirm(in, "Do you want to enter another record?");
            if (!addMore) {
                done = true;
            }
        }

        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setDialogTitle("Save File");

        int result = chooser.showSaveDialog(frame);
        frame.dispose();

        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No save location specified. Exiting.");
            System.exit(0);
        }

        Path filePath = chooser.getSelectedFile().toPath();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (int i = 0; i < records.size(); i++) {
                writer.write(records.get(i));
                writer.newLine();
            }
            System.out.println("Saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}