import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVHandler {

    public static void main(String[] args) {
        // String fileName = "src/sample.csv";
        String fileName = args[0];

        // read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<NormalizedLine> normalizedLines = stream.skip(1).map(line -> {
                line = line.replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "?");
                String[] columns = line.split(",");
                return new NormalizedLine(columns);
            }).collect(Collectors.toList());

            normalizedLines.forEach(System.out::println);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
