package platform.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LocalizedHelper {
    private static String enAngularFile = "C:/projects/gitHubProjects/EnglishWayPlatformAngular/public/i18n/en.json";
    private static String ruAngularFile = "C:/projects/gitHubProjects/EnglishWayPlatformAngular/public/i18n/ru.json";
    private static String ukrAngularFile = "C:/projects/gitHubProjects/EnglishWayPlatformAngular/public/i18n/ukr.json";
    private static String startLineForJsonFile = "  ";

    public static void main(String[] args) {
        sortJsonFile(ukrAngularFile);
    }

    public static void sortJsonFile(String fileName) {
        List<String> lines = readJsonFileToList(fileName);
        lines = lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.equals("{") && !line.equals("}"))
                //.toList();
                        .collect(Collectors.toList());
        Collections.sort(lines, String::compareTo);
        writeLinesToJsonFile(lines, fileName);
    }

    public static List<String> readJsonFileToList(String file) {
        try {
            Path path = Path.of(file);
            return Files.readAllLines(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeLinesToJsonFile(List<String> lines, String file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("{\n");
            for (String line : lines) {
                writer.write(startLineForJsonFile+line + "\n");
            }
            writer.write("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
