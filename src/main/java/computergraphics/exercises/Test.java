package computergraphics.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:\\Users\\floha\\semester4workspace\\java\\assets\\volumedata\\neghip.vox");
		byte[] f = Files.readAllBytes(path);
		System.out.println(Arrays.toString(f));
	}
}
