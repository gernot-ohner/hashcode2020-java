package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Library {
    private final int id;
    private double score;
    private final int signupTime;
    private final int shippingSpeed;
    private final List<Book> books;
}
