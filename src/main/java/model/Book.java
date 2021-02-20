package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private int id;
    private int score;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
