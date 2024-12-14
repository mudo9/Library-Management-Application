package uk.ac.sheffield.com1003.library;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import uk.ac.sheffield.com1003.library.catalogue.CatalogueItem;
import uk.ac.sheffield.com1003.library.exceptions.ItemNotFoundException;
import uk.ac.sheffield.com1003.library.exceptions.NoCopyAvailableException;
import uk.ac.sheffield.com1003.library.exceptions.ItemAlreadyReturnedException;

public class Library {
    /**
     * Use this constant to initialise the catalogue
     */
    private final int CATALOGUE_CAPACITY = 100;
    private String name;
    private CatalogueItem[] catalogue;
    private Loan[] loans;
    private int loanLength;

    /**
     * Constructor that initialises the library name to "Library"
     * and the loan length to 10 days
     */
    public Library() {
        this("Library", 10); // Default values
        this.loans = new Loan[CATALOGUE_CAPACITY];
    }

    /**
     * Constructor that takes a library name as parameter and
     * initialises the loan length to 10 days
     * @param name The name of the library
     */
    public Library(String name) {
        this(name, 10); // Default loan length
        this.loans = new Loan[CATALOGUE_CAPACITY];
    }

    /**
     * Constructor that takes a library name and the loan length
     * as parameters
     * @param name The name of the library
     * @param loanLength The number of days books should be loaned for
     */
    public Library(String name, int loanLength) {
        this.name = name;
        this.loanLength = loanLength;
        this.catalogue = new CatalogueItem[CATALOGUE_CAPACITY];
        this.loans = new Loan[CATALOGUE_CAPACITY];
    }

    /**
     * Returns a simple welcome message
     * @return "Welcome to <library name>"
     */
    public String welcome() {
        return "Welcome to " + name;
    }

    /**
     * Returns the library name
     * @return Library name
     */
    public String getName() {
       return name;
    }

    /**
     * Add the given book to the catalogue.
     * 
     * @param newItem Item to be added to catalogue
     * @return Returns true if there is space in the catalogue and new item is successfully added; false otherwise
     */
    public boolean addItem(CatalogueItem newItem) {
        for (int i = 0; i < catalogue.length; i++){
            if (catalogue[i] == null){
                catalogue[i] = newItem;
                return true;
            }
        }
        return false;
    }

    /**
     * Finds item with given title or ISBN and removes a copy from the catalogue.
     * Assumes there are no duplicated title or ISBN.
     * @param titleOrISBN Exact title or ISBN of the item to be removed.
     */
    public void removeItem(String titleOrISBN) throws ItemNotFoundException {
        CatalogueItem item = findItem(titleOrISBN);
        if (item == null) {
            throw new ItemNotFoundException("Item with title or ISBN " + titleOrISBN + " not found.");
        }

        // Remove a copy of the item
        if (!removeCopy(item)) {
            throw new ItemNotFoundException("No copies of item with title or ISBN " + titleOrISBN + " found.");
        }
    }

    /**
     * Find item with given title or ISBN and remove a given number of copies from the catalogue.
     * Assumes there are no duplicated title or ISBN.
     * @param titleOrISBN Exact title or ISBN of the item to be removed
     * @param n Number of copies to remove
     */
    public void removeItem(String titleOrISBN, int n) throws ItemNotFoundException {
        CatalogueItem item = findItem(titleOrISBN);
        if (item == null) {
            throw new ItemNotFoundException("Item with title or ISBN " + titleOrISBN + " not found.");
        }

        // Remove n copies of the item
        for (int i = 0; i < n; i++) {
            if (!removeCopy(item)) {
                break; // No more copies left
            }
        }
    } 
    
    private boolean removeCopy(CatalogueItem item) {
        for (int i = 0; i < catalogue.length; i++) {
            if (catalogue[i] != null && catalogue[i].equals(item)) {
                if (catalogue[i].getCopies() > 1) {
                    catalogue[i].decrementCopies();
                } else {
                    catalogue[i] = null; // Remove the last copy
                }
                return true;
            }
        }
        return false; // No copies left
    }

