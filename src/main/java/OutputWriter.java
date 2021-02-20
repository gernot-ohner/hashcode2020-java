import model.Book;
import model.OutputObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class OutputWriter {

    public void writeOutput(String filename, OutputObject outputObject) throws IOException {

        final var now = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
        final var taskFolder = new File(String.valueOf(filename.charAt(0)));
        taskFolder.mkdir();
        final var pathname = filename.charAt(0) + "/" + now + ".txt";
        final var file = new File(pathname);
        file.createNewFile();
        final var fw = new FileWriter(file);

        fw.write(outputObject.getNumOfLibs() + "\n");

        outputObject.getLibraries().forEach((OutputObject.Library lib) -> {
            try {
                fw.write(lib.getId() + " " + lib.getBooks().size() + "\n");
                final var bookString = lib.getBooks().stream()
                        .map(Book::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "));
                fw.write(bookString);
                fw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        fw.close();
    }
}
