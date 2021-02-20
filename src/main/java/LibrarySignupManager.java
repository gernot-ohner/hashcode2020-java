import model.Book;
import model.InputObject;
import model.Library;
import model.OutputObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class LibrarySignupManager {


    public OutputObject compute(InputObject inputObject) {
        final var libsToSignUp = new ArrayList<OutputObject.Library>();

        AtomicLong daysLeft = new AtomicLong(inputObject.getDaysForScanning());

        var libraries = inputObject.getLibraries();
        var books = inputObject.getBooks();

        final var sortedLibraries = libraries.stream().sorted(Comparator.comparing(Library::getScore))
                .collect(Collectors.toList());

        while (daysLeft.get() > 0 && !sortedLibraries.isEmpty()) {
            System.out.println("daysLeft.get() = " + daysLeft.get());
            libraries.forEach(lib -> {
                double score = computeLibScore(lib, daysLeft.get(), books);
                lib.setScore(score);
            });

            final var chosenLib = sortedLibraries.remove(0);
            daysLeft.getAndUpdate(operand -> operand - chosenLib.getSignupTime());

            if (daysLeft.get() > chosenLib.getSignupTime()) {
                libsToSignUp.add(new OutputObject.Library(chosenLib.getId(), chosenLib.getBooks()));
            } else {
                break;
            }
        }

        final var outputObject = new OutputObject();
        outputObject.setNumOfLibs(libsToSignUp.size());
        outputObject.setLibraries(libsToSignUp);

        return outputObject;
    }

    // If you have time and it's computationally feasible, consider only the books that are still relevant
    // though what that means is not a trivial question.
    // books that arent scanned yet?
    // or books that aren't in libs that are signed up yet?
    // THE LATTER
    private double computeLibScore(Library lib, long daysLeft, List<Book> books) {
        final var timeAvailableForScanning = daysLeft - lib.getSignupTime();
        if (timeAvailableForScanning < 0) {
            return -1;
        }
        final var numOfBooksLibCanScan = Math.min(timeAvailableForScanning * lib.getShippingSpeed(), lib.getBooks().size());
        return lib.getBooks().subList(0, (int) numOfBooksLibCanScan).stream().mapToInt(Book::getScore).sum();
    }


}
