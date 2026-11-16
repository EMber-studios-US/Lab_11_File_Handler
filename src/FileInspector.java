import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            System.exit(0);
        }

        Path selectedPath = chooser.getSelectedFile().toPath();

        try {
            List<String> lines = Files.readAllLines(selectedPath);

            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));
            }

            System.out.println("File Name: " + selectedPath.getFileName().toString());
            System.out.println("Total Lines: " + lines.size());

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}