    /**
     * Registers the loan of a book given its exact title or ISBN.
     * 
     * @param titleOrISBN The title or ISBN of the book to be loaned
     * @param user Name of user loaning catalogue item
     * @return true if the item's title or ISBN exist in the catalogue and there is a copy available; false otherwise
     * @throws ItemNotFoundException if a book with the given title does not exist in the catalogue
     * @throws NoCopyAvailableException if there are no copies available of the given book
     */
    public Loan loanItem(String titleOrISBN, Person user) throws ItemNotFoundException, NoCopyAvailableException {
        CatalogueItem item = findItem(titleOrISBN);
        if (item == null) {
            throw new ItemNotFoundException("Item with title or ISBN '" + titleOrISBN + "' not found in the catalogue.");
        }

        /*if (copiesAvailable(item) == 0) {
            throw new NoCopyAvailableException("No copies available for item with title or ISBN '" + titleOrISBN + "'.");
        }*/

        // Create a new loan
        Loan loan = new Loan(item, user, loanLength);
        item.addCopy();

        return loan;
    }

    /**
     * Find an item in the catalogue given a title or ISBN.
     * 
     * @param titleOrISBN Title or ISBN of item to find
     * @return The catalogue item found or null otherwise
     */
    private CatalogueItem findItem(String titleOrISBN) {
        for (CatalogueItem item : catalogue) {
            if (item != null && (item.getTitle().equals(titleOrISBN) || item.getIsbn().equals(titleOrISBN))) {
                return item;
            }
        }
        return null;
    }


    /**
     * Mark the item in a Returns a given loan
     */
    public void returnItem(Loan loan) throws ItemAlreadyReturnedException {
        loan.returnItem(); // This may throw ItemAlreadyReturnedException
    }
    

    /**
     * Extends the given loan
     */
    public void extendLoan(Loan loan, int days) throws ItemAlreadyReturnedException {
        loan.extendLoan(days);
    }

    /**
     * Return the number of copies available for a given catalogue item
     * @param item the item
     * @return number of copies available for the given item
     */
    public int copiesAvailable(CatalogueItem item) {
        return item.getCopies();
    }

    /**
     * Returns the current list of books in the catalogue excluding nulls
     *
     * @return Array of books contained in the catalogue (excluding nulls)
     */
    public CatalogueItem[] getCatalogue() {
        // Create a list to store non-null catalogue items
        List<CatalogueItem> nonNullItems = new ArrayList<>();
    
        // Iterate through the catalogue and add non-null items to the list
        for (CatalogueItem item : catalogue) {
            if (item != null) {
                nonNullItems.add(item);
            }
        }
    
        // Convert the list to an array and return it
        return nonNullItems.toArray(new CatalogueItem[0]);
    }

    /**
     * Print the full catalogue in the following format (sorted by title)
     * ====================
     * <Output of call to {@link Library#welcome()}>
     * Catalogue
     * ====================
     * <Output of call to method toString in the item>
     * ====================
     */
    public void printCatalogue() {
        System.out.println("====================");
        System.out.println(welcome());
        System.out.println("Catalogue");
        System.out.println("====================");
        // Sort the catalogue by title in ascending order
        Arrays.sort(catalogue, Comparator.nullsLast(Comparator.comparing(CatalogueItem::getTitle)));
        for (CatalogueItem item : catalogue) {
            if (item != null) {
                System.out.println(item.toString());
            }
        }
        System.out.println("====================");
    }

    /**
     * Print the list of loans that are overdue (i.e., due date has passed),
     * sorted by the number of days they are overdue by, and in the following
     * format:
     * ====================
     * Loans Overdue
     * ====================
     * <Output of call to {@link Loan#toString()}; one overdue loan>
     * ====================
     */
    public void printOverdue() {
        System.out.println("====================");
        System.out.println("Loans Overdue");
        System.out.println("====================");

        // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();

        // Create an array to store overdue loans
        List<Loan> overdueLoans = new ArrayList<>();

        // Iterate through all loans
        for (Loan loan : loans) {
            if (loan != null && loan.getDueDate().isBefore(currentDate)) {
                overdueLoans.add(loan);
            }
        }

        // Sort overdue loans by the number of days overdue in descending order
        overdueLoans.sort((loan1, loan2) -> {
            long daysOverdue1 = ChronoUnit.DAYS.between(loan1.getDueDate(), currentDate);
            long daysOverdue2 = ChronoUnit.DAYS.between(loan2.getDueDate(), currentDate);
            return Long.compare(daysOverdue2, daysOverdue1);
        });

        // Print overdue loans
        if (overdueLoans.isEmpty()) {
            System.out.println("====================");
        } else {
            for (Loan loan : overdueLoans) {
                if (loan != null){
                    System.out.println(loan.toString());
                    System.out.println("====================");
                }
            } 
        }
    }
}       
