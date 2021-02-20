package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputObject {

    private int daysForScanning;
    private int numOfBooks;
    private int numOfLibraries;
    private List<Book> books;
    private List<Library> libraries;

}
