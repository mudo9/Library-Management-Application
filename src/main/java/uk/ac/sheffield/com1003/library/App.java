package uk.ac.sheffield.com1003.library;
import uk.ac.sheffield.com1003.library.catalogue.Book;
import uk.ac.sheffield.com1003.library.catalogue.Book.Genre;
import uk.ac.sheffield.com1003.library.exceptions.ItemNotFoundException;
import uk.ac.sheffield.com1003.library.exceptions.NoCopyAvailableException;
import uk.ac.sheffield.com1003.library.exceptions.ItemAlreadyReturnedException;
import uk.ac.sheffield.com1003.library.catalogue.Magazine;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException, IllegalArgumentException, ItemNotFoundException, NoCopyAvailableException, ItemAlreadyReturnedException{
        Library myLibrary = new Library("Sheffield Central Library", 10);

        Person authorBook1 = new Person("Robert Martin");
        Book book1 = new Book("Clean Code", authorBook1, "9780136083238",2008,Genre.UNSPECIFIED,1);
        myLibrary.addItem(book1);

        /*String filePath = "src/main/resources/Lee60.bib";
        String bibtex = readBibtexFromFile(filePath);
        Book book2 = Book.fromBibtex(bibtex);
        myLibrary.addItem(book2);*/

        myLibrary.printCatalogue();

        // Creating loans
        Person borrower = new Person("Michael Udo");

        // Loaning Book 1
        Loan loan1 = myLibrary.loanItem(book1.getIsbn(), borrower);

        // Loaning Book 2
        //Loan loan2 = myLibrary.loanItem(book2.getIsbn(), borrower);

        // Returning Book 1
        myLibrary.returnItem(loan1);

        // Extending Loan 2 by 5 days
        //myLibrary.extendLoan(loan2, 5);

        myLibrary.printOverdue();

        Magazine magazine1 = new Magazine("Sports Today", 2024, 1);
        myLibrary.addItem(magazine1);
        Loan loan3 = myLibrary.loanItem(magazine1.getTitle(), borrower);
        if (loan3 != null) {
            System.out.println("Magazine loaned successfully!");
        } else {


            System.out.println("Failed to loan magazine.");
        }

    }

    private static String readBibtexFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("/n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
        }
        return content.toString();
    }
}
    

        




        
    

