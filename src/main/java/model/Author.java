package model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private String nationality;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new ArrayList<Book>();
    }

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
        this.books= new ArrayList<Book>();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
