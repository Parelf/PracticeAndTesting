import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		ResourceBundle resourceBundle = ResourceBundle.getBundle("myResources",Locale.ENGLISH);
		JOptionPane jOptionPane;
		Pattern userPattern;
		Matcher userMatcher;
		Path blackBox2BPath = Paths.get("blackboxes","2b_blackbox_log.txt");

		List<String> blackBox2BLines = collectLines(blackBox2BPath);

		// For testing, printing whole blackbox in messageBox.
		displayInfo(blackBox2BLines,resourceBundle);

	}

	public static List<String> collectLines(Path filePath){

		List<String> collectedLines;

		if (Files.exists(filePath)) {
			try {
				collectedLines = Files.readAllLines(filePath);

				collectedLines = collectedLines.stream()
						.map(s -> s.replace("]", "]\n"))
						.map(s -> s.trim()) // remove leading/trailing whitespace
						.map(s -> s.startsWith(",") ? s.substring(1).trim() : s) // remove leading comma if present
						.map(s -> s.trim()) // remove leading/trailing whitespace
						.collect(Collectors.toList());

				// My attempts at making a clean square box.
//				collectedLines = collectedLines.stream()
//						.map(s -> s.replace("]", "]\n"))
//						.map(s -> String.join("\n",s))
//						.map(s -> {
//							if (s.substring(0,2).equals(", ")){
//								s = s.substring(3);
//							}
//							return s;
//						})
//						.collect(Collectors.toList());
				// Keeping failed code for revision.
//				collectedLines.stream().
//					map(s -> s.split("\\[")).
//					map(s -> s + "\n").
//					collect(Collectors.toList());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Unexpected Error. Closing program.");
				throw new RuntimeException(e);
			}
			return collectedLines;
		}else {
			JOptionPane.showMessageDialog(null,"File Not Found.");
			return new ArrayList<>();
		}

	}

	public static void displayInfo(List collectedElements, ResourceBundle resourceBundle){

		String finalText = String.join("\n", collectedElements);
		JOptionPane.showMessageDialog(null, finalText,
				resourceBundle.getString("Title"), JOptionPane.INFORMATION_MESSAGE);


	}

}