package uk.ac.sheffield.com1003.library.catalogue;

import uk.ac.sheffield.com1003.library.Person;
import java.text.ParseException;
import java.util.*;

public class Book extends CatalogueItem {

    public enum Genre {UNSPECIFIED, NONFICTION};
    private Person author;
    private String isbn;
    private int year;
    private Genre genre;

    public Book(String title) {
        super(title);
    }
    public Book(String title, Person author) {
        super(title);
        this.author = author;
    }

    public Book(String title, Person author, String isbn) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String title, Person author, String isbn, int year) {
        super(title, year);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String title, Person author, String isbn, int year, Genre genre) {
        super(title, year);
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
    }

    public Book(String title, Person author, String isbn, int year, Genre genre, int nCopies) throws IllegalArgumentException {
        super(title, year);
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.setCopies(nCopies);
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person person) {
        this.author = person;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        Book book = (Book) other;
        // Check if titles are equal or one is a prefix of the other
        if (!getTitle().startsWith(book.getTitle()) && !book.getTitle().startsWith(getTitle())) return false;
        // Check if authors' first names are the same
        String[] thisAuthorNames = author.getFirstName().split(" ");
        String[] otherAuthorNames = book.author.getFirstName().split(" ");
        if (!thisAuthorNames[0].equals(otherAuthorNames[0])) return false;
        // Check if authors' last names are the same
        if (!author.getLastName().equals(book.author.getLastName())) return false;
        return true;
    }

    public static Book fromBibtex(String bibtex) throws ParseException {
        // Extracting necessary fields from the Bibtex entry
        String title = extractField(bibtex, "title");
        String authorName = extractField(bibtex, "author");
        String isbn = extractField(bibtex, "isbn");
        String yearStr = extractField(bibtex, "year");

        //  Validating extracted fields
        if (title == null || authorName == null || isbn == null || yearStr == null) {
            throw new IllegalArgumentException("Invalid BibTex entry");
        }

        // Parse year string to integer
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid year format in BibTex entry", 0);
        }

        // Extract author's first name and last name
        String[] names = authorName.split(" ");
        if (names.length != 2) {
            throw new ParseException("Invalid author name format in BibTex entry", 0);
        }
        Person author = new Person(names[0], names[1]);

        // Create and return the Book instance
        return new Book(title, author, isbn, year);
    }

    private static String extractField(String bibtex, String field) {
        int startIndex = bibtex.indexOf(field + "=");
        if (startIndex == -1) {
            return null;
        }
        
        // Move past the field name and '='
        startIndex += field.length() + 1; 
        
        // Find the end of the field value
        int endIndex = -1;
        if (bibtex.charAt(startIndex) == '{') { // Check if the value is enclosed in {}
            endIndex = bibtex.indexOf("}", startIndex); // Look for the closing '}'
        } else {
            endIndex = bibtex.indexOf(",", startIndex); // Look for the next comma
            // If no comma is found, set the end index to the end of the BibTeX entry
            if (endIndex == -1) {
            endIndex = bibtex.length();
            }
        }
        
        /*  Handle the case when endIndex is not found
        if (endIndex == -1 || startIndex >= endIndex) {
            return null;
        }*/
        
        // Extract the field value
        String fieldValue = bibtex.substring(startIndex, endIndex).trim();
        
        // Check if the field value contains unwanted characters
        if (fieldValue.startsWith("{") && fieldValue.endsWith("}")) {
            // If the field value is enclosed in {}, remove them
            fieldValue = fieldValue.substring(1, fieldValue.length() - 1);
        }
        
        return fieldValue;
    }
    


    /**
     * Returns a string representation of a book
     *
     * @return A string representation of a book in
     * the format "Book: Author=<{@link Person#toString()}>; ISBN=<ISBN>; Genre=<Book genre>"
     */
    @Override
    public String toString() {
        return "Book: Title=" + getTitle() +
            "; Author=" + author.toString() +
            "; ISBN=" + isbn +
            "; Genre=" + genre;
}

    @Override
    public int hashCode() {
        return Objects.hash(author, isbn, year, genre);
    }

}
