import model.Book;
import model.InputObject;
import model.Library;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InputReader {

    public InputObject parse(String filename) throws IOException, URISyntaxException {
        final var strings = readFile(filename);

        final var metadata = Arrays.stream(strings.get(0).split(" "))
                .map(Integer::valueOf).collect(Collectors.toList());
        final var numOfBooks = metadata.get(0);
        final var numOfLibs = metadata.get(1);
        final var daysToScan = metadata.get(2);
        strings.remove(0);

        AtomicInteger bookIndex = new AtomicInteger(0);
        final var books = getBooks(strings.get(0)).stream()
                .map(bookScore -> new Book(bookIndex.getAndIncrement(), bookScore))
                .sorted(Comparator.comparing(Book::getScore).reversed())
                .collect(Collectors.toList());
        strings.remove(0);

        final var libraries = new ArrayList<Library>();

        AtomicInteger libraryIndex = new AtomicInteger(0);
        for (int i = 0; i < strings.size() - 1; i = i + 2) {
            final var s = strings.get(i).split(" ");
            final var signupDuration = Integer.parseInt(s[1]);
            final var shippingSpeed = Integer.parseInt(s[2]);
            final var libraryBooks = getBooks(strings.get(i + 1)).stream()
                    .map(books::get).collect(Collectors.toList());
            final var library = new Library(libraryIndex.getAndIncrement(), signupDuration, shippingSpeed, libraryBooks);
            libraries.add(library);
        }

        return new InputObject(daysToScan, numOfBooks, numOfLibs, books, libraries);
    }

    public List<String> readFile(String filename) throws IOException, URISyntaxException {
        final var uri = getClass()
                .getClassLoader()
                .getResource(filename)
                .toURI();
        final var path = Paths.get(uri);
        final var lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }

    private List<Integer> getBooks(String bookString) {
        return Arrays.stream(bookString.split(" ")).map(Integer::valueOf).collect(Collectors.toList());
    }
}
