import java.util.Optional;

public class Controller {
    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static void main(String[] args) {
        ViewCSV vcsv = new ViewCSV(500, 700);
    }
}
