package model;

import lombok.Data;

import java.util.List;

@Data
public class OutputObject {

    private int numOfLibs;
    private List<Library> libraries;

    public static class Library {
        private final int id;
        private final List<Book> books;

        public Library(int id, List<Book> books) {
            this.id = id;
            this.books = books;
        }

        public int getId() {
            return id;
        }

        public List<Book> getBooks() {
            return books;
        }
    }
}
