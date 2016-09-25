package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceLoader {

	private static String userDir = System.getProperty("user.dir");
	private static Path PATH = Paths.get(userDir).resolve(Paths.get("Resources"));
	
	public static FileInputStream loadResourceExtern(String path) {
		try {
			return new FileInputStream(new File(PATH.toString()+path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
