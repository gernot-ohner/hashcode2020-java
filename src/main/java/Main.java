import model.Book;
import model.OutputObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String[] filenames = new String[] {
                "a_example.txt",
                "b_read_on.txt",
                "c_incunabula.txt",
                "d_tough_choices.txt",
                "e_so_many_books.txt",
                "f_libraries_of_the_world.txt"
        };

        String filename = filenames[3];

        final var wrapperObject = new InputReader().parse(filename);

        final var outputObject = new LibrarySignupManager().compute(wrapperObject);
        final var score = computeScore(outputObject);
        System.out.println("score = " + score);

        new OutputWriter().writeOutput(filename, outputObject);
    }

    private static long computeScore(OutputObject outputObject) {
        return outputObject.getLibraries().stream().flatMap(library -> library.getBooks().stream())
                .distinct().mapToLong(Book::getScore).sum();
    }
}
